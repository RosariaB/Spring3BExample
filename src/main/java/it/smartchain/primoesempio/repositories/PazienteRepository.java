package it.smartchain.primoesempio.repositories;

import it.smartchain.primoesempio.entities.Paziente;
import it.smartchain.primoesempio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PazienteRepository extends JpaRepository <Paziente, Long> {

    // Method query
    public Paziente findByNomeAndCognome(String nome, String cognome);

    // JPQL uquery (uso le entità e le classi java)
    @Query("select p from Paziente p where p.nome = ?1 and p.cognome = ?2")
    public Paziente trovaInBaseANomeeCognome(String nome, String cognome);

    //Native Query
    @Query (value = "SELECT * " +
            "FROM paziente " +
            "WHERE nome = ?1 and cognome = ?2", nativeQuery = true)
    public Paziente cercaPerNomeeCognome(String nome, String cognome);

    @Query("select p from Paziente p where p.user.id = ?1")
    public List<Paziente> trovaInBaseAlloUserId(Long userid);

    public Optional<Paziente> findByUser(User user);
}
