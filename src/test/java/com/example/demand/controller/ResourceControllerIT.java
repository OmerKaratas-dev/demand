package com.example.demand.controller;

import com.example.demand.entities.Resource;
import com.example.demand.mappers.ResourceMapper;
import com.example.demand.model.ResourceDTO;
import com.example.demand.repositories.ResourceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class ResourceControllerIT {
    @Autowired
    ResourceController resourceController;
    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testPatchResourceBadName() throws Exception {
        Resource resource = resourceRepository.findAll().get(0);

        Map<String, Object> resourceMap = new HashMap<>();
        resourceMap.put("name", "New Name 343433434567890343433434567890343433434567890");

        mockMvc.perform(patch(ResourceController.RESOURCE_PATH_ID, resource.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resourceMap)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deleteByIdNotFound() {
        assertThrows(NotFoundException.class,
                () -> resourceController.deleteResourceById(-1L));
    }

    @Test
    @Rollback
    @Transactional
    void deleteById() {
        Resource resource = resourceRepository.findAll().get(0);

        ResponseEntity responseEntity = resourceController.deleteResourceById(resource.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        //resourceController.deleteResourceById(resource.getId());
        assertThat(resourceRepository.findById(resource.getId()).isEmpty());
    }

    @Test
    void testUpdateNotFound() {
        ResourceDTO resourceDTO = ResourceDTO.builder()
                .name("Updated Resource Name")
                .build();
        assertThrows(NotFoundException.class,
                () -> resourceController.updateResourceById(-1L, resourceDTO));
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingResource() {
        Resource resource = resourceRepository.findAll().get(0);
        ResourceDTO resourceDTO = resourceMapper.resourceToResourceDTO(resource);
        resourceDTO.setName("Updated Resource Name");
        resourceDTO.setId(null);

        ResponseEntity responseEntity = resourceController.updateResourceById(resource.getId(), resourceDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Resource updatedResource = resourceRepository.findById(resource.getId()).get();
        assertThat(updatedResource).isNotNull();
        assertThat(updatedResource.getName()).isEqualTo("Updated Resource Name");
    }

    @Test
    void getAllResources() {
        List<ResourceDTO> resources = resourceController.listResources();
        assertThat(resources.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        resourceRepository.deleteAll();
        List<ResourceDTO> resources = resourceController.listResources();
        assertThat(resources.size()).isEqualTo(0);
    }

    @Test
    void testGetResourceByIdNotFound() {
        assertThrows(NotFoundException.class,
                () -> resourceController.getResourceById(-1L));
    }

    @Test
    void testGetResourceById() {
        Resource resource = resourceRepository.findAll().get(0);
        ResourceDTO resourceDTO = resourceController.getResourceById(resource.getId());
    }

    @Test
    void testCreateNewResource() {
        ResourceDTO resourceDTO = ResourceDTO.builder()
                .name("New Resource")
                .build();
        ResponseEntity responseEntity = resourceController.handlePost(resourceDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        Long newResourceId = Long.valueOf(locationUUID[locationUUID.length - 1]);

        Resource resource = resourceRepository.findById(newResourceId).orElse(null);
        assertThat(resource).isNotNull();
        assertThat(resource.getName()).isEqualTo("New Resource");

    }

}