package com.esliceu.PracticaDrawing2.Entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Draw {
    private int id;
    private String nameDraw;
    private int owner_id;
    private String creationDate;

    private boolean visualization;

    private boolean inTheTrash;

    public Draw() {
    }

    public Draw(String nameDraw, int owner_id) {
        this.nameDraw = nameDraw;
        this.owner_id = owner_id;
    }

    public Draw(String nameDraw, int owner_id, String creationDate, boolean visualization, boolean inTheTrash) {
        this.nameDraw = nameDraw;
        this.owner_id = owner_id;
        this.creationDate = creationDate;
        this.visualization = visualization;
        this.inTheTrash = inTheTrash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameDraw() {
        return nameDraw;
    }

    public void setNameDraw(String nameDraw) {
        this.nameDraw = nameDraw;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isVisualization() {
        return visualization;
    }

    public void setVisualization(boolean visualization) {
        this.visualization = visualization;
    }

    public boolean isInTheTrash() {
        return inTheTrash;
    }

    public void setInTheTrash(boolean inTheTrash) {
        this.inTheTrash = inTheTrash;
    }
}
