/*
AgenteDispositivo.java representa os dispositivos moveis que formam os nós
e consequentemente definem os caminhos aos quais as formigaas irão percorrer
*/
package agentes;

import graph.RectangleCell;
import graph.Graph;
import jade.core.Agent;
import javafx.application.Platform;

/**
 *
 * @author Rodolfo
 */
public class AgenteDispositivo extends Agent{
    
    private RectangleCell cell;
    private Graph graph;

    @Override
    protected void setup() {
        this.cell = new RectangleCell(this.getLocalName());
        this.graph = (graph.Graph)getArguments()[0];
        graph.getModel().addCell(cell);
        
        updateView();
    }
    
    private void updateView(){
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
