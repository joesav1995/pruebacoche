package com.pruebacoche.coche;

import com.pruebacoche.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/coche")
public class CocheController {

    @Autowired
    private ApplicationService applicationService;
    private Object List;

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
    @RequestMapping(value = "/llenarCoches", method = RequestMethod.GET)
    public void  llenarLista() {


        String fechaV1 ="09:00 12/12/2014";
        String fechaV2 ="09:00 01/02/2010";
        String fechaV3 ="09:00 06/07/2020";
        String fechaV4 ="09:00 11/11/2011";

        String fechaI1 ="09:00 02/03/2004";
        String fechaI2 ="09:00 01/02/2003";
        String fechaI3 ="09:00 06/07/2008";
        String fechaI4 ="09:00 10/10/2010";



        Date fechaVenta1=null;
        Date fechaVenta2=null;
        Date fechaVenta3=null;
        Date fechaVenta4=null;

        Date fechaIngreso1=null;
        Date fechaIngreso2=null;
        Date fechaIngreso3=null;
        Date fechaIngreso4=null;


        SimpleDateFormat objSDF = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");

        try {
            fechaVenta1 = objSDF.parse(fechaV1);
            fechaVenta2 = objSDF.parse(fechaV2);
            fechaVenta3 = objSDF.parse(fechaV3);
            fechaVenta4 = objSDF.parse(fechaV4);

            fechaIngreso1 = objSDF.parse(fechaI1);
            fechaIngreso2 = objSDF.parse(fechaI2);
            fechaIngreso3 = objSDF.parse(fechaI3);
            fechaIngreso4 = objSDF.parse(fechaI4);
        } catch (ParseException e)
        {

        }


        applicationService.addVariosCoches("Alfa Romeo",18000.0,"Giulia",fechaVenta1,fechaIngreso1,false,null,18000.0);
        applicationService.addVariosCoches("Audi",17000.0,"A3",fechaVenta2,fechaIngreso2,false,null,15000.0);
        applicationService.addVariosCoches("BMW",16000.0,"X2",fechaVenta3,fechaIngreso3,false,null,17000.0);
        applicationService.addVariosCoches("Cupra",15000.0,"Ateca",fechaVenta4,fechaIngreso4,false,null,16000.0);

    }

}
