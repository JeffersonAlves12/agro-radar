package com.agro.radar.repository;

import com.agro.radar.models.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {
    
    @Query("SELECT DISTINCT g FROM Gateway g "
         + "LEFT JOIN FETCH g.dispositivos d "
         + "LEFT JOIN FETCH d.sensores")
    List<Gateway> findAllWithDispositivosAndSensores();
}