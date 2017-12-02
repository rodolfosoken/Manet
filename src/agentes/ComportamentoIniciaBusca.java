/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import graph.Cell;
import graph.RectangleCell;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.Arrays;

/**
 *
 * @author Rodolfo
 */
public class ComportamentoIniciaBusca extends OneShotBehaviour{
        
    private String sourceID;
    private String targetID;
    private Object [] args;
    private AgenteDispositivo agente;

    public ComportamentoIniciaBusca(Agent a, String sourceID, String targetID) {
        super(a);
        this.sourceID = sourceID;
        this.targetID = targetID;
        this.agente = (AgenteDispositivo)a;
        System.out.println("Inciando Busca em "+a.getLocalName());
        
        //registrar a fant criada na tabela para rejeitar duplicatas
        agente.registraFant(Arrays.asList(sourceID,targetID));
    }
    
    @Override
    public void action() {
        
        for(Cell children : ((AgenteDispositivo)myAgent).getCell().getCellChildren()){
            //System.out.println("Coord: "+children.getPosX()+" , "+children.getPosY());
            args = new Object[5];
            //grafo
            args[0] = ((AgenteDispositivo)myAgent).getGraph();
            //id celula de origem (o primeiro agente que iniciou a busca e nao o anterior)
            args[1] = sourceID;
            //ID da celula de destino
            args[2] = targetID;
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
        } catch (StaleProxyException s) {
            s.printStackTrace();
        }
    }
    
}
