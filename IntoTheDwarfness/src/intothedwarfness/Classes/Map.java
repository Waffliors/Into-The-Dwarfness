/******************************************************************************
 ** Class Map, where the map will be created with a tile list and where it   **
 ** will be drawn based on three Matrices: one for the floor, one for the    **
 ** walls and the last for objects, here you will also find the graph used   **
 ** for the artificial intelligence of the enemies and for player movement   **
 ******************************************************************************/
package intothedwarfness.Classes;

import intothedwarfness.IA.Node;
import java.util.List;
import java.util.Arrays;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import intothedwarfness.Interfaces.Drawable;

public class Map extends JPanel implements Drawable {
/* ***************************Class Variables******************************** */
    private final int XPOS, YPOS, LINES, COLUMNS;
    private final BufferedImage SPRITE;   
    private final ArrayList<Tile> TILEMAP;
    private final List<Integer> UNBLOCKEDTILES;
    
    private int wallMap[][];
    private int floorMap[][];
    private int objectMap[][];
    
    private boolean gUnblockedT[][];
    private Node nodeMap[][];
    
    public int actualStage;

/* **************************Class Constructor******************************* */
    public Map(BufferedImage spriteSheet, int lines, int columns) {
        this.XPOS = 0;
        this.YPOS = 0;
        this.LINES = lines;
        this.COLUMNS = columns;
        this.SPRITE = spriteSheet;
        this.TILEMAP = loadTile();
        this.actualStage = 1;
        this.UNBLOCKEDTILES = loadUblockedTiles();

        loadUblockedTiles();
        stageCreator(1);
        nodeMap = loadNodeMap();
    }

    public ArrayList<Tile> getTMList() {
        return TILEMAP;
    }

/* ********************Auxiliary methods of the Constructor****************** */
    //Method that load the TileS
    private ArrayList<Tile> loadTile() {
        ArrayList<Tile> imageTiles = new ArrayList();
        int x, y, srcX1, srcY1, srcX2, srcY2, id = -1;
        for (y = 0; y < 20; y++) {
            srcY1 = 32 * y;
            srcY2 = srcY1 + 32;
            for (x = 0; x < 16; x++) {
                srcX1 = 32 * x;
                srcX2 = srcX1 + 32;
                imageTiles.add(new Tile(srcX1, srcY1, srcX2, srcY2, id += 1));
            }
        }
        return imageTiles;
    }
    
    //Method that will define de unblocked pieces os map
    private List<Integer> loadUblockedTiles (){
        List<Integer> unblockedTiles = 
                Arrays.asList( 20, 23, 24, 25, 26, 27, 28, 29, 30, 48, 49, 50,
                               55, 56, 57, 58, 59, 60, 61, 62, 68, 69, 70, 81, 
                               82, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94,
                               95,100,101,102,103,104,105,106,107,108,109,110,
                              116,117,118,133,134,135,136,137,138,139,140,141,
                              142,143,144,145,146,147,148,149,150,152,153,154,
                              155,156,157,158,160,164,165,169,170,171,172,173,
                              174,176,177,178,179,180,181,182,184,185,186,187,
                              188,189,190,193,194,197,198,199,200,201,202,203,
                              204,205,206,213,214,216,217,218,219,220,221,222,
                              229,233,234,235,237,238,240,241,242,248,250,254,
                              258,259,266,267,269,270,274,275,276,277,278,282,
                              283,284,285,286,304,305);
        
        return unblockedTiles;
    }
    
