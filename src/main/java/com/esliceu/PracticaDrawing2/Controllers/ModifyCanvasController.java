package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Entities.Version;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import com.esliceu.PracticaDrawing2.Services.VersionService;
import com.esliceu.PracticaDrawing2.utils.ObjectCounter;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ModifyCanvasController {
    @Autowired
    HttpSession session;
    @Autowired
    DrawService drawService;
    @Autowired
    VersionService versionService;

    @GetMapping("/ModifyCanvas")
    public String ModifyCanvas(Model model, @RequestParam String drawName,
                               @RequestParam int drawId) {
        //La sesion del usuario actual
        User user = (User) session.getAttribute("user");
        //Comprobar si el usuario tiene permisos de modificar
        if (!drawService.validateDrawModifyAndTrash(drawId, user)) {
            return "redirect:/AllDraw";
        }
        // Obtener el dibujo por su ID
        Version selectedDraw = versionService.getVersionById(drawId);

        //Metodo para obtener la visibilidad
        boolean visibility = drawService.getVisibility(drawId);

        // Convertir la cadena de figuras a una cadena JSON
        String selectedFiguresJson = selectedDraw.getFigures();

        // Establecer los atributos en la solicitud.
        model.addAttribute("drawName", drawName);
        model.addAttribute("drawId", drawId);
        model.addAttribute("selectedFiguresJson", selectedFiguresJson);
        model.addAttribute("visibility", visibility);
        model.addAttribute("OwnerPropietary", drawService.propietaryDraw(drawId, user.getId()));
        return "ModifyCanvas";
    }

    @PostMapping("/ModifyCanvas")
    public String PostModifyCanvas(Model model,
                                   @RequestParam int drawId,
                                   @RequestParam String figures,
                                    @RequestParam String visibility,
                                    @RequestParam String drawName) {
        //Obtenemos el usuario actual
        User user = (User) session.getAttribute("user");
        //Comprobar si el usuario tiene permisos de modificar el dibujo correspondiente.
        if (!drawService.validateDrawModifyAndTrash(drawId, user)) {
            System.out.println("error");
            return "redirect:/AllDraw";
        }
        // Llamamos al servicio para realizar las operaciones correspondientes
        String modifyCanvas = drawService.processUpdateDrawAndCreatVersion(
                drawName, drawId, figures, visibility, user);
        //Si no es un , es que hay un error y muestra ese mensaje.
        if (modifyCanvas != null){
            model.addAttribute("error", modifyCanvas);
            return "ModifyCanvas";
        }
        return "redirect:/AllDraw";
    }

}
