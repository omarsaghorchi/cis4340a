public class OuterSer implements Serializable {
  private int rank;
  class InnerSer implements Serializable {
    protected String name;
    // ...
  }
}
