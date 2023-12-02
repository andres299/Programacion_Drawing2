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

        // Verificar si existDraw es nulo y si el usuario que creó la imagen es el que está intentando modificarla
        if (selectedDraw == null || !drawService.hasPermissionsWriting(drawId, user.getId())) {
            // Redirigir a la página de todos los dibujos si el dibujo no existe o no pertenece al usuario
            return "redirect:/AllDraw";
        }

        //Metodo para obtener la visibilidad
        //boolean visibility = drawService.getVisibility(drawId);

        // Convertir la cadena de figuras a una cadena JSON
        String selectedFiguresJson = selectedDraw.getFigures();

        // Establecer los atributos JSON en la solicitud, el nombre del dibujo y el ID.
        model.addAttribute("drawName", drawName);
        model.addAttribute("drawId", drawId);
        model.addAttribute("selectedFiguresJson", selectedFiguresJson);

        return "ModifyCanvas";
    }
    /*
    @PostMapping("/ModifyCanvas")
    public String PostModifyCanvas(Model model,
                                   @RequestParam String figures,
                                   @RequestParam int drawId, @RequestParam String drawName) {
        //La sesion del usuario actual
        User user = (User) session.getAttribute("user");

        // Obtener la información existente del dibujo
        Draw existDraw = drawService.getDrawById(drawId);

        // Verificar si hay al menos una figura
        if (figures.isEmpty()) {
            model.addAttribute("error", "No se han dibujado figuras. Debes dibujar al menos una figura.");
            return "ModifyCanvas";
        }


        //Si el nombre esta vacia , genera uno aleatorio
        String newName = drawName.isEmpty() ? drawService.generateRandomName() : drawName;

        //Actualizar los datos del dibujo
        drawService.updateDraw(drawId, newName, originalCreationDate,ModificationDate,figures,login);
        return "redirect:/AllDraw";
    }

     */
}
