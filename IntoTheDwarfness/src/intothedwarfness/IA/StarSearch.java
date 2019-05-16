
package intothedwarfness.IA;

import intothedwarfness.Classes.Map;
import intothedwarfness.Classes.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StarSearch {
    public static List<Tile> listaFechada = new ArrayList();
    public static List<Tile> listaAberta = new ArrayList();
    public static List<Tile> caminho = new ArrayList();    
    public static int colunasDoMapa = 0;
    public static int linhasDoMapa = 0;
    public static int tamanhoDoMapa = 0;
   
    public static List<Tile> aEstrela(Tile tileInicial, Tile tileDestitile, Map mapa)
    {
        colunasDoMapa = mapa.getColumns();
        linhasDoMapa = mapa.getLines();
        tamanhoDoMapa = mapa.getTMList().size();
        
        listaFechada.clear();
        listaAberta.clear();
        caminho.clear();
        boolean achouCaminho = false;
    
        Tile tileAtual = tileInicial;
        listaAberta.add(tileInicial);
        
        while(!achouCaminho)
        {
            tileAtual = procularMetilerF();
            listaAberta.remove(tileAtual);
            listaFechada.add(tileAtual);
            achouCaminho = tileAtual.equals(tileDestitile);
            
            
            for(Tile tile: tileAtual.getNeighbors())
            {
                if(tile.isBlocked()|| listaFechada.contains(tile))
                {
                    continue;
                }else{
                    if(!listaAberta.contains(tile))
                    {
                        listaAberta.add(tile);
                        tile.setFather(tileAtual);
                        tile.setH(calcularH(tile, tileDestitile));
                        tile.setG(calcularG(tile, tileAtual));
                        tile.setF(calcularF(tile));
                    }else{
                        if(tile.getG()<tileAtual.getG())
                        {
                            tile.setFather(tileAtual);
                            tile.setG(calcularG(tileAtual, tile));
                            tile.setF(calcularF(tile));
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
        
        return montaCaminho(tileInicial, tileDestitile, mapa);
    }
    
    public static Tile procularMetilerF() {
        Collections.sort(listaAberta, Comparator.comparing(Tile::getF));
        return listaAberta.get(0);
        
    }
    
    
       
    public static float calcularF(Tile tile)
    {
        return tile.getG()+ tile.getH();

    }
    
    public static float calcularG(Tile tileAtual, Tile tileVizinho)
    {
        if (tileVizinho.getId() % colunasDoMapa == tileAtual.getId() % colunasDoMapa || tileVizinho.getId() + 1 == tileAtual.getId() || tileVizinho.getId() - 1 == tileAtual.getId()) {
            return tileVizinho.getG() + 10;
        } else {
            return tileVizinho.getG() + 14;
        }
        
    }
 
    
    public static float calcularH(Tile tileAtual, Tile tileDestitile)
    {
        int posicaoDestitileX = (tileDestitile.getId()%colunasDoMapa)+1;
        int posicaoNoAtualX = (tileAtual.getId()%colunasDoMapa)+1;
        
        int distanciaX = posicaoDestitileX > posicaoNoAtualX ? posicaoDestitileX - posicaoNoAtualX : posicaoNoAtualX - posicaoDestitileX;
        
        int posicaoDestitileY = (tileDestitile.getId()/linhasDoMapa)+1;
        int posicaoNoAtualY = (tileAtual.getId()/linhasDoMapa)+1;
        
        int distanciaY = posicaoDestitileY > posicaoNoAtualY ? posicaoDestitileY - posicaoNoAtualY : posicaoNoAtualY - posicaoDestitileY;
        
        float distanciaTotal = (float)Math.sqrt((Math.pow(distanciaX, 2)+Math.pow(distanciaY, 2)))*10;
                
        return distanciaTotal;
    }

    
    
    private static List<Tile> montaCaminho(Tile tileInicial, Tile tileDestitile, Map mapa) {
        List<Tile> listaAuxiliar = new ArrayList();
        Tile tileAtual = tileDestitile;
        int contador = 0;
        while (!listaAuxiliar.contains(tileInicial) || contador > tamanhoDoMapa)
        {
            listaAuxiliar.add(tileAtual);
            
            tileAtual = tileAtual.getFather();
                        
            contador++;
        }
        Collections.reverse(listaAuxiliar);
        
        
        //imprimir caminho
        System.out.println("Caminho: ");
        for(Tile tile: listaAuxiliar)
        {
            System.out.print(" -> " + tile.getId());
        }
        //inicio artificio apenas para printar caminho
        for(Tile tile: mapa.getTMList())
        {
          if(!listaAuxiliar.contains(tile))  tile.setFather(null);
          
        }
        //fim do artificio
        
        System.out.println("");
        desenha(mapa);
        System.out.println("Fim ! ");
        
        //retortile do caminho
        return listaAuxiliar;
    }
    
    public static void desenha(Map mapa){
      System.out.println("");
      for (int i = 0; i<mapa.getLines(); i++)
        {
            for (int j = 0; j<mapa.getColumns(); j++)
            {
              Tile tile = mapa.getTMList().get((i*mapa.getColumns())+j);
              if(tile.getFather()!= null ){
                System.out.print("[-]");
              }else if(tile.isBlocked()){
                System.out.print("[X]");
              }else{
                System.out.print("[ ]");
              }
            }
             System.out.println();
        }
    }
}
