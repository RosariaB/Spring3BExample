package it.smartchain.primoesempio.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.smartchain.primoesempio.dtos.AmministratoreDTO;
import it.smartchain.primoesempio.services.AmministratoreService;
import it.smartchain.primoesempio.utilities.AngularErrorResponse;
import jakarta.persistence.EntityExistsException;

@RestController
@RequestMapping("/amministratore")
public class AmministratoreController {
    @Autowired
    AmministratoreService amministratoreService;

    @PostMapping("/crea-amministratore")
    public ResponseEntity<Object> creaAmministratore(@RequestBody AmministratoreDTO amministratoreDTO) {
        try {
            if (amministratoreDTO!= null) {
                return ResponseEntity.ok(amministratoreService.creaAmministratore(amministratoreDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Il body amministratoreDTO é nullo."));
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(e.getMessage()));
        }
        catch (EntityExistsException es) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(es.getMessage()));
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/trova-amministratore")
    public ResponseEntity<Object> trovaAmministratore(@RequestParam Long id){
        try {

            return ResponseEntity.status(200).body(amministratoreService.trovaAmministratore(id));
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(ex.getMessage()));
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }

    @PutMapping("/put-amministratore")
    public ResponseEntity<Object> modificaAmministratore(@RequestBody AmministratoreDTO amministratoreDTO, @RequestParam Long id) {
        try {
            if (amministratoreDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Il dato deve contenere dati"));
            }
            if (id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("L'id non deve essere nullo"));
            }
            return ResponseEntity.ok(amministratoreService.modificaAmministratore(amministratoreDTO,id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(e.getMessage()));

        } catch (EntityExistsException es) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(es.getMessage()));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }
    @DeleteMapping("/elimina-amministratore-by-id")
    public ResponseEntity<?> eliminaAmministratorePerId(@RequestParam Long id) {
        try {
            if (id != null) {
                amministratoreService.eliminaAmministratore(id);
                return ResponseEntity.ok("Amministratore eliminato");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Errore nella cancellazione dell'amministratore"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(e.getMessage()));
        }
    }
}
