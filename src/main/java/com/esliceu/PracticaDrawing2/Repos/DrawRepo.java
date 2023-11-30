package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Version;

import java.util.List;

public interface DrawRepo {
    Draw saveDraw(Draw draw);

    void saveVersion(Version version);
     /*
    void saveDraw(Draw draw);
    List<Draw> getDraws();

    void deleteDraw(int id, String login);

    List<Draw> getDrawByUser(String login);

    Draw getDrawById(int id);

    void updateDraw(Draw existDraw);
     */
}
