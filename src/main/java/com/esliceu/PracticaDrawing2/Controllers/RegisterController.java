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
        //Posible errores
        if (userService.existsLogin(login)) {
            model.addAttribute("error", "El usuario ya existe.");
        } else if (password.length() < 5) {
            model.addAttribute("error", "La contraseña debe tener al menos 5 caracteres.");
        } else if (!validUsername(login)) {
            model.addAttribute("error", "El login solo permite letras y números.");
        } else if (!noSpaces(password)) {
            model.addAttribute("error", "La contraseña no debe contener espacios.");
        } else {
            userService.register(login, name, password);
            return "redirect:/login";
        }

        return "register";
    }

    // Métodos para validar login y contraseñas
    private boolean validUsername(String word) {
        return word.matches("[a-zA-Z0-9]+");
    }

    private boolean noSpaces(String password) {
        return !password.contains(" ");
    }
}
