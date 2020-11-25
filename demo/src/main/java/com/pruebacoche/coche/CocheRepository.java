package com.pruebacoche.coche;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CocheRepository extends JpaRepository<Coche, Long> {
    List<Coche> findByMarca(String marca);

    Optional<Coche> findByMatricula(String matricula);

    List<Coche> findByVendidoTrue();

    List<Coche> findByVendidoOrderByFechaVentaDesc(Boolean vendido);

    List<Coche> findByVendidoOrderByFechaIngresoDesc(Boolean vendido);

    @Query("select coche " +
            "from Concesionario as concesionario " +
            "inner join concesionario.coches coche " +
            "where coche.vendido = :vendido and concesionario.id = :concesionarioId ")
    List<Coche> findByVendido(Boolean vendido, Long concesionarioId);
}
