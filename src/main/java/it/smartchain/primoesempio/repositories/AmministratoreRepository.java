package it.smartchain.primoesempio.repositories;

import it.smartchain.primoesempio.entities.Amministratore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AmministratoreRepository extends JpaRepository<Amministratore, Long> {
    @Query("select a from Amministratore a where a.user.id = ?1")
    public List<Amministratore> trovaInBaseAlloUserId(Long userid);
}
