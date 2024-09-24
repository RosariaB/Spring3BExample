package it.smartchain.primoesempio.services;

import it.smartchain.primoesempio.builders.Builders;
import it.smartchain.primoesempio.entities.Group;
import it.smartchain.primoesempio.repositories.GroupRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GroupService {
    private static final Logger log = LoggerFactory.getLogger(GroupService.class);
    @Autowired
    GroupRepository groupRepository;



    public it.smartchain.primoesempio.dtos.GroupDTO creaGruppo(it.smartchain.primoesempio.dtos.GroupDTO groupDTO) {
        Group group = Builders.DTOToEntity(groupDTO);
        if (group.getId() != null) {
            throw new NoSuchElementException("Non puoi inserire un id");
        }
        else{
            return Builders.entityToDTO(groupRepository.save(group));

        }
    }

    public it.smartchain.primoesempio.dtos.GroupDTO modificaGruppo(it.smartchain.primoesempio.dtos.GroupDTO groupDTO, Long id) {
        Group group = Builders.DTOToEntity(groupDTO);
        Optional<Group> groupFound = groupRepository.findById(id);
        if(groupFound.isEmpty()){
            throw new NoSuchElementException("Il gruppo cercato non è presente");
        }
        Group groupToSave = groupFound.get();

        if (StringUtils.isNotBlank(group.getGroupName()))  {
            groupToSave.setGroupName(group.getGroupName());

        }
        return Builders.entityToDTO(groupRepository.save(groupToSave));

    }

    public List<it.smartchain.primoesempio.dtos.GroupDTO> trovaGruppo() {
        List<Group> listaGroup = groupRepository.findAll();
        if(listaGroup.isEmpty()){
            throw new NoSuchElementException("La lista è vuota");
        }
        return listaGroup.stream().map(Builders::entityToDTO).toList();
    }

}
