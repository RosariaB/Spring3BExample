package it.smartchain.primoesempio.entities;

import jakarta.persistence.*;

@Entity
public class Amministratore {

    /*
    @Id viene utilizzata per indicare il campo di un'entità che funge da chiave primaria (primary key) nel database.
    Questo campo sarà un identificatore univoco per ogni record nella tabella associata all'entità.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    private Long id;
    // @OneToOne indica una relazione uno a uno con l'entità User.
    // @PrimaryKeyJoinColumn mappa la relazione usando la chiave primaria di questa entità.

    @OneToOne
    @JoinColumn(name = "user_oid", referencedColumnName = "oid")
    private User user;

    public Amministratore(){

    }

    // Costruttore parametrizzato
    public Amministratore(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    // Getters e Setters
    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{ "+
                ", id = "+id+
                ", user id = "+ user.getId()+
                "}";
    }
}

