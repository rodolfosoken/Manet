/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layout.base;

import java.util.List;
import java.util.Random;
import graph.Cell;
import graph.Graph;
import javafx.application.Platform;



public class RandomLayout extends Layout {

    Graph graph;

    Random rnd = new Random();

    public RandomLayout(Graph graph) {

        this.graph = graph;

    }

    public void execute() {
        List<Cell> cells = graph.getModel().getAllCells();

        for (Cell cell : cells) {

            double x = rnd.nextDouble() * 500;
            double y = rnd.nextDouble() * 500;
            cell.setPosX(x);
            cell.setPosY(y);

            cell.relocate(x, y);
            
            //atualiza a view na thread principal
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                graph.updateNode(cell);
            }
        });

        }

    }

}
