 // Trusted.java
 
import java.security.*;
 
public class Trusted {
 
   // load native libraries
   static{
      System.loadLibrary("NativeMethodLib1");
      System.loadLibrary("NativeMethodLib2");
      ...
   }
 
   // private native methods
   private native void nativeOperation1(byte[] data, int offset, int len);
   private native void nativeOperation2(...)
   ...
  
   // wrapper methods perform SecurityManager and input validation checks
   public void doOperation1(byte[] data, int offset, int len) {
      // permission needed to invoke native method
      securityManagerCheck();
 
      if (data == null) {
         throw new NullPointerException();
      }
 
      // copy mutable input
      data = data.clone();
 
      // validate input
      if ((offset < 0) || (len < 0) || (offset > (data.length - len))) {
         throw new IllegalArgumentException();
      }
 
      nativeOperation1(data, offset, len);
   }
    
   public void doOperation2(...){
      ...
   }
}
