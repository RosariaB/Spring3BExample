package it.smartchain.primoesempio.services;

import it.smartchain.primoesempio.builders.Builders;
import it.smartchain.primoesempio.entities.Medico;
import it.smartchain.primoesempio.entities.User;
import it.smartchain.primoesempio.repositories.MedicoRepository;
import it.smartchain.primoesempio.repositories.UserRepository;
import it.smartchain.primoesempio.utilities.Costanti;
import it.smartchain.primoesempio.utilities.Utility;
import jakarta.persistence.EntityExistsException;
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
public class MedicoService {
    private static final Logger log = LoggerFactory.getLogger(MedicoService.class);
    @Autowired
    MedicoRepository medicoRepository;
    @Autowired
    UserRepository userRepository;


    public it.smartchain.primoesempio.dtos.MedicoDTO creaMedico(it.smartchain.primoesempio.dtos.MedicoDTO medicoDTO){
        Medico medico = Builders.DTOToEntity(medicoDTO);
        if(medico.getUser().getId() != null){
            Optional<User> userOptional = userRepository.findById(medico.getUser().getId());
            if(userOptional.isEmpty()){
                throw new NoSuchElementException("Lo user non è presente");
            }
            User user = userOptional.get();
            if(Utility.gruppoUserEsiste(Builders.entityToDTO(user)) && user.getGruppo().getId().equals(Costanti.ID_GRUPPO_MEDICO)){
                List<Medico> pazienteOptional = medicoRepository.trovaInBaseAlloUserId(medico.getUser().getId());
                if (!pazienteOptional.isEmpty()) {
                    throw new EntityExistsException("Lo user id è già assegnato");
                }
                medico.setUser(user);
            }
        }
        return Builders.entityToDTO(medicoRepository.save(medico));
    }



    public it.smartchain.primoesempio.dtos.MedicoDTO dammiMedicoDTOBuilder(Long id){
        Optional<Medico> medicoopt = medicoRepository.findById(id);
        if(medicoopt.isEmpty()){
            throw new NoSuchElementException("Il medico non è presente");
        }
        Medico medico = medicoopt.get();
        return Builders.entityToDTO(medico);
    }

    public it.smartchain.primoesempio.dtos.MedicoDTO modificaMedico(it.smartchain.primoesempio.dtos.MedicoDTO medicoDTO, Long id, Long userid){
        Optional<Medico> medicoOptional = medicoRepository.findById(id);
        if(medicoOptional.isEmpty()){
            throw new NoSuchElementException("Il medico da modificare non è stato trovato");
        }
        Medico medicoToSave = medicoOptional.get();
        Medico medico = Builders.DTOToEntity(medicoDTO);
        if(StringUtils.isNotBlank(medico.getNome())){
            medicoToSave.setNome(medico.getNome());
        }
        if(StringUtils.isNotBlank(medico.getCognome())){
            medicoToSave.setCognome(medico.getCognome());
        }

        if(userid != null){
            Optional<User> userOptional = userRepository.findById(userid);
            if(userOptional.isEmpty()){
                throw new NoSuchElementException("Lo user non è presente");
            }
            medicoToSave.setUser(userOptional.get());
        }
        return Builders.entityToDTO(medicoRepository.save(medicoToSave));
    }

    @Transactional
    public void eliminaMedico(Long id) {
        medicoRepository.deleteById(id);
    }
}
