package it.smartchain.primoesempio.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class PrimoComponente {
    private String nome;
    @Value("${primocomponente.stringa-ciao}")
    private String ciao;


    public String dammiUnaStringa() {
        return ciao;
    }

    @Bean
    public String dammiUnaStringaBean(){
        return "Sono un bean";
    }



}
