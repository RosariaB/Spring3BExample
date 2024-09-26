package it.smartchain.primoesempio.builders;

import it.smartchain.primoesempio.dtos.*;
import it.smartchain.primoesempio.entities.*;

public class Builders {

    public static AmministratoreDTO entityToDTO(Amministratore amministratore) {
        AmministratoreDTO amministratoreDTO = new AmministratoreDTO();
        amministratoreDTO.setId(amministratore.getId());
        if(amministratore.getUser() != null){
            amministratoreDTO.setUser(Builders.entityToDTO(amministratore.getUser()));
        }
        return amministratoreDTO;
    }

    public static Amministratore DTOToEntity(AmministratoreDTO amministratoreDTO) {
        Amministratore amministratore = new Amministratore();
        amministratore.setId(amministratoreDTO.getId());
        if(amministratoreDTO.getUser() != null) {
            amministratore.setUser(Builders.DTOToEntity(amministratoreDTO.getUser()));
        }
        return amministratore;
    }

    public static CartellaClinicaDTO entityToDTO(CartellaClinica cartellaClinica) {
        CartellaClinicaDTO cartellaClinicaDTO = new CartellaClinicaDTO();
        cartellaClinicaDTO.setId(cartellaClinica.getId());
        if(cartellaClinica.getMedico() != null){
            cartellaClinicaDTO.setMedicoDTO(Builders.entityToDTO(cartellaClinica.getMedico()));
        }
        if(cartellaClinica.getPaziente() != null) {
            cartellaClinicaDTO.setPazienteDTO(Builders.entityToDTO(cartellaClinica.getPaziente()));
        }
        if(cartellaClinica.getDati()!= null && !cartellaClinica.getDati().isEmpty()) {
            cartellaClinicaDTO.setDati(cartellaClinica.getDati().stream().map(Builders::entityToDTO).toList());
        }
        return cartellaClinicaDTO;

    }

    public static CartellaClinica DTOToEntity(CartellaClinicaDTO cartellaClinicaDTO) {
        CartellaClinica cartellaClinica = new CartellaClinica();
        cartellaClinica.setId(cartellaClinicaDTO.getId());
        if(cartellaClinicaDTO.getMedicoDTO() != null) {
            cartellaClinica.setMedico(Builders.DTOToEntity(cartellaClinicaDTO.getMedicoDTO()));
        }
        if(cartellaClinicaDTO.getPazienteDTO()!= null) {
            cartellaClinica.setPaziente(Builders.DTOToEntity(cartellaClinicaDTO.getPazienteDTO()));
        }
        return cartellaClinica;

    }

    public static DatoDTO entityToDTO(Dato dato) {
        DatoDTO datoDTO = new DatoDTO();
        datoDTO.setId(dato.getId());
        datoDTO.setReparto(dato.getReparto());
        datoDTO.setDiagnosi(dato.getDiagnosi());
        datoDTO.setTerapia(dato.getTerapia());
        if(dato.getImmagini() != null && !dato.getImmagini().isEmpty()) {
            datoDTO.setImmagini(dato.getImmagini().stream().map(Builders::entityToDTO).toList());
        }
        return datoDTO;
    }

    public static Dato DTOToEntity(DatoDTO datoDTO) {
        Dato dato = new Dato();
        dato.setId(datoDTO.getId());
        dato.setReparto(datoDTO.getReparto());
        dato.setDiagnosi(datoDTO.getDiagnosi());
        dato.setTerapia(datoDTO.getTerapia());
        return dato;
    }

    public static GroupDTO entityToDTO(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setGroupName(group.getGroupName());
        return groupDTO;
    }

    public static Group DTOToEntity(GroupDTO groupDTO) {
        Group group = new Group();
        group.setId(groupDTO.getId());
        group.setGroupName(groupDTO.getGroupName());
        return group;
    }

    public static ImmagineDTO entityToDTO(Immagine immagine) {
        ImmagineDTO immagineDTO = new ImmagineDTO();
        immagineDTO.setId(immagine.getId());
        immagineDTO.setNome(immagine.getNome());
        immagineDTO.setFile(immagine.getFile());
        immagineDTO.setDataInserimento(immagine.getDataInserimento());
        immagineDTO.setTipo(immagine.getTipo());
        immagineDTO.setIdDato(immagine.getDato().getId());
        return immagineDTO;

    }

    public static Immagine DTOToEntity(ImmagineDTO immagineDTO) {
        Immagine immagine = new Immagine();
        immagine.setId(immagineDTO.getId());
        immagine.setNome(immagineDTO.getNome());
        immagine.setFile(immagineDTO.getFile());
        immagine.setDataInserimento(immagineDTO.getDataInserimento());
        immagine.setTipo(immagineDTO.getTipo());

        return immagine;
    }

    public static MedicoDTO entityToDTO(Medico medico) {
        MedicoDTO medicoDTO = new MedicoDTO();
        medicoDTO.setId(medico.getId());
        medicoDTO.setNome(medico.getNome());
        medicoDTO.setCognome(medico.getCognome());
        if(medico.getUser() != null) {
            medicoDTO.setUserDTO(Builders.entityToDTO(medico.getUser()));
        }
        return medicoDTO;
    }

    public static Medico DTOToEntity(MedicoDTO medicoDTO) {
        Medico medico = new Medico();
        medico.setId(medicoDTO.getId());
        medico.setNome(medicoDTO.getNome());
        medico.setCognome(medicoDTO.getCognome());
        if(medicoDTO.getUserDTO() != null) {
            medico.setUser(Builders.DTOToEntity(medicoDTO.getUserDTO()));
        }
        return medico;
    }

    public static PazienteDTO entityToDTO(Paziente paziente) {
        PazienteDTO pazienteDTO = new PazienteDTO();
        pazienteDTO.setId(paziente.getId());
        pazienteDTO.setNome(paziente.getNome());
        pazienteDTO.setCognome(paziente.getCognome());
        pazienteDTO.setEmail(paziente.getEmail());
        pazienteDTO.setCodiceFiscale(paziente.getCodiceFiscale());
        pazienteDTO.setComuneDiResidenza(paziente.getComuneDiResidenza());
        pazienteDTO.setNumeroDiTelefono(paziente.getNumeroDiTelefono());
        if(paziente.getUser() != null) {
            pazienteDTO.setUserDTO(Builders.entityToDTO(paziente.getUser()));
        }
        return pazienteDTO;
    }

    public static Paziente DTOToEntity(PazienteDTO pazienteDTO) {
        Paziente paziente = new Paziente();
        paziente.setId(pazienteDTO.getId());
        paziente.setNome(pazienteDTO.getNome());
        paziente.setCognome(pazienteDTO.getCognome());
        paziente.setEmail(pazienteDTO.getEmail());
        paziente.setCodiceFiscale(pazienteDTO.getCodiceFiscale());
        paziente.setComuneDiResidenza(pazienteDTO.getComuneDiResidenza());
        paziente.setNumeroDiTelefono(pazienteDTO.getNumeroDiTelefono());
        if(pazienteDTO.getUserDTO() != null) {
            paziente.setUser(Builders.DTOToEntity(pazienteDTO.getUserDTO()));
        }
        return paziente;
    }

    public static UserDTO entityToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        if(user.getGruppo() != null) {
            userDTO.setGroupDTO(Builders.entityToDTO(user.getGruppo()));
        }
        return userDTO;
    }

    public static User DTOToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        if(userDTO.getGroupDTO() != null) {
            user.setGruppo(Builders.DTOToEntity(userDTO.getGroupDTO()));
        }
        return user;
    }

}
