package com.example.demand.services.impl;

import com.example.demand.enums.ResourceType;
import com.example.demand.model.Resource;
import com.example.demand.services.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private Map<Long, Resource> resourceMap;

    public ResourceServiceImpl() {

        this.resourceMap = new HashMap<>();

        Resource resource1 = Resource.builder()
                .id(1L)
                .name("Projector")
                .type(ResourceType.HUMAN)
                .description("HD Projector")
                .code("PRJ-001")
                .available(true)
                .build();

        Resource resource2 = Resource.builder()
                .id(2L)
                .name("Conference Room")
                .type(ResourceType.MATERIAL)
                .description("Main conference room")
                .code("CR-101")
                .available(true)
                .build();

        Resource resource3 = Resource.builder()
                .id(3L)
                .name("Laptop")
                .type(ResourceType.FINANCIAL)
                .description("High-performance laptop")
                .code("LTP-202")
                .available(false)
                .build();

        resourceMap.put(resource1.getId(), resource1);
        resourceMap.put(resource2.getId(), resource2);
        resourceMap.put(resource3.getId(), resource3);
    }

    @Override
    public List<Resource> getResources() {
        return new ArrayList<>(resourceMap.values());
    }

    @Override
    public Resource getResource(Long id) {
        log.debug("Get Beer by Id - in service. Id: " + id.toString());
        return resourceMap.get(id);
    }

    @Override
    public Resource createNewResource(Resource resource) {
        Resource savedResource = Resource.builder()
                .id(4L)
                .name("Mobile")
                .type(ResourceType.HUMAN)
                .description("High-performance mobile")
                .code("MOB-202")
                .available(true)
                .build();

        resourceMap.put(savedResource.getId(), savedResource);
        return savedResource;
    }

    @Override
    public void updateResource(Long resourceId, Resource resource) {
        Resource existing = resourceMap.get(resourceId);
        existing.setName(resource.getName());
        existing.setDescription(resource.getDescription());
        existing.setCode(resource.getCode());
        existing.setAvailable(resource.getAvailable());

        resourceMap.put(existing.getId(), existing);
    }

    @Override
    public void deleteResourceBy(Long resourceId) {
        resourceMap.remove(resourceId);
    }

    @Override
    public void patchResourceById(Long resourceId, Resource resource) {

        Resource existing = resourceMap.get(resourceId);

        if (StringUtils.hasText(resource.getName())){
            existing.setName(resource.getName());
        }
        if (resource.getType() != null) {
            existing.setType(resource.getType());
        }
        if (resource.getCode() != null) {
            existing.setCode(resource.getCode());
        }
        if (resource.getDescription() != null){
            existing.setDescription(resource.getDescription());
        }
        if (resource.getAvailable() != null) {
            existing.setAvailable(resource.getAvailable());
        }
    }

}
