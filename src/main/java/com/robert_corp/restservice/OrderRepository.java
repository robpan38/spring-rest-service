package com.robert_corp.restservice;

import org.springframework.data.jpa.repository.JpaRepository;
import com.robert_corp.restservice.payroll.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
