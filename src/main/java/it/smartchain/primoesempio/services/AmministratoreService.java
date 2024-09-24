package it.smartchain.primoesempio.services;

import it.smartchain.primoesempio.builders.Builders;
import it.smartchain.primoesempio.dtos.AmministratoreDTO;
import it.smartchain.primoesempio.entities.*;
import it.smartchain.primoesempio.repositories.AmministratoreRepository;
import it.smartchain.primoesempio.repositories.UserRepository;
import it.smartchain.primoesempio.utilities.Costanti;
import it.smartchain.primoesempio.utilities.Utility;
import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class AmministratoreService {
    private static final Logger log = LoggerFactory.getLogger(AmministratoreService.class);
    @Autowired
    AmministratoreRepository amministratoreRepository;
    @Autowired
    UserRepository userRepository;

    public AmministratoreDTO creaAmministratore(AmministratoreDTO amministratoreDTO) throws NoSuchFieldException {
        Amministratore amministratore = Builders.DTOToEntity(amministratoreDTO);
        if (amministratore.getUser().getId() != null) {
            Optional<User> userOptional = userRepository.findById(amministratore.getUser().getId());
            if (userOptional.isEmpty()) {
                throw new NoSuchElementException("Lo user non esiste");
            }
            User user = userOptional.get();
            if(Utility.gruppoUserEsiste(Builders.entityToDTO(user)) && Objects.equals(user.getGruppo().getId(), Costanti.ID_GRUPPO_AMMINISTRATORE)){
                List<Amministratore> amministratoreOptional = amministratoreRepository.trovaInBaseAlloUserId(amministratore.getUser().getId());
                if (!amministratoreOptional.isEmpty()) {
                    throw new EntityExistsException("Lo user id è già assegnato ad un paziente");
                }
                amministratore.setUser(user);
            }
            else{
                throw new NoSuchElementException("Il paziente non può essere registrato, entità user compromessa");
            }
        }
        return Builders.entityToDTO(amministratoreRepository.save(amministratore));

    }

    public AmministratoreDTO trovaAmministratore(Long id) {
        Optional<Amministratore> amministratoreopt = amministratoreRepository.findById(id);
        if (amministratoreopt.isEmpty()) {
            throw new NoSuchElementException("L'amministratore non è presente");
        }
        return Builders.entityToDTO(amministratoreopt.get());
    }

    public AmministratoreDTO modificaAmministratore(AmministratoreDTO amministratoreDTO, Long id) {
        Amministratore amministratore = Builders.DTOToEntity(amministratoreDTO);
        Optional<Amministratore> amministratoreOptional = amministratoreRepository.findById(id);
        if (amministratoreOptional.isEmpty()) {
            throw new NoSuchElementException("L'amministratore da modificare non è stato trovato");
        }
        Amministratore amministratoreToSave = amministratoreOptional.get();
        Optional<User> userOptional = userRepository.findById(amministratore.getUser().getId());
        if (userOptional.isEmpty()) {
            throw new NoSuchElementException("Lo user non è presente non è presente");
        }
        amministratoreToSave.setUser(userOptional.get());
        return Builders.entityToDTO(amministratoreRepository.save(amministratoreToSave));
    }

    @Transactional
    public void eliminaAmministratore(Long id) {
        amministratoreRepository.deleteById(id);
    }
}
