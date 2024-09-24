package it.smartchain.primoesempio.services;

import it.smartchain.primoesempio.builders.Builders;
import it.smartchain.primoesempio.dtos.CartellaClinicaDTO;
import it.smartchain.primoesempio.dtos.UtentiDTO;
import it.smartchain.primoesempio.entities.CartellaClinica;
import it.smartchain.primoesempio.entities.Immagine;
import it.smartchain.primoesempio.entities.Medico;
import it.smartchain.primoesempio.entities.Paziente;
import it.smartchain.primoesempio.exceptions.CartellaClinicaException;
import it.smartchain.primoesempio.repositories.CartellaRepository;
import it.smartchain.primoesempio.repositories.ImmagneRepository;
import it.smartchain.primoesempio.repositories.MedicoRepository;
import it.smartchain.primoesempio.repositories.PazienteRepository;
import jakarta.persistence.EntityExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartellaService {
    private static final Logger log = LoggerFactory.getLogger(CartellaService.class);
    @Autowired
    CartellaRepository cartellaRepository;
    @Autowired
    PazienteRepository pazienteRepository;
    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    DatoService datoService;
    @Autowired
    ImmagneRepository immagneRepository;


    public CartellaClinicaDTO creaCartella(CartellaClinicaDTO cartellaClinicaDTO) {
        CartellaClinica cartellaClinica = Builders.DTOToEntity(cartellaClinicaDTO);
        if (cartellaClinica.getMedico() != null && cartellaClinica.getMedico().getId() != null && cartellaClinica.getPaziente() != null && cartellaClinica.getPaziente().getId() != null) {
            Optional<Paziente> pazienteOptional = pazienteRepository.findById(cartellaClinica.getPaziente().getId());
            Optional<Medico> medicoOptional = medicoRepository.findById(cartellaClinica.getMedico().getId());
            if (pazienteOptional.isEmpty() || medicoOptional.isEmpty()) {
                throw new NoSuchElementException("Il paziente e/o il medico non esiste");
            }
            Medico medico = medicoOptional.get();
            Paziente paziente = pazienteOptional.get();
            List<CartellaClinica> cartellaOptional = cartellaRepository.trovaInBaseAlPazienteId(cartellaClinica.getPaziente().getId());
            if (!cartellaOptional.isEmpty()) {
                throw new EntityExistsException("La cartella è stata già assegnata ad un paziente");
            }

            cartellaClinica.setMedico(medico);
            cartellaClinica.setPaziente(paziente);
        }
        return Builders.entityToDTO(cartellaRepository.save(cartellaClinica));
    }


    public Long dammiCartella(Long cartellaid) throws NullPointerException {
        Optional<CartellaClinica> cartellaOpt = cartellaRepository.findById(cartellaid);
        if (cartellaOpt.isPresent()) {
            CartellaClinica cartella = cartellaOpt.get();
            log.info("Cartella trovata");
            return cartella.getId();
        } else {
            log.warn("Cartella non trovato");
            throw new NullPointerException("Cartella non trovata");
        }
    }

    public Long dammiPaziente(Long id) {
        Optional<CartellaClinica> cartellaOpt = cartellaRepository.findById(id);
        return cartellaOpt.orElseThrow().getPaziente().getId();
    }

    public it.smartchain.primoesempio.dtos.CartellaClinicaDTO dammiCartellaDTO(Long id) {
        Optional<CartellaClinica> cartellaClinicaOptional = cartellaRepository.findById(id);
        if (cartellaClinicaOptional.isEmpty()) {
            throw new NoSuchElementException("La cartella non è presente");
        }
        CartellaClinica cartella = cartellaClinicaOptional.get();
        /*CartellaClinicaDTO cartellaDTO = new CartellaClinicaDTO();
        cartellaDTO.setId(cartella.getId());
        cartellaDTO.setNomeMedico(cartella.getMedico().getNome());
        cartellaDTO.setNomePaziente(cartella.getPaziente().getNome());
        return cartellaDTO;*/
        return Builders.entityToDTO(cartella);
    }

    public List<CartellaClinicaDTO> dammiListaDelleCartelle(UtentiDTO utentiDTO) {
        if(utentiDTO.getMedicoDTO() == null) {
            throw new NullPointerException("L'utente che sta effettando l'opreazione non è medico");
        }
        List<CartellaClinica> listacartelle = cartellaRepository.trovaInBaseAlMedicoId(utentiDTO.getMedicoDTO().getId());
        if (listacartelle.isEmpty()) {
            throw new NoSuchElementException("Non ci sono cartelle per quel medico");
        }
        return listacartelle.stream().map(Builders::entityToDTO).toList();
        //toList --> prende uno stream(collectors) e fornisce una lista
        //stream --> ritorna ogni elemento della lista
        //map --> torna un collector, uno stream
    }

    public CartellaClinicaDTO modificaCartella(it.smartchain.primoesempio.dtos.CartellaClinicaDTO cartellaClinicaDTO, Long id) {
        CartellaClinica cartella = Builders.DTOToEntity(cartellaClinicaDTO);
        Optional<CartellaClinica> cartellaClinicaOptional = cartellaRepository.findById(id);
        if (cartellaClinicaOptional.isEmpty()) {
            throw new NoSuchElementException("La cartella da modificare non è stato trovato");
        }
        CartellaClinica cartellaToSave = cartellaClinicaOptional.get();
        Optional<Medico> medicoOptional = medicoRepository.findById(cartella.getMedico().getId());
        if (medicoOptional.isEmpty()) {
            throw new NoSuchElementException("il medico non è presente");
        }
        cartellaToSave.setMedico(medicoOptional.get());

        Optional<Paziente> pazienteOptional = pazienteRepository.findById(cartella.getPaziente().getId());
        if (pazienteOptional.isEmpty()) {
            throw new NoSuchElementException("Il paziente non è presente");
        }
        cartellaToSave.setPaziente(pazienteOptional.get());

        return Builders.entityToDTO(cartellaRepository.save(cartellaToSave));
    }

    @Transactional

    public void eliminaCartella(Long medicoId, Long cartellaId) {
        Optional<Medico> medico = medicoRepository.findById(medicoId);
        if(medico.isEmpty()){
            throw new NoSuchElementException(("Il medico non è presente"));
        }
        Optional<CartellaClinica> cartella = cartellaRepository.findById(cartellaId);
        if (cartella.isEmpty()) {
            throw new NullPointerException(("La cartella non è presente"));
        }
        List<Long> listaIdDati = datoService.dammiIdDato(cartellaId);
        immagneRepository.deleteByDatoIdIn(listaIdDati);
        datoService.eliminaDati(cartellaId);
        cartellaRepository.deleteById(cartellaId);
    }


    public void eliminaCartellaPerPaziente(Long pazienteId) throws CartellaClinicaException {
        List<CartellaClinica> cartelle = cartellaRepository.trovaInBaseAlPazienteId(pazienteId);
        if (cartelle.size() > 1) {
            throw new CartellaClinicaException("Non è possibile avere più di una cartella clinica, contattare l'amministratote");
        }
        if (cartelle.isEmpty()) {
            throw new NullPointerException(("La cartella non è presente"));
        }
        Long idCartella = cartelle.get(0).getId();
        List<Long> listaIdDati = datoService.dammiIdDato(idCartella);
        immagneRepository.deleteByDatoIdIn(listaIdDati);
        datoService.eliminaDati(idCartella);
        cartellaRepository.deleteById(idCartella);
    }

}
