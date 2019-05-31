package intothedwarfness.IA;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import intothedwarfness.Classes.Map;
import java.util.LinkedList;

public class AStar {

    public static int mapSize = 0;
    public static int mapLines = 0;
    public static int mapCollumns = 0;
    public static List<Node> openList = new ArrayList();
    public static List<Node> closedList = new ArrayList();

    //Method that get the pathe
    public static List<Node> aEstrela(Node start, Node goal, Map map) {
        mapLines = map.getLINES();
        mapCollumns = map.getCOLUMNS();
        mapSize = map.getNodeList().size();
        openList.clear();
        closedList.clear();
        boolean findPath = false;
        openList.add(start);
        Node currentNode;

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
            if (openList.isEmpty()) {
                System.out.println("Não achou ");
                return null;
            }
        }
        return makePath(start, goal, map);
    }

    public static Node getBestF() {
        Collections.sort(openList, Comparator.comparing(Node::getF));
        return openList.get(0);
    }

    public static float calculateF(Node no) {
        return no.getG() + no.getH();
    }

    public static float calculateG(Node currentNode, Node neighbor) {
        if (neighbor.getId() % mapCollumns == currentNode.getId() % mapCollumns
                || neighbor.getId() + 1 == currentNode.getId()
                || neighbor.getId() - 1 == currentNode.getId()) {

            return neighbor.getG() + 10;
        } else {
            return neighbor.getG() + 14;
        }
    }

    public static float calculateH(Node currentNode, Node goal) {
        //Get the goal x position and the current node x position
        int goalX = (goal.getId() % mapCollumns) + 1;
        int currentNodeX = (currentNode.getId() % mapCollumns) + 1;
        //Get the distance in X
        int xDistance = goalX > currentNodeX ? goalX - currentNodeX : currentNodeX - goalX;

        //Get the goal y position and the current node x position
        int goalY = (goal.getId() / mapLines) + 1;
        int currentNodeY = (currentNode.getId() / mapLines) + 1;
        //Get the distane in Y
        int yDistance = goalY > currentNodeY ? goalY - currentNodeY : currentNodeY - goalY;

        //Get the total distance
        float totalDistance = (float) Math.sqrt((Math.pow(xDistance, 2) + Math.pow(yDistance, 2))) * 10;
        return totalDistance;
    }

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

        //imprimir caminho
        System.out.println("Caminho: ");
        for (Node node : auxList) {
            System.out.print(" -> " + node);
        }
        //inicio artificio apenas para printar caminho
        for (Node no : map.getNodeList()) {
            if (!auxList.contains(no)) {
                no.setFather(null);
            }

        }
        //fim do artificio

        System.out.println("");
        draw(map);
        System.out.println("Fim ! ");

        //retorno do caminho
        return auxList;
    }

    public static void draw(Map map) {
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
