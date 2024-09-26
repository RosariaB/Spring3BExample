package it.smartchain.primoesempio.dtos;

import java.util.List;

public class ListaDatiDTO {
    private AnagraficaUtentiDTO anagrafica;
    private List<DatoDTO> dati;


    public ListaDatiDTO(AnagraficaUtentiDTO anagrafica, List<DatoDTO> dati) {
        this.anagrafica = anagrafica;
        this.dati = dati;
    }

    public ListaDatiDTO(){

    }

    public AnagraficaUtentiDTO getAnagrafica() {
        return anagrafica;
    }

    public void setAnagrafica(AnagraficaUtentiDTO anagrafica) {
        this.anagrafica = anagrafica;
    }

    public List<DatoDTO> getDati() {
        return dati;
    }

    public void setDati(List<DatoDTO> dati) {
        this.dati = dati;
    }
}
