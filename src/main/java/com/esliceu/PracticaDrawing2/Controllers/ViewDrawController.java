package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Entities.Version;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class ViewDrawController {
    @Autowired
    HttpSession session;
    @Autowired
    DrawService drawService;

    @GetMapping("/ViewDraw")
    public String ViewDraw(Model model, @RequestParam String drawName,
                           @RequestParam int drawId, @RequestParam boolean visualization)  {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");

        // Obtener el dibujo por su ID
        Version selectedDraw = drawService.getVersionById(drawId);

        // Estos atributos se enviarán a la página JSP asociada para poder mostrarlos.
        model.addAttribute("selectedFiguresJson", selectedDraw.getFigures());
        model.addAttribute("drawName", drawName);

        // Mostrar la vista si tiene permisos
        return "ViewDraw";

}

    // Manejo de la solicitud POST para procesar el formulario de registro
    @PostMapping("/ViewDraw")
    public String PostViewDraw(Model model, @RequestParam String visibility) {
        System.out.println(visibility);
        return "ViewDraw";
    }
}
