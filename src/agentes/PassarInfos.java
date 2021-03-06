/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import java.util.Random;

/**
 *
 * @author rodolfo.soken
 */
public class PassarInfos extends SimpleBehaviour {

    private Fant agente;
    private boolean fim = false;
    private int nExecucoes;

    public PassarInfos(Agent a) {
        super(a);
        nExecucoes = 0;
        this.agente = (Fant) a;
        System.out.println(myAgent.getLocalName()+": "+
                "Infos de " + a.getLocalName()
                + " chegando em " + agente.getCellNext().getAgente().getLocalName());
    }

    @Override
    public void action() {
        //passar parametros do agente fant para o proximo dispositivo                  
        fim = agente.getCellNext().getAgente().recebeFant(agente);
        
        //se ainda há uma fant sendo processada e esta nao for recebida
        //entao esperar por um tempo aleatorio e tentar novamente
        if (!fim) {
            try {
                long LOWER_RANGE = 50; //assign lower range value
                long UPPER_RANGE = 200; //assign upper range value
                Random random = new Random();

                long randomValue = LOWER_RANGE
                        + (long) (random.nextDouble() * (UPPER_RANGE - LOWER_RANGE));
                Thread.sleep(randomValue);
            } catch (Exception e) {
                System.out.println(myAgent.getLocalName()+": "+"Erro: " + e);
            }
        }else{
            if(nExecucoes > 0)
            System.out.println(myAgent.getLocalName()+": "+
                    "OK: Envio de "+myAgent.getLocalName()+" para " +
                    agente.getCellNext().getAgente().getLocalName()+" concluido!");
            if(nExecucoes>30){
                fim=true;
                System.out.println("FALHA NO ENVIO DA : "+myAgent.getLocalName());
            }
        }
        
        nExecucoes++;
    }

    @Override
    public boolean done() {
        return fim;
    }

}
