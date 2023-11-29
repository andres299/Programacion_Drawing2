package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Services.DrawService;
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
    public String PostCanvasDraw(Model model, @RequestParam String figures, @RequestParam String NomImage) {
        //Obtenemos el usuario atual
        String login = (String) session.getAttribute("login");
        System.out.println("figures" + figures);
        System.out.println("nombre" + NomImage);
        // Verificar si hay al menos una figura
        if (figures.isEmpty()) {
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
        drawService.saveDraw(newName,currentDateString,figures,login);
        return "CanvasDraw";
    }

}
