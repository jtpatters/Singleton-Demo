import java.util.*;

class ThreadExecutor {
  List<Long> durations = new ArrayList<Long>();
  List<Thread> threads = new ArrayList<Thread>();

  LockType type;
  CheckedSingleton checkedSingleton = null;
  
  public ThreadExecutor(LockType t) {
    type = t;
    if (type == LockType.DOUBLE_CHECKED)
        checkedSingleton = new DoubleCheckedSingleton();
      else
        checkedSingleton = new SingleCheckedSingleton();
  }

  public void run() {
    executeThreads();
    printStats();
  }

  public void executeThreads() {
    initiateAndRunThreads(200, 0);
   // Util.sleep(2000);
   // initiateAndRunThreads(20, 0);
  }

  public void initiateAndRunThreads(int count, int delay) {
    for (int i = 0; i < count; i++) { 
      Thread t = new Thread(new SingletonCaller(this,checkedSingleton));
      threads.add(t);
    }
    Iterator<Thread> threadIterator = threads.iterator();
    while (threadIterator.hasNext())
      ((Thread) threadIterator.next()).start();
  }

  public synchronized void addTime(long duration) {
    durations.add(duration);
  }

  public void printStats() {
    Iterator<Thread> threadIterator = threads.iterator();
    long sum = 0;
    long max = 0;
    while (threadIterator.hasNext())
      try {
        ((Thread) threadIterator.next()).join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    Iterator<Long> durationIterator = durations.iterator();
    while (durationIterator.hasNext()) {
      long value = ((Long) durationIterator.next()).longValue();
      sum += value;
      if (value > max)
        max = value;
    }
    System.out.println("Type: " + type + " Avg: " + sum / durations.size() + " Max: " + max
        + " Calls: " + durations.size());
  }
}