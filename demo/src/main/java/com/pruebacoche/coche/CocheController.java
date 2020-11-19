package com.pruebacoche.coche;

import com.pruebacoche.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coche")
public class CocheController {

    /*
        CRUD -> Create Read Update Delete
        //api "rest"
        Create -> POST
        Update -> PUT
        Delete -> DELETE
        Read / mostrar info -> GET
     */

    @Autowired
    private ApplicationService applicationService;
    private Object List;

    //get todos coches
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody
    List<Coche> getCoches() {
        return applicationService.getCoches();
    }

    //get 1 coche
    @RequestMapping(value = "/{idCoche}", method = RequestMethod.GET)
    public @ResponseBody
    Coche getCOcheById(@PathVariable("idCoche") String id) {
        if (applicationService.getCocheByDireccion(id) != null) {
            return applicationService.getCocheByDireccion(id);
        }
        return null;
    }

    //añadir coche
    @RequestMapping(value = "", method = RequestMethod.POST)
    public @ResponseBody
    Coche addCoche(@RequestBody CocheRequest cocheRequest) {
        return applicationService.addCoche(cocheRequest);
    }

    //añadir varios coches (mockeo inicial)
    @RequestMapping(value = "/llenarCoches", method = RequestMethod.GET)
    public @ResponseBody
    List<Coche> llenarLista() {


        String fechaI1 = "2014-12-12 09:00:00";
        String fechaI2 = "2010-02-01 09:00:00";
        String fechaI3 = "2020-07-06 09:00:00";
        String fechaI4 = "2011-11-11 09:00:00";



        Date fechaIngreso1 = null;
        Date fechaIngreso2 = null;
        Date fechaIngreso3 = null;
        Date fechaIngreso4 = null;

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            fechaIngreso1 = formato.parse(fechaI1);
            fechaIngreso2 = formato.parse(fechaI2);
            fechaIngreso3 = formato.parse(fechaI3);
            fechaIngreso4 = formato.parse(fechaI4);

        } catch (Exception e) {

        }


        applicationService.addVariosCoches("alfa-romeo-gasolina-familiar-2009","Alfa Romeo", 18000.0, fechaIngreso1, false, null, 18000.0);
        applicationService.addVariosCoches("audi-a3-gasolina-sport-2015","Audi", 17000.0, fechaIngreso2, false, null, 15000.0);
        applicationService.addVariosCoches("bmw-x2-diesel-coupe-2017","BMW", 16000.0, fechaIngreso3, true, null, 17000.0);
        applicationService.addVariosCoches("cupra-ateca-diesel-2020","Cupra", 15000.0, fechaIngreso4, false, null, 16000.0);

        return applicationService.getCoches();
    }
    @RequestMapping(value = "/mostrarFechaVenta", method = RequestMethod.GET)
    public @ResponseBody List<Coche> mostrarFechaVenta() {
        return applicationService.getCochesFechaVenta();
    }

    @RequestMapping(value = "/mostrarFechaIngreso", method = RequestMethod.GET)
    public @ResponseBody List<Coche> mostrarFechaIngreso() {
        return applicationService.getCochesFechaIngreso();
    }

    @RequestMapping(value = "/getMarcas", method = RequestMethod.GET)
    public @ResponseBody Map<String,String> getMarcas() {
        return applicationService.getMarcas();
    }

    @RequestMapping(value = "/getMarcasv2", method = RequestMethod.GET)
    public @ResponseBody
    Map<String,String> getMarcasv2() {
        return applicationService.getMarcasv2();
    }

}
