package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DeleteDrawController {

    @Autowired
    HttpSession session;

    @Autowired
    DrawService drawService;
/*
    @GetMapping("/DeleteDraw")
    public String showAllDraws(Model model,@RequestParam int id) {
        String login = (String) session.getAttribute("login");

        // Obtén todos los dibujos, incluidos los eliminados
        List<Draw> allDraws = drawService.getAllDraws(id);

        // Estos atributos se enviarán a la página JSP asociada para mostrarlos
        model.addAttribute("allDraws", allDraws);
        model.addAttribute("user", login);
        return "DeleteDraw"; // Nombre de tu vista que mostrará la lista de dibujos
    }

    @PostMapping("/DeleteDraw")
    public String handleDeleteDraw(Model model, @RequestParam int id, @RequestParam String action) {
        String login = (String) session.getAttribute("login");

        if ("delete".equals(action)) {
            drawService.deleteDraw(id, login);
        }

        else if ("restore".equals(action)) {
            drawService.restoreDraw(id);
        }
        return "redirect:/DeleteDraw";
    }
    */
}

