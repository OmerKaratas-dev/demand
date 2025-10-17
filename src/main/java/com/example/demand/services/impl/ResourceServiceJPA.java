package com.example.demand.services.impl;

import com.example.demand.controller.NotFoundException;
import com.example.demand.entities.Resource;
import com.example.demand.mappers.ResourceMapper;
import com.example.demand.model.ResourceDTO;
import com.example.demand.repositories.ResourceRepository;
import com.example.demand.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class ResourceServiceJPA implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    @Override
    public List<ResourceDTO> getResources() {
        return resourceRepository.findAll()
                .stream()
                .map(resourceMapper::resourceToResourceDTO)
                .toList();
    }

    @Override
    public Optional<ResourceDTO> getResource(Long id) {
        return Optional.ofNullable(resourceMapper.resourceToResourceDTO(
                resourceRepository.findById(id).orElse(null)
        ));
    }

    @Override
    public ResourceDTO createNewResource(ResourceDTO resourceDTO) {
        return resourceMapper.resourceToResourceDTO
                (resourceRepository.save(resourceMapper.resourceDTOToResource(resourceDTO)));
    }

    @Override
    public Optional<ResourceDTO> updateResource(Long resourceId, ResourceDTO resourceDTO) {
        Resource foundResource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new NotFoundException("Resource not found"));
        foundResource.setName(resourceDTO.getName());
        foundResource.setType(resourceDTO.getType());
        foundResource.setDescription(resourceDTO.getDescription());
        foundResource.setCode(resourceDTO.getCode());
        foundResource.setAvailable(resourceDTO.getAvailable());
        Resource updatedResource = resourceRepository.save(foundResource);
        return Optional.of(resourceMapper.resourceToResourceDTO(updatedResource));
    };

    @Override
    public Boolean deleteResourceBy(Long resourceId) {
        if (resourceRepository.existsById(resourceId)) {
            resourceRepository.deleteById(resourceId);
            return true;
        }
        return false;
    }

    @Override
    public void patchResourceById(Long resourceId, ResourceDTO resourceDTO) {

    }
}
