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
public class AllDrawController {
    @Autowired
    HttpSession session;
    @Autowired
    DrawService drawService;

    @GetMapping("/AllDraw")
    public String AllDraw(Model model) {
        //La sesion del usuario actual
        String login = (String) session.getAttribute("login");

        // Obtén todos los dibujos
        List<Draw> allDraws = drawService.getDraws();

        //Estos atributos se enviaran a la página JSP asociada para poder mostralo.
        model.addAttribute("allDraws", allDraws);
        model.addAttribute("user",login);
        // Agregamos la función countFiguresInJson como un atributo del modelo
        model.addAttribute("countFiguresInJson", countFiguresInJson);
        return "AllDraw";
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

    @PostMapping("/AllDraw")
    public String PostAllDraw(Model model, @RequestParam int id){
        //La sesion del usuario actual
        String login = (String) session.getAttribute("login");
        //Metodo para borrar la imagen y nor redirige a AllDraw.
        drawService.deleteDraw(id,login);
        return "redirect:/AllDraw";
    }
}

