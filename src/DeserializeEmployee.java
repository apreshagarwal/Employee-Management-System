import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class DeserializeEmployee {
    private final static String FILE_NAME = "DO_NOT_DELETE.ser";
    /*
     * TODO : Change the name of the file according to your preference, but
     *  make sure the file name matches the file name in SerializeEmployee class
     */
    public static ArrayList<Employee> deserialize() {
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Employee> employees = (ArrayList<Employee>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return employees;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
