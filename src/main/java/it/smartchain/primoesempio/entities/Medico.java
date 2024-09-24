package it.smartchain.primoesempio.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Medico {
    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cognome;
     @OneToOne
     @JoinColumn(name = "user_oid", referencedColumnName = "oid")
    private User user;

     @OneToMany(mappedBy = "medico")
    private List<CartellaClinica> cartelleCliniche;
    public Medico(){

    }

    public Medico(Long id, String nome, String cognome, User user){
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
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

    public User getUser() {
        return user;
    }

    public List<CartellaClinica> getCartelleCliniche() {
        return cartelleCliniche;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCartelleCliniche(List<CartellaClinica> cartelleCliniche) {
        this.cartelleCliniche = cartelleCliniche;
    }

    @Override
    public String toString() {
        return "{ "+
                "Id = "+ id +
                ", nome = " +nome+
                ", cognome = " +cognome+
                ", user id = " + user.getId()+
                "}";
    }
}
