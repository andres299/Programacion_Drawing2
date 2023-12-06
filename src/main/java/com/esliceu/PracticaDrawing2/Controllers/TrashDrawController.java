package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import com.esliceu.PracticaDrawing2.Services.PermissionService;
import com.esliceu.PracticaDrawing2.utils.ObjectCounter;
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
public class TrashDrawController {

    @Autowired
    HttpSession session;

    @Autowired
    DrawService drawService;
    @Autowired
    PermissionService permissionService;

    @GetMapping("/TrashDraw")
    public String TrashDrawController(Model model) {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");

        // Crear una lista para almacenar información sobre el dibujo y su versión
        List<DrawWithVersionDTO> drawWithVersionList = drawService.getDrawsTrash(user.getId());

        // Agregar la lista de DTOs al modelo
        model.addAttribute("allDraws", drawWithVersionList);
        return "TrashDraw";
    }

    @PostMapping("/TrashDraw")
    public String PostTrashDrawController(Model model, @RequestParam int id, @RequestParam String action) {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");

        // Llamamos al servicio para realizar las operaciones correspondientes
        drawService.deleteTrashDraw(id, action, user);
        return "redirect:/TrashDraw";
    }
}

