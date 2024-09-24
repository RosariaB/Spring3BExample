package it.smartchain.primoesempio.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Dato {
    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reparto;
    private  String diagnosi;
    private String terapia;

    @ManyToOne
    @JoinColumn(name = "cartellaclinica_oid", referencedColumnName = "oid")
    private CartellaClinica cartellaClinica;

    @OneToMany(mappedBy = "dato")
    private List<Immagine> immagini;

    public Dato(){
   }

    public Dato(Long id, String reparto, String diagnosi, String terapia, CartellaClinica cartellaClinica){
        this.id = id;
        this.reparto = reparto;
        this.diagnosi = diagnosi;
        this.terapia = terapia;
        this.cartellaClinica = cartellaClinica;

    }

    public Long getId() {
        return id;
    }

    public String getTerapia() {
        return terapia;
    }

    public String getDiagnosi() {
        return diagnosi;
    }

    public String getReparto() {
        return reparto;
    }

    public CartellaClinica getCartellaClinica() {
        return cartellaClinica;
    }

    public List<Immagine> getImmagini() {
        return immagini;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTerapia(String terapia) {
        this.terapia = terapia;
    }

    public void setReparto(String reparto) {
        this.reparto = reparto;
    }

    public void setDiagnosi(String diagnosi) {
        this.diagnosi = diagnosi;
    }

    public void setCartellaClinica(CartellaClinica cartellaClinica) {
        this.cartellaClinica = cartellaClinica;
    }

    public void setImmagini(List<Immagine> immagini) {
        this.immagini = immagini;
    }

    @Override
    public String toString() {
        return "{ " +
                "id = " + id +
                ", reparto = " +reparto+
                ", diagnosi= " +diagnosi+
                ", terapia= " +terapia+
                ", cartella clinica Id= " + cartellaClinica.getId()+
                "}";

    }
}
