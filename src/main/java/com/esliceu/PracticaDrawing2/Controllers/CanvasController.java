package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import com.esliceu.PracticaDrawing2.Services.VersionService;
import com.esliceu.PracticaDrawing2.utils.ObjectCounter;
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
        //Metdodo para guardar el dibujo y la version.
        String saveDrawAndVersion = drawService.saveDrawAndVersion(user, NomImage, visibility, figures);
        //Si no se ha guardado correctamente, mensaje de error.
        if (saveDrawAndVersion != null) {
            model.addAttribute("error", saveDrawAndVersion);
            return "CanvasDraw";
        } else {
            return "redirect:/CanvasDraw";
        }
    }
}