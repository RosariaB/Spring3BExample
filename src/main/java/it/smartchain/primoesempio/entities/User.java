package it.smartchain.primoesempio.entities;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)// id generato in automatico dal db ed è di tipo identificativo
    private Long id;
    private String username;
    private String password;
    private String email;

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_oid")
    //@Column(name = "group_oid")
    private Group gruppo;

    @OneToOne(mappedBy = "user")
    private Amministratore amministratore;

    @OneToOne(mappedBy = "user")
    private Medico medico;

    @OneToOne(mappedBy = "user")
    private Paziente paziente;

    public User(){

    }

    public User(Long id, String username, String password, String email, Group gruppo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.gruppo = gruppo;

    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Group getGruppo() {
        return gruppo;
    }
/*
    public Medico getMedico() {
        return medico;
    }


    public Paziente getPaziente() {
        return paziente;
    }

    public Amministratore getAmministratore() {
        return amministratore;
    }
 */
    public void setGruppo(Group gruppo) {
        this.gruppo = gruppo;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setAmministratore(Amministratore amministratore) {
        this.amministratore = amministratore;
    }

    @Override
    public String toString() {
        return " { " +
                "id = " + id +
                "; User = " + username +
                "; Password = " + password +
                "; email = " + email +
                "; group id= " + gruppo.toString() +
                "}";

    }


}


