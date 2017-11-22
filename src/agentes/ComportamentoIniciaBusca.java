/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

/**
 *
 * @author Rodolfo
 */
public class ComportamentoIniciaBusca extends OneShotBehaviour{
    
    private String sourceID;
    private String targetID;

    public ComportamentoIniciaBusca(Agent a, String sourceID, String targetID) {
        super(a);
    }
    
    @Override
    public void action() {
        System.out.println("Action do Comportamento busca");
        
       
    }
    
}
