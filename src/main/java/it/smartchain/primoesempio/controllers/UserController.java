package it.smartchain.primoesempio.controllers;

import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
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

import it.smartchain.primoesempio.dtos.LoginDataDTO;
import it.smartchain.primoesempio.dtos.UserDTO;
import it.smartchain.primoesempio.dtos.UtentiDTO;
import it.smartchain.primoesempio.exceptions.NoGroupException;
import it.smartchain.primoesempio.services.PrimoService;
import it.smartchain.primoesempio.services.UserService;
import it.smartchain.primoesempio.utilities.AngularErrorResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PrimoService primoService;

    public UserController(UserService userService, PrimoService primoService) {
        // invece d usare l'@autowired, si può usare il costruttore cin private final per
        // avere un'istanza unica di quell'oggetto per tutta la classe
        this.userService = userService;
        this.primoService = primoService;
    }


    @GetMapping("/get-user-dto")
    public ResponseEntity<Object> dammiUserDto(@RequestParam Long id) {
        try {

            return ResponseEntity.status(200).body(userService.dammiUserDto(id));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }

    }

    @GetMapping("/get-user-password-dto")
    public ResponseEntity<Object> dammiUserPasswordDto(@RequestParam Long id) {
        try {
            return ResponseEntity.status(200).body(userService.dammiPasswordUserDto(id));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse("Errore durante il recupero della password"));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }


    @PostMapping("/crea-user-paziente")
    public ResponseEntity<Object> creaUser(@RequestBody UtentiDTO utentiDTO) {
//TODO togliere requestbody e mettere string
        try {
            if (utentiDTO.getUserDTO() == null) {
                throw new NoSuchElementException("Lo user è vuoto");
            }
            return ResponseEntity.ok(userService.createUser(utentiDTO));

        } catch (EntityExistsException ex) {
            return ResponseEntity.status(409).body(new AngularErrorResponse(ex.getMessage()));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(ex.getMessage()));
        } catch (NoGroupException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }

   /* @PutMapping("/put-username/{id}")
    public ResponseEntity<Object> modificaUsername(@RequestParam String username, @PathVariable Long id) {
        if(username != null && id != null) {
            return ResponseEntity.ok(userService.modificaUsername(id, username));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username e id non validi");
        }
    }*/

    @PutMapping("/put-user/{id}")
    public ResponseEntity<Object> modificaUser(@RequestBody UserDTO userDTO, @PathVariable Long id, @RequestParam(required = false) Long groupid) {
        try {
            if (userDTO == null) {
                throw new IllegalArgumentException("Lo user deve contenere dati");
            }
            if (id == null) {
                throw new IllegalArgumentException("Lo user deve contenere dati");
            }
            //return ResponseEntity.ok(userService.modificaUser(id,user)); // crea lo status e gli dai il body che è userService.modificaUser
            return ResponseEntity.status(HttpStatus.OK).body(userService.modificaUser(id, userDTO, groupid));
        } catch (NullPointerException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AngularErrorResponse(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/get-user-by-password-and-username")
    public ResponseEntity<?> trovaUserByPasswordAndUsername(@RequestParam String username, @RequestParam String password) {
        if (username != null && password != null) {
            return ResponseEntity.ok(userService.trovaUtenti(username, password));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Username e password sono inesistenti"));
        }
    }

    @DeleteMapping("/elimina-user-by-id")
    public ResponseEntity<Object> eliminaUserPerId(@RequestParam Long id) {
        try {
            if (id != null) {
                userService.eliminaUser(id);
                return ResponseEntity.ok("User eliminato");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Errore nella cancellazione dello user"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginDataDTO loginDataDTO) {
        try {
            if (StringUtils.isNotBlank(loginDataDTO.getEmail()) && StringUtils.isNotBlank(loginDataDTO.getPassword())) {
                return ResponseEntity.ok(userService.loginUser(loginDataDTO));
            } else {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse("Email o password non sono presenti"));
            }
        } catch (EntityNotFoundException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AngularErrorResponse(e.getMessage()));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AngularErrorResponse(e.getMessage()));
        }

    }

    @GetMapping("/stringa")
    public String dammiString() {
        return primoService.rispostaComponente() + primoService.rispostaBean();
    }
}