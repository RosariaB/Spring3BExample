package it.smartchain.primoesempio.dtos;

import it.smartchain.primoesempio.entities.Dato;

import java.io.Serializable;
import java.util.Date;

public class ImmagineDTO implements Serializable {
    private Long id;
    private String nome;
    private String file;
    private Date dataInserimento;
    private String tipo;
    private Long idDato;
    private byte[] bytes;


    public Long getIdDato() {
        return idDato;
    }

    public void setIdDato(Long idDato) {
        this.idDato = idDato;
    }

    public ImmagineDTO() {

    }

    public ImmagineDTO(Long id, String nome, String file, Date dataInserimento, String tipo, Long idDato, byte[] bytes) {
        this.id = id;
        this.nome = nome;
        this.file = file;
        this.dataInserimento = dataInserimento;
        this.tipo = tipo;
        this.idDato = idDato;
        this.bytes = bytes;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}


