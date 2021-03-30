import java.util.Scanner;

public class Main {
    final static String WELCOME_TEXT = "  ___            _                    __  __                                       _     ___         _             \n" +
            " | __|_ __  _ __| |___ _  _ ___ ___  |  \\/  |__ _ _ _  __ _ __ _ ___ _ __  ___ _ _| |_  / __|_  _ __| |_ ___ _ __  \n" +
            " | _|| '  \\| '_ \\ / _ \\ || / -_) -_) | |\\/| / _` | ' \\/ _` / _` / -_) '  \\/ -_) ' \\  _| \\__ \\ || (_-<  _/ -_) '  \\ \n" +
            " |___|_|_|_| .__/_\\___/\\_, \\___\\___| |_|  |_\\__,_|_||_\\__,_\\__, \\___|_|_|_\\___|_||_\\__| |___/\\_, /__/\\__\\___|_|_|_|\n" +
            "           |_|         |__/                                |___/                             |__/                  \n";

    final static String GITHUB_LINK = "https://github.com/apreshagarwal/Employee-Management-System";
    final static String LINKEDIN_LINK = "https://linkedin.com/in/apreshagarwal";

    public static void displayMenu() {
        System.out.println();
        System.out.println("1. Register New Employees");
        System.out.println("2. Edit Employee Details");
        System.out.println("3. Delete Employee");
        System.out.println("4. Raise Employee's Salary");
        System.out.println("5. Display All Employees");
        System.out.println("6. Export Data");
        System.out.println("7. Exit");
        System.out.print("Please enter your choice: ");
    }

    public static void registerEmployee(Scanner STDIN) {
        String choice;
        boolean isNotDone = Employee.registerEmployee();
        while (isNotDone) {
            choice = STDIN.nextLine();
            switch (choice) {
                case "Y":
                case "y":
                    isNotDone = Employee.registerEmployee();
                    break;

                case "N":
                case "n":
                    isNotDone = false;
                    break;

                default:
                    System.out.println("ERROR: Sorry, this is an invalid input. Please try again.");
                    System.out.print("Please enter your choice: ");
            }
        }
    }

    private static void deleteEmployee() {
        System.out.println();
        if (Employee.getEmployees().size() == 0) {
            System.out.println("ERROR: Register at least one employee to use this operation.");
            System.out.print("Please enter a choice (SUGGESTED: 1): ");
            return;
        }
        Employee.deleteEmployee();
    }

    private static void raiseEmployeeSalary() {
        System.out.println();
        if (Employee.getEmployees().size() == 0) {
            System.out.println("ERROR: Register at least one employee to use this operation.");
            System.out.print("Please enter a choice (SUGGESTED: 1): ");
            return;
        }
        Employee.raiseEmployeeSalary();
    }

    private static void displayEmployees() {
        System.out.println();
        if (Employee.getEmployees().size() == 0) {
            System.out.println("ERROR: Register at least one employee to use this operation.");
            System.out.print("Please enter a choice (SUGGESTED: 1): ");
            return;
        }
        Employee.displayEmployees();
    }

    private static void exitApplication() {
        System.out.println();
        if (Employee.getEmployees().size() > 0) {
            System.out.print("Saving the data... ");
            if (Employee.saveData()) {
                System.out.println("Saved.");
            } else {
                System.out.println("Not saved.");
            }
        }
        System.out.println("Exiting the application...");
        System.out.println("*** Thank you for using the Employee Management System ***");
    }

    public static void main(String[] args) {
        Scanner STDIN = new Scanner(System.in);

        System.out.println(WELCOME_TEXT);
        System.out.printf("GitHub: %s %n", GITHUB_LINK);

        displayMenu();
        boolean shouldExit = false;
        String choice;

        MENU: do {
            choice = STDIN.nextLine();
            switch (choice) {
                case "1":
                    registerEmployee(STDIN);
                    displayMenu();
                    break;

                case "2":
                    break;

                case "3":
                    deleteEmployee();
                    System.out.println();
                    displayMenu();
                    break;

                case "4":
                    raiseEmployeeSalary();
                    System.out.println();
                    displayMenu();
                    break;

                case "5":
                    displayEmployees();
                    System.out.println();
                    displayMenu();
                    break;

                case "6":
                    break;

                case "7":
                    STDIN.close();
                    exitApplication();
                    break MENU;

                default:
                    System.out.println();
                    System.out.println("ERROR: Sorry, this is an invalid input. Please try again.");
                    System.out.print("Please enter your choice: ");
            }
        } while (true);
    }
}
