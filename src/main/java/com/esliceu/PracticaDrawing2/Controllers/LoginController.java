package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Services.LoginDiscordServices;
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
    @Autowired
    LoginDiscordServices loginDiscordServices;
    @GetMapping("/login")
    public String Login() {
        return "login";
    }

    @PostMapping("/login")
    public String PostLogin(Model model,@RequestParam String login, @RequestParam String password) {
        //Si la contraseña esta vacia
        if (password.isEmpty()){
            //Si no se ha iniciado correctamente sale el error.
            model.addAttribute("error", "La contraseña esta vacia.");
            return "login";
        }
        //Metodo para iniciar sesion, si se ha iniciado correctamente.
        if (userService.userExists(login, password)) {
            session.setAttribute("user", userService.user(login));
            return "redirect:/CanvasDraw";
        }
        //Si no se ha iniciado correctamente sale el error.
        model.addAttribute("error", "El usuario o la contraseña incorrectos.");
        return "login";
    }

    @GetMapping("/logindiscord")
    public String logindiscord() throws Exception {
        return "redirect:" + loginDiscordServices.getDiscordRedirection();
    }

    @GetMapping("/discord/callback")
    public String discordCallback(@RequestParam String code) throws Exception{
        String email = loginDiscordServices.getDiscordUserEmail(code);
        if (!loginDiscordServices.existsLogin(email)) {
            loginDiscordServices.registerUserByDiscrod(email, email, "");
            return "login";
        }
        session.setAttribute("user", loginDiscordServices.user(email));
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String successController(Model model){
        User user = (User) session.getAttribute("user");
        if (user == null) return "error";
        model.addAttribute("email",user.getLogin());
        return "success";
    }
}
