package it.smartchain.primoesempio.controllers;

import it.smartchain.primoesempio.dtos.PazienteDTO;
import it.smartchain.primoesempio.services.PazienteService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/paziente")
public class PazienteController {
    @Autowired
    PazienteService pazienteService;

    @GetMapping("/get-username-by-paziente-id")
    public ResponseEntity<String> dammiUsernameByPazienteId(@RequestParam Long id) {
        if (id != null) {
            return ResponseEntity.ok(pazienteService.dammiUserNamePaziente(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Inserire un id valido");
        }
    }

    @PostMapping("/post-create-paziente")
    public ResponseEntity<Object> creaPaziente(@RequestBody PazienteDTO pazienteDTO) {
        try {
            if (pazienteDTO != null) {
                return ResponseEntity.ok(pazienteService.creaPaziente(pazienteDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EntityExistsException es) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(es.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/get-paziente-dto")
    public ResponseEntity<Object> dammiPazienteDto(@RequestParam Long id) {
        try {
            // return ResponseEntity.status(200).body(pazienteService.dammiPazienteDTO(id));
            return ResponseEntity.status(200).body(pazienteService.dammiPazienteDTOBuilder(id));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/get-paziente-by-nome-and-cognome")
    public ResponseEntity<Object> trovaPaziente(@RequestParam String nome, @RequestParam String cognome) {
        try {
            return ResponseEntity.ok(pazienteService.trovaPaziente(nome, cognome));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }

    @GetMapping("/get-lista-pazienti")
    public ResponseEntity<List<PazienteDTO>> dammiListaPazienti() {
        try {
            return ResponseEntity.ok(pazienteService.dammiListaDiPazienti());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/put-paziente")
    public ResponseEntity<Object> modificaPaziente(@RequestBody PazienteDTO paziente, @RequestParam Long pazienteId, @RequestParam(required = false) Long userid) {
        try {
            if (paziente == null) {
                throw new IllegalArgumentException("Il paziente deve contenere dati");
            }
            if (pazienteId == null) {
                throw new IllegalArgumentException("Id del paziente non deve essere nullo");
            }
            return ResponseEntity.ok(pazienteService.modiicaPaziente(paziente, pazienteId, userid));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping("/elimina-paziente-by-id")
    public ResponseEntity eliminaPazientePerId(@RequestParam Long pazienteId) {
        try {
            if (pazienteId != null) {
                pazienteService.eliminaPaziente(pazienteId);
                return ResponseEntity.ok("Il paziente è stato eliminato, comprese la relativa cartella");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore nella cancellazione del paziente");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
