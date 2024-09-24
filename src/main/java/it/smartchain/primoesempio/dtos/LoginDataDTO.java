package it.smartchain.primoesempio.dtos;

import java.io.Serializable;

public class LoginDataDTO implements Serializable {
    private String email;
    private String password;

    public LoginDataDTO(){

    }

    public LoginDataDTO(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

