package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Waste;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.repositories.WasteRespository;

@Service
public class WasteService {
    private final WasteRespository wasteRespository;

    public WasteService(WasteRespository wasteRespository) {
        this.wasteRespository = wasteRespository;
    }

    public Page<Waste> getWaste(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return wasteRespository.findAll(pageable);
    }

    public Optional<Waste> getWasteById(UUID id) {
        return wasteRespository.findById(id);
    }

    // public Optional<Waste> getWasteByName(String name) {
    // return wasteRespository.findByName(name);
    // }

    public Waste createWaste(Waste waste) {
        return wasteRespository.save(waste);
    }

    public void deleteWasteById(UUID id) {
        wasteRespository.deleteById(id);
    }

    public void deleteWaste() {
        wasteRespository.deleteAll();
    }
}
