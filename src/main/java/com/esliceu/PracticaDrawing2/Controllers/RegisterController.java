package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;
    @GetMapping("/register")
    public String Register() {
        return "register";
    }

    @PostMapping("/register")
    public String PostRegister(Model model, @RequestParam String login,
                               @RequestParam String name,
                               @RequestParam String password) {
        //Metodo para registrar el usuario.
        String register = userService.registerUser(login, name, password);
        //Si no cumple con los requisitos te muestra un mensaje de error.
        if (register != null) {
            model.addAttribute("error", register);
            return "/register";
        } else { //Si se ha guardado correctamente te redirige a login.
            return "redirect:/login";
        }
    }
}
