package it.smartchain.primoesempio.entities;

import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

@Entity
public class Paziente {
    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cognome;
    @Column(name = "comunediresidenza")
    private String comuneDiResidenza;
    @Column(name = "codicefiscale")
    private String codiceFiscale;
    private String email;
    @Column(name = "numeroditelefono")
    private String numeroDiTelefono;

    @OneToOne(mappedBy = "paziente")
    private CartellaClinica cartellaClinica;

    @OneToOne
    @JoinColumn(name = "user_oid", referencedColumnName = "oid")
    private User user;

    public Paziente(){

    }

    public Paziente(Long id, String nome, String cognome, String comuneDiResidenza, String codiceFiscale, String email, String numeroDiTelefono, User user){
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.comuneDiResidenza = comuneDiResidenza;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.numeroDiTelefono = numeroDiTelefono;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }
/*
    public CartellaClinica getCartellaClinica() {
        return cartellaClinica;
    }
*/
    public User getUser() {
        return user;
    }

    public String getNumeroDiTelefono() {
        return numeroDiTelefono;
    }

    public String getComuneDiResidenza() {
        return comuneDiResidenza;
    }

    public String getEmail() {
        return email;
    }

    public void setNumeroDiTelefono(String numeroDiTelefono) {
        this.numeroDiTelefono = numeroDiTelefono;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }
/*
    public void setCartellaClinica(CartellaClinica cartellaClinica) {
        this.cartellaClinica = cartellaClinica;
    }
*/
    public void setComuneDiResidenza(String comuneDiResidenza) {
        this.comuneDiResidenza = comuneDiResidenza;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{ "+
                "Id = "+ id +
                ", nome = "+nome+
                ", cognome = "+cognome+
                ", comune di residenza  = "+comuneDiResidenza+
                ", codice fiscale  = "+codiceFiscale+
                ", email = "+email+
                ", numero di telefono = " +numeroDiTelefono+
                ", id user  = "+ user.getId()+
                "}";

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
