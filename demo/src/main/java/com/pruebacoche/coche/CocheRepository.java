package com.pruebacoche.coche;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CocheRepository extends JpaRepository<Coche, Long> {
    Optional<Coche> findByMarca(String marca);

}
