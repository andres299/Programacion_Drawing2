package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Entities.Version;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import com.esliceu.PracticaDrawing2.Services.VersionService;
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

@Controller
public class ModifyCanvasController {
    @Autowired
    HttpSession session;
    @Autowired
    DrawService drawService;

    @Autowired
    VersionService versionService;

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
        System.out.println(UserPermission);
        //Si no tienes te redirige.
        if (!OwnerPropietary && !UserPermission) {
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
        int owner_id = user.getId();

        //Comprobar si las figuras estan vacias
        if (figures.isEmpty()) {
            model.addAttribute("error", "No se han dibujado figuras. Debes dibujar al menos una figura.");
            return "CanvasDraw";
        }

        //Si el nombre esta vacia , genera uno aleatorio
        String newName = drawName.isEmpty() ? drawService.generateRandomName() : drawName;

        //Actualizar el draw
        drawService.updateVisibility(newName,drawId, visibility);
        // Guardar la versión
        versionService.saveVersion(drawId, figures, owner_id);

        //Actualizar los datos del dibujo
        return "redirect:/AllDraw";
    }

}
