package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class UserDrawController {
    HttpSession session;
    @Autowired
    DrawService drawService;

    @GetMapping("/UserDraw")
    public String UserDraw(Model model) {
        //La sesion del usuario actual
        String login = (String) session.getAttribute("login");

        // Obtenemos los dibujos del usuario que ha iniciado sesión
        List<Draw> userDraws = drawService.getDrawsByUser(login);

        //Estos atributos se enviaran a la página JSP asociada para poder mostralo.
        model.addAttribute("userDraws", userDraws);
        return "UserDraw";
    }

    @PostMapping("/UserDraw")
    public String PostUserDraw(){
        return "UserDraw";
    }
}
