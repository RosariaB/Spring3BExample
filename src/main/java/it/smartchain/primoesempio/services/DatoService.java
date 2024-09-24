package it.smartchain.primoesempio.services;

import it.smartchain.primoesempio.builders.Builders;
import it.smartchain.primoesempio.dtos.AnagraficaUtentiDTO;
import it.smartchain.primoesempio.dtos.DatoDTO;
import it.smartchain.primoesempio.dtos.ListaDatiDTO;
import it.smartchain.primoesempio.entities.*;
import it.smartchain.primoesempio.exceptions.CartellaClinicaException;
import it.smartchain.primoesempio.repositories.CartellaRepository;
import it.smartchain.primoesempio.repositories.DatoRepository;
import it.smartchain.primoesempio.repositories.ImmagneRepository;
import it.smartchain.primoesempio.repositories.PazienteRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DatoService {
    private static final Logger log = LoggerFactory.getLogger(DatoService.class);
    @Autowired
    DatoRepository datoRepository;
    @Autowired
    CartellaRepository cartellaRepository;
    @Autowired
    PazienteRepository pazienteRepository;
    @Autowired
    ImmagneRepository immagneRepository;


    public DatoDTO creaDato(DatoDTO datoDTO, Long cartellaId) {
        Dato dato = Builders.DTOToEntity(datoDTO);
        if (cartellaId != null) {
            Optional<CartellaClinica> cartellaOptional = cartellaRepository.findById(cartellaId);
            if (cartellaOptional.isEmpty()) {
                throw new NoSuchElementException("La cartella non esiste");
            }
            CartellaClinica cartellaClinica = cartellaOptional.get();
            dato.setCartellaClinica(cartellaClinica);
        }
        return Builders.entityToDTO(datoRepository.save(dato));
    }

    public DatoDTO modificaDato(DatoDTO datoDTO, Long id, Long cartellaid) {
        Dato dato = Builders.DTOToEntity(datoDTO);
        Optional<Dato> datoOptional = datoRepository.findById(id);
        if (datoOptional.isEmpty()) {
            throw new NoSuchElementException("Il dato da modificare non è stato trovato");
        }
        Dato datoToSave = datoOptional.get();
        if (StringUtils.isNotBlank(dato.getTerapia())) {
            datoToSave.setTerapia(dato.getTerapia());
        }
        if (StringUtils.isNotBlank(dato.getDiagnosi())) {
            datoToSave.setDiagnosi(dato.getDiagnosi());
        }
        if (StringUtils.isNotBlank(dato.getReparto())) {
            datoToSave.setReparto(dato.getReparto());
        }

        if (cartellaid != null) {
            Optional<CartellaClinica> cartellaClinicaOptional = cartellaRepository.findById(cartellaid);
            if (cartellaClinicaOptional.isEmpty()) {
                throw new NoSuchElementException("La cartella clinica non è presente");
            }
            datoToSave.setCartellaClinica(cartellaClinicaOptional.get());
        }
        return Builders.entityToDTO(datoRepository.save(datoToSave));
    }

    @Transactional
    public void eliminaDatoPerId(Long datoId) {
        immagneRepository.eliminaInBaseAlIdDelDato(datoId);
        datoRepository.deleteById(datoId);
    }


    public ListaDatiDTO dammiDati(Long pazienteId) throws CartellaClinicaException {
        List<CartellaClinica> cartelle = cartellaRepository.trovaInBaseAlPazienteId(pazienteId);
        if (cartelle.size() > 1) {
            throw new CartellaClinicaException("Non è possibile avere più di una cartella clinica, contattare l'amministratote");
        }
        if (cartelle.isEmpty()) {
            throw new CartellaClinicaException("La cartella è vuota");
        }
        CartellaClinica cartellaClinica = cartelle.get(0);
        AnagraficaUtentiDTO anagraficaUtentiDTO = new AnagraficaUtentiDTO(cartellaClinica.getPaziente().getNome(), cartellaClinica.getPaziente().getCognome(), cartellaClinica.getPaziente().getCodiceFiscale(), cartellaClinica.getMedico().getNome());
        List<Dato> dati = datoRepository.trovaInBaseAlIdDellaCartella(cartellaClinica.getId());
        ListaDatiDTO listaDatiDTO = new ListaDatiDTO(anagraficaUtentiDTO, dati.stream().map(Builders::entityToDTO).toList());
        return listaDatiDTO;
    }


    public DatoDTO dammiDatoById(Long datoId) throws CartellaClinicaException {
       Optional<Dato> optionalDato = datoRepository.findById(datoId);
       if(optionalDato.isEmpty()){
            throw new NoSuchElementException("Dato non trovato");
        }
       Dato dato = optionalDato.get();
       return Builders.entityToDTO(dato);
    }

    public List<Long> dammiIdDato(Long cartellaId) {
        if(cartellaId == null){
            throw new NoSuchElementException("L'id non può essere vuoto");
        }
        List<Long> IdDati = datoRepository.findDatoIdsByCartellaClinicaId(cartellaId);
        return IdDati;
    }

    public void eliminaDati(Long cartellaId){

        //TODO bisogna verificare che solo l'utente autenticato con il ruolo di medico può fare questa cosa.
        if(cartellaId == null){
            throw new NoSuchElementException("L' id della cartella clinica non può nullo");
        }
        datoRepository.deleteByCartellaClinicaId(cartellaId);
    }

}


