package org.example.labo3censohyrule.repositories;

import org.example.labo3censohyrule.domain.entities.Specimen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpecimenRepository extends JpaRepository<Specimen, UUID> {
    Specimen findSpecimenBy(UUID id);

}
