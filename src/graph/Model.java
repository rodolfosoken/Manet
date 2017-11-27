/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    Cell graphParent;

    private List<Cell> allCells;
    private List<Cell> addedCells;
    private List<Cell> removedCells;

    private List<Edge> allEdges;
    private List<Edge> addedEdges;
    private List<Edge> removedEdges;

    Map<String,Cell> cellMap; // <id,cell>

    public Model() {

         graphParent = new Cell( "_ROOT_");

         // clear model, create lists
         clear();
    }

    public void clear() {

        allCells = new ArrayList<>();
        addedCells = new ArrayList<>();
        removedCells = new ArrayList<>();

        allEdges = new ArrayList<>();
        addedEdges = new ArrayList<>();
        removedEdges = new ArrayList<>();

        cellMap = new HashMap<>(); // <id,cell>

    }

    public void clearAddedLists() {
        addedCells.clear();
        addedEdges.clear();
    }

    public List<Cell> getAddedCells() {
        return addedCells;
    }

    public List<Cell> getAllCells() {
        return allCells;
    }

    public List<Edge> getAddedEdges() {
        return addedEdges;
    }

    public List<Edge> getRemovedEdges() {
        return removedEdges;
    }

    public List<Edge> getAllEdges() {
        return allEdges;
    }

    public void addCell(String id, CellType type) {

        switch (type) {

        case RECTANGLE:
            RectangleCell rectangleCell = new RectangleCell(id);
            addCell(rectangleCell);
            break;

        case TRIANGLE:
            TriangleCell circleCell = new TriangleCell(id);
            addCell(circleCell);
            break;

        default:
            throw new UnsupportedOperationException("Unsupported type: " + type);
        }
    }

    public void addCell( Cell cell) {
        if(!addedCells.contains(cell)){
            addedCells.add(cell);

            cellMap.put( cell.getCellId(), cell);
        }
    }

    public void addEdge( String sourceId, String targetId) {
        
        Cell sourceCell = cellMap.get( sourceId);
        Cell targetCell = cellMap.get( targetId);

        Edge edge = new Edge( sourceCell, targetCell);
        if(!addedEdges.contains(edge)&&!allEdges.contains(edge)){
            addedEdges.add( edge);
        }else{
            edge.removeChildParent();
        }

    }
    
    public void removeEdge(String sourceId, String targetId){
        Cell sourceCell = cellMap.get(sourceId);
        Cell targetCell = cellMap.get(targetId);
        
        sourceCell.children.remove(targetCell);
        targetCell.parents.remove(sourceCell);
        Edge edge = new Edge( sourceCell, targetCell);
        edge.removeChildParent();
        removedEdges.add(edge);
        
    }
    
    public void removeCell(Cell cell){
        for(Cell cellProx : allCells){
            removeEdge(cell.getCellId(),cellProx.getCellId());
            removeEdge(cellProx.getCellId(),cell.getCellId());     
        }
        getRemovedCells().add(cell);
    }
    
    /**
     * Attach all cells which don't have a parent to graphParent 
     * @param cellList
     */
    public void attachOrphansToGraphParent( List<Cell> cellList) {

        for( Cell cell: cellList) {
            if( cell.getCellParents().size() == 0) {
                graphParent.addCellChild( cell);
            }
        }

    }

    /**
     * Remove the graphParent reference if it is set
     * @param cellList
     */
    public void disconnectFromGraphParent( List<Cell> cellList) {

        for( Cell cell: cellList) {
            graphParent.removeCellChild( cell);
        }
    }

    public void merge() {

        // cells
        allCells.addAll( addedCells);
        allCells.removeAll(getRemovedCells());

        addedCells.clear();
        getRemovedCells().clear();

        // edges
        allEdges.addAll( addedEdges);
        allEdges.removeAll( removedEdges);

        addedEdges.clear();
        removedEdges.clear();

    }

    /**
     * @return the removedCells
     */
    public List<Cell> getRemovedCells() {
        return removedCells;
    }
}
