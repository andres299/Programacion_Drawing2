package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Permissions;

public interface PermissionRepo {
    void permissionUser(Permissions permissions);

    void updatePermission(Permissions permissions);

    boolean ExistpermissionUser(int drawId, int userId);

    void deletePermissionsUser(int drawId, int userId);
}
