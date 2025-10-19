package com.example.demand.services.impl;

import com.example.demand.entities.Consumer;
import com.example.demand.repositories.ConsumerRepository;
import com.example.demand.services.ConsumerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumerServiceJPA implements ConsumerService {

    private final ConsumerRepository repository;

    public ConsumerServiceJPA(ConsumerRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Consumer> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Consumer> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Consumer create(Consumer consumer) {
        // ensure id is null so JPA will insert
        consumer.setId(null);
        return repository.save(consumer);
    }

    @Override
    @Transactional
    public Optional<Consumer> update(Long id, Consumer consumer) {
        return repository.findById(id).map(existing -> {
            existing.setName(consumer.getName());
            existing.setUsername(consumer.getUsername());
            existing.setMail(consumer.getMail());
            existing.setPhoneNumber(consumer.getPhoneNumber());
            // keep existing.createdDate and id
            return repository.save(existing);
        });
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
