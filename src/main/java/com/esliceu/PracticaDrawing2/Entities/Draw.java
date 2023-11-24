package com.esliceu.PracticaDrawing2.Entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Draw {
    private int id;
    private String name;
    private String creationDate;
    private String modificationDate;
    private List<Figure> figures;
    private String createdByUser;
    
    public Draw() {
        this.figures = new ArrayList<>();
    }

    public Draw(int id, String name, String creationDate, String modificationDate, List<Figure> figures, String createdByUser) {
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

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public List<Figure> getFigures() {
        // Lógica para obtener la lista de figuras
        return figures;
    }
}
