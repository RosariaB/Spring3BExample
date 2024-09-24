package it.smartchain.primoesempio.controllers;

import it.smartchain.primoesempio.components.PrimoComponente;
import it.smartchain.primoesempio.services.PrimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sara")
public class PrimoController {
    @Autowired
    PrimoComponente pippo;

    @Autowired
    PrimoService primo;

    @GetMapping("/get") //trasforma in un servizio http
    public String primoEndpoint(){
       // return "hello world!";
        return primo.primoServizio();
    }

    /*
    Dovrei fare un'operazione di post (es scrivere su un db), ma posso fare qualsiasi operazione
    l'importante che il metodo che definisco nella richiesta (di postman) è uguale a quello che ho scritto
    con @
     */
    @PostMapping("/get")
    public String secondoEndpoint(){
        //return "Ciao ciao!";
        return primo.primoServizio();
    }



}
