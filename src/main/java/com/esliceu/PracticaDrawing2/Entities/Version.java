package com.esliceu.PracticaDrawing2.Entities;

public class Version {
    private int id;
    private int id_draw;
    private String figures;
    private int numFigures;
    private String modificationDate;
    private int id_user;
    public Version() {
    }

    public Version(int id, int id_draw, String figures, int numFigures, String modificationDate, int id_user) {
        this.id = id;
        this.id_draw = id_draw;
        this.figures = figures;
        this.numFigures = numFigures;
        this.modificationDate = modificationDate;
        this.id_user = id_user;
    }

    public Version(int id_draw, String figures, int numFigures, int id_user) {
        this.id_draw = id_draw;
        this.figures = figures;
        this.numFigures = numFigures;
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_draw() {
        return id_draw;
    }

    public void setId_draw(int id_draw) {
        this.id_draw = id_draw;
    }

    public String getFigures() {
        return figures;
    }

    public void setFigures(String figures) {
        this.figures = figures;
    }

    public int getNumFigures() {
        return numFigures;
    }

    public void setNumFigures(int numFigures) {
        this.numFigures = numFigures;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
