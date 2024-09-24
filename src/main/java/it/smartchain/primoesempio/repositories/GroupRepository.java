package it.smartchain.primoesempio.repositories;

import it.smartchain.primoesempio.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository <Group,Long> {
    @Query("select g from Group g where g.id = ?1")
    public Group findGruppo(Long id);
}
