package it.smartchain.primoesempio.dtos;

import it.smartchain.primoesempio.entities.User;

import java.io.Serializable;

public class AmministratoreDTO implements Serializable {
    private Long id;
    private UserDTO userdto;

    public AmministratoreDTO(){

    }

    public AmministratoreDTO(Long id, UserDTO userdto) {
        this.id = id;
        this.userdto = userdto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return userdto;
    }

    public void setUser(UserDTO userdto) {
        this.userdto = userdto;
    }
}
