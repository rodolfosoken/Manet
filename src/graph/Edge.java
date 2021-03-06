/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import javafx.scene.Group;
import javafx.scene.shape.Line;

public class Edge extends Group {

    private double pheromone;
    private Cell source;
    private Cell target;

    Line line;

    public Edge(Cell source, Cell target) {

        this.source = source;
        this.target = target;

        source.addCellChild(target);
        target.addCellParent(source);
        //grafo nao direcionado
        target.addCellChild(source);
        source.addCellParent(target);
       

        line = new Line();

        line.startXProperty().bind( source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind( source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind( target.layoutXProperty().add( target.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind( target.layoutYProperty().add( target.getBoundsInParent().getHeight() / 2.0));

        getChildren().add( line);

    }
    
    public void removeChildParent(){
        source.removeCellChild(target);
        source.removeCellParent(target);
        target.removeCellParent(source);
        target.removeCellChild(source);
        
    }

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Edge){
            Edge edge = (Edge) obj;
            return getSource().getCellId().equals(edge.getSource().getCellId())&& getTarget().getCellId().equals(edge.getTarget().getCellId());
        }else return false;
     }

    /**
     * @return the pheromone
     */
    public double getPheromone() {
        return pheromone;
    }

    /**
     * @param pheromone the pheromone to set
     */
    public void setPheromone(double pheromone) {
        this.pheromone = pheromone;
    }
    
    

}
