package com.dapr.dapr;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.State;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DaprApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaprApplication.class, args);
    }

    @Value("${STATE_STORE_NAME:kvstore}")
    private String STATE_STORE_NAME = "kvstore";

    private String CUSTOMER_KEY = "customer-";

    @PostMapping("/")
    public ResponseEntity<Customer> storeCustomer(@RequestBody Customer customer) {
        try (DaprClient client = new DaprClientBuilder().build()) {
            client.saveState(STATE_STORE_NAME, CUSTOMER_KEY + customer.id, customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<Customer> getCustomer(@RequestParam String customerId) {

        try (DaprClient client = new DaprClientBuilder().build()) {
            State<Customer> customerState = client.getState(STATE_STORE_NAME, CUSTOMER_KEY + customerId, Customer.class).block();
            if (customerState.getValue() == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(customerState.getValue());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public record Customer(String id, String name, String email) {
    }

}