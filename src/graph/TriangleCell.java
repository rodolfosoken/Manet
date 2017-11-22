/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import agentes.Fant;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriangleCell extends Cell {
    private Fant agente;

    public TriangleCell( String id) {
        super( id);

        double width = 50;
        double height = 50;

        Polygon view = new Polygon( width / 2, 0, width, height, 0, height);
        Label label = new Label(id);
        
        view.setStroke(Color.RED);
        view.setFill(Color.RED);

        setView( view);
        setView(label);
    }

    /**
     * @return the agente
     */
    public Fant getAgente() {
        return agente;
    }

    /**
     * @param agente the agente to set
     */
    public void setAgente(Fant agente) {
        this.agente = agente;
    }
    
    

}