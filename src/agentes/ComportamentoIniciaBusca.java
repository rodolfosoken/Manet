/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import graph.Cell;
import graph.Edge;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/**
 *
 * @author Rodolfo
 */
public class ComportamentoIniciaBusca extends OneShotBehaviour{
        
    private String sourceID;
    private String targetID;
    private Object [] args;

    public ComportamentoIniciaBusca(Agent a, String sourceID, String targetID) {
        super(a);
        this.sourceID = sourceID;
        this.targetID = targetID;
    }
    
    @Override
    public void action() {
        
        for(Cell children : ((AgenteDispositivo)myAgent).getCell().getCellChildren()){
            //System.out.println("Coord: "+children.getPosX()+" , "+children.getPosY());
            args = new Object[4];
            args[0] = ((AgenteDispositivo)myAgent).getGraph();
            args[1] = ((AgenteDispositivo)myAgent).getCell();
            args[2] = children.getPosX();
            args[3] = children.getPosY();
            addAgent(myAgent.getContainerController(), "FANT"+graph.Graph.getQtdFant(), Fant.class.getName(), args );
            graph.Graph.incrQtdFant();
        }
        
    }
    
    private void addAgent(ContainerController cc, String agent, String classe, Object[] args) {
        try {
            //agentController = cc.createNewAgent(agent, classe, args);
            AgentController agentController = cc.createNewAgent(agent, classe, args);
            agentController.start();
        } catch (StaleProxyException s) {
            s.printStackTrace();
        }
    }
    
}
