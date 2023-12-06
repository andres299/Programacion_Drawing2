package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AllDrawController {
    @Autowired
    HttpSession session;
    @Autowired
    DrawService drawService;

    @GetMapping("/AllDraw")
    public String AllDraw(Model model) {
        //La sesion del usuario actual
        User user = (User) session.getAttribute("user");

        // Crear una lista para almacenar información sobre el dibujo y su versión
        List<DrawWithVersionDTO> drawWithVersionList = drawService.getDraws(user.getId());

        // Agregar la lista de DTOs al modelo
        model.addAttribute("allDraws", drawWithVersionList);
        model.addAttribute("current_id",user.getId());
        return "AllDraw";
    }

    @PostMapping("/AllDraw")
    public String PostAllDraw(@RequestParam int id){
        //La sesion del usuario actual
        User user = (User) session.getAttribute("user");
        boolean success = drawService.processAllDraw(id, user);
        if (success){
            return "redirect:/TrashDraw";
        }
        return "redirect:/AllDraw";
    }


}

