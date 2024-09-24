package it.smartchain.primoesempio.entities;

import jakarta.persistence.*;

@Entity
/*
racchiudere il nome della tabella group, che è una parola riservata in MySQL, tra backtick (`).
Questo segnala a MySQL che group è il nome di una tabella e non una parola riservata per la clausola GROUP BY.
 */
@Table(name = "`group`")
public class Group {
    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "groupname")
    private String groupName;


    public Group(){

    }

    public Group(Long id, String groupName, Module module){
        this.id = id;
        this.groupName = groupName;

    }

    public Long getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }


    public void setId(Long id) {
        this.id = id;
    }



    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "{ " +
                "id = " + id +
                ", nome gruppo = " +groupName+
                "}";
    }
}
