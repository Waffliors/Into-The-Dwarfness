/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Class Map, where Map will be created with a TileMap list and where it will *
 *  be drawn based on three Matrices: one for the floor, one for the walls and *
 *  the last for objects                                                       *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package intothedwarfness.Classes;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Map extends JPanel {
    
/*-----------------------------------------------------------------------------*
 *                              Class Variables                                *
 *-----------------------------------------------------------------------------*/
    private int xPos, yPos;
    private final BufferedImage SSheet;
    private final ArrayList<TileMap> TMList;
    private final ArrayList<ScreenPiece>screePieceList;
    private  int gWallMap[][];
    private  int gFloorMap[][];
    private int gObjectMap[][];

/*-----------------------------------------------------------------------------*
 *                             Class Contructor                                *
 *-----------------------------------------------------------------------------*/
    public Map(ArrayList<TileMap> TilemapList, BufferedImage spriteSheet, ArrayList<ScreenPiece> screenPieceList) {
        this.xPos = 0;
        this.yPos = 0;
        this.SSheet = spriteSheet;
        this.TMList = TilemapList;
        this.screePieceList = screenPieceList;
        
        
        this.gFloorMap = new int[][]{
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

        this.gObjectMap = new int[][]{
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

        
        this.gWallMap = new int[][]{
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

 /*-----------------------------------------------------------------------------*
 *                              Class Methods                                  *
 *-----------------------------------------------------------------------------*/
    public void stage2() {
        this.gFloorMap = new int[][]{
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

        this.gObjectMap = new int[][]{
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

        this.gWallMap = new int[][]{
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
    
    public void stage4() {
        this.gFloorMap = new int[][]{
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {105, 105, 105, 105,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {105, 105, 105, 105,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};


        this.gObjectMap = new int[][]{
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};

        this.gWallMap = new int[][]{
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},
            {  6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6,   6},};
   }

    public void stage3() {
        this.gFloorMap = new int[][]{
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

        this.gObjectMap = new int[][]{
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

        this.gWallMap = new int[][]{
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

    
    @Override
    public void paintComponent(Graphics g) {
        stage2();
        //stage4();
        stage3();
        
        for (int x = 0; x < gFloorMap[0].length; x++) {
            for (int y = 0; y < gFloorMap.length; y++) {
                
                g.drawImage(SSheet, 
                		(x * 64) + xPos, (y * 64) + yPos, 
                		(x * 64) + 64 + xPos, (y * 64) + 64 + yPos,     
                        TMList.get(gFloorMap[y][x]).getSrcX1(), TMList.get(gFloorMap[y][x]).getSrcY1(),        
                        TMList.get(gFloorMap[y][x]).getSrcX2(), TMList.get(gFloorMap[y][x]).getSrcY2(),       
                        null);        
                               
                g.drawImage(SSheet, 
                        (x * 64) + xPos, (y * 64) + yPos, 
                        (x * 64) + 64 + xPos, (y * 64) + 64 + yPos,
                        TMList.get(gObjectMap[y][x]).getSrcX1(), TMList.get(gObjectMap[y][x]).getSrcY1(),
                        TMList.get(gObjectMap[y][x]).getSrcX2(), TMList.get(gObjectMap[y][x]).getSrcY2(), 
                        null);
                
                g.drawImage(SSheet,
                        (x * 64) + xPos, (y * 64) + yPos,
                        (x * 64) + 64 + xPos, (y * 64) + 64 + yPos, 
                        TMList.get(gWallMap[y][x]).getSrcX1(), TMList.get(gWallMap[y][x]).getSrcY1(),
                        TMList.get(gWallMap[y][x]).getSrcX2(), TMList.get(gWallMap[y][x]).getSrcY2(), 
                        null);

            }
        }
    }


    public void move(KeyEvent e) {
        if (e.getKeyChar() == 'l') {
            stage2();
        }
        if (e.getKeyChar() == 'a') {
            this.xPos = this.xPos + 64;
        }
        if (e.getKeyChar() == 'd') {
            this.xPos = this.xPos - 64;
        }
        if (e.getKeyChar() == 'w') {
            this.yPos = this.yPos + 64;
        }
        if (e.getKeyChar() == 's') {
            this.yPos = this.yPos - 64;
        }
    }
}
