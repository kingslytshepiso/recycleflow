package com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.exceptions.DuplicateRecordException;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.exceptions.RecordNotFoundException;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.models.Waste;
import com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.repositories.WasteRespository;

import static com.enviro.assessment.grad001.kingslymokgwathi.recycleflow.helpers.IsUUID.isUUID;

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

    public Waste getWasteByIdOrName(String idOrName) {
        if (isUUID(idOrName)) {
            return wasteRespository.findById(UUID.fromString(idOrName))
                    .orElseThrow(() -> new RecordNotFoundException("Waste not found with UUID: " + idOrName));
        } else {
            return wasteRespository.findByName(idOrName)
                    .orElseThrow(() -> new RecordNotFoundException("Waste not found with Name: " + idOrName));
        }
    }

    public Waste createWaste(Waste waste) {
        Boolean exists = wasteRespository.existsByName(waste.getName());
        if (exists) {
            throw new DuplicateRecordException("Waste with name " + waste.getName() + " already exists");
        }
        return wasteRespository.save(waste);
    }

    public void updateWaste(UUID id, Waste waste) {
        Waste wasteToUpdate = wasteRespository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Waste with id " + id + " not found"));
        wasteToUpdate.setName(waste.getName());
        wasteToUpdate.setDescription(waste.getDescription());
        wasteRespository.save(wasteToUpdate);
    }

    public void deleteWasteById(UUID id) {
        wasteRespository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Waste with id " + id + " not found"));
        wasteRespository.deleteById(id);
    }

    public void deleteWaste() {
        wasteRespository.deleteAll();
    }
}
