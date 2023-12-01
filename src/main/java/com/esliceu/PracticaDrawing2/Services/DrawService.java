package com.esliceu.PracticaDrawing2.Services;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Version;
import com.esliceu.PracticaDrawing2.Repos.DrawRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DrawService {
    @Autowired
    DrawRepo drawRepo;
    // Método que genera un nombre aleatorio para una imagen.
    public String generateRandomName() {return "image_" + UUID.randomUUID().toString();}

    //Metodo para guardar el dibujo
    public Draw saveDraw(String newName, int owner_id) {
        Draw draw = new Draw();
        draw.setNameDraw(newName);
        draw.setOwner_id(owner_id);
        return drawRepo.saveDraw(draw);
    }
    //Metodo para guardar el dibujo
    public void saveVersion(int drawId, String figures, int owner_id) {
        Version version = new Version();
        version.setId_draw(drawId);
        version.setFigures(figures);
        version.setId_user(owner_id);
        drawRepo.saveVersion(version);
    }

    //Obtener una lista de los dibujos
    public List<DrawWithVersionDTO> getDraws(int id) {
        return drawRepo.getDraws(id);
    }

    //Actualizar el dibujo a la papelera
    public void updateDraw(int id, int id_user) {
        drawRepo.updateDraw(id,id_user);
    }

    //Obtener una lista de los dibujos en la papelera
    public List<DrawWithVersionDTO> getDrawsTrash(int id) {
        return drawRepo.getDrawsTrash(id);
    }

    //Crear el usuario con permisos
    public void userPermissions(int drawId, int owner_id) {
        drawRepo.userPermissions(drawId,owner_id);
    }

    //Metodo para ver si el usuario tiene permisos lectura o escitura del dibujo
    public boolean hasPermissionsWriting(int id_draw, int id_user) {
        return drawRepo.hasPermissionsWriting(id_draw,id_user);
    }

    public boolean hasPermissionsReading(int drawId, int id_user) {
        return drawRepo.hasPermissionsReading(drawId,id_user);

    }

    //Metodo borrar el dibujo con su version
    public void deleteDraw(int id_draw) {
        drawRepo.deleteDraw(id_draw);
    }

    //Metodo restaurar el dibujo
    public void restoreDraw(int id_draw) {drawRepo.restoreDraw(id_draw);}

    //Metodo para obtener las figuras de la version del dibujo
    public Version getVersionById(int drawId) {
        return drawRepo.getVersionById(drawId);

    }
    /*

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
