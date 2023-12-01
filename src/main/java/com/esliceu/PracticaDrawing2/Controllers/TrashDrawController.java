package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Services.DrawService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TrashDrawController {

    @Autowired
    HttpSession session;

    @Autowired
    DrawService drawService;
    @GetMapping("/DeleteDraw")
    public String TrashDrawController(Model model) {
        String login = (String) session.getAttribute("login");


        return "DeleteDraw"; // Nombre de tu vista que mostrará la lista de dibujos
    }

    @PostMapping("/DeleteDraw")
    public String PostTrashDrawController(Model model, @RequestParam int id) {
        String login = (String) session.getAttribute("login");
        return "redirect:/DeleteDraw";
    }
}

