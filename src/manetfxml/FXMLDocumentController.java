/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manetfxml;

import agentes.AgenteDispositivo;
import agentes.Fant;
import graph.Graph;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import layout.base.RandomLayout;
import layout.base.Layout;

/**
 *
 * @author Rodolfo
 */
public class FXMLDocumentController implements Initializable {
    
    private static ContainerController containerController;
    private static AgentController agentController;
    private static int qtdDispositivo;
    
    private static int qtdFant;
    private static boolean isAdicionandoFant;
    
    
    private static Graph graph;
    private Layout layout;
    private Object[] args;
    
    @FXML    
    private ScrollPane scrollPane;
    @FXML    
    private Button buscaButton;
    @FXML
    private TextField idDestino;
    
    @FXML
    private void criarDispositivo(ActionEvent event) {
        System.out.println("Dispositivo"+qtdDispositivo+" criado!");
        //adicionando agente
        //SINTAXE: addAgent(container, nome_do_agente, classe, parametros de inicializacao)
        addAgent("D"+qtdDispositivo, AgenteDispositivo.class.getName(), args );
        qtdDispositivo++;
        //layout.execute();
    }
    
    @FXML
    private void inciarBusca(ActionEvent e){
        //System.out.println("iniciarBusca");
        args[2] = idDestino.getText().toUpperCase();
        try {
            containerController.getAgent(graph.getCellSelected().getCellId()).activate();
        } catch (ControllerException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         //iniciando main container
        startMainContainer("127.0.0.1", Profile.LOCAL_PORT, "MANET");
        //adicionando agente RMA
        addAgent( "rma", jade.tools.rma.rma.class.getName(), null);     
        graph = new Graph();
        scrollPane.setContent(graph.getScrollPane());
        layout = new RandomLayout(graph);
        
        graph.getScrollPane().setOnMousePressed(clickMouse);
        
        args = new Object[3];
        args[0] = graph;
        args[1] = containerController;
        
        qtdDispositivo = 0;
        qtdFant = 0;
        isAdicionandoFant=false;

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

    public static void addAgent(String agent, String classe, Object[] args) {
        try {
            //agentController = cc.createNewAgent(agent, classe, args);
            agentController = containerController.createNewAgent(agent, classe, args);
            agentController.start();
        } catch (StaleProxyException s) {
            s.printStackTrace();
        }
    }
    
    
    public synchronized static void addAgentFant(Object[] args) {
        try {
            //agentController = cc.createNewAgent(agent, classe, args);
            agentController = containerController.createNewAgent("F" + ++qtdFant, Fant.class.getName(),args);
            agentController.start();
        } catch (StaleProxyException s) {
            s.printStackTrace();
        }
    }

    EventHandler<MouseEvent> clickMouse = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.isPrimaryButtonDown() && graph.getCellSelected()!=null){
                idDestino.setDisable(false);
                buscaButton.setDisable(false);
            }
        }
    };
    


}
