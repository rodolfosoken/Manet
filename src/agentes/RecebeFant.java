/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodolfo
 */
public class RecebeFant extends OneShotBehaviour {

    AgenteDispositivo agente;

    List<String> resultado;

    public RecebeFant(Agent dispositivo) {
        super(dispositivo);
        agente = (AgenteDispositivo) dispositivo;
        if (agente.getFantRecebida() != null) {
            System.out.println(myAgent.getLocalName() + ": "
                    + agente.getFantRecebida().getLocalName()
                    + " Recebida em setup de: " + agente.getLocalName());
        }
    }

    @Override
    public void action() {

        Fant fant = agente.getFantRecebida();
        System.out.println(myAgent.getLocalName() + ": "
                + "Action recebe fant em " + myAgent.getLocalName());

        if (fant != null) {
            System.out.println(myAgent.getLocalName() + ": "
                    + fant.getLocalName() + " Recebida em onWake() de " + myAgent.getLocalName());
            List<String> key = Collections.unmodifiableList(Arrays.asList(fant.getIdSource(), fant.getIdTarget()));

            //verificar primeiramente se é o destino
            if (!agente.getTabela().containsKey(key) && agente.getLocalName().equals(fant.getIdTarget())) {
                //======= DISPOSITIVO ENCONTRADO ============                
                agente.registraFant(key);
                resultado = new ArrayList<>(agente.getFantRecebida().getCellVisitadas());
                resultado.add(agente.getLocalName());
                ((Polygon) fant.getCellFant().getView()).setFill(Color.CHARTREUSE);
                fant.getCellFant().setOnMouseClicked(onMouseDoubleClickHandler);
                agente.updateView();

               showMessage();

            } else if (!agente.getTabela().containsKey(key)) {
                //==========RETRANSMITIR FANT==========
                //se não há registro desta fant na tabela então registrar e retransmitir

                fant.getCellVisitadas().add(agente.getLocalName());
                agente.setFantRecebida(fant);
                agente.registraFant(key);
                agente.setIsRetransmitir(true);
                myAgent.doWake();

            } else {
                //=========REMOVER FANT DUPLICADA ===========
                //caso haja registro na tabela, entao descartar a fant

                System.out.println(myAgent.getLocalName() + ": "
                        + "REMOVENDO: "
                        + fant.getLocalName() + " em " + agente.getLocalName());
                fant.doDelete();
            }

            agente.setIsProcessandoFant(false);
        } else {
            System.out.println(myAgent.getLocalName() + ": "
                    + "FANT NULA!!");
        }
    }

    private void showMessage() {
        System.out.println(myAgent.getLocalName() + ": CAMINHO PERCORRIDO ===> "
                + resultado);

        Thread t = new Thread(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, "CAMINHO PERCORRIDO " + resultado);

            }
        });
        t.start();

    }

    EventHandler<MouseEvent> onMouseDoubleClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()>1)
                showMessage();
        }
    };

}
