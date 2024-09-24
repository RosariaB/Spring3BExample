package it.smartchain.primoesempio.dtos;

import it.smartchain.primoesempio.entities.User;


import java.io.Serializable;

public class MedicoDTO implements Serializable {
    private Long id;
    private String nome;
    private String cognome;
    private UserDTO userDTO;

    public MedicoDTO() {

    }

    public MedicoDTO(Long id, String nome, String cognome, UserDTO userDTO) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.userDTO = userDTO;
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
