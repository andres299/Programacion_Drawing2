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

    public Draw() {
    }

    public Draw(int id, String nameDraw, int owner_id, String creationDate, boolean visualization) {
        this.id = id;
        this.nameDraw = nameDraw;
        this.owner_id = owner_id;
        this.creationDate = creationDate;
        this.visualization = visualization;
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
}
