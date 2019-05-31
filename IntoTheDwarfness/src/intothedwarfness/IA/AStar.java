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

    private static LinkedList<Node> makePath(Node start, Node goal, Map mapa) {
        LinkedList<Node> listaAuxiliar = new LinkedList();
        Node noAtual = goal;

        int contador = 0;
        while (!listaAuxiliar.contains(start) || contador > mapSize) {
            listaAuxiliar.add(noAtual);

            noAtual = noAtual.getFather();
            contador++;
        }
        Collections.reverse(listaAuxiliar);

        //imprimir caminho
        System.out.println("Caminho: ");
        for (Node no : listaAuxiliar) {
            System.out.print(" -> " + no);
        }
        //inicio artificio apenas para printar caminho
        for (Node no : mapa.getNodeList()) {
            if (!listaAuxiliar.contains(no)) {
                no.setFather(null);
            }

        }
        //fim do artificio

        System.out.println("");
        desenha(mapa);
        System.out.println("Fim ! ");

        //retorno do caminho
        return listaAuxiliar;
    }

    public static void desenha(Map mapa) {
        System.out.println("");
        for (int i = 0; i < mapa.getLINES(); i++) {
            for (int j = 0; j < mapa.getCOLUMNS(); j++) {
                Node no = mapa.getNode(i, j);
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
