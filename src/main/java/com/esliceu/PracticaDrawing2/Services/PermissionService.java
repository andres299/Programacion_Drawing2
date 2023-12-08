package com.esliceu.PracticaDrawing2.Services;

import com.esliceu.PracticaDrawing2.Entities.Permissions;
import com.esliceu.PracticaDrawing2.Repos.DrawRepo;
import com.esliceu.PracticaDrawing2.Repos.PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    @Autowired
    PermissionRepo permissionRepo;
    //Metodo para crear permisos
    public void permissionUser(int drawId, int userId, String permission) {
        Permissions permissions = new Permissions(drawId,userId,permission);
        permissionRepo.permissionUser(permissions);
    }
    //Metodo para crear comprobar si tiene.
    public boolean existpermissionUser(int drawId, int userId) {
        return permissionRepo.ExistpermissionUser(drawId,userId);
    }

    //Metodo para actualizar permisos
    public void updatePermission(int drawId, int userId, String permission) {
        Permissions permissions = new Permissions(drawId,userId,permission);
        permissionRepo.updatePermission(permissions);
    }
    //Metodo para borrar permisos
    public void deletePermissionsUser(int drawId, int userId) {
        permissionRepo.deletePermissionsUser(drawId,userId);
    }

    //Metodo para actualizar la papelera de permisos.
    public void updatePermissionTrash(int id) {
        permissionRepo.updatePermissionTrash(id);
    }
}
