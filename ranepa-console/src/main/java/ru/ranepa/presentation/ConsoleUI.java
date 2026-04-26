package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleUI {
    private EmployeeService service;
    private Scanner scanner;

    public ConsoleUI() {
        this.service = new EmployeeService(new EmployeeRepositoryImpl());
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            printMenu();
            int choice = getIntInput("Choose option: ");

            switch (choice) {
                case 1:
                    showAllEmployees();
                    break;
                case 2:
                    addEmployee();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    findEmployeeById();
                    break;
                case 5:
                    showStatistics();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== HRM System Menu ===");
        System.out.println("1. Show all employees");
        System.out.println("2. Add employee");
        System.out.println("3. Delete employee");
        System.out.println("4. Find employee by ID");
        System.out.println("5. Show statistics");
        System.out.println("6. Exit");
    }

    private void showAllEmployees() {
        System.out.println("\n=== All Employees ===");
        for (Employee emp : service.getAllEmployees()) {
            System.out.println(emp);
        }
    }

    private void addEmployee() {
        System.out.println("\n=== Add New Employee ===");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter position: ");
        String position = scanner.nextLine();
        double salary = getDoubleInput("Enter salary: ");
        System.out.print("Enter hire date (YYYY-MM-DD): ");
        boolean goodDate = false;
        LocalDate hireDate = LocalDate.now();
        while (!goodDate) {
            try {
                hireDate = LocalDate.parse(scanner.nextLine());
                goodDate = true;
            }
            catch(DateTimeParseException e){
                System.out.print("Incorrect format. Enter hire date (YYYY-MM-DD): ");
            }
        }
        Employee employee = new Employee(name, position, salary, hireDate);
        System.out.println(service.addEmployee(employee));
    }

    private void deleteEmployee() {
        System.out.println("\n=== Delete Employee ===");
        Long id = getLongInput("Enter employee ID: ");
        System.out.println(service.deleteEmployee(id));
    }

    private void findEmployeeById() {
        System.out.println("\n=== Find Employee ===");
        Long id = getLongInput("Enter employee ID: ");
        service.findEmployeeById(id).ifPresentOrElse(
                emp -> System.out.println("Found: " + emp),
                () -> System.out.println("Employee not found")
        );
    }

    private void showStatistics() {
        System.out.println("\n=== Statistics ===");
        System.out.printf("Average salary: %.2f%n", service.calculateAverageSalary());
        Employee highest = service.findHighestPaid();
        if (highest != null) {
            System.out.println("Highest paid: " + highest.getName() +
                    " (" + highest.getSalary() + ")");
        }
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter number: ");
            scanner.next();
        }
        int result = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return result;
    }

    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Enter number: ");
            scanner.next();
        }
        double result = scanner.nextDouble();
        scanner.nextLine();
        return result;
    }

    private Long getLongInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) {
            System.out.print("Invalid input. Enter ID: ");
            scanner.next();
        }
        Long result = scanner.nextLong();
        scanner.nextLine();
        return result;
    }
}