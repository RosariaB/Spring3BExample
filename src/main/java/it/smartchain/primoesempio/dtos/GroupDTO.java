package it.smartchain.primoesempio.dtos;

import java.io.Serializable;

public class GroupDTO implements Serializable {
    private Long id;
    private String groupName;

    public GroupDTO(){

    }

    public GroupDTO(Long id, String groupName) {
        this.id = id;
        this.groupName = groupName;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


}
