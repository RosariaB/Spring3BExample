package it.smartchain.primoesempio.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Immagine {
    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    /*
    @Lob in JPA (Java Persistence API) e Spring Data JPA viene utilizzata per indicare che un campo di un'entità deve essere trattato come un Large Object (LOB) nel database.
    I LOB sono tipi di dati che possono contenere grandi quantità di dati, come testi lunghi o dati binari.
    Esistono due principali tipi di LOB in JPA: BLOB e CLOB.
    L'annotazione @Lob può essere applicata a campi di tipo String, byte[].
     */
    private String file;
    // L'annotazione @Temporal(TemporalType.DATE) specifica come JPA dovrebbe trattare il tipo Date
    @Temporal(TemporalType.DATE)
    // TemporalType.DATE --> memorizza solo la data (es. 2024-08-22).
    @Column(name = "datainserimento")
    private Date dataInserimento;
    private String tipo;
    @ManyToOne
    @JoinColumn(name = "dato_oid", referencedColumnName = "oid")
    private Dato dato;

    public Immagine(){

    }

    public Immagine(Long id, String nome, String file, Date dataInserimento, String tipo, Dato dato){
        this.id = id;
        this.nome = nome;
        this.file = file;
        this.dataInserimento = dataInserimento;
        this.tipo = tipo;
        this.dato = dato;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public Dato getDato() {
        return dato;
    }

    public String getFile() {
        return file;
    }

    public String getTipo() {
        return tipo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public void setDato(Dato dato) {
        this.dato = dato;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String toString() {
        return "{ " +
                "id = " +id+
                ", nome = " +nome+
                ", file = " +file+
                ", data inserimento = " +dataInserimento+
                ", tipologia = " +tipo+
                ", dato id = " + dato.getId()+
                "}";
    }
}
