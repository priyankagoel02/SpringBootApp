package com.luxoft.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    public EmployeeRepository employeeRepository;

    @Autowired
    public OfficeRepository officeRepository;

    @GetMapping("/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> getEmployees(@RequestParam(required = false) Integer employeeId) {
        Iterable<Employee> iterable = employeeRepository.findAll();
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : iterable) {
            employees.add(employee);
        }

        if (employeeId != null) {
            employees.stream()
                    .filter(employee -> employee.getId() == employeeId)
                    .collect(Collectors.toList());
        }
        return employees;
    }

    @GetMapping("/offices/save")
    @ResponseStatus(HttpStatus.OK)
    public void saveOffice(@RequestBody Office office) {
        if (office.getId() == null || office.getId() == 0) {
            Iterable<Office> offices = officeRepository.findAll();
            Integer highestOfficeId = 0;
            for (Office office2 : offices) {
                highestOfficeId = Math.max(highestOfficeId, office2.id);
            }
            office.id = ++highestOfficeId;
        }

        officeRepository.save(office);
    }

    @GetMapping("/countries")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getCountries() {
        Iterable<Employee> iterable = employeeRepository.findAll();
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : iterable) {
            employees.add(employee);
        }
        List<String> countries = employees.stream().map(employee -> employee.getCountry()).collect(Collectors.toList());
        return countries;
    }

    @GetMapping("/employee")
    @ResponseStatus(HttpStatus.OK)
    public void saveEmployee(@RequestBody Employee employee) {
        if (employee.getOffice() != null) {
            if (employee.getOffice().getId() == null || employee.getOffice().getId() == 0) {
                if (employee.getOffice().getName() == null) {
                    employee.getOffice().setName("Main building");
                }
                officeRepository.save(employee.getOffice());
            }
        }
        Iterable<Employee> employeeIterable = employeeRepository.findAll();
        List<Employee> employees = new ArrayList<>();
        Integer highestId = 0;
        for (Employee employee1 : employeeIterable) {
            employees.add(employee1);
            highestId = Math.max(highestId, employee1.getId());
        }
        highestId++;
        if (employee.getId() != null) {
            Employee previousEmployee = employees.get(0);
            previousEmployee.setName(employee.getName());
            previousEmployee.setCountry(employee.getCountry());
            previousEmployee.setCity(employee.getCity());
            previousEmployee.setOffice(employee.getOffice());
            employeeRepository.save(previousEmployee);
        } else {
            employee.setId(highestId);
            employeeRepository.save(employee);
        }
    }

    @GetMapping("/cities")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getCities() {
        Iterable<Employee> iterable = employeeRepository.findAll();
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : iterable) {
            employees.add(employee);
        }
        List<String> employeeCities = employees.stream().map(employee -> employee.getCity()).collect(Collectors.toList());

        Iterable<Office> iterable2 = officeRepository.findAll();
        List<Office> offices = new ArrayList<>();
        for (Office office : iterable2) {
            offices.add(office);
        }
        List<String> officeCities = offices.stream().map(office -> office.getCity()).collect(Collectors.toList());
        List<String> allCities = new ArrayList<>();
        allCities.addAll(employeeCities);
        allCities.addAll(officeCities);
        return allCities;
    }

    @GetMapping("/offices")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Office> retrieveOffices() {
        return officeRepository.findAll();
    }
}
