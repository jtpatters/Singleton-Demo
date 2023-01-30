abstract class CheckedSingleton {
  volatile String  bigSlowString = null;
  private int callCount=0;

  public abstract String getInstance();
  
  public void incrementCalls(){
    callCount++;
    if(callCount>100){
      callCount=0;
      bigSlowString=null;
    }
  }
}

class DoubleCheckedSingleton extends CheckedSingleton {
    
    public String getInstance() {
      incrementCalls();
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

  class SingleCheckedSingleton extends CheckedSingleton {
    
    public synchronized String getInstance() {
      incrementCalls();
      //synchronized (SingleCheckedSingleton.class) {
        if (bigSlowString == null) {
          String temp;
          temp = Util.buildString();
          bigSlowString = temp;
        }
      //}
      return bigSlowString;
    }
  }
