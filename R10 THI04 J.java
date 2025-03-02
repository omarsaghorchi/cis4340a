public final class DBConnector implements Runnable {
  private final String query;
  private volatile Statement stmt;
 
  DBConnector(String query) {
    this.query = query;
  }
 
  private static final ThreadLocal<Connection> connectionHolder =
                                       new ThreadLocal<Connection>() {
    Connection connection = null;
 
    @Override public Connection initialValue() {
      try {
        // ...
        connection = DriverManager.getConnection(
            "jdbc:driver:name",
            "username",
            "password"
        );
      } catch (SQLException e) {
        // Forward to handler
      }
      return connection;
    }
  };
 
  public Connection getConnection() {
    return connectionHolder.get();
  }
 
  public boolean cancelStatement() { // Allows client to cancel statement
    Statement tmpStmt = stmt;
    if (tmpStmt != null) {
      try {
        tmpStmt.cancel();
        return true;
      } catch (SQLException e) {
        // Forward to handler
      }
    }
    return false;
  }
 
  @Override public void run() {
    try {
      if (getConnection() != null) {
        stmt = getConnection().createStatement();
      }
      if (stmt == null || (stmt.getConnection() != getConnection())) {
        throw new IllegalStateException();
      }
      ResultSet rs = stmt.executeQuery(query);
      // ...
    } catch (SQLException e) {
      // Forward to handler
    }
    // ...
  }
 
  public static void main(String[] args) throws InterruptedException {
    DBConnector connector = new DBConnector("suitable query");
    Thread thread = new Thread(connector);
    thread.start();
    Thread.sleep(5000);
    connector.cancelStatement();
  }
}
