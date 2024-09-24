package it.smartchain.primoesempio.services;

import it.smartchain.primoesempio.builders.Builders;
import it.smartchain.primoesempio.dtos.LoginDataDTO;
import it.smartchain.primoesempio.dtos.UserDTO;
import it.smartchain.primoesempio.dtos.UtentiDTO;
import it.smartchain.primoesempio.entities.Group;
import it.smartchain.primoesempio.entities.Medico;
import it.smartchain.primoesempio.entities.Paziente;
import it.smartchain.primoesempio.entities.User;
import it.smartchain.primoesempio.exceptions.NoGroupException;
import it.smartchain.primoesempio.repositories.GroupRepository;
import it.smartchain.primoesempio.repositories.MedicoRepository;
import it.smartchain.primoesempio.repositories.PazienteRepository;
import it.smartchain.primoesempio.repositories.UserRepository;
import it.smartchain.primoesempio.utilities.Costanti;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    PazienteRepository pazienteRepository;
    @Autowired
    MedicoRepository medicoRepository;


    public UserDTO dammiUserDto(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        //User user = userOptional.orElseThrow(); // controlla che è già presente
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("Lo user non è presente");
        }
        User user = userOptional.get();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public LoginDataDTO dammiPasswordUserDto(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        //User user = userOptional.orElseThrow(); // controlla che è già presente
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("Lo user non è presente");
        }
        User user = userOptional.get();
        LoginDataDTO loginDataDTO = new LoginDataDTO();
        loginDataDTO.setPassword(user.getPassword());

        return loginDataDTO;
    }


    @Transactional
    public UtentiDTO createUser(UtentiDTO utentiDTO) throws NoGroupException, NoSuchFieldException {
        User user = Builders.DTOToEntity(utentiDTO.getUserDTO());
        if (user.getId() != null) {
            Optional<User> userFound = userRepository.findById(user.getId());
            if (userFound.isPresent()) {
                log.error("Lo user è già presente");
                throw new EntityExistsException("Lo user è già presente");
            }

        }

        // controllo se il paziente è null, cioè se ricevo il body o no
        if (utentiDTO.getPazienteDTO() == null) {
            throw new NoSuchElementException("I dati del paziente sono vuoti");
        }
        // controllo i campi del paziente
        if (!utentiDTO.getPazienteDTO().controllaCampiPaziente()) {
            throw new NoSuchFieldException("Alcuni campi sono vuoti");
        }
        //se sono pieni creo lo user
        Group group = groupRepository.findGruppo(Costanti.ID_GRUPPO_PAZIENTE);
        user.setGruppo(group);
        User userSaved = userRepository.save(user);
        //poi creo il paziente assegnando lo user appena ricevuto dal metodo punto save

        Paziente paziente = Builders.DTOToEntity(utentiDTO.getPazienteDTO());
        paziente.setUser(userSaved);
        Paziente pazienteSaved = pazienteRepository.save(paziente);

        utentiDTO.setPazienteDTO(Builders.entityToDTO(pazienteSaved));
        utentiDTO.setUserDTO(Builders.entityToDTO(userSaved));

        //TODO fare la stessa cosa per medico e amministratore
        return utentiDTO;

    }
//    @Transactional
//    public User modificaUsername(Long id, String username) {
//        User user = userRepository.findById(id)
//                /*
//                orElseThrow ritorna lo user se è presente altrimenti ritorna il contenuto della lambda exception.
//                se non metti nulla manda un eccezione di default
//                 */
//                .orElseThrow(() -> new RuntimeException("User non trovato"));
//        user.setUsername(username);
//        return userRepository.save(user);
//    }

    @Transactional
    public it.smartchain.primoesempio.dtos.UserDTO modificaUser(Long id, it.smartchain.primoesempio.dtos.UserDTO userDTO, Long groupid) {
        User userFound = userRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("User da modificare non trovato"));
        User user = Builders.DTOToEntity(userDTO);
        if (StringUtils.isNotBlank(user.getUsername())) {
            userFound.setUsername(user.getUsername());
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            userFound.setPassword(user.getPassword());
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            userFound.setEmail(user.getEmail());
        }
        if (groupid != null) {
            Optional<Group> groupOptional = groupRepository.findById(groupid);
            if (groupOptional.isEmpty()) {
                throw new NullPointerException("Group non trovato");
            }
            userFound.setGruppo(groupOptional.get());
        }
        return Builders.entityToDTO(userRepository.save(userFound));

    }

    public List<UserDTO> trovaUtenti(String username, String password) {
        //return userRepository.findByUsernameAndPassword(username,password); Method query
        //return userRepository.trovaInBaseAUsernameePassword(username,password); JPQL query
        List<User> listauser = userRepository.cercaInBaseAdUsernameePassword(username, password);//Native Query
        return listauser.stream().map(Builders::entityToDTO).toList();

    }

    @Transactional
    public void eliminaUser(Long id) {
        userRepository.deleteById(id);
    }

    public UtentiDTO loginUser(LoginDataDTO loginDataDTO) {
        Optional<User> user = userRepository.findByEmailAndPassword(loginDataDTO.getEmail(), loginDataDTO.getPassword());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("Lo user non è presente");
        }
        UtentiDTO utentiDTO = new UtentiDTO();
        utentiDTO.setUserDTO(Builders.entityToDTO(user.get()));
        Optional<Paziente> paziente = pazienteRepository.findByUser(user.get());
        if (paziente.isPresent()) {
            utentiDTO.setPazienteDTO(Builders.entityToDTO(paziente.get()));
            return utentiDTO;
        }
        Optional<Medico> medico = medicoRepository.findByUser(user.get());
        if (medico.isPresent()) {
            utentiDTO.setMedicoDTO(Builders.entityToDTO(medico.get()));
            return utentiDTO;
        }
        return utentiDTO;

    }

    public boolean isMedico(Long medicoId) {
        Optional<Medico> optionalMedico = medicoRepository.findById(medicoId);
        if (optionalMedico.isEmpty()) {
            throw new NoSuchElementException("Il medico non è presente");
        }

        // Se l'utente appartiene al gruppo medico, restituisce true
        return true;
    }
}



