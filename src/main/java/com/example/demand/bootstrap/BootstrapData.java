package com.example.demand.bootstrap;

import com.example.demand.entities.Resource;
import com.example.demand.enums.ResourceType;
import com.example.demand.mappers.ResourceMapper;
import com.example.demand.model.ResourceDTO;
import com.example.demand.repositories.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final ResourceRepository resourceRepository;


    @Override
    public void run(String... args) throws Exception {
        loadResourceData();
    }

    private void loadResourceData()
    {
        Resource resource1 = Resource.builder()
                .name("Projector")
                .type(ResourceType.HUMAN)
                .description("HD Projector")
                .code("PRJ-001")
                .available(true)
                .build();

        Resource resource2 = Resource.builder()
                .name("Conference Room")
                .type(ResourceType.MATERIAL)
                .description("Main conference room")
                .code("CR-101")
                .available(true)
                .build();

        Resource resource3 = Resource.builder()
                .name("Laptop")
                .type(ResourceType.FINANCIAL)
                .description("High-performance laptop")
                .code("LTP-202")
                .available(false)
                .build();
        resourceRepository.save(resource1);
        resourceRepository.save(resource2);
        resourceRepository.save(resource3);
    }
}
