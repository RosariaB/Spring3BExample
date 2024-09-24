package it.smartchain.primoesempio.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cartellaclinica")
public class CartellaClinica {
    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "paziente_oid", referencedColumnName = "oid")
    private Paziente paziente;


    @OneToMany(mappedBy = "cartellaClinica" )
    private List<Dato> dati;

    @ManyToOne
    @JoinColumn(name = "medico_oid", referencedColumnName = "oid")
    private Medico medico;

    public CartellaClinica(){

    }

    public CartellaClinica(Long id, Paziente paziente, Medico medico){
        this.id = id;
        this.paziente = paziente;
        this.medico = medico;
    }

    public Long getId() {
        return id;
    }

    public Paziente getPaziente() {
        return paziente;
    }

    public List<Dato> getDati() {
        return dati;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setDati(List<Dato> dati) {
        this.dati = dati;
    }


    @Override
    public String toString() {
        return "{ " +
                "id = " +id+
                ", paziente_id= " + paziente +
                ", medico id= " + medico +
                "}";
    }
}
