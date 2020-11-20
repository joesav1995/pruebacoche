package com.pruebacoche;

import com.pruebacoche.coche.Coche;
import com.pruebacoche.coche.CocheRepository;
import com.pruebacoche.coche.CocheRequest;
import com.pruebacoche.coche.MatriculaRequest;
import com.pruebacoche.excepciones.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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

    public Coche getCocheById(String marca) {
        if (cocheRepository.findByMarca(marca).isPresent()) {
            return cocheRepository.findByMarca(marca).get();
        }

        return null;
    }

    public Coche addCoche(CocheRequest cocheRequest) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        Coche coche = new Coche(cocheRequest.getDireccion(), cocheRequest.getMarca(), cocheRequest.getCoste(),cocheRequest.getFechaIngreso()
                , cocheRequest.getVendido());

        return cocheRepository.save(coche);

    }

    public Coche addVariosCoches(String direccion, String marca, Double coste, Date fechaIngreso, Boolean vendido) {
        Coche coche = new Coche(direccion, marca, coste, fechaIngreso, vendido);
        return cocheRepository.save(coche);
    }
    public Coche addVariosCoches(Coche coche) {
        return cocheRepository.save(coche);
    }
    public Coche matricularCoche(MatriculaRequest matriculaRequest) {
        Date fecha = new Date();

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            fecha = formato.parse(formato.format(fecha));


        } catch (Exception e) {

        }
        if (cocheRepository.findByDireccion(matriculaRequest.getDireccion()).isPresent() && cocheRepository.findByMarca(matriculaRequest.getMarca()).isPresent()){
            Coche cotxe=cocheRepository.findByMarca(matriculaRequest.getMarca()).get();
            if (cotxe.getVendido().equals(false)) {
                cotxe.setMatricula(matriculaRequest.getMatricula());
                cotxe.setPrecioVenta(matriculaRequest.getPrecioVenta());
                cotxe.setVendido(true);
                cotxe.setFechaVenta(fecha);
                return cocheRepository.save(cotxe);
            }else{
                //no se puede comprar coche que ya esta vendido/matriculado

                throw new BadRequestException("No se puede matricular un coche que ya esta vendido.");
            }
        }
        return null;
    }
    public void deleteCoche(String marca) {
        if (cocheRepository.findByMarca(marca).isPresent()){
            Coche cotxe=cocheRepository.findByMarca(marca).get();
            if (cotxe.getVendido().equals(false)) {
                cocheRepository.delete(cotxe);
            }else{
                //no se puede comprar coche que ya esta vendido/matriculado
                throw new BadRequestException("No se puede eliminar un coche que ya esta vendido.");
            }
        }
    }

    public Double verBeneficios(){

        if (cocheRepository.findByVendidoTrue().size() > 0) {
            return cocheRepository.findByVendidoTrue().stream().mapToDouble(coche -> (coche.getPrecioVenta() - coche.getCoste())).sum();
        }else{
            //no hay ningun coche vendido/matriculado
            throw new BadRequestException("No hay ningun coche vendido.");
        }
    }
}
