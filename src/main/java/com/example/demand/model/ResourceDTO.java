package com.example.demand.model;

import com.example.demand.enums.ResourceType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourceDTO {
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String code;

    private Boolean available;
}
