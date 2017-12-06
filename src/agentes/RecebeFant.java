/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Rodolfo
 */
public class RecebeFant extends OneShotBehaviour {

    AgenteDispositivo agente;

    public RecebeFant(Agent dispositivo) {
        super(dispositivo);
        agente = (AgenteDispositivo) dispositivo;
        if(agente.getFantRecebida() != null)
            System.out.println(myAgent.getLocalName()+": "+
                    agente.getFantRecebida().getLocalName()+
                    " Recebida em setup de: " +agente.getLocalName());
    }

    @Override
    public void action() {

        Fant fant = agente.getFantRecebida();
        System.out.println(myAgent.getLocalName()+": "+
                "Action recebe fant em "+ myAgent.getLocalName());

        if (fant != null) {
            System.out.println(myAgent.getLocalName()+": "+
                    fant.getLocalName()+" Recebida em onWake() de " + myAgent.getLocalName());
            List<String> key = Collections.unmodifiableList(Arrays.asList(fant.getIdSource(), fant.getIdTarget()));

            if (!agente.getTabela().containsKey(key) && agente.getLocalName().equals(fant.getIdTarget())) {

                System.out.println("DISPOSITIVO ENCONTRADO!");
                agente.registraFant(key);
                ((Polygon) fant.getCellFant().getView()).setFill(Color.CHARTREUSE);
                agente.updateView();

            } else if (!agente.getTabela().containsKey(key)) {
             //se não há registro desta fant na tabela então registrar e retransmitir
             
             //System.out.println(key);
                agente.setFantRecebida(fant);
                agente.registraFant(key);
                agente.setIsRetransmitir(true);
                myAgent.doWake();
            
            } else {
            //caso haja registro na tabela, entao descartar a fant
            
            System.out.println(myAgent.getLocalName()+": "+
                    "REMOVENDO: "
                        + fant.getLocalName() + " em " + agente.getLocalName());
                fant.doDelete();
            }
            
            agente.setIsProcessandoFant(false);
        }else{
            System.out.println(myAgent.getLocalName()+": "+
                    "FANT NULA!!");
        }
    }
}


