/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodolfo
 */
public class RecebeFant extends OneShotBehaviour{

    AgenteDispositivo agente;
    public RecebeFant(Agent a) {
        super(a);
        agente = (AgenteDispositivo) a;
    }
    
    

    @Override
    public void action() {
        
        Fant fant = null;
        ACLMessage msg = agente.receive();
        try {
            fant = (Fant) msg.getContentObject();
        } catch (UnreadableException ex) {
            Logger.getLogger(AgenteDispositivo.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (fant != null) {
            String[] key = {fant.getIdSource(), fant.getIdTarget()};

            //se há registro na tabela entao ele é o destino ou duplicado
            if (agente.getTabela().containsKey(key) || fant.getIdTarget().equals(myAgent.getLocalName())) {
                if (fant.getIdTarget().equals(myAgent.getLocalName())) {
                    System.out.println("DISPOSITIVO ENCONTRADO");
                } else {
                    fant.doDelete();
                }
            //caso contrário será registrado
            } else {
                agente.setFantRecebida(fant);
                agente.registraFant(key);
                agente.setIsRetransmitir(true);
                agente.doWake();
            }
        }else{
            System.out.println("Falha ao receber fant");
        }
        
    }
    
}
