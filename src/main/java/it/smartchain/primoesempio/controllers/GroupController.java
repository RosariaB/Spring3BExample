package it.smartchain.primoesempio.controllers;

import it.smartchain.primoesempio.dtos.GroupDTO;
import it.smartchain.primoesempio.services.GroupService;
import it.smartchain.primoesempio.utilities.AngularErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    GroupService groupService;

    @PostMapping("/post-crea-gruppo")
    public ResponseEntity<Object> creaGruppo(@RequestBody GroupDTO groupDTO) {
        try {
            if (groupDTO != null) {
                return ResponseEntity.ok(groupService.creaGruppo(groupDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Il gruppo non può essere nullo."));
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(e.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }

    @PutMapping("/put-gruppo/{id}")
    public ResponseEntity<Object> modificaGruppo(@RequestBody GroupDTO groupDTO, @PathVariable Long id) {
        try {
            if (groupDTO != null) {
                return ResponseEntity.ok(groupService.modificaGruppo(groupDTO, id));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Il gruppo non può essere nullo"));
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(e.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/get-group")
    public ResponseEntity<?> trovaGroup() {
        try{
            return ResponseEntity.ok(groupService.trovaGruppo());
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(e.getMessage()));
        }

    }

}
