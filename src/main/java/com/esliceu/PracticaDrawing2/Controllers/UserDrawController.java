package com.esliceu.PracticaDrawing2.Controllers;

import com.esliceu.PracticaDrawing2.Entities.Draw;
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
public class UserDrawController {
    @Autowired
    HttpSession session;
    @Autowired
    DrawService drawService;

    @GetMapping("/UserDraw")
    public String UserDraw(Model model) {
        //La sesion del usuario actual
        String login = (String) session.getAttribute("login");

        // Obtenemos los dibujos del usuario que ha iniciado sesión
        List<Draw> userDraws = drawService.getDrawsByUser(login);

        //Estos atributos se enviaran a la página JSP asociada para poder mostralo.
        model.addAttribute("userDraws", userDraws);
        model.addAttribute("login", login);
        // Agregamos la función countFiguresInJson como un atributo del modelo
        model.addAttribute("countFiguresInJson", countFiguresInJson);


        return "UserDraw";
    }

    // Método extraído
    private Object countFiguresInJson = new Object() {
        public int countFiguresInJson(String figures) {
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
    };

    @PostMapping("/UserDraw")
    public String PostUserDraw(){
        return "UserDraw";
    }
}
