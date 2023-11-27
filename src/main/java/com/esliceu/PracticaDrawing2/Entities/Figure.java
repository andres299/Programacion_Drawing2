package com.esliceu.PracticaDrawing2.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Figure implements Serializable {
    private String type;
    private String color;
    private double size;
    private boolean filled;
    private List<Coordinate> coordinates;

    public Figure() {
        this.coordinates = new ArrayList<>();
    }
    public void addCoordinate(Coordinate coordinate) {
        this.coordinates.add(coordinate);
    }

    public Figure(String type, String color, double size, boolean filled, List<Coordinate> coordinates) {
        this.type = type;
        this.color = color;
        this.size = size;
        this.filled = filled;
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }
}
