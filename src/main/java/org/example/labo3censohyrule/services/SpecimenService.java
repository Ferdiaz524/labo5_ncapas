package org.example.labo3censohyrule.services;

import org.example.labo3censohyrule.domain.dto.request.CreateSpecimenRequest;
import org.example.labo3censohyrule.domain.dto.request.UpdateSpecimenRequest;
import org.example.labo3censohyrule.domain.dto.response.PageableResponse;
import org.example.labo3censohyrule.domain.dto.response.SpecimenResponse;

import java.util.List;
import java.util.UUID;

public interface SpecimenService {
    SpecimenResponse createSpecimen(CreateSpecimenRequest request);
    PageableResponse<SpecimenResponse> getAllSpecimens(int page, int size, String sortBy, String sortOrder);
    SpecimenResponse getSpecimenById(UUID id);
    SpecimenResponse updateSpecimen(UUID id, UpdateSpecimenRequest request);
    public SpecimenResponse deleteSpecimen(UUID id);

}