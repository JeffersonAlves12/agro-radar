package com.agro.radar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.agro.radar.models.Dispositivo;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {

    @Override
    @EntityGraph(attributePaths = {"sensores", "gateway"})
    List<Dispositivo> findAll();
}
