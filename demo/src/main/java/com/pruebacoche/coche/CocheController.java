package com.pruebacoche.coche;

import com.pruebacoche.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coche")
public class CocheController {

    @Autowired
    private ApplicationService applicationService;

    //get todos coches
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody List<Coche> getCoches() {
        return applicationService.getCoches();
    }

    //get 1 coche
    @RequestMapping(value = "/{idCoche}", method = RequestMethod.GET)
    public @ResponseBody Coche getCOcheById(@PathVariable("idCoche") Long id) {
        if(applicationService.getCocheById(id)!=null){
            return applicationService.getCocheById(id);
        }
        return null;
    }

    //añadir coche
    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody Coche addCoche(@RequestBody CocheRequest cocheRequest) {
        return applicationService.addCoche(cocheRequest);
    }

    //añadir varios coches (mockeo inicial)

}
