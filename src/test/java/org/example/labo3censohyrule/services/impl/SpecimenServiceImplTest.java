package org.example.labo3censohyrule.services.impl;

import org.example.labo3censohyrule.common.mappers.SpecimenMapper;
import org.example.labo3censohyrule.domain.dto.request.CreateSpecimenRequest;
import org.example.labo3censohyrule.domain.dto.response.SpecimenResponse;
import org.example.labo3censohyrule.domain.entities.Specimen;
import org.example.labo3censohyrule.exceptions.ResourceNotFoundException;
import org.example.labo3censohyrule.repositories.SpecimenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecimenServiceImplTest {

    @Mock
    private SpecimenRepository specimenRepository;

    @Mock
    private SpecimenMapper specimenMapper;

    @InjectMocks
    private SpecimenServiceImpl specimenService;

    @Test
    void createSpecimen_ShouldSaveSpecimen() {

        CreateSpecimenRequest request = CreateSpecimenRequest.builder()
                .name("Lynel")
                .region("Hebra")
                .dangerLevel(10)
                .isFriendly(false)
                .build();

        Specimen specimen = Specimen.builder()
                .name("Lynel")
                .region("Hebra")
                .dangerLevel(10)
                .isFriendly(false)
                .build();

        Specimen saved = Specimen.builder()
                .id(UUID.randomUUID())
                .name("Lynel")
                .region("Hebra")
                .dangerLevel(10)
                .isFriendly(false)
                .build();

        SpecimenResponse response = SpecimenResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .region(saved.getRegion())
                .dangerLevel(saved.getDangerLevel())
                .isFriendly(saved.getIsFriendly())
                .build();

        when(specimenMapper.toEntityCreate(request)).thenReturn(specimen);
        when(specimenRepository.save(specimen)).thenReturn(saved);
        when(specimenMapper.toDto(saved)).thenReturn(response);

        SpecimenResponse result = specimenService.createSpecimen(request);

        assertNotNull(result);
        assertEquals("Lynel", result.getName());

        verify(specimenMapper).toEntityCreate(request);
        verify(specimenRepository).save(specimen);
        verify(specimenMapper).toDto(saved);
    }

    @Test
    void getAllSpecimens_ShouldReturnPage() {

        Specimen specimen = Specimen.builder()
                .id(UUID.randomUUID())
                .name("Bokoblin")
                .region("Central")
                .dangerLevel(3)
                .isFriendly(false)
                .build();

        Page<Specimen> entityPage =
                new PageImpl<>(List.of(specimen), PageRequest.of(0,5),1);

        SpecimenResponse dto = SpecimenResponse.builder()
                .id(specimen.getId())
                .name(specimen.getName())
                .region(specimen.getRegion())
                .dangerLevel(specimen.getDangerLevel())
                .isFriendly(specimen.getIsFriendly())
                .build();

        Page<SpecimenResponse> dtoPage =
                new PageImpl<>(List.of(dto), PageRequest.of(0,5),1);

        when(specimenRepository.findAll(any(PageRequest.class))).thenReturn(entityPage);
        when(specimenMapper.toDtoList(entityPage)).thenReturn(dtoPage);

        var response = specimenService.getAllSpecimens(0,5,"name","asc");

        assertEquals(1, response.getTotalElements());

        verify(specimenRepository).findAll(any(PageRequest.class));
        verify(specimenMapper).toDtoList(entityPage);
    }

    @Test
    void getSpecimenById_ShouldReturnSpecimen() {

        UUID id = UUID.randomUUID();

        Specimen specimen = Specimen.builder()
                .id(id)
                .name("Hinox")
                .region("Akkala")
                .dangerLevel(8)
                .isFriendly(false)
                .build();

        SpecimenResponse response = SpecimenResponse.builder()
                .id(id)
                .name("Hinox")
                .region("Akkala")
                .dangerLevel(8)
                .isFriendly(false)
                .build();

        when(specimenRepository.findById(id)).thenReturn(Optional.of(specimen));
        when(specimenMapper.toDto(specimen)).thenReturn(response);

        SpecimenResponse result = specimenService.getSpecimenById(id);

        assertEquals(id, result.getId());

        verify(specimenRepository).findById(id);
        verify(specimenMapper).toDto(specimen);
    }

    @Test
    void getSpecimenById_ShouldThrowException_WhenNotFound() {

        UUID id = UUID.randomUUID();

        when(specimenRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> specimenService.getSpecimenById(id)
        );

        verify(specimenRepository).findById(id);
    }

    @Test
    void deleteSpecimen_ShouldDeleteSpecimen() {

        UUID id = UUID.randomUUID();

        Specimen specimen = Specimen.builder()
                .id(id)
                .name("Guardian")
                .region("Castle")
                .dangerLevel(10)
                .isFriendly(false)
                .build();

        SpecimenResponse response = SpecimenResponse.builder()
                .id(id)
                .name("Guardian")
                .region("Castle")
                .dangerLevel(10)
                .isFriendly(false)
                .build();

        when(specimenRepository.findById(id)).thenReturn(Optional.of(specimen));
        when(specimenMapper.toDto(specimen)).thenReturn(response);

        SpecimenResponse result = specimenService.deleteSpecimen(id);

        assertEquals(id, result.getId());

        verify(specimenRepository).findById(id);
        verify(specimenRepository).deleteById(id);
    }
}
