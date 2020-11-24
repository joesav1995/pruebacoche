package com.pruebacoche.coche;

import com.pruebacoche.ApplicationService;
import com.pruebacoche.concesionario.BeneficiosResponse;
import com.pruebacoche.concesionario.Concesionario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coche")
public class CocheController {
    //para debugar
    private final Logger logger = LoggerFactory.getLogger(CocheController.class);

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
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, List<Coche>> findAllCoches() {
        return applicationService.getCoches();
    }

    //añadir coche
    @RequestMapping(value = "/add/{concesionarioId}", method = RequestMethod.POST)
    public @ResponseBody
    Coche addCoche(@PathVariable Long concesionarioId, @RequestBody @Valid CocheRequest cocheRequest) {
        return applicationService.addCoche(concesionarioId, cocheRequest);
    }

    //get 1 coche
    @RequestMapping(value = "/{marca}", method = RequestMethod.GET)
    public @ResponseBody
    List<Coche> getByMarca(@PathVariable(value = "marca") String marca) {
        if (applicationService.getCocheByMarca(marca) != null) {
            return applicationService.getCocheByMarca(marca);
        }
        return null;
    }

    @RequestMapping(value = "/{marca}/{concesionarioId}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Coche> getByMarcaAndConcesionario(@PathVariable(value = "marca") String marca, @PathVariable(value = "concesionarioId") Long concesionarioId) {
//        logger.info("concesionarioid"+concesionarioId);
        if (applicationService.getCocheByMarcaAndConcesionario(concesionarioId, marca) != null) {
            return applicationService.getCocheByMarcaAndConcesionario(concesionarioId, marca);
        }
        return null;
    }


    //añadir varios coches (mockeo inicial)
    @RequestMapping(value = "/llenarCoches", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, List<Coche>> llenarLista() {

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

        //crear varios concesionarios
        Concesionario concesionario1 = applicationService.addVariosConcesionarios("Industria");
        Concesionario concesionario2 = applicationService.addVariosConcesionarios("Sau");
        Concesionario concesionario3 = applicationService.addVariosConcesionarios("Balmes");
        Concesionario concesionario4 = applicationService.addVariosConcesionarios("Aragon");

//        logger.info(concesionario1.toString());
        applicationService.addVariosCoches("Alfa Romeo", 18000.0, fechaIngreso1, false, concesionario1);
        applicationService.addVariosCoches("Audi", 17000.0, fechaIngreso2, false, concesionario2);

        applicationService.addVariosCoches("BMW", 16000.0, fechaIngreso3, false, concesionario1);

        applicationService.addVariosCoches("Cupra", 15000.0, fechaIngreso4, false, concesionario2);

        return applicationService.getCoches();
    }

    @RequestMapping(value = "/mostrarFechaVenta/{concesionarioId}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, List<Coche>> mostrarFechaVenta(@PathVariable(value = "concesionarioId") Long concesionaroId) {
        return applicationService.getCochesFechaVenta(concesionaroId);
    }

    @RequestMapping(value = "/mostrarFechaIngreso/{concesionarioId}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, List<Coche>> mostrarFechaIngreso(@PathVariable(value = "concesionarioId") Long concesionaroId) {
        return applicationService.getCochesFechaIngreso(concesionaroId);
    }

   /* @RequestMapping(value = "/getMarcas", method = RequestMethod.GET)
    public @ResponseBody Map<String,String> getMarcas() {
        return applicationService.getMarcas();
    }

    @RequestMapping(value = "/getMarcasv2", method = RequestMethod.GET)
    public @ResponseBody
    Map<String,String> getMarcasv2() {
        return applicationService.getMarcasv2();
    }*/

    @RequestMapping(value = "/matricularCoche/{concesionarioId}", method = RequestMethod.POST)
    public @ResponseBody
    Coche matricularCoche(@RequestBody @Valid MatriculaRequest matriculaRequest,
                          @PathVariable(value = "concesionarioId") Long concesionarioId) {
        return applicationService.matricularCoche(matriculaRequest, concesionarioId);
    }

    //(@PathVariable(value = "marca") String marca, @PathVariable(value = "concesionarioId") Long concesionarioId)
    @RequestMapping(value = "/eliminarCoche/{marca}/{concesionarioId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("marca") String marca, @PathVariable(value = "concesionarioId") Long concesionarioId) {
        applicationService.deleteCoche(marca,concesionarioId);
    }


    @RequestMapping(value = "/verBeneficios/{concesionarioId}", method = RequestMethod.GET)
    public ResponseEntity<BeneficiosResponse> verBeneficios(@PathVariable(value = "concesionarioId") Long concesionarioId) {
        if (applicationService.verBeneficios(concesionarioId) != 0) {
            return new ResponseEntity<>(new BeneficiosResponse(applicationService.verBeneficios(concesionarioId)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
