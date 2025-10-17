package com.example.demand.services.impl;

import com.example.demand.enums.ResourceType;
import com.example.demand.model.ResourceDTO;
import com.example.demand.services.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private Map<Long, ResourceDTO> resourceMap;

    public ResourceServiceImpl() {

        this.resourceMap = new HashMap<>();

        ResourceDTO resourceDTO1 = ResourceDTO.builder()
                .id(1L)
                .name("Projector")
                .type(ResourceType.HUMAN)
                .description("HD Projector")
                .code("PRJ-001")
                .available(true)
                .build();

        ResourceDTO resourceDTO2 = ResourceDTO.builder()
                .id(2L)
                .name("Conference Room")
                .type(ResourceType.MATERIAL)
                .description("Main conference room")
                .code("CR-101")
                .available(true)
                .build();

        ResourceDTO resourceDTO3 = ResourceDTO.builder()
                .id(3L)
                .name("Laptop")
                .type(ResourceType.FINANCIAL)
                .description("High-performance laptop")
                .code("LTP-202")
                .available(false)
                .build();

        resourceMap.put(resourceDTO1.getId(), resourceDTO1);
        resourceMap.put(resourceDTO2.getId(), resourceDTO2);
        resourceMap.put(resourceDTO3.getId(), resourceDTO3);
    }

    @Override
    public List<ResourceDTO> getResources() {
        return new ArrayList<>(resourceMap.values());
    }

    @Override
    public Optional<ResourceDTO> getResource(Long id) {
        log.debug("Get Beer by Id - in service. Id: " + id.toString());
        return Optional.of(resourceMap.get(id));
    }

    @Override
    public ResourceDTO createNewResource(ResourceDTO resourceDTO) {
        ResourceDTO savedResourceDTO = ResourceDTO.builder()
                .id(4L)
                .name("Mobile")
                .type(ResourceType.HUMAN)
                .description("High-performance mobile")
                .code("MOB-202")
                .available(true)
                .build();

        resourceMap.put(savedResourceDTO.getId(), savedResourceDTO);
        return savedResourceDTO;
    }

    @Override
    public Optional<ResourceDTO> updateResource(Long resourceId, ResourceDTO resourceDTO) {
        ResourceDTO existing = resourceMap.get(resourceId);
        existing.setName(resourceDTO.getName());
        existing.setDescription(resourceDTO.getDescription());
        existing.setCode(resourceDTO.getCode());
        existing.setAvailable(resourceDTO.getAvailable());

        resourceMap.put(existing.getId(), existing);
        return Optional.of(existing);
    }

    @Override
    public Boolean deleteResourceBy(Long resourceId) {
        resourceMap.remove(resourceId);
        return true;
    }

    @Override
    public void patchResourceById(Long resourceId, ResourceDTO resourceDTO) {

        ResourceDTO existing = resourceMap.get(resourceId);

        if (StringUtils.hasText(resourceDTO.getName())){
            existing.setName(resourceDTO.getName());
        }
        if (resourceDTO.getType() != null) {
            existing.setType(resourceDTO.getType());
        }
        if (resourceDTO.getCode() != null) {
            existing.setCode(resourceDTO.getCode());
        }
        if (resourceDTO.getDescription() != null){
            existing.setDescription(resourceDTO.getDescription());
        }
        if (resourceDTO.getAvailable() != null) {
            existing.setAvailable(resourceDTO.getAvailable());
        }
    }

}
