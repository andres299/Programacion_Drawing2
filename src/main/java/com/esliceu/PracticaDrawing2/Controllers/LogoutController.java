package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LogoutController {
    @Autowired
    HttpSession session;

    @GetMapping("/Logout")
    public String Logout() {
        // Invalidar la sesion existente
        if (session != null) {
            session.invalidate();
        }
        // Redirirgir a login
        return "redirect:/login";
    }
}
