package ru.ranepa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {
    private EmployeeService service;

    @BeforeEach
    void setUp() {
        service = new EmployeeService(new EmployeeRepositoryImpl());
    }

    @Test
    void shouldCalculateAverageSalary() {
        // Given
        service.addEmployee(new Employee("John", "Dev", 1000, LocalDate.now()));
        service.addEmployee(new Employee("Jane", "Dev", 2000, LocalDate.now()));
        service.addEmployee(new Employee("Bob", "Dev", 3000, LocalDate.now()));

        // When
        BigDecimal average = service.calculateAverageSalary();

        // Then
        assertEquals(2000.0, average.doubleValue(), 0.01);
    }

    @Test
    void shouldFindHighestPaid() {
        // Given
        service.addEmployee(new Employee("John", "Dev", 1000, LocalDate.now()));
        service.addEmployee(new Employee("Jane", "Dev", 5000, LocalDate.now()));
        service.addEmployee(new Employee("Bob", "Dev", 3000, LocalDate.now()));

        // When
        Employee highest = service.findHighestPaid();

        // Then
        assertEquals("Jane", highest.getName());
        assertEquals(5000.0, highest.getSalary().doubleValue());
    }

    @Test
    void shouldReturnNullWhenNoEmployees() {
        // When
        Employee highest = service.findHighestPaid();

        // Then
        assertNull(highest);
        assertEquals(BigDecimal.ZERO, service.calculateAverageSalary());
    }
}