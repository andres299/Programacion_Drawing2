package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Figure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class DrawRepoImpl implements DrawRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void saveDraw(Draw draw) {
        jdbcTemplate.update("INSERT INTO Draw (name, creationDate, modificationDate, figures, createdByUser)" +
                        "VALUES (?, ?, ?, ?, ?)", draw.getName(), draw.getCreationDate(),
                    draw.getModificationDate(), draw.getFigures(), draw.getCreatedByUser());
    }


    @Override
    public List<Draw> getDraws() {
        String selectSql = "SELECT * FROM Draw";
        return jdbcTemplate.query(selectSql, new BeanPropertyRowMapper<>(Draw.class));
    }

    @Override
    public void deleteDraw(int id, String login) {
        jdbcTemplate.update("DELETE FROM Draw WHERE id = ? AND createdByUser = ?", id, login);
    }

    @Override
    public List<Draw> getDrawByUser(String login) {
        return jdbcTemplate.query("SELECT * FROM Draw WHERE createdByUser = ?",
                new Object[]{login}, new BeanPropertyRowMapper<>(Draw.class));
    }

    @Override
    public List<Figure> getFiguresByDrawId(int drawId) {
        return jdbcTemplate.query("SELECT * FROM Figure WHERE drawId = ?",
                new Object[]{drawId}, new BeanPropertyRowMapper<>(Figure.class));
    }

    @Override
    public Draw getDrawById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Draw WHERE id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Draw.class));
    }

    @Override
    public void updateDraw(Draw existDraw) {
        jdbcTemplate.update("UPDATE Draw SET name = ?, creationDate = ?, modificationDate = ?, figures = ?, createdByUser = ? WHERE id = ?",
                existDraw.getName(), existDraw.getCreationDate(), existDraw.getModificationDate(),
                existDraw.getFigures(), existDraw.getCreatedByUser(), existDraw.getId());
    }


}
    /*
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
     */

