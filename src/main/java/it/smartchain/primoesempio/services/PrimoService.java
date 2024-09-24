package it.smartchain.primoesempio.services;

import it.smartchain.primoesempio.components.PrimoComponente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrimoService {

    @Autowired
    PrimoComponente primoComponente;

    @Autowired
    String dammiUnaStringaBean;


    public String primoServizio(){
        return "Hello world!";
    }

    public String rispostaComponente(){
        return primoComponente.dammiUnaStringa();
    }

    public String rispostaBean(){
        return dammiUnaStringaBean;
    }

}
