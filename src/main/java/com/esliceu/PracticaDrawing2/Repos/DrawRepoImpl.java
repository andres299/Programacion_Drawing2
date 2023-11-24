package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DrawRepoImpl implements DrawRepo{
    static List<Draw> drawList = new ArrayList<>();

    @Override
    public void saveDraw(Draw draw) {
        if (!drawList.isEmpty()) {
            // Encuentra el máximo ID actual en la lista.
            int maxId = drawList.stream()
                    .mapToInt(Draw::getId)
                    .max()
                    .orElse(0);
            // Asigna un ID único incrementando el máximo ID encontrado.
            draw.setId(maxId + 1);
        } else {
            // Si la lista está vacía, asigna el ID 1.
            draw.setId(1);
        }
        // Agrega el objeto Draw con su ID asignado a la lista.
        drawList.add(draw);
        System.out.println(draw.getId() + draw.getCreatedByUser() + draw.getName());
    }
}
