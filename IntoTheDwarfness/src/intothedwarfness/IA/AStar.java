/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness.IA;

import intothedwarfness.Classes.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author T-Gamer
 */
public class AStar {
    public static List<Node> listaFechada = new ArrayList();
    public static List<Node> listaAberta = new ArrayList();
    public static List<Node> caminho = new ArrayList();    
    public static int colunasDoMapa = 0;
    public static int linhasDoMapa = 0;
    public static int tamanhoDoMapa = 0;
   
    public static List<Node> aEstrela(Node noInicial, Node noDestino, Map mapa)
    {
        System.out.println(noInicial+""+noDestino);
        colunasDoMapa = mapa.getCOLUMNS();
        linhasDoMapa = mapa.getLINES();
        tamanhoDoMapa = mapa.getNodeList().size();
        
        listaFechada.clear();
        listaAberta.clear();
        caminho.clear();
        boolean achouCaminho = false;
    
        Node noAtual = noInicial;
        listaAberta.add(noInicial);
        
        while(!achouCaminho)
        {
            noAtual = procularMenorF();
            listaAberta.remove(noAtual);
            listaFechada.add(noAtual);
            achouCaminho = noAtual.equals(noDestino);
            
            
            for(Node no: noAtual.getNeighbors())
            {
                if(no.isBlocked() || listaFechada.contains(no))
                {
                    continue;
                }else{
                    if(!listaAberta.contains(no))
                    {
                        listaAberta.add(no);
                        no.setFather(noAtual);
                        no.setH(calcularH(no, noDestino));
                        no.setG(calcularG(no, noAtual));
                        no.setF(calcularF(no));
                    }else{
                        if(no.getG()<noAtual.getG())
                        {
                            no.setFather(noAtual);
                            no.setG(calcularG(noAtual, no));
                            no.setF(calcularF(no));
                        }
                    
                    }
                }
            
            }
            if(listaAberta.isEmpty())
            {
                System.out.println("Não achou ");
                return null;
            }
        }
        
        return montaCaminho(noInicial, noDestino, mapa);
    }
    
    public static Node procularMenorF() {
        Collections.sort(listaAberta, Comparator.comparing(Node::getF));
        return listaAberta.get(0);
        
    }
    
    
       
    public static float calcularF(Node no)
    {
        return no.getG()+ no.getH();

    }
    
    public static float calcularG(Node noAtual, Node noVizinho)
    {
        if (noVizinho.getId() % colunasDoMapa == noAtual.getId() % colunasDoMapa || noVizinho.getId() + 1 == noAtual.getId() || noVizinho.getId() - 1 == noAtual.getId()) {
            return noVizinho.getG() + 10;
        } else {
            return noVizinho.getG() + 14;
        }
        
    }
 
    
    public static float calcularH(Node noAtual, Node noDestino)
    {
        int posicaoDestinoX = (noDestino.getId()%colunasDoMapa)+1;
        int posicaoNoAtualX = (noAtual.getId()%colunasDoMapa)+1;
        
        int distanciaX = posicaoDestinoX > posicaoNoAtualX ? posicaoDestinoX - posicaoNoAtualX : posicaoNoAtualX - posicaoDestinoX;
        
        int posicaoDestinoY = (noDestino.getId()/linhasDoMapa)+1;
        int posicaoNoAtualY = (noAtual.getId()/linhasDoMapa)+1;
        
        int distanciaY = posicaoDestinoY > posicaoNoAtualY ? posicaoDestinoY - posicaoNoAtualY : posicaoNoAtualY - posicaoDestinoY;
        
        float distanciaTotal = (float)Math.sqrt((Math.pow(distanciaX, 2)+Math.pow(distanciaY, 2)))*10;
                
        return distanciaTotal;
    }

    private static List<Node> montaCaminho(Node noInicial, Node noDestino, Map mapa) {
        List<Node> listaAuxiliar = new ArrayList();
        Node noAtual = noDestino;
        
        int contador = 0;
        while (!listaAuxiliar.contains(noInicial) || contador > tamanhoDoMapa)
        {
            listaAuxiliar.add(noAtual);
            
            noAtual = noAtual.getFather();
                        
            contador++;
        }
        Collections.reverse(listaAuxiliar);
        
        
        //imprimir caminho
        System.out.println("Caminho: ");
        for (Node no : listaAuxiliar) {
            System.out.print(" -> " + no.getId());
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
