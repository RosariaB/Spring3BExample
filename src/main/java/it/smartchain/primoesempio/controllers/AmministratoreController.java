package it.smartchain.primoesempio.controllers;

import it.smartchain.primoesempio.dtos.AmministratoreDTO;
import it.smartchain.primoesempio.dtos.CartellaClinicaDTO;
import it.smartchain.primoesempio.entities.Amministratore;
import it.smartchain.primoesempio.services.AmministratoreService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (EntityExistsException es) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(es.getMessage());
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/trova-amministratore")
    public ResponseEntity<Object> trovaAmministratore(@RequestParam Long id){
        try {

            return ResponseEntity.status(200).body(amministratoreService.trovaAmministratore(id));
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/put-amministratore")
    public ResponseEntity<Object> modificaAmministratore(@RequestBody AmministratoreDTO amministratoreDTO, @RequestParam Long id) {
        try {
            if (amministratoreDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Il dato deve contenere dati");
            }
            if (id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id non deve essere nullo");
            }
            return ResponseEntity.ok(amministratoreService.modificaAmministratore(amministratoreDTO,id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (EntityExistsException es) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(es.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
    @DeleteMapping("/elimina-amministratore-by-id")
    public ResponseEntity eliminaAmministratorePerId(@RequestParam Long id) {
        try {
            if (id != null) {
                amministratoreService.eliminaAmministratore(id);
                return ResponseEntity.ok("Amministratore eliminato");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore nella cancellazione dell'amministratore");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
