package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Figure;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class DrawRepoImpl implements DrawRepo {
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
    }

    @Override
    public List<Draw> getDraws() {
        //Nos devuelve la lista entera.
        return drawList;
    }

    @Override
    public void deleteDraw(int id, String login) {
        // Se utiliza un iterator para recorrer la lista de dibujos
        Iterator<Draw> iterator = drawList.iterator();
        // Se itera sobre la lista de dibujos.
        while (iterator.hasNext()) {
            // Se obtiene el próximo dibujo
            Draw draw = iterator.next();
            // Se verifica si el nombre de usuario es igual al creador del dibujo coincide.
            if (draw.getCreatedByUser().equals(login)) {
                // Si también coincide el ID del dibujo se elimina de la lista.
                if (draw.getId() == id) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public List<Draw> getDrawByUser(String login) {
        // Se crea una nueva lista para almacenar los dibujos del usuario.
        List<Draw> userDraws = new ArrayList<>();
        for (Draw draw : drawList) {
            //Se itera sobre la lista de dibujos almacenada en drawList.
            if (draw.getCreatedByUser().equals(login)) {
                //Se agrega el dibujo a la lista de dibujos del usuario.
                userDraws.add(draw);
            }
        }
        // Se retorna la lista de dibujos del usuario.
        return userDraws;
    }

    @Override
    public List<Figure> getFiguresByDrawId(int drawId) {
        // Se crea una nueva lista para almacenar las figuras del dibujo.
        List<Figure> figuresDraw = new ArrayList<>();
        // Encuentra el dibujo correspondiente al drawId.
        Draw targetDraw = null;
        // Se busca el dibujo correspondiente al drawId.
        for (Draw draw : drawList) {
            //Si las id coinciden se asigna a targetDraw y se sale del bucle.
            if (draw.getId() == drawId) {
                targetDraw = draw;
                break;
            }
        }
        // Si se encuentra el dibujo, se obtienen las figuras asociadas.
        if (targetDraw != null) {
            figuresDraw = targetDraw.getFigures();
        }
        //Se retorna la lista de figuras asociadas al dibujo.
        return figuresDraw;
    }

    @Override
    public Draw getDrawById(int id) {
        //Recorremos la lista estatica.
        for (Draw draw : drawList) {
            //Si coincide retorna el dibujo con la ip asociada.
            if (draw.getId() == id) {
                return draw;
            }
        }
        return null;
    }

    @Override
    public void updateDraw(Draw existDraw) {
        // Eliminar el dibujo existente con el mismo ID
        drawList.removeIf(draw -> draw.getId() == existDraw.getId());
        // Agregar el dibujo actualizado a la lista
        drawList.add(existDraw);
    }
}
