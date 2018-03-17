import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ImageClient {
    public static void main(String[] arg) {

        String[] args = new String[2];
        args[0] = "myfile.jpg";
        args[1] = "res.png";
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 2099); //Клиенту нужно получить серверный stub, чтобы зарегистрироваться.
            ImageInterf server = (ImageInterf) registry.lookup("ImageInterf");//Находим удалённый регистратор и запрашиваем у него stub связанный с именем ImageInterf

            BufferedImage is = ImageIO.read(new File(args[0]));

            ByteArrayOutputStream image1 = new ByteArrayOutputStream();

            ImageIO.write(is, "png", image1);

            byte[] image2 = server.metodImageInterf(image1.toByteArray());

            ByteArrayInputStream inputStream = new ByteArrayInputStream(image2);

            BufferedImage res = ImageIO.read(inputStream);

            ImageIO.write(res, "png", new File(args[1]));

            System.out.println("Operation has been executed successfully.");

        } catch (NotBoundException | MalformedURLException | RemoteException | FileNotFoundException ex) {
            System.err.println("Some error occurred: " + ex.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}