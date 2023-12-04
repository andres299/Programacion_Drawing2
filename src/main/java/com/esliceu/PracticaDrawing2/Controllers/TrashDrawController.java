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
        //Metodo para comprobar si eres el propietario del dibujo.
        boolean OwnerPropietary = drawService.propietaryDraw(id, user.getId());
        //Metodo para comprobar si tienes permisos de escritura.
        boolean UserPermission = drawService.hasPermissionsWriting(id, user.getId());
        System.out.println(UserPermission);
        if ("delete".equals(action)) {
            if (OwnerPropietary) {
                drawService.deleteDraw(id);
            } else if (UserPermission) {
                drawService.deletePermissionUser(id,user.getId());
            }
        } else if ("restore".equals(action)) {
            if (OwnerPropietary) {
                drawService.restoreDraw(id);
            } else if (UserPermission) {
                permissionService.updatePermissionTrash(id);
            }
        }
        return "redirect:/TrashDraw";
    }
}

