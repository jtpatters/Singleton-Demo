import java.util.*;

class Main {

  public static void main(String[] args) {

    new ThreadExecutor(LockType.SINGLE_CHECKED).run();
    new ThreadExecutor(LockType.DOUBLE_CHECKED).run();
    new ThreadExecutor(LockType.SINGLE_CHECKED).run();
    new ThreadExecutor(LockType.DOUBLE_CHECKED).run();
    new ThreadExecutor(LockType.SINGLE_CHECKED).run();
    new ThreadExecutor(LockType.DOUBLE_CHECKED).run();
  }
}

enum LockType {
  DOUBLE_CHECKED, SINGLE_CHECKED
}
