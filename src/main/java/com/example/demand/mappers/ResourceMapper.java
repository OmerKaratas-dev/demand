package com.example.demand.mappers;

import com.example.demand.entities.Resource;
import com.example.demand.model.ResourceDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ResourceMapper {
    Resource resourceDTOToResource(ResourceDTO resourceDTO);
    ResourceDTO resourceToResourceDTO(Resource resource);
}
