package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AllDrawController {
    @Autowired
    HttpSession session;
    @Autowired
    UserService userService;

    @GetMapping("/AllDraw")
    public String Login() {
        return "AllDraw";
    }

}
