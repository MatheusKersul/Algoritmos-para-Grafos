package Construtores;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Objects;

/**
 *
 * @author humberto e douglas
 */
public class Aresta {

    private Vertice origem;
    private Vertice destino;
    private double peso;
    
    public Aresta( Vertice origem, Vertice destino ){
        this.origem = origem;
        this.destino = destino;
        this.peso = 1;
    }
    
    public Aresta( Vertice origem, Vertice destino, double peso ){
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    public Vertice origem() {
        return origem;
    }

    public void setarOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Vertice destino() {
        return destino;
    }

    public void setarDestino(Vertice destino) {
        this.destino = destino;
    }

    public double peso() {
        return peso;
    }

    public void setarPeso(double peso) {
        this.peso = peso;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        
        if (o == null || getClass() != o.getClass()) return false;
        
        Aresta aresta = (Aresta) o;
        
        return Objects.equals(origem, aresta.origem) &&
               Objects.equals(destino, aresta.destino);
    }

    @Override
    public int hashCode() {
        
        return Objects.hash(origem, destino);
    }
}
