package com.pruebacoche.coche;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CocheRepository extends JpaRepository<Coche, String> {
    Optional<Coche> findByMarca(String marca);
    Optional<Coche> findByDireccion(String direccion);

    Optional<Coche> findByMatricula(String matricula);
    List<Coche> findByVendidoTrue();
    List<Coche> findByVendidoOrderByFechaVentaDesc(Boolean vendido);
    List<Coche> findByVendidoOrderByFechaIngresoDesc(Boolean vendido);

    //@Query(value = "select cotxe.marca,cotxe.modelo from Coche cotxe")
    //Map<String,String> findMarca();


}
