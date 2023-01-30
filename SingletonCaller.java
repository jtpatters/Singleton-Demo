class SingletonCaller implements Runnable {

  ThreadExecutor executor;
  CheckedSingleton checkedSingleton;

  public SingletonCaller(ThreadExecutor e, CheckedSingleton cs) {
    executor = e;
    checkedSingleton = cs;
  }

  public void run() {
    long start = System.currentTimeMillis();
    String x = "";
      for(int i=0;i<100;i++)
        x += checkedSingleton.getInstance();
           
    long end = System.currentTimeMillis();
    //System.out.println("-ran for: " + (end - start));
    executor.addTime(end - start);
  }
}