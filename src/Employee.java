import java.io.Serializable;
import java.util.*;

public class Employee implements Serializable {
    private static final long serialVersionUID = 3112462648708808454L;
    private static final ArrayList<Employee> employees;
    private final static int AGE_MIN_LIMIT;
    private final static int AGE_MAX_LIMIT;
    private final static int MAX_REGISTRATION_LIMIT;
    private static final int RAISE_MIN_LIMIT = 5000;
    private static final int RAISE_MAX_LIMIT = 50000;
    private static int LAST_REGISTERED_ID;
    private final static String COMPANY_CODE;
    private static final HashMap<String, Integer> designationsAndSalaries;
    private static final LinkedHashMap<String, Integer> displayColumns;
    private static final Set<String> namesSet = new HashSet<>();
    private final String employeeID;
    private final String name;
    private final int age;
    private final String designation;
    private int salary;

    static {
        AGE_MIN_LIMIT = 18;
        AGE_MAX_LIMIT = 60;
        MAX_REGISTRATION_LIMIT = 10;
        LAST_REGISTERED_ID = 0;
        COMPANY_CODE = "WY";
        System.out.print("Loading Data... ");
        employees = DeserializeEmployee.deserialize();
        designationsAndSalaries = new HashMap<>();
        designationsAndSalaries.put("Manager", 30000);
        designationsAndSalaries.put("Developer", 20000);
        designationsAndSalaries.put("Tester", 25000);
        displayColumns = new LinkedHashMap<>();
        displayColumns.put("", 6);
        displayColumns.put("EMPLOYEE ID", 11);
        displayColumns.put("EMPLOYEE NAME", 0);
        displayColumns.put("AGE", 3);
        displayColumns.put("DESIGNATION", 11);
        displayColumns.put("SALARY ", 10);
    }

    public Employee(String employeeID, String name, String designation, int age, int salary) {
        this.employeeID = employeeID;
        this.name = name;
        this.designation = designation;
        this.age = age;
        this.salary = salary;
    }

    public static boolean registerEmployee() {
        Scanner STDIN = new Scanner(System.in);
        int count = employees.size();

        if (count >= MAX_REGISTRATION_LIMIT) {
            System.out.println("ERROR: Maximum limit reached.");
            return false;
        }

        System.out.printf("%nYou can register %d employees.%n", MAX_REGISTRATION_LIMIT - count);

        System.out.println();

        ++LAST_REGISTERED_ID;

        String employeeID = COMPANY_CODE;
        employeeID += (LAST_REGISTERED_ID < 10) ? ("0" + LAST_REGISTERED_ID) : LAST_REGISTERED_ID;

        System.out.print("Please enter the Employee's Full Name: ");
        String name;
        while (true) {
            name = STDIN.nextLine();
            if (!name.matches("(\\p{Upper}(\\p{Lower}+\\s?)){2,3}")) {
                System.out.println("ERROR: INVALID NAME");
                System.out.println("The name should:");
                System.out.println("-> contain at least a first name and a last name (middle name is optional)");
                System.out.println("-> at max 3 words");
                System.out.println("-> not contain special characters or numbers (alphabets only)");
                System.out.println("-> not begin with space");
                System.out.println("-> not contain two consecutive spaces");
                System.out.println("-> have the first character of each word in upper-case and rest in lower-case");
                System.out.print("Please try again: ");
                continue;
            }
            if (namesSet.contains(name)) {
                System.out.printf("The name \"%s\" is already registered. Please try again: ", name);
                continue;
            }
            name = name.trim();
            namesSet.add(name);
            if (displayColumns.get("EMPLOYEE NAME") < name.length()) {
                displayColumns.replace("EMPLOYEE NAME", name.length());
            }
            break;
        }

        System.out.print("Please enter the Employee's Age: ");
        int age;
        while (true) {
            try {
                age = Integer.parseInt(STDIN.nextLine().trim());
                if (age < AGE_MIN_LIMIT || age > AGE_MAX_LIMIT) {
                    System.out.println("ERROR: INVALID AGE");
                    System.out.printf("Employee should be at least %d and at most %d years old. %n", AGE_MIN_LIMIT, AGE_MAX_LIMIT);
                    System.out.print("Please enter a valid age: ");
                    continue;
                }
            } catch (Exception e) {
                System.out.print("ERROR: INVALID AGE");
                System.out.print("Please enter a valid age: ");
                continue;
            }
            break;
        }

        System.out.print("Please enter the Employee's Designation (Manager, Developer, Tester): ");
        String designation = STDIN.nextLine().trim();

        while (!designationsAndSalaries.containsKey(designation)) {
            System.out.println("ERROR: INVALID DESIGNATION");
            System.out.println("You can choose only from the following designations:");
            for (String key: designationsAndSalaries.keySet()) {
                System.out.printf("-> %s %n", key);
            }
            System.out.print("Please enter a valid designation: ");
            designation = STDIN.nextLine().trim();
        }

        int salary = designationsAndSalaries.get(designation);

        Employee employee = new Employee(employeeID, name, designation, age, salary);
        employees.add(employee);

        System.out.println();

        System.out.println("The employee has been registered.");

        if (++count >= MAX_REGISTRATION_LIMIT) return false;

        System.out.printf("Do you want to register another employee? (%d left) (Y/N): ", MAX_REGISTRATION_LIMIT - (count));
        return true;
    }

