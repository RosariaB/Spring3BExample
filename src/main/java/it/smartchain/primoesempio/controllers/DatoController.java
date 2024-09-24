package it.smartchain.primoesempio.controllers;

import it.smartchain.primoesempio.dtos.DatoDTO;
import it.smartchain.primoesempio.exceptions.CartellaClinicaException;
import it.smartchain.primoesempio.services.DatoService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/dato")
public class DatoController {
    @Autowired
    DatoService datoService;

    @PostMapping("/crea-dato")
    public ResponseEntity<Object> creaDato(@RequestBody DatoDTO datoDTO, @RequestParam Long cartellaId) {
        try{
            if (datoDTO != null) {
                return ResponseEntity.ok(datoService.creaDato(datoDTO, cartellaId));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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

    @PutMapping("/put-dato")
    public ResponseEntity<Object> modificaDato(@RequestBody  DatoDTO datoDTO, @RequestParam Long id, @RequestParam(required = false) Long cartellaid) {
        try {
            if (datoDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Il dato deve contenere dati");
            }
            if (id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id non deve essere nullo");
            }
            return ResponseEntity.ok(datoService.modificaDato(datoDTO,id, cartellaid));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (EntityExistsException es) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(es.getMessage());

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping("/elimina-dato-by-id")
    public ResponseEntity eliminaDatoPerId(@RequestParam Long datoId) {
        try {
            if (datoId != null) {
                datoService.eliminaDatoPerId(datoId);
                return ResponseEntity.ok("Dato eliminato, comprese le relativi immagini");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore nella cancellazione del dato");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-dati")
    public ResponseEntity<Object> dammiDati(@RequestParam Long pazienteId){
        try{
            if (pazienteId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id non deve essere nullo");
            }
            return ResponseEntity.ok(datoService.dammiDati(pazienteId));
        }
        catch (CartellaClinicaException c){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(c.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/get-dati-by-id")
    public ResponseEntity<Object> dammiDatoById(@RequestParam Long datoId){
        try{
            if (datoId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id non deve essere nullo");
            }
            return ResponseEntity.ok(datoService.dammiDatoById(datoId));
        }
        catch (NoSuchElementException c){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(c.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
