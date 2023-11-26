package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Figure;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import com.esliceu.PracticaDrawing2.Services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class ViewDrawController {
    @Autowired
    HttpSession session;
    @Autowired
    DrawService drawService;

    @GetMapping("/ViewDraw")
    public String ViewDraw(Model model, @RequestParam String drawName,
                           @RequestParam int drawId) throws IOException {
        //Creamos una lista que recibira todos los dibujos
        List<Figure> selectedFigures = drawService.getFiguresByDrawId(drawId);

        // Convertir la lista de figuras a una cadena JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String selectedFiguresJson = objectMapper.writeValueAsString(selectedFigures);

        //Estos atributos se enviaran la página JSP asociada para poder mostralo.
        model.addAttribute("selectedFiguresJson", selectedFiguresJson);
        model.addAttribute("drawName",drawName);

        return "ViewDraw";
    }

    // Manejo de la solicitud POST para procesar el formulario de registro
    @PostMapping("/ViewDraw")
    public String PostViewDraw() {
        return "ViewDraw";
    }
}
