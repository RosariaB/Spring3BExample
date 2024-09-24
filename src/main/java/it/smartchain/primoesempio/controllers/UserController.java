package it.smartchain.primoesempio.controllers;

import it.smartchain.primoesempio.dtos.LoginDataDTO;
import it.smartchain.primoesempio.dtos.UserDTO;
import it.smartchain.primoesempio.dtos.UtentiDTO;
import it.smartchain.primoesempio.exceptions.NoGroupException;
import it.smartchain.primoesempio.services.PrimoService;
import it.smartchain.primoesempio.services.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }

    }

    @GetMapping("/get-user-password-dto")
    public ResponseEntity<Object> dammiUserPasswordDto(@RequestParam Long id) {
        try {
            return ResponseEntity.status(200).body(userService.dammiPasswordUserDto(id));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
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
            return ResponseEntity.status(409).body(ex.getMessage());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (NoGroupException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @GetMapping("/get-user-by-password-and-username")
    public ResponseEntity<List<UserDTO>> trovaUserByPasswordAndUsername(@RequestParam String username, @RequestParam String password) {
        if (username != null && password != null) {
            return ResponseEntity.ok(userService.trovaUtenti(username, password));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/elimina-user-by-id")
    public ResponseEntity<Object> eliminaUserPerId(@RequestParam Long id) {
        try {
            if (id != null) {
                userService.eliminaUser(id);
                return ResponseEntity.ok("User eliminato");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore nella cancellazione dello user");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginDataDTO loginDataDTO) {
        try {
            if (StringUtils.isNotBlank(loginDataDTO.getEmail()) && StringUtils.isNotBlank(loginDataDTO.getPassword())) {

                return ResponseEntity.ok(userService.loginUser(loginDataDTO));
            } else {
                Map<String, String> mappa = new HashMap<>();
                mappa.put("error", "Errore nella login dello user, password o email sono vuoti");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mappa);
            }
        } catch (EntityNotFoundException e) {
            Map<String, String> mappa = new HashMap<>();
            mappa.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mappa);
        } catch (Exception e) {
            Map<String, String> mappa = new HashMap<>();
            mappa.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mappa);
        }

    }

    @GetMapping("/stringa")
    public String dammiString() {
        return primoService.rispostaComponente() + primoService.rispostaBean();
    }
}