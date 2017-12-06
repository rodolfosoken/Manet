/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class Graph {

    private double alcance = 200;
    private Model model;

    private Group canvas;

    private ZoomableScrollPane scrollPane;

    MouseGestures mouseGestures;

    private Cell cellSelected;



    /**
     * the pane wrapper is necessary or else the scrollpane would always align
     * the top-most and left-most child to the top and left eg when you drag the
     * top child down, the entire scrollpane would move down
     */
    CellLayer cellLayer;

    public Graph() {

        this.model = new Model();

        canvas = new Group();
        cellLayer = new CellLayer();
        cellLayer.setStyle("-fx-background-color: white");

        canvas.getChildren().add(cellLayer);

        cellSelected = null;
        mouseGestures = new MouseGestures(this);

        scrollPane = new ZoomableScrollPane(canvas);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
      
    }

    public ScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public Pane getCellLayer() {
        return this.cellLayer;
    }

    public Model getModel() {
        return model;
    }

    public void updateNode(Cell cell) {
        if (cell instanceof RectangleCell) {
            List<Cell> allCells = model.getAllCells();

            for (Cell cellProx : allCells) {
                if (cellProx instanceof RectangleCell) {
                    if (!cell.equals(cellProx) && Math.abs(cellProx.getPosX() - cell.getPosX()) < getAlcance() && Math.abs(cellProx.getPosY() - cell.getPosY()) < getAlcance()) {
                        //System.out.println("Criando edge para: " + cell.getCellId()+ ", " + cellProx.getCellId());
                        model.addEdge(cell.getCellId(), cellProx.getCellId());
                    } else {
                        model.removeEdge(cell.getCellId(), cellProx.getCellId());
                        model.removeEdge(cellProx.getCellId(), cell.getCellId());
                    }
                }
            }

            update();
        }
    }
    
    public void updateFant(){
        getCellLayer().getChildren().addAll(model.getAddedCells());

        // remove components from graph pane
        getCellLayer().getChildren().removeAll(model.getRemovedCells());

        // enable dragging of cells
        for (Cell cell : model.getAddedCells()) {
            mouseGestures.makeDraggable(cell);
            cell.relocate(cell.getPosX(), cell.getPosY());
        }

        // every cell must have a parent, if it doesn't, then the graphParent is
        // the parent
        getModel().attachOrphansToGraphParent(model.getAddedCells());

        // remove reference to graphParent
        getModel().disconnectFromGraphParent(model.getRemovedCells());

        // merge added & removed cells with all cells
        getModel().merge();

        
    }

    public void update() {
       
        getCellLayer().getChildren().addAll(model.getAddedEdges());
        getCellLayer().getChildren().addAll(model.getAddedCells());

        // remove components from graph pane
        getCellLayer().getChildren().removeAll(model.getRemovedCells());
        getCellLayer().getChildren().removeAll(model.getRemovedEdges());

        // enable dragging of cells
        for (Cell cell : model.getAddedCells()) {
            mouseGestures.makeDraggable(cell);
            cell.relocate(cell.getPosX(), cell.getPosY());
        }

        // every cell must have a parent, if it doesn't, then the graphParent is
        // the parent
        getModel().attachOrphansToGraphParent(model.getAddedCells());

        // remove reference to graphParent
        getModel().disconnectFromGraphParent(model.getRemovedCells());

        // merge added & removed cells with all cells
        getModel().merge();

    }

    public double getScale() {
        return this.scrollPane.getScaleValue();
    }

    /**
     * @return the alcance
     */
    public double getAlcance() {
        return alcance;
    }

    /**
     * @param alcance the alcance to set
     */
    public void setAlcance(double alcance) {
        this.alcance = alcance;
    }

    /**
     * @return the cellSelected
     */
    public Cell getCellSelected() {
        return cellSelected;
    }

    /**
     * @param cellSelected the cellSelected to set
     */
    public void setCellSelected(Cell cellSelected) {
        this.cellSelected = cellSelected;
    }



}
