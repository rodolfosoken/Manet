/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import graph.Graph;
import graph.RectangleCell;
import graph.TriangleCell;
import jade.core.Agent;
import javafx.application.Platform;

/**
 *
 * @author Rodolfo
 */
public class Fant extends Agent{
    private TriangleCell cell;
    private Graph graph;
    private RectangleCell cellSource;
    
    @Override
    protected void setup(){
        this.cell = new TriangleCell(this.getLocalName());
        this.cellSource = (RectangleCell)getArguments()[1];
        cell.setAgente(this);
        cell.setPosX(80+cellSource.getPosX());
        cell.setPosY(cellSource.getPosY());
        this.graph = (Graph) getArguments()[0];
        graph.getModel().addCell(cell);
        
        updateView();
    }
    
    public void updateView(){
         //atualiza a view na thread principal
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                graph.update();
            }
        });
    }

    @Override
    protected void takeDown() {
        graph.getModel().removeCell(this.cell);
        updateView();
        super.takeDown(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
