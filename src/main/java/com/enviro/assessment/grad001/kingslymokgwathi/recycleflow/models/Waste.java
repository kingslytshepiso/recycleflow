package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Waste {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(unique = true, nullable = false)
    @NotNull(message = "Name is required")
    private String name;
    @NotNull(message = "Description is required")
    private String description;
    private String disposalMethod;
    @ManyToMany(mappedBy = "waste", cascade = CascadeType.ALL)
    @JsonIgnore
    Set<Classification> classifications = new HashSet<Classification>();
}
