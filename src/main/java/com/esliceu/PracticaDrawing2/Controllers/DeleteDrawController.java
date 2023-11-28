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
    public String DeleteDraw(Model model) {
        //La sesion del usuario actual
        String login = (String) session.getAttribute("login");

        // Obtén todos los dibujos marcados para eliminar
        List<Draw> deletedDraws = drawService.getDeletedDraws(login);

        // Estos atributos se enviarán a la página JSP asociada para mostrarlos
        model.addAttribute("deletedDraws", deletedDraws);
        model.addAttribute("user", login);
        return "DeleteDraw";
    }

    @PostMapping("/DeleteDraw")
    public String PostDeleteDraw(Model model, @RequestParam int id){
        //La sesion del usuario actual
        String login = (String) session.getAttribute("login");
        //Metodo para borrar la imagen y nor redirige a AllDraw.
        drawService.deleteDraw(id,login);
        return "redirect:/DeleteDraw";
    }
     */
}
