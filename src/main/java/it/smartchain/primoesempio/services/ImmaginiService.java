package it.smartchain.primoesempio.services;

import it.smartchain.primoesempio.builders.Builders;
import it.smartchain.primoesempio.dtos.ImmagineDTO;
import it.smartchain.primoesempio.dtos.ImmagineResponseDTO;
import it.smartchain.primoesempio.entities.*;
import it.smartchain.primoesempio.repositories.DatoRepository;
import it.smartchain.primoesempio.repositories.ImmagneRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ImmaginiService {
    private static final Logger log = LoggerFactory.getLogger(ImmaginiService.class);
    @Autowired
    ImmagneRepository immagineRepository;
    @Autowired
    DatoRepository datoRepository;
    @Value("${application.cartella-salvataggio-immagine}")
    private String salvataggioImmagine;


    public ImmagineDTO creaImmagine(ImmagineDTO immagineDTO, Long datoId) throws IOException {
        Immagine immagine = Builders.DTOToEntity(immagineDTO);
        if (datoId != null) {
            Optional<Dato> datoOptional = datoRepository.findById(datoId);
            if (datoOptional.isEmpty()) {
                throw new NoSuchElementException("Il dato non esiste");
            }
            Dato dato = datoOptional.get();
            immagine.setDato(dato);
            String pathFile = Paths.get(salvataggioImmagine, LocalDateTime.now().toString()).toString();
            immagine.setFile(pathFile);
            File file = new File(pathFile); // creo la classe file
            try(FileOutputStream fos = new FileOutputStream(file)) { // creo un file (oggetto della classe) ma vuoto
                fos.write(immagineDTO.getBytes()); // riempio il file passando l'array di byte
            }

        }
        return Builders.entityToDTO(immagineRepository.save(immagine));
    }

    @Transactional
    public void eliminaImmagine(Long datoId) {
        immagineRepository.eliminaInBaseAlIdDelDato(datoId);
    }

    @Transactional
    public void eliminaImmaginePerId(Long id) {
        immagineRepository.deleteById(id);
    }

    public ImmagineDTO modificaImmagine(ImmagineDTO immagineDTO, Long id, Long datoId) {
        Optional<Immagine> immagineOptional = immagineRepository.findById(id);
        Immagine immagine = Builders.DTOToEntity(immagineDTO);
        if (immagineOptional.isEmpty()) {
            throw new NoSuchElementException("L'immagine da modificare non è stata trovata");
        }
        Immagine immagineToSave = immagineOptional.get();
        if (StringUtils.isNotBlank(immagine.getNome())) {
            immagineToSave.setNome(immagine.getNome());
        }
        if (immagine.getDataInserimento() != null) {
            immagineToSave.setDataInserimento(immagine.getDataInserimento());
        }
        if (StringUtils.isNotBlank(immagine.getFile())) {
            immagineToSave.setFile(immagine.getFile());
        }
        if (StringUtils.isNotBlank(immagine.getTipo())) {
            immagineToSave.setTipo(immagine.getTipo());
        }
        if (datoId != null) {
            Optional<Dato> optionalDato = datoRepository.findById(datoId);
            if (optionalDato.isEmpty()) {
                throw new NoSuchElementException("Il dato non è presente");
            }
            immagineToSave.setDato(optionalDato.get());
        }
        return Builders.entityToDTO(immagineRepository.save(immagineToSave));
    }

    public ImmagineDTO dammiImmagine(Long immagineId)  {
        Immagine immagini = immagineRepository.trovaInBaseAlId(immagineId);
        if(immagini == null){
            throw new NoSuchElementException("L' immagini è vuota");
        }

        return Builders.entityToDTO(immagini);
    }

    public ImmagineResponseDTO dammiImmagineBase64(Long immagineId) throws IOException {
        Immagine immagine = immagineRepository.trovaInBaseAlId(immagineId);
        if(immagine == null){
            throw new NoSuchElementException("L'immagine è vuota");
        }
        Path path = Paths.get(immagine.getFile());
        byte[] fileContent = Files.readAllBytes(path);


        // Convert the image to base64
        String base64Data = Base64.getEncoder().encodeToString(fileContent);

        // Create the response object
        ImmagineResponseDTO imageResponse = new ImmagineResponseDTO(
                Files.probeContentType(path),
                 // MIME type
                base64Data,
                path.getFileName().toString(),
                Builders.entityToDTO(immagine)
        );

        return imageResponse;
    }

}
