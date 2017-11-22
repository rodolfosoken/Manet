/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manetfxml;

import agentes.AgenteDispositivo;
import graph.Graph;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import layout.base.RandomLayout;
import layout.base.Layout;

/**
 *
 * @author Rodolfo
 */
public class FXMLDocumentController implements Initializable {
    
    private static ContainerController containerController;
    private static List <AgentController> agentController;
    private static int qtdDispositivo = 0;
    
    private static Graph graph;
    private Layout layout;
    
    @FXML    
    private ScrollPane scrollPane;
    @FXML    
    private Button buscaButton;
    
    @FXML
    private void criarDispositivo(ActionEvent event) {
        System.out.println("Agente"+qtdDispositivo+" criado!");
        //adicionando agente
        //SINTAXE: addAgent(container, nome_do_agente, classe, parametros de inicializacao)
        Object[] args = new Object[1];
        args[0] = graph;
        addAgent(containerController, "Dispositivo"+qtdDispositivo, AgenteDispositivo.class.getName(), args );
        buscaButton.setDisable(false);
    }
    
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         //iniciando main container
        startMainContainer("127.0.0.1", Profile.LOCAL_PORT, "MANET");
        agentController = new ArrayList<>();
        //adicionando agente RMA
        addAgent(containerController, "rma", jade.tools.rma.rma.class.getName(), null);     
        graph = new Graph();
        scrollPane.setContent(graph.getScrollPane());
        layout = new RandomLayout(graph);
        layout.execute();         
    }
    
    public static void close(){
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        runtime.setCloseVM(true);
        runtime.shutDown();
        System.out.println("VM finalizada");
    }
    
    
    private static void startMainContainer(String host, String port, String name) {
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, host);
        profile.setParameter(Profile.MAIN_PORT, port);
        profile.setParameter(Profile.PLATFORM_ID, name);
        
        containerController = runtime.createMainContainer(profile);
    }

    private static void addAgent(ContainerController cc, String agent, String classe, Object[] args) {
        try {
            //agentController = cc.createNewAgent(agent, classe, args);
            agentController.add(cc.createNewAgent(agent, classe, args));
            agentController.get(qtdDispositivo).start();
            agentController.get(qtdDispositivo).activate();
            qtdDispositivo++;
        } catch (StaleProxyException s) {
            s.printStackTrace();
        }
    }

    /**
     * @return the qtdFant
     */
    public static int getQtdDispositivo() {
        return qtdDispositivo;
    }
    
   

}
