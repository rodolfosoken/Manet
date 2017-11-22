package graph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleCell extends Cell {
    
    private agentes.AgenteDispositivo agente;

    public RectangleCell( String id) {
        super( id);

        Rectangle view = new Rectangle( 63,60);
        Label label = new Label(id);

        view.setStroke(Color.DODGERBLUE);
        view.setFill(Color.DODGERBLUE);

        setView( view);
        setView(label);

    }

    /**
     * @return the agente
     */
    public agentes.AgenteDispositivo getAgente() {
        return agente;
    }

    /**
     * @param agente the agente to set
     */
    public void setAgente(agentes.AgenteDispositivo agente) {
        this.agente = agente;
    }

}