package it.smartchain.primoesempio.repositories;

import it.smartchain.primoesempio.entities.Dato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatoRepository extends JpaRepository<Dato, Long> {

    @Query("select d from Dato d where d.cartellaClinica.id = ?1")
    public List<Dato> trovaInBaseAlIdDellaCartella(Long cartellaid);

    @Query("select d.id from Dato d where d.cartellaClinica.id = ?1")
    public List<Long> findDatoIdsByCartellaClinicaId(Long cartellaid);

    @Modifying
    public void deleteByCartellaClinicaId(Long cartellaid);


}
