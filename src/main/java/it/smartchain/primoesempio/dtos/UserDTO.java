package it.smartchain.primoesempio.dtos;
import it.smartchain.primoesempio.entities.Group;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String password;
    private GroupDTO groupDTO;

    public UserDTO() {

    }

    public UserDTO(Long id, String username, String email, String password, GroupDTO groupDTO) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.groupDTO = groupDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GroupDTO getGroupDTO() {
        return groupDTO;
    }

    public void setGroupDTO(GroupDTO group) {
        this.groupDTO = group;
    }
}