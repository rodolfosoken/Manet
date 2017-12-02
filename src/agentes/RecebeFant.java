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

/**
 *
 * @author Rodolfo
 */
public class RecebeFant extends OneShotBehaviour{

    AgenteDispositivo agente;
    public RecebeFant(Agent a) {
        super(a);
        agente = (AgenteDispositivo) a;
        System.out.println("Fant Recebida em "+a.getLocalName());
    }
    
    

    @Override
    public void action() {
        
        Fant fant = agente.getFantRecebida();

       
        if (fant != null) {
            List<String> key = Collections.unmodifiableList(Arrays.asList(fant.getIdSource(), fant.getIdTarget()));

            if (agente.getLocalName().equals(fant.getIdTarget())) {

                System.out.println("DISPOSITIVO ENCONTRADO!");

            } else {
                //se não há registro desta fant na tabela então registrar e retransmitir
                if (!agente.getTabela().containsKey(key)) {
                    //System.out.println(key);
                    agente.setFantRecebida(fant);
                    agente.registraFant(key,0);
                    agente.setIsRetransmitir(true);
                    agente.doWake();
                    //caso haja registro na tabela, entao descartar a fant
                } else {
                    System.out.println("Removendo: " + 
                            fant.getLocalName() + " em "+ agente.getLocalName());
                    fant.doDelete();
                }

            }
        }
    }
    
}
