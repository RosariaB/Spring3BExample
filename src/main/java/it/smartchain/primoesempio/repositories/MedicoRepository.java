package it.smartchain.primoesempio.repositories;

import it.smartchain.primoesempio.entities.Medico;
import it.smartchain.primoesempio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    @Query("select m from Medico m where m.user.id = ?1")
    public List<Medico> trovaInBaseAlloUserId(Long userid);

    public Optional<Medico> findByUser(User user);
}
