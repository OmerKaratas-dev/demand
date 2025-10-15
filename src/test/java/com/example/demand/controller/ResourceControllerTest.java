package com.example.demand.controller;

import com.example.demand.model.Resource;
import com.example.demand.services.ResourceService;
import com.example.demand.services.impl.ResourceServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResourceController.class)
public class ResourceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    ResourceService resourceService;

    ResourceServiceImpl resourceServiceImpl;

    @BeforeEach
    void setUp() {
        resourceServiceImpl = new ResourceServiceImpl();
    }

    @Test
    void testPatchResourceById() throws Exception {
        Resource resource = resourceServiceImpl.getResources().get(0);
        Resource resourcePatch = Resource.builder()
                .name("Updated Resource Name")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/resource/" + resource.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resourcePatch)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Resource> captorResource = ArgumentCaptor.forClass(Resource.class);
        verify(resourceService).patchResourceById(captor.capture(), captorResource.capture());
        assertThat(resource.getId()).isEqualTo(captor.getValue());
        assertThat(captorResource.getValue().getName()).isEqualTo("Updated Resource Name");
    }

    @Test
    void testCreateNewResources() throws Exception {
        Resource resource = resourceServiceImpl.getResources().get(0);
        given(resourceService.createNewResource(any(Resource.class))).willReturn(resource);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/resource")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testUpdateNewResource() throws Exception {
        Resource resource = resourceServiceImpl.getResources().get(0);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/resource/" + resource.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(resourceService).updateResource(captor.capture(), any(Resource.class));
        //verify(resourceService).updateResource(captor.capture(), resource);
        assertThat(resource.getId()).isEqualTo(captor.getValue());
    }

    @Test
    void testDeleteResourceById() throws Exception {
        Resource resource = resourceServiceImpl.getResources().get(0);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/resource/" + resource.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(resourceService).deleteResourceBy(captor.capture());
        assertThat(resource.getId()).isEqualTo(captor.getValue());
    }

    @Test
    void testCreateNewResource() throws Exception {
        Resource resource = Resource.builder()
                .name("New Resource")
                .type(null)
                .description("Description of new resource")
                .code("NR-001")
                .available(true)
                .build();

        Resource savedResource = Resource.builder()
                .id(4L)
                .name(resource.getName())
                .type(resource.getType())
                .description(resource.getDescription())
                .code(resource.getCode())
                .available(resource.getAvailable())
                .build();

        given(resourceService.createNewResource(any(Resource.class))).willReturn(savedResource);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/resource")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/resource/" + savedResource.getId()));
    }

    @Test
    void getResources() throws Exception {
        given(resourceService.getResources()).willReturn(resourceServiceImpl.getResources());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/resource")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(3)));
    }


    @Test
    void getResoruceById() throws Exception {
        Resource resource = resourceServiceImpl.getResources().get(0);

        given(resourceService.getResource(resource.getId())).willReturn(resource);

        mockMvc.perform(get("/api/v1/resource/" + resource.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(resource.getName())));
    }

}
