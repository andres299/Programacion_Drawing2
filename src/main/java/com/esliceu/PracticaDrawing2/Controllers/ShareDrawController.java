package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShareDrawController {
    @Autowired
    HttpSession session;
    @Autowired
    UserService userService;
    @GetMapping("/ShareDraw")
    public String ShareDraw(Model model, @RequestParam int drawId) {
        //La sesion del usuario actual
        User user = (User) session.getAttribute("user");

        System.out.println(drawId);
        return "ShareDraw";
    }

    // Manejo de la solicitud POST para procesar el formulario de registro
    @PostMapping("/ShareDraw")
    public String PostShareDraw(Model model){
        return "ShareDraw";
    }
}
