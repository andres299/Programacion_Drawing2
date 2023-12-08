package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Services.DrawService;
import com.esliceu.PracticaDrawing2.Services.PermissionService;
import com.esliceu.PracticaDrawing2.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ShareDrawController {
    @Autowired
    HttpSession session;
    @Autowired
    UserService userService;
    @Autowired
    DrawService drawService;
    @Autowired
    PermissionService permissionService;

    @GetMapping("/ShareDraw")
    public String ShareDraw(Model model, @RequestParam int drawId) {
        //La sesion del usuario actual
        User user = (User) session.getAttribute("user");
        //Comprobar si eres el propietrio y no esta en la basura.
        if (!drawService.canUserShareDraw(drawId, user)) {
            return "redirect:/AllDraw";
        }
        
        List<User> users = userService.allUsers(user.getId());
        model.addAttribute("users",users);
        model.addAttribute("drawId",drawId);
        return "ShareDraw";
    }

    @PostMapping("/ShareDraw")
    public String PostShareDraw(Model model,@RequestParam int drawId, @RequestParam int userId,
                                @RequestParam String permission){
        //La sesion del usuario actual
        User user = (User) session.getAttribute("user");
        // Llamamos al servicio para procesar el compartir dibujo
        drawService.ShareDraw(drawId, userId, permission, user);
        return "redirect:/AllDraw";
    }

    @PostMapping("/DeletePermissions")
    public String PostDeletePermissions(Model model, @RequestParam int drawId, @RequestParam int userId) {
        //La sesion del usuario actual
        User user = (User) session.getAttribute("user");
        //Comprobar si eres el propietrio y no esta en la basura.
        if (!drawService.canUserShareDraw(drawId, user)) {
            return "redirect:/AllDraw";
        }
        //Restaura la imagen.
        permissionService.deletePermissionsUser(drawId, userId);
        return "redirect:/AllDraw";
    }
}
