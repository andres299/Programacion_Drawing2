package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
import com.esliceu.PracticaDrawing2.Entities.User;
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
public class TrashDrawController {

    @Autowired
    HttpSession session;

    @Autowired
    DrawService drawService;
    @GetMapping("/TrashDraw")
    public String TrashDrawController(Model model) {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");

        // Crear una lista para almacenar información sobre el dibujo y su versión
        List<DrawWithVersionDTO> drawWithVersionList = drawService.getDrawsTrash(user.getId());
        for (DrawWithVersionDTO drawWithVersion : drawWithVersionList){
            int figureCount = countFiguresInJson(drawWithVersion.getFigures());
            model.addAttribute("figuresCount", figureCount);
        }

        // Agregar la lista de DTOs al modelo
        model.addAttribute("allDraws", drawWithVersionList);
        return "TrashDraw"; // Nombre de tu vista que mostrará la lista de dibujos
    }

    @PostMapping("/TrashDraw")
    public String PostTrashDrawController(Model model, @RequestParam int id, @RequestParam String action) {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");

        if (drawService.hasPermissions(id, user.getId())){
            if ("delete".equals(action)) {
                drawService.deleteDraw(id);
            } else if ("restore".equals(action)) {
                drawService.restoreDraw(id);
            }
    }
        return "redirect:/TrashDraw";
    }

    // Método para contar figuras
    private int countFiguresInJson(String figures) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(figures);

            if (jsonNode.isArray()) {
                return objectMapper.convertValue(jsonNode, List.class).size();
            } else {
                return 0;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

