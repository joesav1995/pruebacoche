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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private CocheRepository cocheRepository;
    @Autowired
    private ConcesionarioRepository concesionarioRepository;

    private final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    public Map<String, List<Coche>> getCoches() {
        if (concesionarioRepository.findAll().size() > 0) {
            Map<String, List<Coche>> cochesPorConcesionario = new HashMap<>();
            concesionarioRepository.findAll().forEach(concesionario -> {
                cochesPorConcesionario.put(concesionario.getDireccion(), concesionario.getCoches());
            });
            return cochesPorConcesionario;
        } else {
            throw new BadRequestException("No hay ningun coches.");
        }
    }

    public void addVariosCoches(Coche coche, Concesionario concesionario) {
        concesionario.getCoches().add(coche);
        concesionarioRepository.save(concesionario);
    }

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
            String direccion = concesionarioRepository.findDireccionById(concesionarioId);
            cochesPorConcesionario.put(direccion, concesionarioRepository.findByVendidoOrderByFechaVendidoDesc(false, concesionarioId));
            return cochesPorConcesionario;
        } else {
            concesionarioRepository.findAll().stream().forEach(concesionario -> {
                cochesPorConcesionario.put(concesionario.getDireccion(), concesionarioRepository.findByVendidoOrderByFechaVendidoDesc(false, concesionarioId));
            });
            return cochesPorConcesionario;
        }
    }

    public Map<String, List<Coche>> getCochesFechaIngreso(Long concesionarioId) {
        if (concesionarioId != null) {
            Map<String, List<Coche>> cochesPorConcesionario = new HashMap<>();
            String direccion = concesionarioRepository.findDireccionById(concesionarioId);
            cochesPorConcesionario.put(direccion, concesionarioRepository.findByVendidoOrderByFechaIngresoDesc(false, concesionarioId));
            return cochesPorConcesionario;
        } else {
            Map<String, List<Coche>> cochesPorConcesionario = new HashMap<>();
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
        return null;
    }

    public List<Coche> getCocheByMarca(String marcaRequest) {
        if (cocheRepository.findByMarca(marcaRequest).stream().findAny().isPresent()) {
            return cocheRepository.findByMarca(marcaRequest);
        } else {
            throw new BadRequestException("La marca introducida no existe.");
        }
    }

    public Map<String, Coche> getCocheByMarcaAndConcesionario(Long concesionarioId, String marcaRequest) {
        Map<String, Coche> cocheConcesionario = new HashMap<>();
        if (concesionarioRepository.findCocheByMarcaAndConcesionario(marcaRequest, concesionarioId).isPresent()) {
            String direccion = concesionarioRepository.findDireccionById(concesionarioId);
            cocheConcesionario.put(direccion, concesionarioRepository.findCocheByMarcaAndConcesionario(marcaRequest, concesionarioId).get());
            return cocheConcesionario;
        } else {
            throw new BadRequestException("La marca introducida no existe en el concesionario.");
        }
    }

    public Coche addCoche(Long concesionarioId, CocheRequest cocheRequest) {
        Concesionario concesionario = this.getConcesionario(concesionarioId);
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
                        throw new BadRequestException("No se puede matricular un coche que ya esta vendido.");
                    }
                } else {
                    throw new BadRequestException("El coche introducido no existe en este concesionario.");
                }
            }
        } else {
            throw new BadRequestException("El concesionario introducido no existe.");
        }
        return null;
    }

    public void deleteCoche(String marca, Long concesionarioId) {
        if (concesionarioRepository.findCochesById(concesionarioId).stream().findAny().isPresent()) {
            Concesionario concesionario = this.getConcesionario(concesionarioId);
            List<Coche> cocheAEliminar = concesionario.getCoches().stream().filter(coche -> coche.getMarca().equals(marca)).collect(Collectors.toList());
            if (cocheAEliminar.size() > 0) {
                Coche coche = cocheAEliminar.get(0);
                if (coche.getVendido().equals(false)) {
                    concesionario.getCoches().remove(coche);
                    concesionarioRepository.save(concesionario);
                } else {
                    throw new BadRequestException("No se puede eliminar un coche que ya esta vendido.");
                }
            } else {
                throw new BadRequestException("El coche introducido no existe.");
            }
        } else {
            throw new BadRequestException("El concesionario introducido no existe.");
        }
    }

    public Double verBeneficios(Long concesionarioId) {
        if (cocheRepository.findByVendido(true, concesionarioId).size() > 0) {
            return cocheRepository.findByVendido(true, concesionarioId).stream().mapToDouble(coche -> (coche.getPrecioVenta() - coche.getCoste())).sum();
        } else {
            throw new BadRequestException("No hay ningun coche vendido.");
        }
    }

    public Concesionario getConcesionario(Long id) {
        if (concesionarioRepository.findById(id).isPresent()) {
            return concesionarioRepository.findById(id).get();
        } else {
            throw new BadRequestException("El concesionario introducido no existe.");
        }
    }

    public Concesionario addVariosConcesionarios(String direccion) {
        Concesionario concesionario = new Concesionario(direccion);
        return concesionarioRepository.save(concesionario);
    }
}
