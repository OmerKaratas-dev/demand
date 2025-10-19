package com.example.demand.entities;

import com.example.demand.enums.ResourceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
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
    public Resource(Consumer consumer, Boolean available, String code, String description,
                    ResourceType type, String name, Long id) {
        setConsumer(consumer);
        this.available = available;
        this.code = code;
        this.description = description;
        this.type = type;
        this.name = name;
        this.id = id;
    }

    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne
    private Consumer consumer;

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
        consumer.getResources().add(this);
    }
}
