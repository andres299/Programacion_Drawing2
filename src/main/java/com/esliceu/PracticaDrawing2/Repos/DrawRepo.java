package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Version;

import java.util.List;

public interface DrawRepo {
    Draw saveDraw(Draw draw);

    List<DrawWithVersionDTO> getDraws(int id_user);

    void updateDraw(int id, int id_user);

    List<DrawWithVersionDTO> getDrawsTrash(int id);

    boolean hasPermissionsWriting(int id_draw, int id_user);

    void deleteDraw(int id_draw);

    void restoreDraw(int id_draw);

    boolean propietaryDraw(int drawId, int id_user);

    boolean getVisibility(int drawId);

    void updateVisibility(String newName,int drawId, boolean visibility);

    void updateYourTrash(int id, int id_user);

    void deletPermissionUser(int id, int idUser);

    boolean trashDraw(int drawId);

    boolean in_your_trash(int drawId);

    boolean hasPermissions(int drawId, int idUser);
}
