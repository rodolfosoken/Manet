/*
AgenteDispositivo.java representa os dispositivos moveis que formam os nós
e consequentemente definem os caminhos aos quais as formigaas irão percorrer
 */
package agentes;

import graph.RectangleCell;
import graph.Graph;
import jade.core.Agent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.application.Platform;

/**
 *
 * @author Rodolfo
 */
public class AgenteDispositivo extends Agent {
    
    private RectangleCell cell;
    private Graph graph;
    private Map<List<String>, Registro> tabela;
    
    private boolean isRetransmitir = false;
    private boolean isProcessandoFant;
    private Fant fantRecebida;
    
    @Override
    protected void setup() {
        tabela = new HashMap<>();
        this.setCell(new RectangleCell(this.getLocalName()));
        getCell().setAgente(this);
        this.graph = (graph.Graph) getArguments()[0];
        getGraph().getModel().addCell(getCell());
        
        updateView();
        Random random = new Random();
        cell.setPosX(random.nextDouble()*500);
        cell.setPosY(random.nextDouble()*500);
        
        isProcessandoFant = false;
        
    }
    
    public synchronized boolean recebeFant(Fant fant) {        
        if(!isProcessandoFant){
            isProcessandoFant = true;
            this.fantRecebida = fant;
            addBehaviour(new RecebeFant(this));
            return true;
        }
        System.out.println(this.getLocalName()+": "+"Ainda processando "+
                fantRecebida.getLocalName() + " em "+ this.getLocalName());
        return false;
    }
    
    public synchronized void registraFant(List<String> key, double pheromone) {
        this.getTabela().put(key, new Registro(
                fantRecebida.getIdSource(), fantRecebida.getCellAnterior().getCellId(), pheromone));
    }
    
    public synchronized void registraFant(List<String> key) {
        this.getTabela().put(key, new Registro(key.get(0), key.get(1), 0));
    }
    
    @Override
    public synchronized void doWake() {
        if (isRetransmitir) {
            addBehaviour(new RetransmitirFant(this, fantRecebida));
            isRetransmitir = false;
        } else {
            addBehaviour(new ComportamentoIniciaBusca(
                    this, getGraph().getCellSelected().getCellId(), (String) getArguments()[2]));
        }
        
    }
    
    public synchronized void updateView() {
        //atualiza a view na thread principal
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getGraph().update();
            }
        });
    }
    
    @Override
    protected synchronized void takeDown() {
        getGraph().getModel().removeCell(this.getCell());
        updateView();
        super.takeDown(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public class Registro {
        
        private String source, next;
        private double pheromone;
        
        public Registro(String source, String next, double pheromone) {
            this.source = (source);
            this.next = (next);
            this.pheromone = (pheromone);
        }
    }

    /**
     * @return the graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @param cell the cell to set
     */
    public void setCell(RectangleCell cell) {
        this.cell = cell;
    }

    /**
     * @return the cell
     */
    public RectangleCell getCell() {
        return cell;
    }

    /**
     * @return the tabela
     */
    public Map<List<String>, Registro> getTabela() {
        return tabela;
    }

    /**
     * @return the isRetransmitir
     */
    public boolean isIsRetransmitir() {
        return isRetransmitir;
    }

    /**
     * @param isRetransmitir the isRetransmitir to set
     */
    public void setIsRetransmitir(boolean isRetransmitir) {
        this.isRetransmitir = isRetransmitir;
    }

    /**
     * @return the fantRecebida
     */
    public Fant getFantRecebida() {
        return fantRecebida;
    }

    /**
     * @param fantRecebida the fantRecebida to set
     */
    public void setFantRecebida(Fant fantRecebida) {
        this.fantRecebida = fantRecebida;
    }

    /**
     * @return the isProcessandoFant
     */
    public boolean isIsProcessandoFant() {
        return isProcessandoFant;
    }

    /**
     * @param isProcessandoFant the isProcessandoFant to set
     */
    public void setIsProcessandoFant(boolean isProcessandoFant) {
        this.isProcessandoFant = isProcessandoFant;
    }
    
}
