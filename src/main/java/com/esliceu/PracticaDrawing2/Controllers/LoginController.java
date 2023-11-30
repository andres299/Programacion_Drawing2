package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    HttpSession session;
    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String Login() {
        return "login";
    }
    // Manejo de la solicitud POST para procesar el formulario de registro
    @PostMapping("/login")
    public String PostLogin(Model model,@RequestParam String login, @RequestParam String password) {
        Integer loginAttempts = (Integer) session.getAttribute("loginAttempts");
        Long lastFailedLoginTime = (Long) session.getAttribute("lastFailedLoginTime");
        boolean userInPause = false;

        if (loginAttempts != null && lastFailedLoginTime != null) {
            if (loginAttempts >= 3 && (System.currentTimeMillis() - lastFailedLoginTime) <= 60000) {
                loginAttempts = 0;
                userInPause = true;
            }
        }

        if (userInPause) {
            model.addAttribute("error", "Número de intentos fallidos alcanzado. Espere un minuto.");
        } else if (userService.userExists(login, password)) {
            session.setAttribute("user", userService.user(login));
            session.setAttribute("loginAttempts", 0);
            return "redirect:/CanvasDraw";
        } else {
            if (loginAttempts == null) {
                loginAttempts = 1;
            } else {
                loginAttempts++;
            }
            session.setAttribute("loginAttempts", loginAttempts);
            session.setAttribute("lastFailedLoginTime", System.currentTimeMillis());

            if (loginAttempts >= 3) {
                model.addAttribute("error", "Número de intentos fallidos alcanzado. Espere un minuto.");
            } else {
                model.addAttribute("error", "El usuario o la contraseña incorrectos.");
            }
        }
        return "login";
    }
}
