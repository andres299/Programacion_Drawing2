package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepoImpl implements PermissionRepo{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void permissionUser(Permissions permissions) {
        jdbcTemplate.update("INSERT INTO permissions (id_draw,id_user,permissions) VALUES (?,?,?)",
                permissions.getId_draw(),permissions.getId_users(),permissions.getPermissions());
    }
}
