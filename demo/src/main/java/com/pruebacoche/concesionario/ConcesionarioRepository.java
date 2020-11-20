package com.pruebacoche.concesionario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConcesionarioRepository extends JpaRepository<Concesionario, Long> {
}
