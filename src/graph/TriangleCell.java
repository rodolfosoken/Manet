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
    private Label label;

    //recebe tres strings [idAgente,idSource, idTarget]
    public TriangleCell( String[] id) {
        super( id[0]);

        double width = 50;
        double height = 50;

        Polygon view = new Polygon( width / 2, 0, width, height, 0, height);
        this.label = new Label(id[0]);
        
        view.setStroke(Color.RED);
        view.setFill(Color.RED);

        setView(label);
        setView( view);
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