    //Method that load the NodeMap
    private Node[][] loadNodeMap(){
        int x = 0;
        int y = 0;
        Node RESP[][] = new Node[LINES][COLUMNS];
        
        for(int i = 0; i < LINES; i++){
            for (int j = 0; j < COLUMNS; j++){
                //Cria o nó
                RESP[i][j] = new Node(x,y);
                //Se no nó atual, em todas as matrizes não houver um referencia da lista de tiles
                //checar nas 3 matrizes 
                int temp1 = wallMap[i][j];
                int temp2 = objectMap[i][j];
                int temp3 = floorMap[i][j];
                
                if (!UNBLOCKEDTILES.contains(temp1) && temp1 != 6) {
                    RESP[i][j].setBloqueado(true);
                }
                if (!UNBLOCKEDTILES.contains(temp2) && temp2 != 6) {
                    RESP[i][j].setBloqueado(true);
                }
                if (!UNBLOCKEDTILES.contains(temp3)) {
                    RESP[i][j].setBloqueado(true);
                }
                
                if(actualStage == 1){
                    if(i == 11){
                        if (j == 7 || j == 8){
                            RESP[i][j].setTransition(true);
                        }
                    }
                }
                x += 64;
            }
            x = 0;
            y +=64;
        }
        // PRINT DO MAPA EM GRAPHO
//        for (int i = 0; i < LINES; i++) {
//            System.out.println();
//            for (int j = 0; j < COLUMNS; j++) {
//                if(RESP[i][j].estaBloqueado()){
//                    System.out.print("[1]");
//                }
//                else
//                    System.out.print("[0]");
//            }
//        }
        return RESP;
    }
    
/* ****************************Class Methods********************************* */
    
