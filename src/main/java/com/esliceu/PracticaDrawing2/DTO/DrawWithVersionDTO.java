package com.esliceu.PracticaDrawing2.DTO;

import com.esliceu.PracticaDrawing2.Entities.Draw;
import com.esliceu.PracticaDrawing2.Entities.User;
import com.esliceu.PracticaDrawing2.Entities.Version;

public class DrawWithVersionDTO {
    private int id;
    private String nameDraw;
    private int owner_id;
    private String creationDate;
    private boolean visualization;
    private boolean inTheTrash;
    private String figures;
    private int numFigures;
    private String modificationDate;
    private String permissions;
    private boolean in_your_trash;

    public DrawWithVersionDTO() {
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

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public boolean isIn_your_trash() {
        return in_your_trash;
    }

    public void setIn_your_trash(boolean in_your_trash) {
        this.in_your_trash = in_your_trash;
    }
}
