import java.util.*;

class Main {
  static List<Long> durations = new ArrayList<Long>();
  static List<Thread> threads = new ArrayList<Thread>();

  public static void main(String[] args) {
    for (int i = 0; i < 20; i++) {
      Thread t = new Thread(new SingletonPrinter());
      threads.add(t);
      t.start();
    }
    for (int i = 0; i < 40; i++) {
      Thread t = new Thread(new SingletonPrinter());
      threads.add(t);
      t.start();
      Singleton.sleep(1);
    }

    printStats();
  }

  public static void addTime(long duration) {
    durations.add(duration);
  }

  public static void printStats() {
    Iterator<Thread> threadIterator = threads.iterator();
    long sum = 0;
    while (threadIterator.hasNext())
      try {
        ((Thread) threadIterator.next()).join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    Iterator<Long> durationIterator = durations.iterator();
    while (durationIterator.hasNext())
      sum += ((Long) durationIterator.next()).longValue();
    System.out.println(" Average: " + sum / durations.size());
  }
}

class Singleton {
  private static volatile String bigSlowString = null;

  public static String getInstance() {
    // if (bigSlowString == null)
    //synchronized (Singleton.class) {
      if (bigSlowString == null) {
        String temp;
        temp = buildString();
        bigSlowString = temp;
      }
    //}
    return bigSlowString;
  }

  private static String buildString() {
    System.out.println("building string...");
    String temp = "start...";
    for (int i = 0; i < 10; i++) {
      sleep(10);
      temp += i;
    }
    return temp;
  }

  public static void sleep(int ms) {
    try {
      Thread.sleep(ms);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

class SingletonPrinter implements Runnable {
  public void run() {
    long start = System.currentTimeMillis();
    String result = Singleton.getInstance();
    long end = System.currentTimeMillis();
    System.out.println(result + " - ran for: " + (end - start));
    Main.addTime(end - start);
  }
}