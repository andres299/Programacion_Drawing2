package com.esliceu.PracticaDrawing2.Services;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Version;
import com.esliceu.PracticaDrawing2.Repos.DrawRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DrawService {
    @Autowired
    DrawRepo drawRepo;
    // Método que genera un nombre aleatorio para una imagen.

    public String generateRandomName() {return "image_" + UUID.randomUUID().toString();}

    @Transactional
    public Draw saveDraw(String newName, int owner_id) {
        Draw draw = new Draw();
        draw.setNameDraw(newName);
        draw.setOwner_id(owner_id);
        drawRepo.saveDraw(draw);
        return drawRepo.saveDraw(draw);
    }

    public void saveVersion(int drawId, String figures, int owner_id) {
        Version version = new Version();
        version.setId_draw(drawId);
        version.setFigures(figures);
        version.setId_user(owner_id);
        drawRepo.saveVersion(version);
    }


/*
    // Método para obtener todos los dibujos de la lista.
    public List<Draw> getDraws() { return drawRepo.getDraws();}

    //Metodo para borrar un dibujo especifico.
    public void deleteDraw(int id , String login) {
        drawRepo.deleteDraw(id, login);
    }

    // Método para obtener una lista de dibujos hechos por un usuario.
    public List<Draw> getDrawsByUser(String login) {
        return drawRepo.getDrawByUser(login);
    }

    // Método para obtener un dibujo por su ID.
    public Draw getDrawById(int id) {
        return drawRepo.getDrawById(id);
    }

    // Método para actualizar un dibujo.
    public void updateDraw(int drawId, String drawName, String originalCreationDate, String modificationDate, String figures, String login) {
        Draw existDraw = new Draw();
        existDraw.setId(drawId);
        existDraw.setName(drawName);
        existDraw.setCreationDate(originalCreationDate);
        existDraw.setModificationDate(modificationDate);
        existDraw.setFigures(figures);
        existDraw.setCreatedByUser(login);
        drawRepo.updateDraw(existDraw);
    }
     */
}
