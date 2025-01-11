package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Waste;

@Repository
public interface WasteRespository extends JpaRepository<Waste, UUID> {
    // Optional<Waste> findByName(String name);

    // void deleteByName(String name);
}
