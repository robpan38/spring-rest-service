package com.robert_corp.restservice;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;

    EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all() {
        return CollectionModel.of(
                repository.findAll().stream()
                        .map(employee -> assembler.toModel(employee))
                        .collect(Collectors.toList()),
                linkTo(methodOn(EmployeeController.class).all()).withSelfRel()
        );
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return assembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@PathVariable Long id, @RequestBody Employee newEmployee) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
//                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
