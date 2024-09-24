package it.smartchain.primoesempio.dtos;

import java.io.Serializable;

public class AnagraficaUtentiDTO implements Serializable {
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String nomeMedico;

    public AnagraficaUtentiDTO(String nome, String cognome, String codiceFiscale, String nomeMedico) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.nomeMedico = nomeMedico;
    }
    public AnagraficaUtentiDTO(){

    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }
}