    //For each stage, all the three matrices are redrawn
    public void stageCreator(int ref) {
        this.actualStage = ref;
        //Create stage 1
        if (actualStage == 1) {
            this.floorMap = new int[][]{
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6, 138, 138, 138, 138, 138, 105, 105, 138, 138, 138, 138, 138,   6,   6},
                {  6,   6, 138, 138, 138, 138, 170, 105, 105, 174, 138, 138, 138, 138,   6,   6},
                {  6,   6, 140, 140, 140, 140, 155, 105, 105, 157, 140, 140, 140, 140,   6,   6},
                {  6,   6, 151, 151, 151, 151, 151, 105, 105, 151, 151, 151, 151, 151,   6,   6},
                {  6,   6, 167, 167, 167, 167, 167, 105, 105, 167, 167, 167, 167, 167,   6,   6},
                {  6,   6, 167, 167, 167, 167, 167, 105, 105, 167, 167, 167, 167, 167,   6,   6},
                {  6,   6, 204, 204, 204, 204, 187, 105, 105, 189, 204, 204, 204, 204,   6,   6},
                {  6,   6, 138, 138, 140, 138, 170, 105, 105, 174, 154, 138, 138, 138,   6,   6},
                {  6,   6, 138, 170, 173, 174, 170, 105, 105, 174, 138, 138, 138, 138,   6,   6},
                {  6,   6, 138, 138, 204, 138, 170, 105, 105, 174, 138, 138, 138, 138,   6,   6},
                {  6,   6, 138, 138, 138, 138, 170, 105, 105, 174, 138, 138, 138, 138,   6,   6},};

            this.objectMap = new int[][]{
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6, 304, 305,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,  68, 209,   6,   6,   6,   6,   6,   6,   6, 240,   6,   6,   6},
                {  6,   6,   6,   6, 225,   6,   6,   6,   6,   6,   6, 226,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6, 209,   6,   6,   6,   6,   6,   6, 208,   6,   6,   6,   6},
                {  6,   6,   6,   6, 225,   6,   6,   6,   6,   6,   6, 225,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,  86,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6, 242,   6,   6,   6,   6, 101,   6,   6, 274,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};

            this.wallMap = new int[][]{
                {  6,   6,   3,   4,   4,   4,   4,   4,   4,   4,   4,   4,   4,   5,   6,   6},
                {  6,   6,  19,  27,  27,  27,  27,  23,  24,  27,  30,  27,  27,  21,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6, 112,  52,  52,  52, 113,   6,   6, 114,  52,  52,  52, 115,   6,   6},};
        }
        //Create stage 2
        if (actualStage == 2) {
            this.floorMap = new int[][]{
                {  6,   6,   6,   6,   6,   6, 170, 105, 105, 174,   6,   6,   6,   6,   6,   6},
                {  6, 138, 139, 140, 141, 138, 170, 105, 105, 174, 138, 138, 138, 138, 138,   6},
                {  6, 154, 155, 156, 157, 158, 170, 105, 105, 174, 138, 138, 138, 138, 138,   6},
                {  6, 170, 171, 172, 173, 174, 170, 105, 105, 174, 138, 138, 138, 138, 138,   6},
                {  6, 186, 187, 188, 189, 190, 170, 105, 105, 157, 140, 140, 140, 140, 140, 140},
                {  6, 138, 138, 138, 138, 138, 170, 105, 105, 105, 105, 105, 105, 105, 105, 105},
                {  6, 138, 138, 138, 138, 138, 170, 105, 105, 105, 105, 105, 105, 105, 105, 105},
                {  6, 138, 138, 138, 138, 138, 170, 105, 105, 189, 204, 204, 204, 204, 204, 204},
                {  6, 138, 138, 138, 138, 138, 170, 105, 105, 174, 234, 235, 236, 237, 138,   6},
                {  6, 138, 138, 138, 138, 138, 170, 105, 105, 174, 250, 251, 252, 253, 236,   6},
                {  6, 138, 138, 138, 138, 138, 170, 105, 105, 174, 138, 138, 138, 138, 138,   6},
                {  6,   6,   6,   6,   6,   6, 170, 105, 105, 174,   6,   6,   6,   6,   6,   6},};

            this.objectMap = new int[][]{
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6, 259,   6,   6,   6,   6,   6,   6,   6, 118,  69,  70,   6,   6,   6},
                {  6,   6,   6, 240,   6,   6,   6,   6,   6,   6, 208,  85, 242, 209,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6, 224,  86, 102, 224,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6, 241,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,  68,  69,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,  86,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};

            this.wallMap = new int[][]{
                {  6,  64,   9,  10,  11,  12,  34,   6,   6,  32,  12,  11,  10,   9,   5,   6},
                {  6,  19,  20,  20,  20,  20,  50,   6,   6,  48,  28,  27,  26,  25,  21,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  66,  33},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  48,  20},
                {  6, 112,  52,  52, 113,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,  64,  33,  33,  65,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,  19,  58,  58,  81,   6,   6,   6,   6,   6,   6,   6,   6,   6, 114,  52},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  99,   6},
                {  6, 112,  52,  52,  52,  52, 113,   6,   6, 114,  52,  52,  52,  52, 115,   6},
                {  6,   6,   6,   6,   6,   6,  18,   6,   6,  16,   6,   6,   6,   6,   6,   6},};
        }
        //Create Stage 3
        if (actualStage == 3) {
            this.floorMap = new int[][]{
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},
                {  6, 142, 174, 138, 203, 204, 204, 204, 204, 204, 204, 138, 138, 170, 142,   6},
                {142, 142, 157, 138, 138, 138, 138, 138, 138, 138, 138, 138, 138, 170, 142,   6},
                {105, 105, 105, 138, 138, 234, 235, 236, 236, 236, 236, 237, 238, 170, 142,   6},
                {105, 105, 105, 138, 138, 250, 252, 252, 252, 252, 252, 252, 254, 170, 142,   6},
                {204, 142, 189, 138, 138, 266, 267, 252, 252, 252, 252, 269, 270, 170, 142,   6},
                {  6, 142, 174, 138, 138, 140, 283 ,284, 284, 284, 284, 138, 138, 170, 142,   6},
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},};
            
            this.objectMap = new int[][]{
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6, 209,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6, 225,   6,   6,   6,   6,   6,   6,  68,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6, 240,   6,   6,   6,   6,  69,   6,   6,   6,   6,   6},
                {  6,   6,   6,  86,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6, 101,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6, 209,   6,   6,   6, 140, 140, 140, 140, 140,   6,   6,   6,   6,   6},
                {  6,   6, 225,   6, 241,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};
            
            this.wallMap = new int[][]{
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18, 256,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                { 33,  65, 272,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                { 49,  50,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                { 52, 113,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18, 256,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18, 272,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},};            
        }
        //Create stage 4
        if (actualStage == 4) {
            this.floorMap = new int[][]{
                {  6, 142, 174, 138, 138, 138, 138, 138, 138, 138, 138, 138, 138, 170, 142,   6},
                {  6, 142, 142, 142, 142, 142, 142, 142, 142, 142, 142, 142, 142, 142, 142,   6},
                {  6, 142, 189, 204, 204, 204, 187, 172, 172, 189, 204, 204, 204, 187, 142,   6},
                {  6, 142, 174, 138, 138, 138, 170, 172, 172, 174, 138, 138, 138, 170, 142,   6},
                {  6, 142, 174, 138, 138, 138, 170, 172, 172, 174, 138, 138, 138, 170, 142,   6},
                {  6, 142, 174, 138, 138, 138, 170, 172, 172, 174, 138, 138, 138, 170, 142,   6},
                {  6, 142, 174, 138, 138, 138, 170, 172, 172, 174, 138, 138, 138, 170, 142,   6},
                {  6, 142, 174, 138, 138, 138, 170, 172, 172, 174, 138, 138, 138, 170, 142,   6},
                {  6, 142, 174, 138, 154, 140, 155, 172, 172, 157, 140, 158, 138, 170, 142,   6},
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},};

            this.objectMap = new int[][]{
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6, 304, 305,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6, 209,   6,   6,   6,   6, 209,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6, 225,   6,   6,   6,   6, 225,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6, 240,   6,   6,   6,   6,   6,   6,   6,   6, 241,   6,   6,   6},
                {  6,   6,   6,   6,   6, 209,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6, 225,   6,   6,   6,   6, 225,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};

            this.wallMap = new int[][]{
                {  6,  64,   9,  10,  11,  12,  12,   9,   8,  12,   9,  12,  10,   9,  67,   6},
                {  6,  19,  26,  26,  27,  28,  26,  23,  24,  26,  26,  27,  28,  26,  21,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},};
        }
        //Create stage 5
        if (actualStage == 5) {
            this.floorMap = new int[][]{
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},
                {  6, 142, 174, 138, 170, 171, 172, 172, 172, 172, 172, 174, 138, 170, 142,   6},
                {  6, 142, 174, 138, 203, 204, 204, 204, 204, 204, 204, 138, 138, 170, 142,   6},
                {  6, 142, 174, 138, 138, 138, 138, 138, 138, 138, 138, 138, 138, 170, 142, 142},
                {  6, 142, 174, 138, 138, 138, 138, 138, 138, 138, 138, 138, 138, 203, 204, 204},
                {  6, 142, 174, 138, 138, 138, 235, 236, 236, 237, 138, 138, 138, 139, 140, 140},
                {  6, 142, 174, 250, 236, 236, 251, 252, 252, 253, 236, 236, 254, 170, 142,   6},
                {  6, 142, 174, 283, 284, 284, 284, 284, 284, 284, 284, 284, 285, 170, 142,   6},
                {  6, 142, 157, 140, 140, 140, 140, 140, 140, 140, 140, 140, 140, 155, 142,   6},
                {  6,   6, 174,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};

            this.objectMap = new int[][]{
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,  85,  86,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6, 209,   6, 102, 101,   6, 209,   6,   6,   6,   6,  85},
                {  6,   6,   6,   6,   6, 225,   6,   6,   6,   6, 225,   6,   6,   6, 117, 118},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};

            this.wallMap = new int[][]{
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6, 259,   6,   6,   6,   6,   6,   6,   6, 258,   6,  66,  33},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  48,  20},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6, 242,   6,   6,   6},
                {  6,  18,   6,   6,   6, 272,   6,   6,   6,   6, 272,   6,   6,   6,   6,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6, 114,  52},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6, 112,  52,  52,  52,  52,  52,  52,  52,  52,  52,  52,  52,  52, 115,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};
        }
        //Create stage 6
        if (actualStage == 6) {
            this.floorMap = new int[][]{
                {  6,   6,   6,   6,   6,   6, 133, 133, 133, 133,   6,   6,   6,   6,   6,   6},
                {  6,  89,  90,  91,  92,  93, 133, 133, 133, 133, 133, 133, 133, 133, 133,   6},
                {  6, 104, 105, 106, 107, 108, 109, 104,  92,  93, 133, 133, 133, 133, 133,   6},
                {  6, 133, 133, 133, 133, 133, 105, 106, 107, 108, 109, 104, 133, 133, 248,   6},
                {133, 133, 248, 133, 133, 248, 133, 133, 106, 107, 108, 109, 104,  92, 133,   6},
                {133, 133, 133, 133, 133,  90,  91,  92,  93, 133, 133, 133, 133, 133, 133,   6},
                {133, 248, 133, 133, 133, 133, 133, 133, 248, 133, 133, 133, 133, 133, 133,   6},
                {133, 133, 106, 107, 108, 109, 104,  92, 107, 108, 109, 104, 133, 248, 133,   6},
                {  6, 133,  90,  91,  92,  93, 133, 133, 133,  90,  91,  92,  93, 133, 133,   6},
                {  6, 133, 133, 133, 133,  92,  93, 133, 133, 133, 133, 248, 133, 133, 248,   6},
                {  6, 133, 133, 248, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};
            this.objectMap = new int[][]{
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6, 210,   6,   6,   6,   6,   6,   6, 209,   6,   6,   6,   6,   6,   6},
                {  6,   6, 226,   6,   6,   6,   6,   6,   6, 225,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6, 209,   6,   6,   6,   6, 209,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6, 225,   6,   6,   6,   6, 225,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6, 210,   6,   6,   6,   6, 209,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6, 226,   6,   6,   6,   6, 225,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};
            
            this.wallMap = new int[][]{
                {  6,  64,   9,  10,  11,  12,  12,   9,   8,  12,   9,  12,  10,   9,  67,   6},
                {  6,  19,  26,  26,  27,  28,  26,  26,  26,  26,  26,  30,  28,  26,  21,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                { 33,  65,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                { 49,  50,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                { 52, 113,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6},
                {  6, 112,  52,  52,  52,  52,  52,  52,  52,  52,  52,  52,  52,  52, 115,   6},};
        }
        //Create stage 7
        if (actualStage == 7) {
           this.floorMap = new int[][]{
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6, 105, 105,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6, 105, 105,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6, 105, 105,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6, 204, 204, 204, 204, 187, 105, 105, 189, 204, 204, 204, 204,   6,   6},
                {  6,   6, 138, 138, 138, 138, 170, 105, 105, 174, 138, 138, 138, 138,   6,   6},
                {  6,   6, 138, 138, 138, 138, 170, 105, 105, 174, 138, 138, 138, 138,   6,   6},
                {  6,   6, 138, 138, 138, 138, 170, 105, 105, 174, 138, 138, 138, 138,   6,   6},
                {  6,   6, 138, 138, 138, 138, 170, 105, 105, 174, 138, 138, 138, 138,   6,   6},};

            this.objectMap = new int[][]{
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6, 209,   6,   6, 209,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6, 225,   6,   6, 225,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6, 240,   6, 101,   6,   6,  86,   6,   6,  85,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6, 209,   6,   6, 209,   6, 241,   6,   6,   6,   6},
                {  6,   6,   6, 274,   6,   6, 225,   6,   6, 225,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};

            this.wallMap = new int[][]{
                {  6,   6,   3,   4,   4,   4,   4,   4,   4,   4,   4,   4,   4,   5,   6,   6},
                {  6,   6,  19,  27,  27,  27,  27,  27,  27,  27,  27,  27,  27,  21,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6,  18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16,   6,   6},
                {  6,   6, 112,  52,  52,  52, 113,   6,   6, 114,  52,  52,  52, 115,   6,   6},};
        }
        //Create stage 8
        if (actualStage == 8) {
            this.floorMap = new int[][]{
                {  6,   6,   6,   6,   6,   6, 133, 133, 133, 133,   6,   6,   6,   6,   6,   6},
                {133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133},
                {133, 133, 133, 264, 133, 101,   6, 101,   6, 133, 248, 133, 133, 133, 133, 133},
                {133, 133, 133, 133, 133, 133,   6,   6,   6, 133, 133, 133, 133, 133, 248, 133},
                {133, 133, 248, 133, 133, 248, 133, 133, 133, 133, 133, 133, 264, 133, 133, 133},
                {133, 133, 133, 133, 133, 264, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133},
                {133, 248, 133, 133, 133, 133, 133, 133, 248, 133, 133, 133, 133, 133, 133, 133},
                {133, 133, 264, 133, 133, 133, 133, 133, 264, 133, 133, 133, 133, 248, 133, 133},
                {133, 133, 133, 133, 133, 248, 133, 133, 133, 133, 133, 133, 264, 133, 133, 133},
                {133, 133, 133, 133, 133, 264, 133, 133, 133, 133, 133, 248, 133, 133, 248, 133},
                {133, 133, 133, 248, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};

            this.objectMap = new int[][]{
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6, 209,   6,   6,   6,   6,   6,   6,   6, 210,   6,   6,   6,   6},
                {  6,   6,   6, 225,   6,   6,   6,   6,   6,   6,   6, 226,   6,   6,   6,   6},
                {  6, 240,   6,   6,   6,   6, 241,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6, 240,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6, 210,   6,   6, 240,   6,   6,   6,   6, 209,   6,   6,   6,   6},
                {  6,   6,   6, 226,   6,   6,   6,   6,   6,   6,   6, 225,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
                {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};

            this.wallMap = new int[][]{
                {  3,   4,   4,   4,   4,   4,  34,   6,   6,  32,   4,   4,   4,  45,   4,   5},
                { 19,  20,  20,  20,  20,  20,  50,   6,   6,  48, 144, 145, 145, 148,  20,  21},
                { 18,   6,   6,   6,   6,   6,   6,   6,   6,   6, 160, 161, 161, 164,   6,  16},
                { 18,   6,   6,   6,   6,   6,   6,   6,   6,   6, 176, 177, 177, 180,   6,  16},
                { 18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16},
                { 18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16},
                { 18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16},
                { 18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16},
                { 18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16},
                { 18,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,  16},
                { 35,  36,  36,  36,  36,  36,  36,  36,  36,  36,  36,  36,  36,  36,  36,  37},
                { 51,  52,  52,  52,  52,  52,  52,  52,  52,  52,  52,  52,  52,  52,  52,  53},};            
        }
        //After Create the new map, load the graph
        this.nodeMap = loadNodeMap();
    }

/* *************************Overridden Methods******************************* */
    @Override
    public void paintComponent(Graphics g) {
        for (int x = 0; x < this.floorMap[0].length; x++) {
            for (int y = 0; y < this.floorMap.length; y++) {
                g.drawImage(SPRITE, 
                		(x * 64) + XPOS, (y * 64) + YPOS, 
                		(x * 64) + 64 + XPOS, (y * 64) + 64 + YPOS,     
                        TILEMAP.get(this.floorMap[y][x]).getSrcX1(), TILEMAP.get(this.floorMap[y][x]).getSrcY1(),        
                        TILEMAP.get(this.floorMap[y][x]).getSrcX2(), TILEMAP.get(this.floorMap[y][x]).getSrcY2(),       
                        null);        
                               
                g.drawImage(SPRITE, 
                        (x * 64) + XPOS, (y * 64) + YPOS, 
                        (x * 64) + 64 + XPOS, (y * 64) + 64 + YPOS,
                        TILEMAP.get(this.objectMap[y][x]).getSrcX1(), TILEMAP.get(this.objectMap[y][x]).getSrcY1(),
                        TILEMAP.get(this.objectMap[y][x]).getSrcX2(), TILEMAP.get(this.objectMap[y][x]).getSrcY2(), 
                        null);
                
                g.drawImage(SPRITE,
                        (x * 64) + XPOS, (y * 64) + YPOS,
                        (x * 64) + 64 + XPOS, (y * 64) + 64 + YPOS, 
                        TILEMAP.get(this.wallMap[y][x]).getSrcX1(), TILEMAP.get(this.wallMap[y][x]).getSrcY1(),
                        TILEMAP.get(this.wallMap[y][x]).getSrcX2(), TILEMAP.get(this.wallMap[y][x]).getSrcY2(), 
                        null);
            }
        }
    }
    public Node[][] getNodeMap() {
        return nodeMap;
    }

    @Override
    public Boolean isStage(Map map) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}