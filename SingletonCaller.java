class SingletonCaller implements Runnable {

  ThreadExecutor executor;
  CheckedSingleton checkedSingleton;

  public SingletonCaller(ThreadExecutor e, CheckedSingleton cs) {
    executor = e;
    checkedSingleton = cs;
  }

  public void run() {
    long start = System.nanoTime();
    checkedSingleton.getInstance();  
    long end = System.nanoTime();
    //System.out.println("-ran for: " + (end - start));
    executor.addTime(end - start);
  }
}