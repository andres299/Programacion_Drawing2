package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Entities.Version;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import com.esliceu.PracticaDrawing2.Services.VersionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ViewDrawController {
    @Autowired
    HttpSession session;
    @Autowired
    DrawService drawService;

    @Autowired
    VersionService versionService;
    @GetMapping("/ViewDraw")
    public String ViewDraw(Model model, @RequestParam int drawId , @RequestParam String drawName)  {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");
        //Comprobar si eres el propietrio y no esta en la basura.
        if (!drawService.canUserViewDraw(drawId, user)) {
            return "redirect:/AllDraw";
        }

        //Obtener todas las versiones.
        List<Version> allVersionsOfTheDraw = versionService.getAllVersionById(drawId);

        // Estos atributos se enviarán a la página JSP asociada para poder mostrarlos.
        model.addAttribute("drawName", drawName);
        model.addAttribute("allVersionsOfTheDraw",allVersionsOfTheDraw);
        model.addAttribute("drawId",drawId);
        // Mostrar la vista si tiene permisos
        return "ViewDraw";
}

    @PostMapping("/ViewDraw")
    public String PostViewDraw(Model model, @RequestParam String jsonData, @RequestParam int draw_Id) {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");
        //Comprobar si eres el usuario y no esta en la basura.
        if (!drawService.canUserViewDraw(draw_Id, user)) {
            System.out.println("Hola");
            return "redirect:/AllDraw";
        }

        //Metdodo para guardar el dibujo y la version.
        String copiaDrawAndVersion = drawService.copiaDrawAndVersion(user, jsonData);
        if (copiaDrawAndVersion != null) {
            return "ViewDraw";
        }
            return "redirect:/AllDraw";
    }
}
