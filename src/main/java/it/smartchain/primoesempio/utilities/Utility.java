package it.smartchain.primoesempio.utilities;

import it.smartchain.primoesempio.builders.Builders;
import it.smartchain.primoesempio.dtos.UserDTO;
import it.smartchain.primoesempio.entities.User;

public class Utility {

    public static boolean gruppoUserEsiste(UserDTO userDTO) {
        User user = Builders.DTOToEntity(userDTO);
        if (user.getGruppo() != null && user.getGruppo().getId() != null) {
            return true;
        }
        return false;
    }
}
