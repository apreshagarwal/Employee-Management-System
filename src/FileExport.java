import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileExport {
    final static String FILENAME = "employees_data";
    static FileWriter fileWriter;
    public static boolean exportToText(ArrayList<Employee> employees) {
        final String EXTENSION = ".txt";
        try {
             fileWriter = new FileWriter(FILENAME.concat(EXTENSION));
            for (Employee e: employees) {
                fileWriter.write(String.format("Employee ID: %s %n", e.getEmployeeID()));
                fileWriter.write(String.format("Full Name: %s %n", e.getName()));
                fileWriter.write(String.format("Age: %d %n", e.getAge()));
                fileWriter.write(String.format("Designation: %s %n", e.getDesignation()));
                fileWriter.write(String.format("Current Salary: ₹%,d %n", e.getSalary()));
                fileWriter.write(String.format("%n"));
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean exportToCSV(ArrayList<Employee> employees) {
        final String EXTENSION = ".csv";
        try {
            fileWriter = new FileWriter(FILENAME.concat(EXTENSION));
            fileWriter.write(String.format("%s,%s,%s,%s,%s%n", "Employee ID", "Full Name", "Age", "Designation", "Current Salary"));
            for (Employee e: employees) {
                fileWriter.write(String.format("%s,%s,%d,%s,%,d%n", e.getEmployeeID(), e.getName(), e.getAge(), e.getDesignation(), e.getSalary()));
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean exportToJSON(ArrayList<Employee> employees) {
        final String EXTENSION = ".json";
        try {
            fileWriter = new FileWriter(FILENAME.concat(EXTENSION));
            fileWriter.write(String.format("%s%n", "["));
            int count = 0;
            int len = employees.size();
            for (Employee e: employees) {
                fileWriter.write(String.format("\t%s%n", "{"));
                fileWriter.write(String.format("\t\t\"employee_id\" : \"%s\",%n", e.getEmployeeID()));
                fileWriter.write(String.format("\t\t\"full_name\" : \"%s\",%n", e.getName()));
                fileWriter.write(String.format("\t\t\"age\" : %d,%n", e.getAge()));
                fileWriter.write(String.format("\t\t\"designation\" : \"%s\",%n", e.getDesignation()));
                fileWriter.write(String.format("\t\t\"current_salary\" : %d%n", e.getSalary()));
                fileWriter.write(String.format("\t%s", "}"));
                if (count++ < len - 1)
                    fileWriter.write(String.format("%s%n", ","));
                else
                    fileWriter.write(String.format("%n"));
            }
            fileWriter.write("]");
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
