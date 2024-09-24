package it.smartchain.primoesempio.dtos;

import java.io.Serializable;

public class ImmagineResponseDTO implements Serializable {
    private String type;
    private String base64Date;
    private String nomeFile;
    private ImmagineDTO immagine;

    public ImmagineResponseDTO(String type, String base64Date, String nomeFile, ImmagineDTO immagine) {
        this.type = type;
        this.base64Date = base64Date;
        this.nomeFile = nomeFile;
        this.immagine = immagine;
    }

    public ImmagineResponseDTO(){

    }

    public String getNomeFile() {
        return nomeFile;
    }

    public ImmagineDTO getImmagine() {
        return immagine;
    }

    public void setImmagine(ImmagineDTO immagine) {
        this.immagine = immagine;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBase64Date() {
        return base64Date;
    }

    public void setBase64Date(String base64Date) {
        this.base64Date = base64Date;
    }
}
