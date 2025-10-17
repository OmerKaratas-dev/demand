package com.example.demand.controller;

import com.example.demand.model.ResourceDTO;
import com.example.demand.services.ResourceService;
import com.example.demand.services.impl.ResourceServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResourceController.class)
public class ResourceDTOControllerTest {

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
    void testCreateResourceNullResourceName() throws Exception {

        ResourceDTO beerDTO = ResourceDTO.builder().build();

        given(resourceService.createNewResource(any(ResourceDTO.class))).willReturn(resourceServiceImpl.getResources().get(1));

        MvcResult result = mockMvc.perform(post(ResourceController.RESOURCE_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect((jsonPath("$.length()", is(2))))
                .andReturn();
    }

    @Test
    void testPatchResourceById() throws Exception {
        ResourceDTO resourceDTO = resourceServiceImpl.getResources().get(0);
        ResourceDTO resourceDTOPatch = ResourceDTO.builder()
                .name("Updated Resource Name")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch(ResourceController.RESOURCE_PATH
                              + "/"  + resourceDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resourceDTOPatch)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<ResourceDTO> captorResource = ArgumentCaptor.forClass(ResourceDTO.class);
        verify(resourceService).patchResourceById(captor.capture(), captorResource.capture());
        assertThat(resourceDTO.getId()).isEqualTo(captor.getValue());
        assertThat(captorResource.getValue().getName()).isEqualTo("Updated Resource Name");
    }

    @Test
    void testCreateNewResources() throws Exception {
        ResourceDTO resourceDTO = resourceServiceImpl.getResources().get(0);
        given(resourceService.createNewResource(any(ResourceDTO.class))).willReturn(resourceDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(ResourceController.RESOURCE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resourceDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void getResourceByIdNotFond() throws Exception {
        given(resourceService.getResource(any(Long.class))).willReturn(Optional.empty());

        mockMvc.perform(get(ResourceController.RESOURCE_PATH_ID ,9999)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateNewResource() throws Exception {
        ResourceDTO resourceDTO = resourceServiceImpl.getResources().get(0);

        given(resourceService.updateResource(resourceDTO.getId(), resourceDTO))
                .willReturn(Optional.of(resourceDTO));

        mockMvc.perform(MockMvcRequestBuilders.put(ResourceController.RESOURCE_PATH
                                + "/" + resourceDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resourceDTO)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(resourceService).updateResource(captor.capture(), any(ResourceDTO.class));
        //verify(resourceService).updateResource(captor.capture(), resource);
        assertThat(resourceDTO.getId()).isEqualTo(captor.getValue());
    }

    @Test
    void testDeleteResourceById() throws Exception {
        ResourceDTO resourceDTO = resourceServiceImpl.getResources().get(0);
        mockMvc.perform(MockMvcRequestBuilders.delete(ResourceController.RESOURCE_PATH_ID, resourceDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(resourceService).deleteResourceBy(captor.capture());
        assertThat(resourceDTO.getId()).isEqualTo(captor.getValue());
    }

    @Test
    void testCreateNewResource() throws Exception {
        ResourceDTO resourceDTO = ResourceDTO.builder()
                .name("New Resource")
                .type(null)
                .description("Description of new resource")
                .code("NR-001")
                .available(true)
                .build();

        ResourceDTO savedResourceDTO = ResourceDTO.builder()
                .id(4L)
                .name(resourceDTO.getName())
                .type(resourceDTO.getType())
                .description(resourceDTO.getDescription())
                .code(resourceDTO.getCode())
                .available(resourceDTO.getAvailable())
                .build();

        given(resourceService.createNewResource(any(ResourceDTO.class))).willReturn(savedResourceDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(ResourceController.RESOURCE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resourceDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", ResourceController.RESOURCE_PATH
                        + "/" + savedResourceDTO.getId()));
    }

    @Test
    void getResources() throws Exception {
        given(resourceService.getResources()).willReturn(resourceServiceImpl.getResources());

        mockMvc.perform(MockMvcRequestBuilders.get(ResourceController.RESOURCE_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(3)));
    }


    @Test
    void getResoruceById() throws Exception {
        ResourceDTO resourceDTO = resourceServiceImpl.getResources().get(0);

        given(resourceService.getResource(resourceDTO.getId())).willReturn(Optional.of(resourceDTO));

        mockMvc.perform(get(ResourceController.RESOURCE_PATH + "/" + resourceDTO.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(resourceDTO.getName())));
    }

}
