abstract class CheckedSingleton {
  private String bigSlowString = null;

  public abstract String getInstance();
}

class DoubleCheckedSingleton extends CheckedSingleton {
    private volatile String bigSlowString = null;

    public String getInstance() {
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
    private String bigSlowString = null;

    public String getInstance() {
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
