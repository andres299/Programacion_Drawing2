package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
import com.esliceu.PracticaDrawing2.Entities.Draw;
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

import java.io.IOException;
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
        //Comprobar si eres el usuario.
        boolean isTheOwner = drawService.userCanSee(drawId,user.getId());
        //Comprobamos que no este en la basura.
        boolean TrashDraw = drawService.trashDraw(drawId);
        if (!isTheOwner && !TrashDraw) {return "redirect:/AllDraw";}

        //Obtener todas las versiones.
        List<Version> allVersionsOfTheDraw = versionService.getAllVersionById(drawId);

        // Estos atributos se enviarán a la página JSP asociada para poder mostrarlos.
        model.addAttribute("drawName", drawName);
        model.addAttribute("allVersionsOfTheDraw",allVersionsOfTheDraw);
        model.addAttribute("drawId",drawId);
        // Mostrar la vista si tiene permisos
        return "ViewDraw";

}

    // Manejo de la solicitud POST para procesar el formulario de registro
    @PostMapping("/ViewDraw")
    public String PostViewDraw(Model model, @RequestParam String jsonData, @RequestParam int draw_Id) {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");
        System.out.println(draw_Id);
        //Comprobar si eres el usuario.
        boolean isTheOwner = drawService.userCanSee(draw_Id,user.getId());
        //Generemos un nombre aleatorio y estable la visibilidad por defecto
        String drawName = "Copia " + drawService.generateRandomName();
        boolean visibility = false;
        // Guardar el dibujo
        Draw savedDraw = drawService.saveDraw(drawName, user.getId(), String.valueOf(visibility));

        // Obtener la ID del dibujo recién creado
        int drawId = savedDraw.getId();

        // Guardar la versión
        versionService.saveVersion(drawId, jsonData, user.getId());
        return "ViewDraw";
    }
}
