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
 * @author rodolfo.soken
 */
public class PassarInfos extends OneShotBehaviour{

    private Fant agente;

    public PassarInfos(Agent a) {
        super(a);
        this.agente = (Fant) a;

    }

    @Override
    public void action() {
        //passar parametros do agente fant para o proximo dispositivo
        //agente.getCellNext().getAgente().doWake();

        agente.getCellNext().getAgente().recebeFant(agente);
        
//        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
//        msg.addReceiver(new AID(agente.getCellNext().getCellId(), true));
//        try {
//            msg.setContentObject(agente);
//            agente.send(msg);
//        } catch (IOException ex) {
//            Logger.getLogger(PassarInfos.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
       

    }

}
