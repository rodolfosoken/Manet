package agentes;

import graph.Graph;
import graph.RectangleCell;
import graph.TriangleCell;
import jade.core.Agent;
import java.io.Serializable;
import java.util.Random;
import javafx.application.Platform;

/**
 *
 * @author Rodolfo
 */
public class Fant extends Agent {

    private Graph graph;
    private TriangleCell cellFant;      // Agente fant
    private RectangleCell cellNext;     // Proxima celula a ser visitada
    private RectangleCell cellAnterior; //ultima celula que a fant visitou
    private String idSource;            // Agente dispositivo que criou o fant
    private String idTarget;           // Id da celula de destino
    private double pheromone;           //valor do feromonio calculado pela Fant

    @Override
    protected void setup() {

        //recebe tres strings [idAgente,idSource, idTarget]
        this.cellFant = new TriangleCell(new String[]{this.getLocalName(), idSource, idTarget});
        this.graph = (Graph) getArguments()[0];
        this.idSource = (String) getArguments()[1];
        this.idTarget = (String) getArguments()[2];
        this.cellNext = (RectangleCell) getArguments()[3];
        this.cellAnterior = (RectangleCell) getArguments()[4];
        cellFant.setAgente(this);

        //faz o posicionamento gráfico do agente
        if (cellNext != null) {
            //posiciona fant na celula em que está visitando
            cellFant.setPosX(cellNext.getPosX());
            cellFant.setPosY(cellNext.getPosY());
        }

        //adiciona a celula ao modelo grafico
        graph.getModel().addCell(cellFant);
        updateView();

        addBehaviour(new PassarInfos(this));

    }

    public synchronized void updateView() {
        //atualiza a view na thread principal
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    long LOWER_RANGE = 20; //assign lower range value
                    long UPPER_RANGE = 70; //assign upper range value
                    Random random = new Random();

                    long randomValue = LOWER_RANGE
                            + (long) (random.nextDouble() * (UPPER_RANGE - LOWER_RANGE));
                    
                    Thread.sleep(randomValue);
                } catch (Exception e) {
                    System.out.println("Erro: " + e);
                }
                getGraph().updateFant();
            }
        });
    }

    @Override
    protected synchronized void takeDown() {
        graph.getModel().removeTriangularCell(this.cellFant);
        updateView();
        super.takeDown(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @return the cellFant
     */
    public TriangleCell getCellFant() {
        return cellFant;
    }

    /**
     * @return the cellNext
     */
    public RectangleCell getCellNext() {
        return cellNext;
    }

    /**
     * @return the idSource
     */
    public String getIdSource() {
        return idSource;
    }

    /**
     * @return the idTarget
     */
    public String getIdTarget() {
        return idTarget;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Fant)) {
            return false;
        }
        if (this.idSource.equals(((Fant) other).getIdSource())
                && this.idTarget.equals(((Fant) other).getIdTarget())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the anteriorCell
     */
    public RectangleCell getCellAnterior() {
        return cellAnterior;
    }

}
