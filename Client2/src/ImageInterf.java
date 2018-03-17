
import java.io.IOException;
import java.rmi.Remote;


public interface ImageInterf extends Remote {
    byte[] metodImageInterf (byte[] image) throws  IOException;
}
