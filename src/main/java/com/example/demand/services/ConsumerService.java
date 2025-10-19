package com.example.demand.services;

import com.example.demand.entities.Consumer;

import java.util.List;
import java.util.Optional;

public interface ConsumerService {
    List<Consumer> findAll();
    Optional<Consumer> findById(Long id);
    Consumer create(Consumer consumer);
    Optional<Consumer> update(Long id, Consumer consumer);
    boolean deleteById(Long id);
}
