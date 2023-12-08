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
            // Crear un objeto ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            // Convertir la cadena JSON en un árbol JSON.
            JsonNode jsonNode = objectMapper.readTree(figures);
            // Verificar si el nodo raíz del árbol es un array JSON.
            if (jsonNode.isArray()) {
                // Si es un array JSON, devolver la cantidad de elementos
                return jsonNode.size();
            } else {
                // Si no es un array JSON, devolver 0
                return 0;
            }
            // Si hay un error al procesar la cadena JSON
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
