package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class ShareDrawController {
    @Autowired
    UserService userService;
    @GetMapping("/ShareDraw")
    public String ShareDraw() {

        return "ShareDraw";
    }

    // Manejo de la solicitud POST para procesar el formulario de registro
    @PostMapping("/ShareDraw")
    public String PostShareDraw(Model model){
        return "ShareDraw";
    }
}
