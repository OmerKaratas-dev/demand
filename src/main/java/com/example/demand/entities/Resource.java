package com.example.demand.entities;

import com.example.demand.enums.ResourceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Resource {
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(length = 100)
    private String name;

    @Enumerated(jakarta.persistence.EnumType.STRING)
    private ResourceType type;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String code;
    private Boolean available;
}
