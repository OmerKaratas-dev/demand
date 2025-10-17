package com.example.demand.services;

import com.example.demand.model.ResourceDTO;

import java.util.List;
import java.util.Optional;

public interface ResourceService {
    public List<ResourceDTO> getResources();

    public Optional<ResourceDTO> getResource(Long id);

    public ResourceDTO createNewResource(ResourceDTO resourceDTO);

    Optional<ResourceDTO> updateResource(Long resourceId, ResourceDTO resourceDTO);

    Boolean deleteResourceBy(Long resourceId);

    void patchResourceById(Long resourceId, ResourceDTO resourceDTO);
}
