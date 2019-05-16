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
public class Node {
        private int id;
    private float h, g, f;
    private boolean bloqueado, visitado;

    
    
    
    public boolean isVisitado() {
        return visitado;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
    private Node pai;
    public  List<Node> vizinhos = new ArrayList();

    public List<Node> getVizinhos() {
        return vizinhos;
    }

    public int getId() {
        return id;
    }
    
    public Node(int id){
        this.id = id;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public boolean estaBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Node getPai() {
        return pai;
    }

    public void setPai(Node pai) {
        this.pai = pai;
    }
}
