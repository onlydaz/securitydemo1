package ltweb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ltweb.model.Customer;

import java.util.List;

@RestController
public class CustomerController {

    private final List<Customer> customers = List.of(
            Customer.builder().id("001").name("Nguyễn Duy Đạt").email("duydat@gmail.com").build(),
            Customer.builder().id("002").name("Duy Đạt").email("dat@gmail.com").build()
    );

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello is Guest");
    }

    @GetMapping("/customer/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Customer>> getCustomerList() {
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") String id) {
        return ResponseEntity.ok(
                customers.stream()
                        .filter(customer -> customer.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Customer not found"))
        );
    }
}