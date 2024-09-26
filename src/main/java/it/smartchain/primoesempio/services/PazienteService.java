package it.smartchain.primoesempio.services;

import it.smartchain.primoesempio.builders.Builders;
import it.smartchain.primoesempio.dtos.PazienteConCartellaDTO;
import it.smartchain.primoesempio.dtos.PazienteDTO;
import it.smartchain.primoesempio.entities.Paziente;
import it.smartchain.primoesempio.entities.User;
import it.smartchain.primoesempio.exceptions.CartellaClinicaException;
import it.smartchain.primoesempio.repositories.PazienteRepository;
import it.smartchain.primoesempio.repositories.UserRepository;
import it.smartchain.primoesempio.utilities.Costanti;
import it.smartchain.primoesempio.utilities.Utility;
import jakarta.persistence.EntityExistsException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class PazienteService {
    private static final Logger log = LoggerFactory.getLogger(PazienteService.class);
    @Autowired
    PazienteRepository pazienteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartellaService cartellaService;


    public String dammiUserNamePaziente(Long id) {
        Optional<Paziente> pazienteOpt = pazienteRepository.findById(id);
        return pazienteOpt.orElseThrow().getUser().getUsername();
    }

    public PazienteDTO creaPaziente(PazienteDTO pazienteDTO) throws NoSuchFieldException {
        Paziente paziente = Builders.DTOToEntity(pazienteDTO);
        if (paziente.getUser().getId() != null) {
            Optional<User> userOptional = userRepository.findById(paziente.getUser().getId());
            if (userOptional.isEmpty()) {
                throw new NoSuchElementException("Lo user non esiste");
            }
            User user = userOptional.get();
            if(Utility.gruppoUserEsiste(Builders.entityToDTO(user)) && Objects.equals(user.getGruppo().getId(), Costanti.ID_GRUPPO_PAZIENTE)){
                List<Paziente> pazienteOptional = pazienteRepository.trovaInBaseAlloUserId(paziente.getUser().getId());
                if (!pazienteOptional.isEmpty()) {
                    throw new EntityExistsException("Lo user id è già assegnato ad un paziente");
                }
                paziente.setUser(user);
            }
            else{
                throw new NoSuchElementException("Il paziente non può essere registrato, entità user compromessa");
            }
        }
        Paziente pazienteSaved = pazienteRepository.save(paziente);
        return Builders.entityToDTO(pazienteSaved);
       // return Builders.entityToDTO(pazienteRepository.save(paziente));

    }

    public PazienteDTO dammiPazienteDTO(Long id) {
        Optional<Paziente> pazienteopt = pazienteRepository.findById(id);
        if (pazienteopt.isEmpty()) {
            throw new NoSuchElementException("Il paziente non è presente");
        }
        Paziente paziente = pazienteopt.get();

        PazienteDTO pazienteDTO = new PazienteDTO();

        pazienteDTO.setId(paziente.getId());
        pazienteDTO.setNome(paziente.getNome());
        pazienteDTO.setCognome(paziente.getCognome());
        pazienteDTO.setEmail(paziente.getEmail());
        pazienteDTO.setCodiceFiscale(paziente.getCodiceFiscale());
        //pazienteDTO.setSara(paziente.getUser().getUsername());
        return pazienteDTO;
    }

    public PazienteDTO dammiPazienteDTOBuilder(Long id) {
        Optional<Paziente> pazienteopt = pazienteRepository.findById(id);
        if (pazienteopt.isEmpty()) {
            throw new NoSuchElementException("Il paziente non è presente");
        }
        Paziente paziente = pazienteopt.get();

        return Builders.entityToDTO(paziente);
    }

    public PazienteDTO trovaPaziente(String nome, String cognome) {
        //Paziente paziente = pazienteRepository.findByNomeAndCognome(nome,cognome); // Method query
        //Paziente paziente = pazienteRepository.trovaInBaseANomeeCognome(nome, cognome); //JPQL query
        Paziente paziente = pazienteRepository.cercaPerNomeeCognome(nome, cognome); //Native Query
        if (paziente == null) {
            throw new NoSuchElementException("Il paziente non è presente");
        }
        return Builders.entityToDTO(paziente);

    }

    public List<PazienteDTO> dammiListaDiPazienti(){ //deve ritornare un dto
        List<Paziente> listaPazienti = pazienteRepository.findAll();
        if (listaPazienti.isEmpty()) {
            throw new NoSuchElementException("Non ci sono pazienti");
        }
        List<PazienteDTO> listaPazientiDTO = listaPazienti.stream().map(Builders::entityToDTO).toList();
        return listaPazientiDTO;
    }



    public List<PazienteConCartellaDTO> dammiListaDiPazientiConCartella() {

        // Ottieni la lista dei pazienti dal repository
        List<Paziente> listaPazienti = pazienteRepository.findAll();
        if (listaPazienti.isEmpty()) {
            throw new NoSuchElementException("Non ci sono pazienti");
        }

        // Trasforma la lista dei pazienti in DTO con cartella clinica associata
        List<PazienteConCartellaDTO> listaPazientiConCartella = listaPazienti.stream().map(paziente -> {
            // Converti Paziente in PazienteDTO
            PazienteDTO pazienteDTO = Builders.entityToDTO(paziente);

            // Recupera l'ID della cartella clinica associata

            Long cartellaId = null;
            try {
                cartellaId = cartellaService.dammiCartellaByPazienteId(pazienteDTO.getId());
            } catch (CartellaClinicaException e) {
                log.error("Il paziente non ha la cartela associata");
            }


            // Crea un PazienteConCartellaDTO
            return new PazienteConCartellaDTO(pazienteDTO, cartellaId);
        }).toList();

        return listaPazientiConCartella;
    }


    public PazienteDTO modiicaPaziente(PazienteDTO pazienteDTO, Long pazienteId, Long userid){
        Optional<Paziente> pazienteOptional = pazienteRepository.findById(pazienteId);
        if(pazienteOptional.isEmpty()){
            throw new NoSuchElementException("Il paziente da modificare non è stato trovato");
        }
        Paziente pazienteToSave = pazienteOptional.get();
        if(StringUtils.isNotBlank(pazienteDTO.getNome())){
            pazienteToSave.setNome(pazienteDTO.getNome());
        }
        if(StringUtils.isNotBlank(pazienteDTO.getCognome())){
            pazienteToSave.setCognome(pazienteDTO.getCognome());
        }
        if(StringUtils.isNotBlank(pazienteDTO.getComuneDiResidenza())){
            pazienteToSave.setComuneDiResidenza(pazienteDTO.getComuneDiResidenza());
        }
        if(StringUtils.isNotBlank(pazienteDTO.getCodiceFiscale())){
            pazienteToSave.setCodiceFiscale(pazienteDTO.getCodiceFiscale());
        }
        if(StringUtils.isNotBlank(pazienteDTO.getEmail())){
            pazienteToSave.setEmail(pazienteDTO.getEmail());
        }
        if(StringUtils.isNotBlank(pazienteDTO.getNumeroDiTelefono())){
            pazienteToSave.setNumeroDiTelefono(pazienteDTO.getNumeroDiTelefono());
        }

        if(userid != null){
            Optional<User> userOptional = userRepository.findById(userid);
            if(userOptional.isEmpty()){
                throw new NoSuchElementException("Lo user non è presente");
            }
            pazienteToSave.setUser(userOptional.get());
        }
        Paziente pazienteSaved = pazienteRepository.save(pazienteToSave);
        return Builders.entityToDTO(pazienteSaved);
    }

    @Transactional
    @Modifying
    public void eliminaPaziente(Long pazienteId) throws CartellaClinicaException {
        Optional<Paziente> pa = pazienteRepository.findById(pazienteId);
        if(pa.isEmpty()){
            throw new NoSuchElementException("Il paziente non è presente");
        }
        cartellaService.eliminaCartellaPerPaziente(pazienteId);
        pazienteRepository.delete(pa.get());
    }



}

