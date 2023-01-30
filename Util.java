class Util {
  public static String buildString() {
    //System.out.println("building string...");
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