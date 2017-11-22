/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import graph.RectangleCell;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rodolfo
 */
public class ComportamentoIniciaBusca extends OneShotBehaviour{
        
    private String sourceID;
    private String targetID;
    private List<Fant> listaFant;
    private Object [] args;
    private static int qtdFANT;

    public ComportamentoIniciaBusca(Agent a, String sourceID, String targetID) {
        super(a);
        this.sourceID = sourceID;
        this.targetID = targetID;
        listaFant = new ArrayList<>();
        args = new Object[3];
        args[0] = ((AgenteDispositivo)a).getGraph();
        args[1] = ((AgenteDispositivo)myAgent).getCell();
    }
    
    @Override
    public void action() {
        System.out.println("Action do Comportamento busca");
         addAgent(myAgent.getContainerController(), "FANT"+qtdFANT++, Fant.class.getName(), args );
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
