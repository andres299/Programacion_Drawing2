package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.DTO.DrawWithVersionDTO;
import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Version;

import java.util.List;

public interface DrawRepo {
    Draw saveDraw(Draw draw);
    void saveVersion(Version version);

    List<DrawWithVersionDTO> getDraws(int id);

    void updateDraw(int id, int id_user);

    List<DrawWithVersionDTO> getDrawsTrash(int id);

    void userPermissions(int drawId, int owner_id);

    boolean hasPermissionsWriting(int id_draw, int id_user);

    void deleteDraw(int id_draw);

    void restoreDraw(int id_draw);

    boolean hasPermissionsReading(int drawId, int id_user);

    Version getVersionById(int drawId);

     /*
    void saveDraw(Draw draw);
    List<Draw> getDraws();

    void deleteDraw(int id, String login);

    List<Draw> getDrawByUser(String login);

    Draw getDrawById(int id);

    void updateDraw(Draw existDraw);
     */
}
