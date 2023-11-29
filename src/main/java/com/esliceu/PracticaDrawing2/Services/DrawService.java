package com.esliceu.PracticaDrawing2.Services;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Repos.DrawRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DrawService {
    @Autowired
    DrawRepo drawRepo;
    // Método que genera un nombre aleatorio para una imagen.

    public String generateRandomName() {return "image_" + UUID.randomUUID().toString();}
/*
    public void saveDraw(String nomImage, String currentDateString, String figures, String login) {
        Draw draw = new Draw();
        draw.setName(nomImage);
        draw.setCreationDate(currentDateString);
        draw.setModificationDate(currentDateString);
        draw.setFigures(figures);
        draw.setCreatedByUser(login);
        drawRepo.saveDraw(draw);
    }

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
