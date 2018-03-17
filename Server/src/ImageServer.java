
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ImageServer extends UnicastRemoteObject implements ImageInterf {

    private final float[] kernel = {
            0, -1, 0,
            -1, 5, -1,
            0, -1, 0
    };

    public byte[] metodImageInterf(byte[] imaged) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imaged);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            BufferedImage image = ImageIO.read(inputStream);

            BufferedImageOp imageOp = new ConvolveOp(new Kernel(3, 3, kernel));

            BufferedImage resal = imageOp.filter(image, null);

            ImageIO.write(resal, "png", outputStream);

            return outputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
           return new byte[0];
        }
    }

    private ImageServer() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        ImageServer server;
        try {
            server = new ImageServer();

            Registry registry = LocateRegistry.createRegistry(2099); // Создаём регистратор он будет принимать соеденения на 2099 порту.

            registry.rebind("ImageInterf", server);

            System.out.println("Server is ready!");
        } catch (RemoteException e) {
            System.err.println("Some error occurred: " + e.toString());
        }
    }

}


