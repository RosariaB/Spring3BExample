package it.smartchain.primoesempio.controllers;


import it.smartchain.primoesempio.dtos.MedicoDTO;
import it.smartchain.primoesempio.services.MedicoService;
import it.smartchain.primoesempio.utilities.AngularErrorResponse;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/medico")
public class MedicoController {
    @Autowired
    MedicoService medicoService;

    @GetMapping("/get-medico-dto")
    public ResponseEntity<Object> dammiMedicoDto(@RequestParam Long id) {
        try {
            return ResponseEntity.status(200).body(medicoService.dammiMedicoDTOBuilder(id));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }

    @PostMapping("/post-create-medico")
    public ResponseEntity<Object> creaMedico(@RequestBody MedicoDTO medicoDTO) {
        try {
            if (medicoDTO != null) {
                return ResponseEntity.ok(medicoService.creaMedico(medicoDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(e.getMessage()));
        } catch (EntityExistsException es) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(es.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }

    @PutMapping("/put-medico")
    public ResponseEntity<Object> modificaMedico(@RequestBody MedicoDTO medicoDTO, @RequestParam Long id, @RequestParam(required = false) Long userid) {
        try {
            if (medicoDTO == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Il medico deve contenere dati"));
            }
            if (id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("L'id non deve essere nullo"));
            }
            return ResponseEntity.ok(medicoService.modificaMedico(medicoDTO,id, userid));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(e.getMessage()));

        } catch (EntityExistsException es) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(es.getMessage()));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }

    @DeleteMapping("/elimina-medico-by-id")
    public ResponseEntity eliminaMedicoPerId(@RequestParam Long id) {
        try {
            if (id != null) {
                medicoService.eliminaMedico(id);
                return ResponseEntity.ok("Il medico è stato eliminato");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Errore nella cancellazione del medico"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(e.getMessage()));
        }
    }
}
