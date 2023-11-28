package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Figure;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @GetMapping("/ModifyCanvas")
    public String ModifyCanvas(Model model, @RequestParam String drawName,
                               @RequestParam int drawId) throws IOException {
        //Obtenemos el usuario atual
        String login = (String) session.getAttribute("login");
        // Medida de seguridad que compruebe que el usuario esta modificando su propio dibujo
        Draw existDraw = drawService.getDrawById(drawId);
        // Verificar si existDraw es nulo y si el usuario que creó la imagen es el que está intentando modificarla
        if (existDraw == null || !existDraw.getCreatedByUser().equals(login)) {
            // Redirigir a la página de todos los dibujos si el dibujo no existe o no pertenece al usuario
            return "redirect:/AllDraw";
        }

        // Convertir la cadena de figuras a una cadena JSON
        String selectedFiguresJson = existDraw.getFigures();

        // Establecer los atributos JSON en la solicitud, el nombre del dibujo y el ID.
        model.addAttribute("drawName", drawName);
        model.addAttribute("drawId", drawId);
        model.addAttribute("selectedFiguresJson", selectedFiguresJson);

        // Establecer el atributo JSON en la solicitud, el nombre del dibujo y el id.
        model.addAttribute("drawName",drawName);
        model.addAttribute("drawId",drawId);
        model.addAttribute("selectedFiguresJson", selectedFiguresJson);

        return "ModifyCanvas";
    }

    @PostMapping("/ModifyCanvas")
    public String PostModifyCanvas(Model model,
                                   @RequestParam String figures,
                                   @RequestParam int drawId, @RequestParam String drawName) throws IOException {
        //Obtenemos el usuario atual
        String login = (String) session.getAttribute("login");

        // Obtener la información existente del dibujo
        Draw existDraw = drawService.getDrawById(drawId);

        // Verificar si hay al menos una figura
        if (figures.isEmpty()) {
            model.addAttribute("error", "No se han dibujado figuras. Debes dibujar al menos una figura.");
            return "ModifyCanvas";
        }

        // Obtener la fecha y hora actuales para la modificación
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentModificationDate = new Date();
        String ModificationDate = sdf.format(currentModificationDate);

        // Conservar la fecha de creación
        String originalCreationDate = existDraw.getCreationDate();


        //Si el nombre esta vacia , genera uno aleatorio
        String newName = drawName.isEmpty() ? drawService.generateRandomName() : drawName;

        //Actualizar los datos del dibujo
        drawService.updateDraw(drawId, newName, originalCreationDate,ModificationDate,figures,login);
        return "redirect:/AllDraw";
    }
}
