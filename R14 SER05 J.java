public class OuterSer implements Serializable {
  private int rank;
  class InnerSer {
    protected String name;
    // ...
  }
}
