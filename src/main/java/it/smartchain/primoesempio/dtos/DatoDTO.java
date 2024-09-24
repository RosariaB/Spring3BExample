package it.smartchain.primoesempio.dtos;



import java.io.Serializable;
import java.util.List;

public class DatoDTO implements Serializable {
    private Long id;
    private String reparto;
    private String diagnosi;
    private String terapia;
    private List<ImmagineDTO> immagini;

    public  DatoDTO(){

    }

    public DatoDTO(Long id, String reparto, String diagnosi, String terapia, List<ImmagineDTO> immagini) {
        this.id = id;
        this.reparto = reparto;
        this.diagnosi = diagnosi;
        this.terapia = terapia;

        this.immagini = immagini;
    }

    public List<ImmagineDTO> getImmagini() {
        return immagini;
    }

    public void setImmagini(List<ImmagineDTO> immagini) {
        this.immagini = immagini;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReparto() {
        return reparto;
    }

    public void setReparto(String reparto) {
        this.reparto = reparto;
    }

    public String getDiagnosi() {
        return diagnosi;
    }

    public void setDiagnosi(String diagnosi) {
        this.diagnosi = diagnosi;
    }

    public String getTerapia() {
        return terapia;
    }

    public void setTerapia(String terapia) {
        this.terapia = terapia;
    }

}

