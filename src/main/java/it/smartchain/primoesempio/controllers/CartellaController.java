package it.smartchain.primoesempio.controllers;

import it.smartchain.primoesempio.dtos.CartellaClinicaDTO;
import it.smartchain.primoesempio.dtos.UtentiDTO;
import it.smartchain.primoesempio.exceptions.CartellaClinicaException;
import it.smartchain.primoesempio.services.CartellaService;
import it.smartchain.primoesempio.services.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cartella")
public class CartellaController {
    @Autowired
    CartellaService cartellaService;
    @Autowired
    UserService userService;


    @PostMapping("/crea-cartella")
    public ResponseEntity<Object> creaCartella(@RequestParam Long medicoId, @RequestParam Long pazienteId) {
       try{
           if ( medicoId != null && pazienteId!= null) {
               return ResponseEntity.ok(cartellaService.creaCartella(medicoId,pazienteId));
           } else {
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La cartella inviata non ha un id paziente e/o id medico");
           }
       }catch (NoSuchElementException ex){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

       }catch (EntityExistsException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
       catch (Exception es){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(es.getMessage());
       }

    }

    @PostMapping("/post-lista-cartelle")
    public ResponseEntity<List<CartellaClinicaDTO>> dammiListaCartelleCliniche(@RequestBody UtentiDTO utentiDTO){
        try {
            return ResponseEntity.ok(cartellaService.dammiListaDelleCartelle(utentiDTO));
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




    @GetMapping("/get-by-id")
    public ResponseEntity<Object> dammiCartellaById(@RequestParam Long id) {
        if(id != null) {
            return ResponseEntity.ok(cartellaService.dammiCartella(id));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id non può essere nullo");
        }
    }



    @GetMapping("/get-by-paziente-id")
    public ResponseEntity<Object> dammiCartellaByPazienteId(@RequestParam Long pazienteId) throws CartellaClinicaException {
        if(pazienteId != null) {
            return ResponseEntity.ok(cartellaService.dammiCartellaByPazienteId(pazienteId));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id non può essere nullo");
        }
    }


    @GetMapping("/get-paziente-by-id")
    public ResponseEntity<Object> dammiPazienteIdByCartella(@RequestParam Long id) {
        if(id != null) {
            return ResponseEntity.ok(cartellaService.dammiPaziente(id));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id non può essere nullo");
        }
    }

    @GetMapping("/get-cartella-dto-by-id/{id}")
    public ResponseEntity<Object> dammiCartellaDTOById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(cartellaService.dammiCartellaDTO(id));
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/put-cartella")
    public ResponseEntity<Object> modificaCartella(@RequestBody CartellaClinicaDTO cartellaClinicaDTO, @RequestParam Long id) {
        try {
            if (cartellaClinicaDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Il dato deve contenere dati");
            }
            if (id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id non deve essere nullo");
            }
            return ResponseEntity.ok(cartellaService.modificaCartella(cartellaClinicaDTO,id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (EntityExistsException es) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(es.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
    @DeleteMapping("/elimina-cartella-by-id")
    public ResponseEntity<String> eliminaCartellaPerId(@RequestParam Long medicoId, @RequestParam Long cartellaId) {
        //TODO CAMBIARE IL REQUESTBODY
        try {
            if (!userService.isMedico(medicoId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo un medico può eliminare una cartella clinica");
            }
            if (cartellaId != null) {
                cartellaService.eliminaCartella(medicoId,cartellaId);
                return ResponseEntity.ok("Cartella eliminato, compresi i relativi dati");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore nella cancellazione della cartella");
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (NullPointerException n) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(n.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
