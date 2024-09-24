package it.smartchain.primoesempio.dtos;

public class UtentiDTO {
   private UserDTO userDTO;
   private  PazienteDTO pazienteDTO;
   private MedicoDTO medicoDTO;


   public UtentiDTO(UserDTO userDTO, PazienteDTO pazienteDTO, MedicoDTO medicoDTO) {
      this.userDTO = userDTO;
      this.pazienteDTO = pazienteDTO;
      this.medicoDTO = medicoDTO;

   }
   public UtentiDTO() {

   }

   public UserDTO getUserDTO() {
      return userDTO;
   }

   public void setUserDTO(UserDTO userDTO) {
      this.userDTO = userDTO;
   }

   public PazienteDTO getPazienteDTO() {
      return pazienteDTO;
   }

   public void setPazienteDTO(PazienteDTO pazienteDTO) {
      this.pazienteDTO = pazienteDTO;
   }

   public MedicoDTO getMedicoDTO() {
      return medicoDTO;
   }

   public void setMedicoDTO(MedicoDTO medicoDTO) {
      this.medicoDTO = medicoDTO;
   }
}
