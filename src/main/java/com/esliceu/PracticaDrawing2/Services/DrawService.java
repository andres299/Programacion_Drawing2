package com.esliceu.PracticaDrawing2.Services;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Entities.Version;
import com.esliceu.PracticaDrawing2.Repos.DrawRepo;
import com.esliceu.PracticaDrawing2.utils.ObjectCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;

@Service
public class DrawService {
    @Autowired
    DrawRepo drawRepo;
    @Autowired
    VersionService versionService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    ObjectCounter objectCounter;

    //Actualizar el dibujo a la papelera
    public void updateTrash(int id, int id_user) {
        drawRepo.updateDraw(id, id_user);
    }
    public void updateYourTrash(int id, int id_user) {drawRepo.updateYourTrash(id,id_user);}

    //Obtener una lista de los dibujos en la papelera
    public List<DrawWithVersionDTO> getDrawsTrash(int id) {
        return drawRepo.getDrawsTrash(id);
    }

    //Metodo para ver si el usuario tiene permisos lectura o escitura del dibujo
    public boolean hasPermissionsWriting(int id_draw, int id_user) {
        return drawRepo.hasPermissionsWriting(id_draw, id_user);
    }
    //Metodo para comprobar si tiene permisos.
    private boolean hasPermissions(int drawId, int id_user) {
        return drawRepo.hasPermissions(drawId, id_user);
    }
    //Metodo borrar el dibujo con sus versiones
    public void deleteDraw(int id_draw) {
        drawRepo.deleteDraw(id_draw);
    }

    //Metodo restaurar el dibujo
    public void restoreDraw(int id_draw) {
        drawRepo.restoreDraw(id_draw);
    }

    //Para obtener el propietario del dibujo
    public boolean propietaryDraw(int drawId, int id_user) {
        return drawRepo.propietaryDraw(drawId, id_user);
    }
    //Metodo para obtener la visibilidad
    public boolean getVisibility(int drawId) {
        return drawRepo.getVisibility(drawId);
    }

    //Metodo para cambiar la visibilidad
    public void updateVisibility(String newName, int drawId, String visibility) {
        drawRepo.updateVisibility(newName, drawId, convertToBoolean(visibility));
    }

    //Borrar permisos del usuario sobre el dibujo.
    public void deletePermissionUser(int id, int id_user) {drawRepo.deletPermissionUser(id,id_user);}

    //Pasar de string a booleano.
    public static boolean convertToBoolean(String visibility) {return "public".equalsIgnoreCase(visibility);    }

    //Metodo para comprobar si esta en la basura
    public boolean trashDraw(int drawId) { return drawRepo.trashDraw(drawId); }

    //Metodo para comporbar si esta en la basura tuya.
    public boolean in_your_trash(int drawId) {
        return drawRepo.in_your_trash(drawId);
    }

    //Guradar el dibujo y su version
    public String saveDrawAndVersion(User user, String nomImage, String visibility, String figures) {
        //Comprobar si las figuras están vacías
        if (objectCounter.countFiguresInJson(figures) == 0) {
            return "No se han dibujado figuras. Debes dibujar al menos una figura.";
        }

        //Si el nombre está vacío, genera uno aleatorio
        String drawName = nomImage.isEmpty() ? generateRandomName() : nomImage;

        // Guardar el dibujo
        Draw savedDraw = saveDraw(drawName, user.getId(), visibility);
        // Obtener la ID del dibujo recién creado
        int drawId = savedDraw.getId();

        // Guardar la versión
        versionService.saveVersion(drawId, figures, user.getId());

        // Devolver null indica que la operación se realizó con éxito, sin errores.
        return null;
    }

    // Método que genera un nombre aleatorio para una imagen.
    public String generateRandomName() { return "image_" + UUID.randomUUID().toString();}

    //Metodo para guardar el dibujo
    public Draw saveDraw(String newName, int owner_id, String visibility) {
        Draw draw = new Draw(newName, owner_id, convertToBoolean(visibility));
        return drawRepo.saveDraw(draw);
    }

    //Obtener una lista de los dibujos
    public List<DrawWithVersionDTO> getDraws(int id_user) {return drawRepo.getDraws(id_user);}

    //Metodo para comprobar a que papelera mandarlo
    public boolean processAllDraw(int id, User user) {
        //Comprobar si eres el propietario y sis tienes permisos.
        boolean ownerProprietary = propietaryDraw(id, user.getId());
        boolean userPermission = hasPermissions(id, user.getId());
        //Si es el propietario o tiene permisos
        if (ownerProprietary || userPermission) {
            // Actualizar la imagen a Papelera si se cumple alguna de las condiciones.
            if (ownerProprietary) {
                updateTrash(id, user.getId());
            } else if (userPermission) {
                updateYourTrash(id, user.getId());
            }
            return true; // Indica que la actualización fue exitosa.
        } else {
            return false; // Indica que el usuario no es propietario ni tiene permisos.
        }
    }

