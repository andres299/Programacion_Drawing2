package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import com.esliceu.PracticaDrawing2.Services.VersionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
public class CanvasController {
    @Autowired
    HttpSession session;
    @Autowired
    DrawService drawService;

    @Autowired
    VersionService versionService;

    @GetMapping("/CanvasDraw")
    public String CanvasDraw(Model model) {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user.getLogin());
        return "CanvasDraw";
    }

    @PostMapping("/CanvasDraw")
    public String PostCanvasDraw(Model model, @RequestParam String figures, @RequestParam String NomImage,
                                 @RequestParam String visibility) {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");
        int owner_id = user.getId();
        System.out.println(visibility);
        if (figures.isEmpty()) {
            model.addAttribute("error", "No se han dibujado figuras. Debes dibujar al menos una figura.");
            return "CanvasDraw";
        }

        //Si el nombre esta vacia , genera uno aleatorio
        String newName = NomImage.isEmpty() ? drawService.generateRandomName() : NomImage;

        // Guardar el dibujo
        Draw savedDraw = drawService.saveDraw(newName, owner_id);
        // Obtener la ID del dibujo recién creado
        int drawId = savedDraw.getId();

        // Guardar la versión
        versionService.saveVersion(drawId, figures, owner_id);

        return "CanvasDraw";
    }
}