// Trusted.java
 
import java.security.*;
 
public class Trusted {
 
   public static void loadLibrary(final String library){
      AccessController.doPrivileged(new PrivilegedAction<Void>() {
         public Void run() {
             System.loadLibrary(library);
             return null;
         }
      });
   }
}
 
---------------------------------------------------------------------------------
 
// Untrusted.java
 
public class Untrusted {
 
   private native void nativeOperation();
 
   public static void main(String[] args) {
      String library = new String("NativeMethodLib");
      Trusted.loadLibrary(library);
      new Untrusted.nativeOperation();  // invoke the native method
   }
}
