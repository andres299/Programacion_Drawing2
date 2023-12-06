package com.esliceu.PracticaDrawing2.Services;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Repos.DrawRepo;
import com.esliceu.PracticaDrawing2.utils.ObjectCounter;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;

@Service
public class DrawService {
    @Autowired
    DrawRepo drawRepo;
    @Autowired
    VersionService versionService;
    @Autowired
    ObjectCounter objectCounter;

    //Actualizar el dibujo a la papelera
    public void updateTrash(int id, int id_user) {
        drawRepo.updateDraw(id, id_user);
    }
    public void updateYourTrash(int id, int id_user) {drawRepo.uodateYourTrash(id,id_user);}

    //Obtener una lista de los dibujos en la papelera
    public List<DrawWithVersionDTO> getDrawsTrash(int id) {
        return drawRepo.getDrawsTrash(id);
    }

    //Metodo para ver si el usuario tiene permisos lectura o escitura del dibujo
    public boolean hasPermissionsWriting(int id_draw, int id_user) {
        return drawRepo.hasPermissionsWriting(id_draw, id_user);
    }

    //Metodo borrar el dibujo con su version
    public void deleteDraw(int id_draw) {
        drawRepo.deleteDraw(id_draw);
    }

    //Metodo restaurar el dibujo
    public void restoreDraw(int id_draw) {
        drawRepo.restoreDraw(id_draw);
    }

    public boolean propietaryDraw(int drawId, int id_user) {
        return drawRepo.propietaryDraw(drawId, id_user);
    }

    public boolean getVisibility(int drawId) {
        return drawRepo.getVisibility(drawId);
    }

    public void updateVisibility(String newName, int drawId, String visibility) {
        drawRepo.updateVisibility(newName, drawId, convertToBoolean(visibility));
    }
    public boolean userCanSee(int drawId, int id_user) {
        return drawRepo.userCanSee(drawId, id_user);
    }

    public void deletePermissionUser(int id, int id_user) {
        drawRepo.deletPermissionUser(id,id_user);
    }

    public static boolean convertToBoolean(String visibility) {
        return "public".equalsIgnoreCase(visibility);
    }

    public boolean trashDraw(int drawId) {
        return drawRepo.trashDraw(drawId);
    }

    public boolean in_your_trash(int drawId) {
        return drawRepo.in_your_trash(drawId);
    }

    public String saveDrawAndVersion(User user, Model model, String nomImage, String visibility, String figures) {
        //Comprobar si las figuras están vacías
        if (objectCounter.countFiguresInJson(figures) == 0) {
            model.addAttribute("error", "No se han dibujado figuras. Debes dibujar al menos una figura.");
            return "CanvasDraw";
        }

        //Si el nombre está vacío, genera uno aleatorio
        String drawName = nomImage.isEmpty() ? generateRandomName() : nomImage;

        // Guardar el dibujo
        Draw savedDraw = saveDraw(drawName, user.getId(), visibility);
        // Obtener la ID del dibujo recién creado
        int drawId = savedDraw.getId();

        // Guardar la versión
        versionService.saveVersion(drawId, figures, user.getId());

        // Devolver null indica que la operación se realizó con éxito, sin errores.
        return null;
    }

    // Método que genera un nombre aleatorio para una imagen.
    public String generateRandomName() {
        return "image_" + UUID.randomUUID().toString();
    }

    //Metodo para guardar el dibujo
    public Draw saveDraw(String newName, int owner_id, String visibility) {
        Draw draw = new Draw(newName, owner_id, convertToBoolean(visibility));
        return drawRepo.saveDraw(draw);
    }

    //Obtener una lista de los dibujos
    public List<DrawWithVersionDTO> getDraws(int id_user) {
        return drawRepo.getDraws(id_user);
    }
}
