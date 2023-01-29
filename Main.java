import java.util.*;

class Main {

  public static void main(String[] args) {
    
    new ThreadExecutor(LockType.SINGLE_CHECKED).run(); 
    new ThreadExecutor(LockType.DOUBLE_CHECKED).run();
  }
}

enum LockType {
    DOUBLE_CHECKED, SINGLE_CHECKED
}

class ThreadExecutor {
  List<Long> durations = new ArrayList<Long>();
  List<Thread> threads = new ArrayList<Thread>();

  LockType type;

  public ThreadExecutor(LockType t) {
    type = t;
  }

  public void run() {
    executeThreads();
    printStats();
  }

  public void executeThreads() {
    initiateAndRunThreads(20, 0);
    initiateAndRunThreads(80, 1);
  }

  public void initiateAndRunThreads(int count, int delay) {
    for (int i = 0; i < count; i++) {
      Thread t = null;
      if (type == LockType.DOUBLE_CHECKED)
        t = new Thread(new DoubleCheckedSingletonPrinter(this));
      else
        t = new Thread(new SingleCheckedSingletonPrinter(this));
      threads.add(t);
      t.start();
      Util.sleep(delay);
    }
  }

  public synchronized void addTime(long duration) {
    durations.add(duration);
  }

  public void printStats() {
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
    System.out.println("Type: "+type+" Avg getInstance time: " + sum / durations.size());
  }
}

class DoubleCheckedSingleton {
  private static volatile String bigSlowString = null;

  public static String getInstance() {
    if (bigSlowString == null)
      synchronized (DoubleCheckedSingleton.class) {
        if (bigSlowString == null) {
          String temp;
          temp = Util.buildString();
          bigSlowString = temp;
        }
      }
    return bigSlowString;
  }
}

class SingleCheckedSingleton {
  private static volatile String bigSlowString = null;
  
  public static String getInstance() {
    synchronized (SingleCheckedSingleton.class) {
      if (bigSlowString == null) {
        String temp;
        temp = Util.buildString();
        bigSlowString = temp;
      }
    }
    return bigSlowString;
  }
}
  
class Util {
  public static String buildString() {
    System.out.println("building string...");
    String temp = "start...";
    for (int i = 0; i < 10; i++) {
      sleep(2);
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

abstract class SingletonPrinter implements Runnable {

  ThreadExecutor executor;

  public SingletonPrinter(ThreadExecutor e) {
    executor = e;
  }

  public void run() {
    long start = System.currentTimeMillis();
    call();
    long end = System.currentTimeMillis();
    // System.out.println(result + " - ran for: " + (end - start));
    executor.addTime(end - start);
  }

  public void call() {
  }
}

class DoubleCheckedSingletonPrinter extends SingletonPrinter {

  public DoubleCheckedSingletonPrinter(ThreadExecutor e) {
    super(e);
  }

  public void call() {
    DoubleCheckedSingleton.getInstance();
  }
}

class SingleCheckedSingletonPrinter extends SingletonPrinter {

  public SingleCheckedSingletonPrinter(ThreadExecutor e) {
    super(e);
  }

  public void call() {
    SingleCheckedSingleton.getInstance();
  }
}