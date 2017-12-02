/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

/**
 *
 * @author rodolfo.soken
 */
public class PassarInfos extends WakerBehaviour{

    private Fant agente;

    public PassarInfos(Agent a) {
        super(a,1000);
        this.agente = (Fant) a;
        System.out.println("Infos de "+a.getLocalName()+
                " chegando em " + agente.getCellNext().getAgente().getLocalName());
    }

    @Override
    public void onWake() {
        //passar parametros do agente fant para o proximo dispositivo
       
//        agente.getCellNext().getAgente().setFantRecebida(agente);
//        agente.getCellNext().getAgente().doWake();
           
           agente.getCellNext().getAgente().recebeFant(agente);
    }

}
