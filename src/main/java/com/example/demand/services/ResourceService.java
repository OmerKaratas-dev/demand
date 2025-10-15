package com.example.demand.services;

import com.example.demand.model.Resource;

import java.util.List;

public interface ResourceService {
    public List<Resource> getResources();

    public Resource getResource(Long id);

    public Resource createNewResource(Resource resource);

    void updateResource(Long resourceId, Resource resource);

    void deleteResourceBy(Long resourceId);

    void patchResourceById(Long resourceId, Resource resource);
}
