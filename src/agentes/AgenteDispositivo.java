/*
AgenteDispositivo.java representa os dispositivos moveis que formam os nós
e consequentemente definem os caminhos aos quais as formigaas irão percorrer
*/
package agentes;

import graph.RectangleCell;
import jade.core.Agent;
import javafx.application.Platform;

/**
 *
 * @author Rodolfo
 */
public class AgenteDispositivo extends Agent{
    
    private RectangleCell cell;
    private double pheromone;

    @Override
    protected void setup() {
        this.cell = new RectangleCell(this.getLocalName());
        graph.Graph graph = (graph.Graph)getArguments()[0];
        graph.getModel().addCell(cell);
        
        updateView();
    }
    
    private void updateView(){
         //atualiza a view na thread principal
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((graph.Graph)getArguments()[0]).update();
            }
        });
    }
  

    @Override
    protected void takeDown() {
        graph.Graph graph = (graph.Graph)getArguments()[0];
        graph.getModel().removeCell(this.cell);
        updateView();
        super.takeDown(); //To change body of generated methods, choose Tools | Templates.
    }

  
    
}
