package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;


public class EmployeeService {
    private EmployeeRepository employeeRepository;


    public BigDecimal calculateAverageSalary() {
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        BigDecimal sumSalary = BigDecimal.ZERO;
        int count = 0;

        for (Employee employee : allEmployees) {
            sumSalary = sumSalary.add(employee.getSalary());
            count++;
        }

        if (count == 0) {
            return BigDecimal.ZERO;
        }

        return sumSalary.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP);
    }

    public Employee findHighestPaid() {
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        Employee highest = null;

        for (Employee employee : allEmployees) {
            if (highest == null || employee.getSalary().compareTo(highest.getSalary()) > 0) {
                highest = employee;
            }
        }
        return highest;
    }

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public String addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public String deleteEmployee(Long id) {
            return employeeRepository.delete(id);
    }

    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
}
