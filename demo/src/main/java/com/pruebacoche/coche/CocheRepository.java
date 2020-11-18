package com.pruebacoche.coche;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CocheRepository extends JpaRepository<Coche, String> {
    Optional<Coche> findByMarca(String marca);
    List<Coche> findByVendidoOrderByFechaVentaDesc(Boolean vendido);
    List<Coche> findByVendidoOrderByFechaIngresoDesc(Boolean vendido);

    @Query("select cotxe.marca,cotxe.modelo from Coche cotxe")
    Map<String,String> findMarca();


}
