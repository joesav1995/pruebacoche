package com.pruebacoche;

import com.pruebacoche.coche.Coche;
import com.pruebacoche.coche.CocheRepository;
import com.pruebacoche.coche.CocheRequest;
import com.pruebacoche.coche.MatriculaRequest;
import com.pruebacoche.concesionario.Concesionario;
import com.pruebacoche.concesionario.ConcesionarioRepository;
import com.pruebacoche.excepciones.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    //para debugar
    private final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    @Autowired
    private CocheRepository cocheRepository;

    @Autowired
    private ConcesionarioRepository concesionarioRepository;

    public Map<String, List<Coche>> getCoches() {
        Map<String, List<Coche>> cochesPorConcesionario = new HashMap<>();
        concesionarioRepository.findAll().forEach(concesionario -> {
            cochesPorConcesionario.put(concesionario.getDireccion(), concesionario.getCoches());
        });

        return cochesPorConcesionario;
    }

    public void addVariosCoches(String marca, Double coste, Date fechaIngreso, Boolean vendido, Concesionario concesionario) {
        Coche coche = new Coche(marca, coste, fechaIngreso, vendido);
//        Concesionario concesionario = this.getConcesionario(concesionarioId);
//        logger.error("concesionario antes add"+concesionario.toString());
        concesionario.getCoches().add(coche);
        concesionarioRepository.save(concesionario);
//        logger.error("concesionario despues save"+concesionario.toString());
//        return cocheRepository.save(coche);
    }

    /*public Map<String, String> getMarcas() {
        return cocheRepository.findMarca();
    }*/

    public Map<String, String> getMarcasv2() {
        Map<String, String> marcaMatricula = new HashMap<>();
        cocheRepository.findAll().stream().forEach(coche -> {
            marcaMatricula.put(coche.getMarca(), coche.getMatricula());
        });
        return marcaMatricula;
    }

    public Map<String, List<Coche>> getCochesFechaVenta(Long concesionarioId) {
        Map<String, List<Coche>> cochesPorConcesionario = new HashMap<>();
        if (concesionarioId != null) {
            //mostrar de ese concesionario
            String direccion = concesionarioRepository.findDireccionById(concesionarioId);
            cochesPorConcesionario.put(direccion,concesionarioRepository.findByVendidoOrderByFechaVendidoDesc(false,concesionarioId));
            return cochesPorConcesionario;

        } else {
            //mostrar de todos los concesionarios

            concesionarioRepository.findAll().stream().forEach(concesionario -> {
                cochesPorConcesionario.put(concesionario.getDireccion(),concesionarioRepository.findByVendidoOrderByFechaVendidoDesc(false,concesionarioId));
            });

            return cochesPorConcesionario;
        }
    }

    //    /get -> de todos
    // /get/1 -> concesionario sau

    public Map<String, List<Coche>> getCochesFechaIngreso(Long concesionarioId) {

        if (concesionarioId != null) {
            //mostrar de ese concesionario
            Map<String, List<Coche>> cochesPorConcesionario = new HashMap<>();
            String direccion = concesionarioRepository.findDireccionById(concesionarioId);
            logger.error("hola:"+concesionarioRepository.findByVendidoOrderByFechaIngresoDesc(false, concesionarioId));
            cochesPorConcesionario.put(direccion,concesionarioRepository.findByVendidoOrderByFechaIngresoDesc(false,concesionarioId));
            logger.error("despues de añadir:"+cochesPorConcesionario);
            return cochesPorConcesionario;

        } else {
            //mostrar de todos los concesionarios
            Map<String, List<Coche>> cochesPorConcesionario = new HashMap<>();
            logger.error("hola:"+concesionarioRepository.findByVendidoOrderByFechaIngresoDesc(false, concesionarioId));
            concesionarioRepository.findAll().stream().forEach(concesionario -> {
                cochesPorConcesionario.put(concesionario.getDireccion(), concesionarioRepository.findByVendidoOrderByFechaIngresoDesc(false, concesionarioId));
            });
            return cochesPorConcesionario;
        }
    }

    public List<Coche> getCochesVendidos() {
        if (cocheRepository.findByVendidoOrderByFechaVentaDesc(true).size() > 0) {
            return cocheRepository.findByVendidoOrderByFechaVentaDesc(true);
        }
        //cocheRepository.findAll().get(0).getMarca();
        return null;
    }

    public List<Coche> getCocheByMarca(String marcaRequest) {

        if (cocheRepository.findByMarca(marcaRequest).stream().findAny().isPresent()) {
            return cocheRepository.findByMarca(marcaRequest);
        }
        return null;
    }

    public Map<String, Coche> getCocheByMarcaAndConcesionario(Long concesionarioId, String marcaRequest) {
        Map<String, Coche> cocheConcesionario = new HashMap<>();
        if (concesionarioRepository.findCocheByMarcaAndConcesionario(marcaRequest, concesionarioId).isPresent()) {
            String direccion = concesionarioRepository.findDireccionById(concesionarioId);
            cocheConcesionario.put(direccion, concesionarioRepository.findCocheByMarcaAndConcesionario(marcaRequest, concesionarioId).get());
            return cocheConcesionario;
        }

        return null;
    }

    public Coche addCoche(Long concesionarioId, CocheRequest cocheRequest) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Concesionario concesionario = this.getConcesionario(concesionarioId);
        //tiene sentido que puedas añadir un coche sin hacer referencia al concesionario?

        Coche coche = new Coche(cocheRequest.getMarca(), cocheRequest.getCoste(), cocheRequest.getFechaIngreso()
                , cocheRequest.getVendido());
        concesionario.getCoches().add(coche);

        return cocheRepository.save(coche);

    }


    public Coche matricularCoche(MatriculaRequest matriculaRequest, Long concesionarioId) {
        Date fecha = new Date();

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            fecha = formato.parse(formato.format(fecha));


        } catch (Exception e) {

        }
        logger.info(concesionarioRepository.findCochesById(concesionarioId).toString());
        if (concesionarioRepository.findCochesById(concesionarioId).stream().findAny().isPresent()) {

            List<Coche> coches = concesionarioRepository.findCochesById(concesionarioId);

            for (Coche coche : coches) {
                if (coche.getMarca().equals(matriculaRequest.getMarca())) {
                    if (coche.getVendido().equals(false)) {
                        coche.setMatricula(matriculaRequest.getMatricula());
                        coche.setPrecioVenta(matriculaRequest.getPrecioVenta());
                        coche.setVendido(true);
                        coche.setFechaVenta(fecha);
                        return cocheRepository.save(coche);
                    } else {
                        //no se puede comprar coche que ya esta vendido/matriculado

                        throw new BadRequestException("No se puede matricular un coche que ya esta vendido.");
                    }
                }
            }
        }
        return null;
    }

    public void deleteCoche(String marca, Long concesionarioId) {
        logger.error("delete coche coches"+concesionarioRepository.findCochesById(concesionarioId));
        if (concesionarioRepository.findCochesById(concesionarioId).stream().findAny().isPresent()) {

            Concesionario concesionario = this.getConcesionario(concesionarioId);
            List<Coche> cocheAEliminar = concesionario.getCoches().stream().filter(coche -> coche.getMarca().equals(marca)).collect(Collectors.toList());
            if(cocheAEliminar.size()>0){
                Coche coche = cocheAEliminar.get(0);
                if (coche.getVendido().equals(false)) {
                    concesionario.getCoches().remove(coche);
                    concesionarioRepository.save(concesionario);
                } else {
                    //no se puede comprar coche que ya esta vendido/matriculado
                    throw new BadRequestException("No se puede eliminar un coche que ya esta vendido.");
                }
            }

            logger.error("coche "+cocheAEliminar);
            for (Coche coche : cocheAEliminar) {
                if (coche.getMarca().equals(marca)) {

                }
            }
           /*if (cocheRepository.findByMarca(marca).isPresent()) {
                Coche cotxe = cocheRepository.findByMarca(marca);
                if (cotxe.getVendido().equals(false)) {
                    cocheRepository.delete(cotxe);
                } else {
                    //no se puede comprar coche que ya esta vendido/matriculado
                    throw new BadRequestException("No se puede eliminar un coche que ya esta vendido.");
                }
            }*/
        }

    }

    public Double verBeneficios(Long concesionarioId) {

        if (cocheRepository.findByVendido(true,concesionarioId).size() > 0) {
            return cocheRepository.findByVendido(true,concesionarioId).stream().mapToDouble(coche -> (coche.getPrecioVenta() - coche.getCoste())).sum();
        } else {
            //no hay ningun coche vendido/matriculado
            throw new BadRequestException("No hay ningun coche vendido.");
        }
    }

    public Concesionario getConcesionario(Long id) {
        if (concesionarioRepository.findById(id).isPresent()) {
            return concesionarioRepository.findById(id).get();
        } else {
            //return error
            return null;
        }
    }

    public Concesionario addVariosConcesionarios(String direccion) {
        Concesionario concesionario = new Concesionario(direccion);

        return concesionarioRepository.save(concesionario);
    }
}
