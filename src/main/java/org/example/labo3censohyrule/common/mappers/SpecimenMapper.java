package org.example.labo3censohyrule.common.mappers;

import org.example.labo3censohyrule.domain.dto.request.CreateSpecimenRequest;
import org.example.labo3censohyrule.domain.dto.request.UpdateSpecimenRequest;
import org.example.labo3censohyrule.domain.dto.response.SpecimenResponse;
import org.example.labo3censohyrule.domain.entities.Specimen;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SpecimenMapper {

    public Specimen toEntityCreate(CreateSpecimenRequest request) {
        return Specimen.builder()
                .name(request.getName())
                .region(request.getRegion())
                .dangerLevel(request.getDangerLevel())
                .isFriendly(request.getIsFriendly())
                .build();
    }

    public Specimen toEntityUpdate(UpdateSpecimenRequest request, UUID id) {
        return Specimen.builder()
                .id(id)
                .name(request.getName())
                .region(request.getRegion())
                .dangerLevel(request.getDangerLevel())
                .isFriendly(request.getIsFriendly())
                .build();
    }

    public SpecimenResponse toDto(Specimen specimen) {
        return SpecimenResponse.builder()
                .id(specimen.getId())
                .name(specimen.getName())
                .region(specimen.getRegion())
                .dangerLevel(specimen.getDangerLevel())
                .isFriendly(specimen.getIsFriendly())
                .build();
    }
    public Page<SpecimenResponse> toDtoList(Page<Specimen> specimens) {
        return specimens
                .map(this::toDto);
    }

    // TODO: El estudiante deberá agregar aquí el método para mapear un Page<Specimen> a Page<SpecimenResponse>
    // pista: utilizando .map(this::toDto)
}
