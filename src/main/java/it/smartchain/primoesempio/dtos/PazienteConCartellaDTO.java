package it.smartchain.primoesempio.dtos;

import java.io.Serializable;

public class PazienteConCartellaDTO implements Serializable {
    private PazienteDTO paziente;
    private Long cartellaId;


    public PazienteConCartellaDTO(PazienteDTO paziente, Long cartellaId) {
        this.paziente = paziente;
        this.cartellaId = cartellaId;
    }

    public PazienteConCartellaDTO(){

    }

    public PazienteDTO getPaziente() {
        return paziente;
    }

    public void setPaziente(PazienteDTO paziente) {
        this.paziente = paziente;
    }

    public Long getCartellaId() {
        return cartellaId;
    }

    public void setCartellaId(Long cartellaId) {
        this.cartellaId = cartellaId;
    }
}
