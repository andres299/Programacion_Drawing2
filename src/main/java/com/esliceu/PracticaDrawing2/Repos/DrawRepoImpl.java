package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class DrawRepoImpl implements DrawRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //Metodo para guardar el dibujo(draw) y obtener la id generada en la BBDD.
    @Override
    public Draw saveDraw(Draw draw) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO draw (nameDraw, owner_id, creationDate, visualization) VALUES (?, ?, NOW(), ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, draw.getNameDraw());
            ps.setInt(2, draw.getOwner_id());
            ps.setBoolean(3, draw.isVisualization());
            return ps;
        }, keyHolder);

        // Obtener el ID generado automáticamente
        Number key = keyHolder.getKey();

        if (key != null) {
            draw.setId(key.intValue());
        }
        return draw;
    }

    //Metodo para mostrar las imagenes
    @Override
    public List<DrawWithVersionDTO> getDraws(int id_user) {
        String sql = "SELECT draw.*, version.numFigures AS numFigures, version.figures AS figures, " +
                "version.modificationDate AS modificationDate, permissions.permissions AS permissions " +
                "FROM draw JOIN version ON draw.id = version.id_draw LEFT JOIN permissions ON draw.id = " +
                "permissions.id_draw AND permissions.id_user = ? WHERE (draw.visualization = 1 OR draw.owner_id = ? OR (permissions.permissions IN ('R', 'RW') AND permissions.id_user = ?)) \n" +
                "  AND draw.inTheTrash = 0 AND ((permissions.id_user IS NULL OR permissions.id_user <> ?) OR " +
                "(permissions.id_user = ? AND in_your_trash = false)) AND version.modificationDate = (SELECT " +
                "MAX(modificationDate) FROM version WHERE id_draw = draw.id) ORDER BY version.modificationDate " +
                "DESC;";

        List<DrawWithVersionDTO> allDrawWhithVersion = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(DrawWithVersionDTO.class),id_user, id_user, id_user, id_user, id_user);
        return allDrawWhithVersion;
    }


    // Método para actualizar el campo inTheTrash en la tabla draw
    @Override
    public void updateDraw(int id, int id_user) {
        // Aquí verificamos que el id_user es el propietario de la imagen antes de realizar la actualización
        String sql = "UPDATE draw SET inTheTrash = 1 WHERE id = ? AND owner_id = ?";
        jdbcTemplate.update(sql, id, id_user);
    }

    //Metodo para actualizar tu papelera
    @Override
    public void updateYourTrash(int id, int id_user) {
    //Aquí verificamos que el id_user es el propietario de la imagen antes de realizar la actualización
        String sql = "UPDATE permissions SET in_your_trash = 1 WHERE id_draw = ? AND id_user = ?";
        jdbcTemplate.update(sql, id, id_user);
    }

    //Metodo para obetener lista de dibujos
    @Override
    public List<DrawWithVersionDTO> getDrawsTrash(int id) {
        String sql = "SELECT draw.*, MAX(version.figures) AS figures, " +
                "MAX(version.numFigures) AS numFigures, " +
                "MAX(version.modificationDate) AS modificationDate, " +
                "permissions.permissions " +
                "FROM draw " +
                "JOIN version ON draw.id = version.id_draw " +
                "LEFT JOIN permissions ON draw.id = permissions.id_draw AND permissions.id_user = ? " +
                "WHERE (draw.inTheTrash = true AND draw.owner_id = ?) OR (permissions.id_user = ? AND " +
                "permissions.in_your_trash = true) GROUP BY draw.id;";
        //String sql = "SELECT COUNT (*) FROM draw LEFT "
        List<DrawWithVersionDTO> allDrawWhithVersion = jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(DrawWithVersionDTO.class),id,id,id);
        return allDrawWhithVersion;
    }

    //Si tiene permisos
    @Override
    public boolean hasPermissions(int drawId, int idUser) {
        String checkPermissionsSql = "SELECT COUNT(*) FROM permissions WHERE id_draw = ? AND id_user = ? AND (permissions = 'RW' OR permissions = 'R')";
        int count = jdbcTemplate.queryForObject(checkPermissionsSql, Integer.class, drawId, idUser);
        return count > 0;
    }
    //SI tiene permisos de escritura
    @Override
    public boolean hasPermissionsWriting(int id_draw, int id_user) {
        String checkPermissionsSql = "SELECT COUNT(*) FROM permissions WHERE id_draw = ? " +
                "AND id_user = ? AND permissions = 'RW'";
        int count = jdbcTemplate.queryForObject(checkPermissionsSql, Integer.class, id_draw, id_user);
        return count > 0;
    }

    //Borrar el dibujo.
    @Override
    public void deleteDraw(int id_draw) {
        // Eliminar de la tabla permissions
        String deletePermissionsSql = "DELETE FROM permissions WHERE id_draw = ?";
        jdbcTemplate.update(deletePermissionsSql, id_draw);

        // Eliminar de la tabla version
        String deleteVersionSql = "DELETE FROM version WHERE id_draw = ?";
        jdbcTemplate.update(deleteVersionSql, id_draw);

        // Eliminar de la tabla draw
        String deleteDrawSql = "DELETE FROM draw WHERE id = ?";
        jdbcTemplate.update(deleteDrawSql, id_draw);
    }

    //Eliminar permisos usuario
    @Override
    public void deletPermissionUser(int id, int idUser) {
        // Eliminar de la tabla permissions
        String deletePermissionsSql = "DELETE FROM permissions WHERE id_draw = ? AND id_user = ?";
        jdbcTemplate.update(deletePermissionsSql, id, idUser);
    }

    //Verificar que esta en la basura general
    @Override
    public boolean trashDraw(int drawId) {
        // Consulta SQL para verificar si el dibujo está en la papelera en ambas tablas
        String sql = "SELECT COUNT(*) FROM draw WHERE id = ? AND inTheTrash = false";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, drawId);
        return count > 0;
    }

    //Verificar sis esta en tu basura
    @Override
    public boolean in_your_trash(int drawId) {
        String sql = "SELECT COUNT(*) FROM permissions WHERE id_draw = ? AND in_your_trash = false";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, drawId);
        return count > 0;
    }

    //Restaurar el dibujo
    @Override
    public void restoreDraw(int id_draw) {
        String sql = "UPDATE draw SET inTheTrash = 0 WHERE id = ?";
        jdbcTemplate.update(sql, id_draw);
    }

    //Comprobar el propietario del dibujo
    @Override
    public boolean propietaryDraw(int drawId, int idUser) {
        String checkUserDraw = "SELECT COUNT(*) FROM draw WHERE id = ? AND owner_id = ?";
        int count = jdbcTemplate.queryForObject(checkUserDraw, Integer.class, drawId, idUser);
        return count > 0;
    }

    //Obtener la visibilidad
    @Override
    public boolean getVisibility(int drawId) {
        String checkVisibilityDraw = "SELECT visualization FROM draw WHERE id = ?";
        return jdbcTemplate.queryForObject(checkVisibilityDraw, boolean.class, drawId);
         }
    //Actualizar la visibilidad
    @Override
    public void updateVisibility(String newName,int drawId, boolean visibility) {
        String sql = "UPDATE draw SET nameDraw = ?, visualization = ? WHERE id = ?";
        jdbcTemplate.update(sql, newName, visibility, drawId);
    }
}


