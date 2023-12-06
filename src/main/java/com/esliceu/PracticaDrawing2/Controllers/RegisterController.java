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

    // Manejo de la solicitud POST para procesar el formulario de registro
    @PostMapping("/register")
    public String PostRegister(Model model, @RequestParam String login,
                               @RequestParam String name,
                               @RequestParam String password) {
        //
        String registrationError = userService.registerUser(login, name, password);

        if (registrationError != null) {
            model.addAttribute("error", registrationError);
            return "/register";
        } else {
            return "redirect:/login";
        }
    }
}
