package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Figure;
import com.esliceu.PracticaDrawing2.Services.DrawService;
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
public class CanvasController {
    @Autowired
    HttpSession session;
    @Autowired
    DrawService drawService;
    @GetMapping("/CanvasDraw")
    public String CanvasDraw() {
        return "CanvasDraw";
    }

    @PostMapping("/CanvasDraw")
    public String PostCanvasDraw(Model model, @RequestParam String figures, @RequestParam String NomImage) throws IOException {
        //Obtenemos el usuario atual
        String login = (String) session.getAttribute("login");

        // Analiza el JSON para obtener el array de figuras
        ObjectMapper objectMapper = new ObjectMapper();
        List<Figure> newFigures = objectMapper.readValue(figures, new TypeReference<List<Figure>>() {});

        // Verificar si hay al menos una figura
        if (newFigures.isEmpty()) {
            model.addAttribute("error", "No se han dibujado figuras. Debes dibujar al menos una figura.");
            return "CanvasDraw";
        }

        // Obtener la fecha y hora actual
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String currentDateString = sdf.format(currentDate);

        //Si el nombre esta vacia , genera uno aleatorio
        String newName = NomImage.isEmpty() ? drawService.generateRandomName() : NomImage;

        // Guardar el dibujo
        drawService.saveDraw(newName,currentDateString,newFigures,login);
        return "CanvasDraw";
    }

}
