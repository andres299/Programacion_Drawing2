package com.esliceu.PracticaDrawing2.Services;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Version;
import com.esliceu.PracticaDrawing2.Repos.DrawRepo;
import com.esliceu.PracticaDrawing2.utils.ObjectCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DrawService {
    @Autowired
    DrawRepo drawRepo;

    @Autowired
    ObjectCounter objectCounter;
    // Método que genera un nombre aleatorio para una imagen.
    public String generateRandomName() {return "image_" + UUID.randomUUID().toString();}

    //Metodo para guardar el dibujo
    public Draw saveDraw(String newName, int owner_id) {
        Draw draw = new Draw(newName,owner_id);
        return drawRepo.saveDraw(draw);
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

    //Metodo para ver si el usuario tiene permisos lectura o escitura del dibujo
    public boolean hasPermissionsWriting(int id_draw, int id_user) {
        return drawRepo.hasPermissionsWriting(id_draw,id_user);
    }

    //Metodo borrar el dibujo con su version
    public void deleteDraw(int id_draw) {
        drawRepo.deleteDraw(id_draw);
    }

    //Metodo restaurar el dibujo
    public void restoreDraw(int id_draw) {drawRepo.restoreDraw(id_draw);}

}
