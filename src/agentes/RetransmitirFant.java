/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import graph.Cell;
import jade.content.onto.basic.Done;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import manetfxml.FXMLDocumentController;

/**
 *
 * @author rodolfo.soken
 */
public class RetransmitirFant extends WakerBehaviour {

    private Object[] args;
    private Fant fant;

    public RetransmitirFant(Agent a, Fant fant) {
        super(a, 2000);
        this.fant = fant;
        System.out.println(myAgent.getLocalName()+": "+
                "Retransmitindo " + fant.getLocalName());
    }

    @Override
    public void onWake() {
        
        int passos = 1;
        //ao retransmitir a fant é preciso considerar o tempo de atualizacao da view
        for (Cell children : ((AgenteDispositivo) myAgent).getCell().getCellChildren()) {
            try {
                long tempoMinimo = 70;
                Thread.sleep(tempoMinimo*(++passos));
            } catch (Exception e) {
                System.out.println(myAgent.getLocalName()+": "+"Erro: " + e);
            }
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
            args[4] = ((AgenteDispositivo) myAgent).getCell();
            FXMLDocumentController.addAgentFant(args);
        }
        System.out.println(myAgent.getLocalName()+": "+
                fant.getLocalName() + " Fant Retransmitida e sendo eliminada em " + myAgent.getLocalName());
        fant.doDelete();
    }
    
    

}
