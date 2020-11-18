package com.pruebacoche;

import com.pruebacoche.coche.Coche;
import com.pruebacoche.coche.CocheRepository;
import com.pruebacoche.coche.CocheRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private CocheRepository cocheRepository;

    public List<Coche> getCoches() {
        return cocheRepository.findAll();
    }

    public Coche getCocheById(Long idCoche) {
        if(cocheRepository.findById(idCoche).isPresent()) {
            return cocheRepository.findById(idCoche).get();
        }
        return null;
    }

    public Coche addCoche(CocheRequest cocheRequest) {
        Coche coche = new Coche(cocheRequest.getMarca(), cocheRequest.getCoste(),cocheRequest.getModelo(), cocheRequest.getFechaVenta(), cocheRequest.getFechaIngreso(),cocheRequest.getVendido(),cocheRequest.getMatricula(),cocheRequest.getPreciVenta());
        return cocheRepository.save(coche);
    }

    public Coche addVariosCoches(String marca, Double coste, String modelo, Date fechaVenta, Date fechaIngreso, Boolean vendido, String matricula, Double preciVenta) {
        Coche coche = new Coche(marca, coste, modelo, fechaVenta, fechaIngreso, vendido, matricula, preciVenta);
        return cocheRepository.save(coche);
    }
}
