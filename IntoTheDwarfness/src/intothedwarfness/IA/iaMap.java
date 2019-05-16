/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package intothedwarfness.IA;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author T-Gamer
 */
public class iaMap {
    public static List<Node> mapa = new ArrayList();
    public static int linhas ;
    public static int colunas;
    
    public iaMap (int linhas, int colunas){
        this.linhas= linhas;
        this.colunas = colunas;
        criaMapa();
        configuraMapa();
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public static List<Node> getMapa() {
        return mapa;
    }
    
    public static void criaMapa()
    {
        int contador = 0;
        for (int i = 0; i<linhas; i++)
        {
            for (int j = 0; j<colunas; j++)
            {
                
                Node no = new Node(contador);
                mapa.add(no);
                
                contador++;
            }
        }
    }
    
    
    public static void configuraMapa()
    {
        for(Node no: mapa)
        {
            no.vizinhos.addAll(acharCantos(no));
            no.vizinhos.addAll(acharOrtogonais(no));
        }
    }
    
    public static List<Node> acharCantos(Node no)
    {
        int id = no.getId();
        List<Node> list = new ArrayList();
        
        //calcular linha
        int linhaDoNo = (no.getId()/linhas)+1;
        //calcula coluna
        int colunaDoNo = (no.getId()%colunas)+1;
            
        //pega canto superior esquerda
        if (linhaDoNo > 1 && colunaDoNo > 1) {
            list.add(mapa.get((id - colunas) - 1));
        }
        //pega canto superior direita
        if (linhaDoNo > 1 && colunaDoNo < colunas) {
            list.add(mapa.get((id - colunas) + 1));
        }
        
        //pegar canto infoerior esquerdo
        if (linhaDoNo < mapa.size() / linhas && colunaDoNo > 1) {
            list.add(mapa.get((id + colunas) - 1));
        }
        //pegar canto inferior direito
        if (linhaDoNo < mapa.size() / linhas && colunaDoNo < colunas) {
            list.add(mapa.get((id + colunas) + 1));
        }

        return list;
    }
    
    public static List<Node> acharOrtogonais (Node no)
    {   
        //calcular linha
        int linhaDoNo = (no.getId()/linhas)+1;
        //calcula coluna
        int colunaDoNo = (no.getId()%colunas)+1;
        List<Node> list = new ArrayList();
        int id = no.getId();
        //pegar vizinho esquerdo
        if (colunaDoNo > 1) {
            list.add(mapa.get(id - 1));
        }
        //pegar vizinho direito
       	if (colunaDoNo < colunas) {
            list.add(mapa.get(id + 1));
        }
        //pegar vizinho cima
        if (linhaDoNo > 1) {
            list.add(mapa.get((id - linhas)+1));
        }
        //pegar vizinho baixo
        if (linhaDoNo < mapa.size()/linhas) {
            list.add(mapa.get(id + colunas));
        }
        
        return list;
    }
}
