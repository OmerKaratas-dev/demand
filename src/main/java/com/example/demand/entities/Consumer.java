package com.example.demand.entities;

import com.example.demand.enums.ResourceType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Consumer {
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(length = 100)
    private String name;

    @NotNull
    @NotBlank
    private String username;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @NotNull
    @NotBlank
    private String mail;

    @NotNull
    @NotBlank
    private String phoneNumber;

    @OneToMany(mappedBy = "consumer")
    private Set<Resource> resources;
}
