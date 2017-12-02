/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Cell extends Pane {

    private String cellId;

    List<Cell> children = new ArrayList<>();
    List<Cell> parents = new ArrayList<>();
    private double posX,posY;

    Node view;

    public Cell(String cellId) {
        this.cellId = cellId;
    }

    public void addCellChild(Cell cell) {
        children.add(cell);
    }

    public List<Cell> getCellChildren() {
        return children;
    }

    public void addCellParent(Cell cell) {
        parents.add(cell);
    }

    public List<Cell> getCellParents() {
        return parents;
    }

    public void removeCellChild(Cell cell) {
        children.remove(cell);
    }
    
    public void removeCellParent(Cell cell){
        parents.remove(cell);
    }

    public void setView(Node view) {

        this.view = view;
        getChildren().add(view);

    }
    
    public void removeView(Node view){
        getChildren().remove(view);
    }

    public Node getView() {
        return this.view;
    }

    public String getCellId() {
        return cellId;
    }

    /**
     * @return the posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * @param posX the posX to set
     */
    public void setPosX(double posX) {
        this.posX = posX;
    }

    /**
     * @return the posY
     */
    public double getPosY() {
        return posY;
    }

    /**
     * @param posY the posY to set
     */
    public void setPosY(double posY) {
        this.posY = posY;
    }

    /**
     * @param cellId the cellId to set
     */
    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cell)     
            return this.cellId.equals(((Cell)obj).getCellId());
        else 
            return false;
    }
    
    
}