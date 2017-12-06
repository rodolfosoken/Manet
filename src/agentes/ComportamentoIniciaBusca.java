/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import graph.Cell;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import manetfxml.FXMLDocumentController;

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
        System.out.println(myAgent.getLocalName()+": "+
                "Inciando Busca em "+a.getLocalName());
        
        //registrar a fant criada na tabela para rejeitar duplicatas
         List<String> key = Collections.unmodifiableList(Arrays.asList(sourceID,targetID));
        agente.registraFant(key);
    }
    
    @Override
    public void action() {
        
        for(Cell children : ((AgenteDispositivo)myAgent).getCell().getCellChildren()){
            //System.out.println("Coord: "+children.getPosX()+" , "+children.getPosY());
            args = new Object[6];
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
            //lista com celulas já visitadas pela fant
            args[5] = Arrays.asList(myAgent.getLocalName());
            FXMLDocumentController.addAgentFant(args);
            
        }
        
    }
    
}
