package com.example.demand.model;

import com.example.demand.enums.ResourceType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resource {
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    private String description;

    private String code;

    private Boolean available;
}
