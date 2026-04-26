package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.Optional;
//alt + inter -> implement interface после создания методов
public interface EmployeeRepository {
    String save(Employee employee);
    Optional<Employee> findById(Long id);
    String delete(Long id);
    Iterable<Employee> findAll();
}
