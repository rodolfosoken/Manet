/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Rodolfo
 */
public class RecebeFant extends WakerBehaviour {

    AgenteDispositivo agente;

    public RecebeFant(Agent dispositivo) {
        super(dispositivo, 200);
        agente = (AgenteDispositivo) dispositivo;
        System.out.println("Fant Recebida em " + dispositivo.getLocalName());
    }

    @Override
    public void onWake() {

        Fant fant = agente.getFantRecebida();

        if (fant != null) {
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
            
            System.out.println("Removendo: "
                        + fant.getLocalName() + " em " + agente.getLocalName());
                fant.doDelete();
            }

        }
    }
}


