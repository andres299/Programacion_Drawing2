package com.esliceu.PracticaDrawing2.Repos;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.Figure;

import java.util.List;

public interface DrawRepo {
    void saveDraw(Draw draw);

    List<Draw> getDraws();

    void deleteDraw(int id, String login);

    List<Draw> getDrawByUser(String login);

    List<Figure> getFiguresByDrawId(int drawId);

    Draw getDrawById(int id);

    void updateDraw(Draw existDraw);
}
