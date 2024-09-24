package it.smartchain.primoesempio.repositories;

import it.smartchain.primoesempio.entities.Immagine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImmagneRepository extends JpaRepository<Immagine, Long> {

    @Query("select i from Immagine i where i.dato.id = ?1")
    public List<Immagine> trovaInBaseAlIdDelDato(Long datoid);

    @Query("select i from Immagine i where i.id = ?1")
    public Immagine trovaInBaseAlId(Long immagineId);

    @Modifying
    @Query("delete from Immagine i where i.dato.id = ?1")
    public void eliminaInBaseAlIdDelDato(Long datoid);

    @Modifying
    public void deleteByDatoIdIn(List<Long> datoid);
}
