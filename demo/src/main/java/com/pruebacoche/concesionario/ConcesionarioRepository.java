package com.pruebacoche.concesionario;

import com.pruebacoche.coche.Coche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConcesionarioRepository extends JpaRepository<Concesionario, Long> {

    @Query("select concesionario.coches "+
            "from Concesionario as concesionario " +
            "where concesionario.id = :concesionarioId")
    List<Coche> findCochesById(Long concesionarioId);

    @Query("select coche from Concesionario as concesionario " +
            "inner join concesionario.coches coche "+
            "where coche.marca = :marcaRequest and concesionario.id = :concesionarioId")
    Optional<Coche> findCocheByMarcaAndConcesionario(String marcaRequest,Long concesionarioId);

    @Query("select concesionario.direccion "+
            "from Concesionario as concesionario "+
            "where concesionario.id = :concesionarioId")
    String findDireccionById(Long concesionarioId);

    @Query("select coche "+
            "from Concesionario as concesionario " +
            "inner join concesionario.coches coche "+
            "where coche.vendido = :vendido and concesionario.id = :concesionarioId "+
            "order by coche.fechaVenta DESC")
    List<Coche> findByVendidoOrderByFechaVendidoDesc(Boolean vendido, Long concesionarioId);

    @Query("select coche "+
            "from Concesionario as concesionario " +
            "inner join concesionario.coches coche "+
            "where coche.vendido = :vendido and concesionario.id = :concesionarioId "+
            "order by coche.fechaIngreso DESC")
    List<Coche> findByVendidoOrderByFechaIngresoDesc(Boolean vendido,Long concesionarioId);


    void deleteCochesByCochesId(Long cocheId);





}