    //SI tiene permisos para ver el dibujo
    public boolean canUserViewDraw(int drawId, User user) {
        // Comprobar si eres el propietario del dibujo.
        boolean isTheOwner = propietaryDraw(drawId, user.getId());

        // Comprobamos que no esté en la basura.
        boolean trashDrawGeneral = trashDraw(drawId);

        //Comprobamos que tiene permisos.
        boolean userPermission = hasPermissions(drawId, user.getId());

        // Método para comprobar si está en la papelera del usuario.
        boolean inYourTrash = in_your_trash(drawId);

        boolean getVisibility = getVisibility(drawId);

        return isTheOwner && trashDrawGeneral || userPermission && inYourTrash || getVisibility;
        }

    //Copiar el dibujo y su version
    public String copiaDrawAndVersion(User user, String jsonData) {
        String drawName = "Copia " + generateRandomName();
        boolean visibility = false;
        // Guardar el dibujo
        Draw savedDraw = saveDraw(drawName, user.getId(), String.valueOf(visibility));

        // Obtener la ID del dibujo recién creado
        int drawId = savedDraw.getId();

        // Guardar la versión
        versionService.saveVersion(drawId, jsonData, user.getId());
        return null;
    }

    //Borrar la imagen o restaurarla
    public void deleteOrRestoreTrashDraw(int id, String action, User user) {
        // Método para comprobar si eres el propietario del dibujo.
        boolean ownerPropietary = propietaryDraw(id, user.getId());
        // Método para comprobar si tienes permisos.
        boolean userPermission = hasPermissions(id, user.getId());
        // Método para comprobar que si esta en la basura general.
        boolean trashDraw = trashDraw(id);
        // Método para comprobar si está en la papelera del usuario.
        boolean inYourTrash = in_your_trash(id);
        if ("delete".equals(action)) {
            if (ownerPropietary) {
                if (!trashDraw) {
                        deleteDraw(id);
                }
            } else if (userPermission) {
                if (!inYourTrash) {
                        deletePermissionUser(id, user.getId());
                    }
                }
        } else if ("restore".equals(action)) {
                if (ownerPropietary) {
                        restoreDraw(id);
            } else if (userPermission) {
                        permissionService.updatePermissionTrash(id);
            }
        }
    }

    //Validar que puede modificar.
    public boolean validateDrawModifyAndTrash(int drawId, User user) {
        // Método para comprobar si eres el propietario del dibujo.
        boolean ownerPropietary = propietaryDraw(drawId, user.getId());

        // Método para comprobar si tienes permisos de escritura.
        boolean userPermissionWriting = hasPermissionsWriting(drawId, user.getId());

        // Método para comprobar que no esté en la basura general.
        boolean trashDraw = trashDraw(drawId);

        // Método para comprobar si está en la papelera del usuario.
        boolean inYourTrash = in_your_trash(drawId);

        // Si eres el propietario y el dibujo no está en la basura general,
        // o tienes permisos de escritura y el dibujo no está en tu papelera,
        // redirige a la página principal de dibujos.
        if ((ownerPropietary && trashDraw) || (userPermissionWriting && inYourTrash)) {
            return true; // Acceso válido
        } else {
            return false; // No tienes acceso, redirige a /AllDraw
        }
    }

    //Subir la version modificada
    public String processUpdateDrawAndCreatVersion(String drawName, int drawId, String figures, String visibility, User user) {
        // Obtener la última versión
        List<Version> allVersionsOfTheDraw = versionService.getAllVersionById(drawId);
        Version lastVersion =allVersionsOfTheDraw.get(0);

        // Comprobar si el dibujo está vacío o es igual al anterior
        if (objectCounter.countFiguresInJson(figures) == 0 || lastVersion.getFigures().equals(figures)) {
            return "No se han dibujado figuras. Debes dibujar al menos una figura.";
        }

        // Si el nombre está vacío, genera uno aleatorio
        String newName = drawName.isEmpty() ? generateRandomName() : drawName;

        // Actualizar el dibujo
        updateVisibility(newName, drawId, visibility);

        // Guardar la nueva versión
        versionService.saveVersion(drawId, figures, user.getId());
        return null;
    }

    //Metodo para compartir el dibujo
    public void ShareDraw(int drawId, int userId, String permission, User user) {
        // Comprobamos si el usuario es el propietario
        boolean ownerPropietary = propietaryDraw(drawId, user.getId());

        // Método para comprobar que no esté en la basura.
        boolean trashDraw = trashDraw(drawId);

        // Si el usuario es el propietario y el dibujo no está en la basura,
        // procedemos a compartir el dibujo.
        if (ownerPropietary && trashDraw) {
            // Si ya tiene permisos, los actualiza, sino los añade.
            boolean existPermission = permissionService.existpermissionUser(drawId, userId);

            if (permission.equals("R") || permission.equals("RW")) {
                if (existPermission) {
                    permissionService.updatePermission(drawId, userId, permission);
                } else {
                    permissionService.permissionUser(drawId, userId, permission);
                }
            }
        }
    }

    //Comprobar si el usuario puede compartir.
    public boolean canUserShareDraw(int drawId, User user) {
        // Comprobar si eres el propietario del dibujo.
        boolean isTheOwner = propietaryDraw(drawId, user.getId());

        // Comprobamos que no esté en la basura.
        boolean trashDrawGeneral = trashDraw(drawId);

        return isTheOwner && trashDrawGeneral;
    }
}
