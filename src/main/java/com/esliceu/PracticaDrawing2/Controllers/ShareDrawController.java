package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.User;
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
    PermissionService permissionService;

    @GetMapping("/ShareDraw")
    public String ShareDraw(Model model, @RequestParam int drawId, @RequestParam int owner_id) {
        //La sesion del usuario actual
        User user = (User) session.getAttribute("user");
        if (owner_id != user.getId()){
           return "redirect:/AllDraw";
        }
        List<User> users = userService.allUsers(user.getId());
        model.addAttribute("users",users);
        model.addAttribute("drawId",drawId);
        return "ShareDraw";
    }

    // Manejo de la solicitud POST
    @PostMapping("/ShareDraw")
    public String PostShareDraw(Model model,@RequestParam int drawId, @RequestParam int userId,
                                @RequestParam String permission){
        permissionService.permissionUser(drawId,userId,permission);
        return "redirect:/AllDraw";
    }
}
