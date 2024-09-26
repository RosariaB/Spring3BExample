package it.smartchain.primoesempio.controllers;

import it.smartchain.primoesempio.dtos.ImmagineDTO;
import it.smartchain.primoesempio.services.ImmaginiService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/immagine")
public class ImmagineController {
    @Autowired
    ImmaginiService immaginiService;

    @PostMapping(value = "/crea-immagine", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> creaImmagine(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nome") String nome,
            @RequestParam("dataInserimento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataInserimento,
            @RequestParam("tipo") String tipo,
            @RequestParam("idDato") Long idDato) {

        try {
            // Convert MultipartFile to byte[]
            byte[] bytes = file.getBytes();

            ImmagineDTO immagineDTO = new ImmagineDTO();
            immagineDTO.setNome(nome);
            immagineDTO.setDataInserimento(dataInserimento);
            immagineDTO.setTipo(tipo);
            immagineDTO.setBytes(bytes);
            immagineDTO.setIdDato(idDato);

            return ResponseEntity.ok(immaginiService.creaImmagine(immagineDTO, idDato));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la lettura del file");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }


    @DeleteMapping("/elimina-immagine")
    public ResponseEntity eliminaImmagine(@RequestParam Long datoId) {
        try {
            if (datoId != null) {
                immaginiService.eliminaImmagine(datoId);
                return ResponseEntity.ok("Immagini eliminate");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/get-immagine")
    public ResponseEntity<Object> dammiImmagini(@RequestParam Long immagineId){
        try{
            if (immagineId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id non deve essere nullo");
            }
            return ResponseEntity.ok(immaginiService.dammiImmagine(immagineId));
        }
        catch (NoSuchElementException c){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(c.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-immagine-base64-by-id")
    public ResponseEntity<Object> dammiImmaginiBase64(@RequestParam Long immagineId){
        try{
            if (immagineId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'id non deve essere nullo");
            }
            return ResponseEntity.ok(immaginiService.dammiImmagineBase64(immagineId));
        }
        catch (NoSuchElementException c){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(c.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    @DeleteMapping("/elimina-immagine-by-id")
    public ResponseEntity eliminaImmagineById(@RequestParam Long id) {
        try {
            if (id != null) {
                immaginiService.eliminaImmaginePerId(id);
                return ResponseEntity.ok("Immagini eliminate");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/put-immagine/{id}")
    public ResponseEntity<Object> modificaImmagine(@RequestBody ImmagineDTO immagineDTO, @PathVariable Long id, @RequestParam(required = false) Long datoId) {
        try {
            if (immagineDTO != null) {
                return ResponseEntity.ok(immaginiService.modificaImmagine(immagineDTO, id, datoId));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'immagine inserita non può essere nulla");
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

}
