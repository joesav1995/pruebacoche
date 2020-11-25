package com.pruebacoche.coche;

import com.pruebacoche.ApplicationService;
import com.pruebacoche.concesionario.BeneficiosResponse;
import com.pruebacoche.concesionario.Concesionario;
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

    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, List<Coche>> findAllCoches() {
        return applicationService.getCoches();
    }

    @RequestMapping(value = "/add/{concesionarioId}", method = RequestMethod.POST)
    public @ResponseBody
    Coche addCoche(@PathVariable Long concesionarioId, @RequestBody @Valid CocheRequest cocheRequest) {
        return applicationService.addCoche(concesionarioId, cocheRequest);
    }

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

        if (applicationService.getCocheByMarcaAndConcesionario(concesionarioId, marca) != null) {
            return applicationService.getCocheByMarcaAndConcesionario(concesionarioId, marca);
        }
        return null;
    }

    @RequestMapping(value = "/llenarCoches", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, List<Coche>> llenarLista() {

        String fechaI1 = "2020-12-12 09:00:00";
        String fechaI2 = "2009-11-01 09:00:00";
        String fechaI3 = "2007-10-06 09:00:00";
        String fechaI4 = "2006-9-11 09:00:00";
        String fechaV1 = "2020-12-20 09:00:00";
        Date fechaIngreso1 = null;
        Date fechaIngreso2 = null;
        Date fechaIngreso3 = null;
        Date fechaIngreso4 = null;
        Date fechaVenta1 = null;

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            fechaIngreso1 = formato.parse(fechaI1);
            fechaIngreso2 = formato.parse(fechaI2);
            fechaIngreso3 = formato.parse(fechaI3);
            fechaIngreso4 = formato.parse(fechaI4);
            fechaVenta1 = formato.parse(fechaV1);
        } catch (Exception e) {

        }

        //mock inicial por la bd en H2 (concretamente en memoria)
        Concesionario concesionario1 = applicationService.addVariosConcesionarios("Industria");
        Concesionario concesionario2 = applicationService.addVariosConcesionarios("Sau");
        Concesionario concesionario3 = applicationService.addVariosConcesionarios("Balmes");
        Concesionario concesionario4 = applicationService.addVariosConcesionarios("Aragon");

        Coche coche1 = new Coche("Alfa Romeo", 18000.0,fechaVenta1, fechaIngreso1, true, "0500czv", 21000.0);
        Coche coche2 = new Coche("Audi", 17000.0, fechaIngreso2, false);
        Coche coche3 = new Coche("BMW", 16000.0, fechaIngreso3, false);
        Coche coche4 = new Coche("Cupra", 15000.0, fechaIngreso4, false);
        applicationService.addVariosCoches(coche1,concesionario1);
        applicationService.addVariosCoches(coche2,concesionario2);
        applicationService.addVariosCoches(coche3,concesionario3);
        applicationService.addVariosCoches(coche4,concesionario4);

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

    @RequestMapping(value = "/matricularCoche/{concesionarioId}", method = RequestMethod.POST)
    public @ResponseBody
    Coche matricularCoche(@RequestBody @Valid MatriculaRequest matriculaRequest,
                          @PathVariable(value = "concesionarioId") Long concesionarioId) {
        return applicationService.matricularCoche(matriculaRequest, concesionarioId);
    }

    @RequestMapping(value = "/eliminarCoche/{marca}/{concesionarioId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("marca") String marca, @PathVariable(value = "concesionarioId") Long concesionarioId) {
        applicationService.deleteCoche(marca, concesionarioId);
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