    public static void displayEmployees() {
        printTableDivider();
        for (String column : displayColumns.keySet())
            System.out.format("| %-".concat(displayColumns.get(column).toString()).concat("s "), column);
        System.out.printf("|%n");
        printTableDivider();
        int count = 0;
        for (Employee employee : employees)
            System.out.format("| %-6s | %-11s | %-" + displayColumns.get("EMPLOYEE NAME") + "s | %-3d | %-11s | ₹%-,9d |%n", (++count < 10) ? "0" + count : count, employee.employeeID, employee.name, employee.age, employee.designation, employee.salary);
        printTableDivider();
    }

    public static void deleteEmployee() {
        Scanner STDIN = new Scanner(System.in);
        Employee e = getEmployee();
        employees.remove(e);
        namesSet.remove(e.name);
        System.out.printf("Employee \"%s\" has been removed from the system.%n", e.name);
    }

    private static Employee getEmployee() {
        Scanner STDIN = new Scanner(System.in);
        Employee e = null;
        System.out.print("Please enter the Employee ID: ");
        while (true) {
            String employeeID = STDIN.nextLine().trim();
            if (!employeeID.startsWith(COMPANY_CODE) || !employeeID.matches(COMPANY_CODE + "(\\d{2})")) {
                System.out.println("ERROR: The Employee ID is INVALID.");
                System.out.print("Please enter the Employee ID again: ");
                continue;
            }
            for (Employee employee : employees) {
                if (employee.employeeID.equals(employeeID)) {
                    e = employee;
                    break;
                }
            }
            if (e == null) {
                System.out.println("ERROR: The Employee is not registered.");
                System.out.print("Please enter the valid Employee ID: ");
                continue;
            }
            break;
        }
        return e;
    }

    public static void raiseEmployeeSalary() {
        Scanner STDIN = new Scanner(System.in);
        Employee e = getEmployee();
        System.out.print("Please enter the raise: ");
        int raise;
        while (true) {
            try {
                raise = Integer.parseInt(STDIN.nextLine().replaceAll(",", ""));
                if (raise < RAISE_MIN_LIMIT || raise > RAISE_MAX_LIMIT) {
                    System.out.printf("ERROR: ₹%d is not within the set limit. Please make sure the raise is between ₹%,d and ₹%,d.%n", raise, RAISE_MIN_LIMIT, RAISE_MAX_LIMIT);
                    System.out.print("Please enter the raise: ");
                    continue;
                }
            } catch (Exception exception) {
                System.out.println("ERROR: Invalid value for salary.");
                System.out.print("Please enter a valid value: ");
                continue;
            }
            break;
        }
        e.salary += raise;
        System.out.printf("The salary of %s has been raised by ₹%,d.\n", e.name, raise);
    }

    public static boolean saveData() {
        return SerializeEmployee.serialize(employees);
    }

    public static boolean loadData() {
        if (employees.size() > 0) {

            for (Employee e: employees) {
                namesSet.add(e.name);
                LAST_REGISTERED_ID = Integer.parseInt(e.employeeID.substring(2));
                if (displayColumns.get("EMPLOYEE NAME") < e.name.length()) {
                    displayColumns.replace("EMPLOYEE NAME", e.name.length());
                }
            }
            return true;
        }
        return false;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDesignation() {
        return designation;
    }

    public int getSalary() {
        return salary;
    }

    public static ArrayList<Employee> getEmployees() {
        return employees;
    }

    private static void printTableDivider() {
        System.out.print("+");
        for (String columns : displayColumns.keySet()) {
            int padding = displayColumns.get(columns) + 2;
            for (int i = 0; i < padding; ++i) System.out.print("-");
            System.out.print("+");
        }
        System.out.println();
    }
}
