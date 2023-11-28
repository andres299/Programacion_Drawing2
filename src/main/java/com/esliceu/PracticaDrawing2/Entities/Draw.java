package com.esliceu.PracticaDrawing2.Entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Draw {
    private int id;
    private String name;
    private String creationDate;
    private String modificationDate;
    private String figures;
    private String createdByUser;

    public Draw() {
    }

    public Draw(int id, String name, String creationDate, String modificationDate, String figures, String createdByUser) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.figures = figures;
        this.createdByUser = createdByUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getFigures() {
        return figures;
    }

    public void setFigures(String figures) {
        this.figures = figures;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }
}
