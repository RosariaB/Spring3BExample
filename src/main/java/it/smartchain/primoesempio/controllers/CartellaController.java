package it.smartchain.primoesempio.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.smartchain.primoesempio.dtos.CartellaClinicaDTO;
import it.smartchain.primoesempio.dtos.UtentiDTO;
import it.smartchain.primoesempio.exceptions.CartellaClinicaException;
import it.smartchain.primoesempio.services.CartellaService;
import it.smartchain.primoesempio.services.UserService;
import it.smartchain.primoesempio.utilities.AngularErrorResponse;
import jakarta.persistence.EntityExistsException;

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
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("La cartella inviata non ha un id paziente e/o id medico"));
           }
       }catch (NoSuchElementException ex){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(ex.getMessage()));

       }catch (EntityExistsException e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(e.getMessage()));
       }
       catch (Exception es){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(es.getMessage()));
       }

    }

    @PostMapping("/post-lista-cartelle")
    public ResponseEntity<?> dammiListaCartelleCliniche(@RequestBody UtentiDTO utentiDTO){
        try {
            return ResponseEntity.ok(cartellaService.dammiListaDelleCartelle(utentiDTO));
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(ex.getMessage()));

        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }




    @GetMapping("/get-by-id")
    public ResponseEntity<Object> dammiCartellaById(@RequestParam Long id) {
        if(id != null) {
            return ResponseEntity.ok(cartellaService.dammiCartella(id));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("L'id non può essere nullo"));
        }
    }



    @GetMapping("/get-by-paziente-id")
    public ResponseEntity<Object> dammiCartellaByPazienteId(@RequestParam Long pazienteId) throws CartellaClinicaException {
        if(pazienteId != null) {
            return ResponseEntity.ok(cartellaService.dammiCartellaByPazienteId(pazienteId));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("L'id non può essere nullo"));
        }
    }


    @GetMapping("/get-paziente-by-id")
    public ResponseEntity<Object> dammiPazienteIdByCartella(@RequestParam Long id) {
        if(id != null) {
            return ResponseEntity.ok(cartellaService.dammiPaziente(id));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("L'id non può essere nullo"));
        }
    }

    @GetMapping("/get-cartella-dto-by-id/{id}")
    public ResponseEntity<Object> dammiCartellaDTOById(@PathVariable Long id){
        try{
            return ResponseEntity.ok(cartellaService.dammiCartellaDTO(id));
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(ex.getMessage()));
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }

    @PutMapping("/put-cartella")
    public ResponseEntity<Object> modificaCartella(@RequestBody CartellaClinicaDTO cartellaClinicaDTO, @RequestParam Long id) {
        try {
            if (cartellaClinicaDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Il dato deve contenere dati"));
            }
            if (id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("L'id non deve essere nullo"));
            }
            return ResponseEntity.ok(cartellaService.modificaCartella(cartellaClinicaDTO,id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(e.getMessage()));

        } catch (EntityExistsException es) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(es.getMessage()));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }
    @DeleteMapping("/elimina-cartella-by-id")
    public ResponseEntity<?> eliminaCartellaPerId(@RequestParam Long medicoId, @RequestParam Long cartellaId) {
        //TODO CAMBIARE IL REQUESTBODY
        try {
            if (!userService.isMedico(medicoId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AngularErrorResponse("Solo un medico può eliminare una cartella clinica"));
            }
            if (cartellaId != null) {
                cartellaService.eliminaCartella(medicoId,cartellaId);
                return ResponseEntity.ok("Cartella eliminato, compresi i relativi dati");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Errore nella cancellazione della cartella"));
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(e.getMessage()));
        }
        catch (NullPointerException n) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(n.getMessage()));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(e.getMessage()));
        }
    }
}
