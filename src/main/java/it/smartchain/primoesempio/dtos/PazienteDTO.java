package it.smartchain.primoesempio.dtos;

import it.smartchain.primoesempio.entities.User;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class PazienteDTO implements Serializable {
    private Long id;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String email;
    //private String sara /*(username)*/;
    private String comuneDiResidenza;
    private String numeroDiTelefono;
    private UserDTO userDTO;

    public PazienteDTO() {

    }

    public PazienteDTO(Long id, String nome, String cognome, String codiceFiscale, String email, String comuneDiResidenza, String numeroDiTelefono, UserDTO userDTO) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.comuneDiResidenza = comuneDiResidenza;
        this.numeroDiTelefono = numeroDiTelefono;
        this.userDTO = userDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComuneDiResidenza() {
        return comuneDiResidenza;
    }

    public void setComuneDiResidenza(String comuneDiResidenza) {
        this.comuneDiResidenza = comuneDiResidenza;
    }

    public String getNumeroDiTelefono() {
        return numeroDiTelefono;
    }

    public void setNumeroDiTelefono(String numeroDiTelefono) {
        this.numeroDiTelefono = numeroDiTelefono;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public boolean controllaCampiPaziente(){

        if(StringUtils.isBlank(this.getNome())){
            return false;
        }
        if(StringUtils.isBlank(this.getCognome())){
            return false;
        }
        if(StringUtils.isBlank(this.getComuneDiResidenza())){
            return false;
        }
        if(StringUtils.isBlank(this.getCodiceFiscale())){
            return false;
        }
        if(StringUtils.isBlank(this.getEmail())){
            return false;
        }
        if(StringUtils.isBlank(this.getNumeroDiTelefono())){
            return false;
        }
        return true;
    }
}
