package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepoImpl implements PermissionRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    //Metodo para dar permisos
    @Override
    public void permissionUser(Permissions permissions) {
        jdbcTemplate.update("INSERT INTO permissions (id_draw,id_user,permissions) VALUES (?,?,?)",
                permissions.getId_draw(),permissions.getId_users(),permissions.getPermissions());
    }
    //Metodo para actualizar permisos.
    @Override
    public void updatePermission(Permissions permissions) {
        String updateSql = "UPDATE permissions SET permissions = ? WHERE id_draw = ? AND id_user = ?";
        jdbcTemplate.update(updateSql, permissions.getPermissions(), permissions.getId_draw(), permissions.getId_users());
    }
    //Metod para comprobar si tiene permisos
    @Override
    public boolean ExistpermissionUser(int drawId, int userId) {
        String selectSql = "SELECT COUNT(*) FROM permissions WHERE id_draw = ? AND id_user = ?";
        int count = jdbcTemplate.queryForObject(selectSql, Integer.class, drawId, userId);
        return count > 0;    }
    //Metodo para borrar permisos
    @Override
    public void deletePermissionsUser(int drawId, int userId) {
        String deleteSql = "DELETE FROM permissions WHERE id_draw = ? AND id_user = ?";
        jdbcTemplate.update(deleteSql, drawId, userId);
    }
    //Metodo para actalizar papelera
    @Override
    public void updatePermissionTrash(int id) {
        String updateTrash = "UPDATE permissions SET in_your_trash = 0 WHERE id_draw = ?";
        jdbcTemplate.update(updateTrash,id);
    }
}
