public final class ConnectionFactory {
  private static Connection dbConnection;
  // Other fields ...
 
  static {
    Thread dbInitializerThread = new Thread(new Runnable() {
        @Override public void run() {
          // Initialize the database connection
          try {
            dbConnection = DriverManager.getConnection("connection string");
          } catch (SQLException e) {
            dbConnection = null;
          }
        }
    });
 
    // Other initialization, for example, start other threads
 
    dbInitializerThread.start();
    try {
      dbInitializerThread.join();
    } catch (InterruptedException ie) {
      throw new AssertionError(ie);
    }
  }
 
  public static Connection getConnection() {
    if (dbConnection == null) {
      throw new IllegalStateException("Error initializing connection");
    }
    return dbConnection;
  }
 
  public static void main(String[] args) {
    // ...
    Connection connection = getConnection();
  }
}
