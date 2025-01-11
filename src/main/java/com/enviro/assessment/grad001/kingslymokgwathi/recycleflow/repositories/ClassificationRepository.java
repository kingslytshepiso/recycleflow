package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Classification;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, UUID> {

}
