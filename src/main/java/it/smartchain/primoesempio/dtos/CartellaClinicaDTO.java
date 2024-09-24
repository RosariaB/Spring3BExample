package it.smartchain.primoesempio.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(value = { "" })
public class CartellaClinicaDTO implements Serializable {
    private Long id;
    //private String nomeMedico;
    //private String nomePaziente;
    private PazienteDTO pazienteDTO;
    private MedicoDTO medicoDTO;
    private List<DatoDTO> dati;


    public CartellaClinicaDTO() {

    }

    public CartellaClinicaDTO(Long id, PazienteDTO pazienteDTO, MedicoDTO medicoDTO, List<DatoDTO> dati) {
        this.id = id;
        this.pazienteDTO = pazienteDTO;
        this.medicoDTO = medicoDTO;
        this.dati = dati;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DatoDTO> getDati() {
        return dati;
    }

    public void setDati(List<DatoDTO> dati) {
        this.dati = dati;
    }

    public PazienteDTO getPazienteDTO() {
        return pazienteDTO;
    }

    public void setPazienteDTO(PazienteDTO pazienteDTO) {
        this.pazienteDTO = pazienteDTO;
    }

    public MedicoDTO getMedicoDTO() {
        return medicoDTO;
    }

    public void setMedicoDTO(MedicoDTO medicoDTO) {
        this.medicoDTO = medicoDTO;
    }


}