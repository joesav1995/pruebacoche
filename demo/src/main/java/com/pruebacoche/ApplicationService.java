package com.pruebacoche;

import com.pruebacoche.coche.Coche;
import com.pruebacoche.coche.CocheRepository;
import com.pruebacoche.coche.CocheRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationService {

    @Autowired
    private CocheRepository cocheRepository;

    public List<Coche> getCoches() {
        return cocheRepository.findAll();
    }

    public Map<String, String> getMarcas() {
        return cocheRepository.findMarca();
    }

    public Map<String, String> getMarcasv2() {
        Map<String, String> marcaMatricula = new HashMap<>();
        cocheRepository.findAll().stream().forEach(coche -> {
            marcaMatricula.put(coche.getMarca(), coche.getMatricula());
        });
        return marcaMatricula;
    }

    public List<Coche> getCochesFechaVenta() {
        if (cocheRepository.findByVendidoOrderByFechaVentaDesc(false).size() > 0) {
            return cocheRepository.findByVendidoOrderByFechaVentaDesc(false);
        }
        return null;
    }

    public List<Coche> getCochesFechaIngreso() {
        if (cocheRepository.findByVendidoOrderByFechaVentaDesc(false).size() > 0) {
            return cocheRepository.findByVendidoOrderByFechaIngresoDesc(false);
        }
        return null;
    }

    public List<Coche> getCochesVendidos() {
        if (cocheRepository.findByVendidoOrderByFechaVentaDesc(true).size() > 0) {
            return cocheRepository.findByVendidoOrderByFechaVentaDesc(true);
        }
        //cocheRepository.findAll().get(0).getMarca();
        return null;
    }

    public Coche getCocheByDireccion(String idCoche) {
        if (cocheRepository.findById(idCoche).isPresent()) {
            return cocheRepository.findById(idCoche).get();
        }

        return null;
    }

    public Coche addCoche(CocheRequest cocheRequest) {
        Coche coche = new Coche(cocheRequest.getDireccion(), cocheRequest.getMarca(), cocheRequest.getCoste(), cocheRequest.getFechaIngreso(), cocheRequest.getVendido(), cocheRequest.getMatricula());
        return cocheRepository.save(coche);
    }

    public Coche addVariosCoches(String direccion, String marca, Double coste, Date fechaIngreso, Boolean vendido, String matricula) {
        Coche coche = new Coche(direccion, marca, coste, fechaIngreso, vendido, matricula);
        return cocheRepository.save(coche);
    }

    public Coche matricularCoche(CocheRequest cocheRequest) {
        if (cocheRepository.findByDireccion(cocheRequest.getDireccion()).isPresent()){
            Coche cotxe=cocheRepository.findByDireccion(cocheRequest.getDireccion()).get();
            if (cotxe.getVendido().equals(false)) {
                cotxe.setMatricula(cocheRequest.getMatricula());
                cotxe.setPrecioVenta(cocheRequest.getPreciVenta());
                cotxe.setVendido(true);
                return cocheRepository.save(cotxe);
            }else{
                //no se puede comprar coche que ya esta vendido
            }
        }
        return null;
    }
}
