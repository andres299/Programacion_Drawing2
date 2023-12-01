package com.esliceu.PracticaDrawing2.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;

import java.util.List;
@Controller
public class ObjectCounter {
    public int countFiguresInJson(String figures) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(figures);

            if (jsonNode.isArray()) {
                return jsonNode.size();
            } else {
                return 0;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
