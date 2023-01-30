
class Main {

  public static void main(String[] args) {

    for(int i=0;i<10;i++){
    new ThreadExecutor(LockType.SINGLE_CHECKED).run();
    new ThreadExecutor(LockType.DOUBLE_CHECKED).run();
  }

  }
}

enum LockType {
  DOUBLE_CHECKED, SINGLE_CHECKED
}
