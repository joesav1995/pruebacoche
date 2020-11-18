package com.pruebacoche;

import com.pruebacoche.coche.Coche;
import com.pruebacoche.coche.CocheRepository;
import com.pruebacoche.coche.CocheRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Coche coche = new Coche(cocheRequest.getMarca(),cocheRequest.getModelo(),cocheRequest.getCv());
        return cocheRepository.save(coche);
    }
}
