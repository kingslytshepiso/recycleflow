package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Classification {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true, nullable = false)
    @NotNull(message = "Name is required")
    private String name;
    private String description;
    private Set<String> importantNotes;
    @OneToMany(mappedBy = "classification", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Waste> waste = new HashSet<Waste>();
}
