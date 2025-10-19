package com.example.demand.controller;

import com.example.demand.entities.Consumer;
import com.example.demand.services.ConsumerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/consumers")
@Slf4j
@AllArgsConstructor
public class ConsumerController {

    private final ConsumerService consumerService;

    @GetMapping
    public ResponseEntity<List<Consumer>> getAllConsumers() {
        log.info("GET /api/consumers - list all consumers");
        List<Consumer> list = consumerService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consumer> getConsumerById(@PathVariable Long id) {
        log.info("GET /api/consumers/{} - get consumer", id);
        return consumerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Consumer> createConsumer(@Valid @RequestBody Consumer consumer) {
        log.info("POST /api/consumers - create consumer");
        Consumer created = consumerService.create(consumer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consumer> updateConsumer(@PathVariable Long id, @Valid @RequestBody Consumer consumer) {
        log.info("PUT /api/consumers/{} - update consumer", id);
        return consumerService.update(id, consumer)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumer(@PathVariable Long id) {
        log.info("DELETE /api/consumers/{} - delete consumer", id);
        boolean deleted = consumerService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}

