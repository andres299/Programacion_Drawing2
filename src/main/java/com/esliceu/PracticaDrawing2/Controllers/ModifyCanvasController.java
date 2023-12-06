package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Entities.Version;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import com.esliceu.PracticaDrawing2.Services.VersionService;
import com.esliceu.PracticaDrawing2.utils.ObjectCounter;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ModifyCanvasController {
    @Autowired
    HttpSession session;
    @Autowired
    DrawService drawService;
    @Autowired
    VersionService versionService;
    @Autowired
    ObjectCounter objectCounter;

    @GetMapping("/ModifyCanvas")
    public String ModifyCanvas(Model model, @RequestParam String drawName,
                               @RequestParam int drawId) {
        //La sesion del usuario actual
        User user = (User) session.getAttribute("user");
        // Obtener el dibujo por su ID
        Version selectedDraw = versionService.getVersionById(drawId);

        //Metodo para comprobar si eres el propietario del dibujo.
        boolean OwnerPropietary = drawService.propietaryDraw(drawId, user.getId());
        //Metodo para comprobar si tienes permisos de escritura.
        boolean UserPermission = drawService.hasPermissionsWriting(drawId, user.getId());
        //Metodo para comprobamr que no este en la basura.
        boolean TrashDraw = drawService.trashDraw(drawId);
        //Si no tienes te redirige.
        if (!OwnerPropietary && !UserPermission || !TrashDraw) {
            return "redirect:/AllDraw";
        }

        //Metodo para obtener la visibilidad
        boolean visibility = drawService.getVisibility(drawId);

        // Convertir la cadena de figuras a una cadena JSON
        String selectedFiguresJson = selectedDraw.getFigures();

        // Establecer los atributos en la solicitud.
        model.addAttribute("drawName", drawName);
        model.addAttribute("drawId", drawId);
        model.addAttribute("selectedFiguresJson", selectedFiguresJson);
        model.addAttribute("visibility", visibility);
        model.addAttribute("OwnerPropietary", OwnerPropietary);

        return "ModifyCanvas";
    }

    @PostMapping("/ModifyCanvas")
    public String PostModifyCanvas(Model model,
                                   @RequestParam int drawId,
                                   @RequestParam String figures,
                                    @RequestParam String visibility,
                                    @RequestParam String drawName) {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");
        //Metodo para comprobar si eres el propietario del dibujo.
        boolean OwnerPropietary = drawService.propietaryDraw(drawId, user.getId());
        //Metodo para comprobar si tienes permisos de escritura.
        boolean UserPermission = drawService.hasPermissionsWriting(drawId, user.getId());
        //Metodo para comprobamos que no este en la basura general.
        boolean TrashDraw = drawService.trashDraw(drawId);
        //Metodo para comprobamos que no este en la basura general.
        boolean in_your_trash = drawService.in_your_trash(drawId);

        //Obtener la ultima version.
        List<Version> allVersionsOfTheDraw = versionService.getAllVersionById(drawId);
        Version lastVersion = allVersionsOfTheDraw.get(0);
        //Si no tienes te redirige.
        if (OwnerPropietary && !TrashDraw || UserPermission && !in_your_trash) {
            return "redirect:/AllDraw";
        }

        //Comprobar si las figuras estan vacias
        if (objectCounter.countFiguresInJson(figures) == 0 || lastVersion.getFigures().equals(figures)) {
            model.addAttribute("error", "No se han dibujado figuras. Debes dibujar al menos una figura.");
            return "CanvasDraw";
        }
        //Si el nombre esta vacia , genera uno aleatorio
        String newName = drawName.isEmpty() ? drawService.generateRandomName() : drawName;

        //Actualizar el draw
        drawService.updateVisibility(newName,drawId, visibility);
        // Guardar la versión
        versionService.saveVersion(drawId, figures, user.getId());

        //Actualizar los datos del dibujo
        return "redirect:/AllDraw";
    }

}
