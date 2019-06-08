/** *****************************************************************************
 **     AI Class                                                              **
 **                                                                           **
 ** This class is responsible for applying the                                **
 ** algorithm A * and returning the shortest path from one Node to another    **
 **                                                                           **
 **                                                                           **
 ** The algorithm used as the basis for this code was provided by teacher     **
 ** Adalberto Bosco Pereira, for contact follows the link:                    **
 **       https://www.linkedin.com/in/adalberto-pereira-08497517              **
 ***************************************************************************** */
package intothedwarfness.IA;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Collections;
import intothedwarfness.Classes.Map;

public class AStar {

    public static int mapSize;
    public static int mapLines;
    public static int mapCollumns;
    public static List<Node> openList = new ArrayList();
    public static List<Node> closedList = new ArrayList();

    /**
     * Method that get the star and the final Node to make the path
     *
     * @param start: the start Node
     * @param goal: the final Node
     * @param map: the game map
     * @return a List that contains the path
     */
    public static List<Node> aStar(Node start, Node goal, Map map) {
        openList.clear();
        closedList.clear();
        mapLines = map.getLINES();
        mapCollumns = map.getCOLUMNS();
        mapSize = map.getNodeList().size();

        boolean findPath = false;
        openList.add(start);
        Node currentNode;

        //While not find the path
        while (!findPath) {
            currentNode = getBestF();
            openList.remove(currentNode);
            closedList.add(currentNode);
            findPath = currentNode.equals(goal);
            for (Node node : currentNode.getNeighbors()) {
                if (node.isBlocked() || closedList.contains(node)) {
                    continue;
                } else {
                    if (!openList.contains(node)) {
                        openList.add(node);
                        node.setFather(currentNode);
                        node.setH(calculateH(node, goal));
                        node.setG(calculateG(node, currentNode));
                        node.setF(calculateF(node));
                    } else {
                        if (node.getG() < currentNode.getG()) {
                            node.setFather(currentNode);
                            node.setG(calculateG(currentNode, node));
                            node.setF(calculateF(node));
                        }
                    }
                }
            }
            //If open list is empty, there's no path, then return null
            if (openList.isEmpty()) {
                return null;
            }
        }
        return makePath(start, goal, map);
    }

    /**
     * @return the Node with the lowest fitness 
     */
    private static Node getBestF() {
        Collections.sort(openList, Comparator.comparing(Node::getF));
        return openList.get(0);
    }

    /**
     * Method that receives a Node and calculate the fitness
     * @param no
     * @return 
     */
    private static float calculateF(Node node) {
        return node.getG() + node.getH();
    }

    /**
     * Method that calculate the current cost until goal node
     * 
     * @param currentNode: the current Node
     * @param neighbor: the neighbor of the current Node
     * @return: the current cost
     */
    private static float calculateG(Node currentNode, Node neighbor) {
        if (neighbor.getId() % mapCollumns == currentNode.getId() % mapCollumns
                || neighbor.getId() + 1 == currentNode.getId()
                || neighbor.getId() - 1 == currentNode.getId()) {

            return neighbor.getG() + 10;
        } else {
            return neighbor.getG() + 14;
        }
    }

    /**
     * Method that calculate the heuristic of the path
     * 
     * @param currentNode : the currentNode 
     * @param goalNode : the final Node
     * @return : the heuristic until the final Node
     */
    private static float calculateH(Node currentNode, Node goalNode) {   
        // Get the x position of the goal Node
        int goalPosX = (goalNode.getId() % mapCollumns) + 1;
        // Get the x position of the current Node
        int currentPosX = (currentNode.getId() % mapCollumns) + 1;
        //Get the x distance between the nodes
        int xDistance;
        if(goalPosX > currentPosX){
            xDistance = goalPosX - currentPosX;
        }else{
            xDistance = currentPosX - goalPosX;
        }
          
        // Get the y position of the goal Node
        int goalPosY = (goalNode.getId() / mapLines) + 1;
        // Get the Y position of the current Node
        int currentPosY = (currentNode.getId() / mapLines) + 1;
        int yDistance;
        if(goalPosY > currentPosY){
            yDistance = goalPosY - currentPosY;
        }else{
            yDistance = currentPosY - goalPosY;
        }
            
        //Calculate the distance between the Nodes
        float distanciaTotal = (float) Math.sqrt((Math.pow(xDistance, 2) + 
                                       Math.pow(yDistance, 2))) * 10;
        return distanciaTotal;
    }

    /**
     * Mathod that makes the path
     * 
     * @param start : get the start node
     * @param goal : get the goal node
     * @param map : get thegame map
     * @return : a Linked list with the path
     */
    private static LinkedList<Node> makePath(Node start, Node goal, Map map) {
        LinkedList<Node> auxList = new LinkedList();
        Node currentNode = goal;
        int cont = 0;
        
        while (!auxList.contains(start) || cont > mapSize) {
            auxList.add(currentNode);
            currentNode = currentNode.getFather();
            cont++;
        }
        Collections.reverse(auxList);
               
        //Uncomment to see the path being printed
        /*
        System.out.println("Caminho: ");
        for (Node no : auxList) {
            System.out.print(" -> " + no);
        }
        for (Node no : map.getNodeList()) {
            if (!auxList.contains(no)) {
                no.setFather(null);
            }
        }     
        System.out.println("");
        draw(map);
        System.out.println("Fim ! ");
        */
        //Return the path
        return auxList;
    }

    /**
     * Method that draw the map
     * @param map : the map of the game
     */
    private static void draw(Map map) {
        System.out.println("");
        for (int i = 0; i < map.getLINES(); i++) {
            for (int j = 0; j < map.getCOLUMNS(); j++) {
                Node no = map.getNode(i, j);
                if (no.getFather() != null) {
                    System.out.print("[-]");
                } else if (no.isBlocked()) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }
}
