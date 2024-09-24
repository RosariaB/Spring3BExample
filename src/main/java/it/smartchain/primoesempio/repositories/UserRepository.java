package it.smartchain.primoesempio.repositories;

import it.smartchain.primoesempio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Method query
    public List<User> findByUsernameAndPassword(String username, String password);

    // JPQL(java persistence query language) (uso le entità e le classi java)
    @Query("select u from User u where u.username = ?1 and u.password = ?2")
    public List<User> trovaInBaseAUsernameePassword(String username, String password);

    //Native Query (SQL) --> devo seguire il dialetto del db in uso
    @Query (value = "SELECT * " +
            "FROM user " +
            "WHERE username = ?1 and password = ?2", nativeQuery = true)
    public List<User> cercaInBaseAdUsernameePassword(String username, String password);

    public Optional<User> findByEmailAndPassword(String email, String password);






}


