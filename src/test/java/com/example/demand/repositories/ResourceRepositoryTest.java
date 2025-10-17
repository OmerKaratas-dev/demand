package com.example.demand.repositories;

import com.example.demand.entities.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
class ResourceRepositoryTest {
    @Autowired
    ResourceRepository resourceRepository;

    @Test
    void testSaveResource() {
        Resource savedREsource = resourceRepository.save(Resource.
                builder().name("Test Resource")
                        .code("CDD-DD")
                        .description("Description")
                .build());

        resourceRepository.flush();

        assertThat(savedREsource).isNotNull();
        assertThat(savedREsource.getId()).isNotNull();
    }

    @Test
    void testSaveResourceTooLongName() {
        assertThrows(ConstraintViolationException.class, () -> {
            Resource savedREsource = resourceRepository.save(Resource.
                    builder().name("Test Resource Name That is way too long")
                            .code("CDD-DD")
                            .description("Description")
                    .build());

            resourceRepository.flush();
        });
    }


}