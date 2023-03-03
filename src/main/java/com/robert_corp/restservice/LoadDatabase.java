package com.robert_corp.restservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.robert_corp.restservice.payroll.Order;
import com.robert_corp.restservice.payroll.Status;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        return args -> {
            log.info("Preloading " + employeeRepository.save(new Employee("Robert", "Pandele", "CEO")));
            log.info("Preloading " + employeeRepository.save(new Employee("Matei", "Swag", "CFO")));
            log.info("Preloading " + orderRepository.save(new Order("iphone cu storage coaie", Status.IN_PROGRESS)));
            log.info("Preloading " + orderRepository.save(new Order("macbook cu m1 swagger", Status.COMPLETED)));
        };
    }
}
