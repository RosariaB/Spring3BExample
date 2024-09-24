package it.smartchain.primoesempio.repositories;

import it.smartchain.primoesempio.entities.CartellaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
/*
gli passo nel tipo la tabella su cui la repositoy fa la query e il tipo della chiave primaria di quella tabella stessa
 */
public interface CartellaRepository extends JpaRepository<CartellaClinica, Long> {

    @Query("select c from CartellaClinica c where c.paziente.id = ?1")
    public List<CartellaClinica> trovaInBaseAlPazienteId(Long pazienteid);



    @Query("select c from CartellaClinica c where c.medico.id = ?1")
    public List<CartellaClinica> trovaInBaseAlMedicoId(Long medicoid);



}
