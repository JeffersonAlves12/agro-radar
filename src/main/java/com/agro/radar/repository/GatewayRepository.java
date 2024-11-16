package com.agro.radar.repository;

import com.agro.radar.models.Gateway;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {

    @Override
    @EntityGraph(attributePaths = {"dispositivos"})
    List<Gateway> findAll();

    @EntityGraph(attributePaths = {"dispositivos"})
    List<Gateway> findByNome(String nome);
}
