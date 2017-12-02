/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import graph.Cell;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

/**
 *
 * @author rodolfo.soken
 */
public class RetransmitirFant extends OneShotBehaviour{

    private Object[] args;
    private Fant fant;
    
    public RetransmitirFant(Agent a, Fant fant) {
        super(a);
        this.fant = fant;
        System.out.println("Retransmitindo "+ fant.getLocalName());
    }

    @Override
    public void action() {
         for(Cell children : ((AgenteDispositivo)myAgent).getCell().getCellChildren()){
            //System.out.println("Coord: "+children.getPosX()+" , "+children.getPosY());
            args = new Object[5];
            //grafo
            args[0] = fant.getGraph();
            //id celula de origem (o primeiro agente que iniciou a busca e nao o anterior)
            args[1] = fant.getIdSource();
            //ID da celula de destino
            args[2] = fant.getIdTarget();
            // proxima celula a ser visitada
            args[3] = children;
            //agente deste comportamento
            args[4] = ((AgenteDispositivo)myAgent).getCell();
            addAgent(myAgent.getContainerController(), "F"+graph.Graph.getQtdFant(), Fant.class.getName(), args );
            graph.Graph.incrQtdFant();
        } 
    }
    
    
        private void addAgent(ContainerController cc, String agent, String classe, Object[] args) {
        try {
            //agentController = cc.createNewAgent(agent, classe, args);
            AgentController agentController = cc.createNewAgent(agent, classe, args);
            agentController.start();
            fant.doDelete();
            ((AgenteDispositivo) myAgent).setFantRecebida(null);
        } catch (StaleProxyException s) {
            s.printStackTrace();
        }
    }
    
}
