package Metodos;
import java.util.ArrayList;

import Construtores.Aresta;
import Construtores.Vertice;
import Interfaces.Grafo;

public class Matriz_de_Adjacencia  implements Grafo {
    
    private double matriz[][];
    private ArrayList<Vertice> vertices;
    private int numArestas;
    
    public void adicionarVertice(Vertice vertice){}

    private boolean contemVerticePorId(Vertice vertice) {
        if (vertice == null) return false;
        for (Vertice v : this.vertices) {
            if (v.id() == vertice.id()) {
                return true;
            }
        }
        return false;
    }

    public Matriz_de_Adjacencia(int numeroDeVertices){
        this.matriz = new double[numeroDeVertices][numeroDeVertices]; //tamanho maximo 100
        this.vertices = new ArrayList<>();
        this.numArestas = 0;

        for (int i = 0; i < numeroDeVertices; i++){

            vertices.add(new Vertice(i));       //id do vertice
        }
    }

    public void adicionarAresta(Vertice origem, Vertice destino){
        if(contemVerticePorId(origem) && contemVerticePorId(destino)){

            adicionarAresta(origem, destino, 1.0);
        }
        else{
            throw new IllegalArgumentException("Um ou mais vértices não foram encontrados");
        }  
    }
    
    public void adicionarAresta(Vertice origem, Vertice destino, double peso){
        if(contemVerticePorId(origem) && contemVerticePorId(destino)){

            numArestas += 1;
            matriz[origem.id()][destino.id()] = peso;
        } 
        else{
            throw new IllegalArgumentException("Um ou mais vértices não foram encontrados");
        }
    }
    
    public boolean existeAresta(Vertice origem, Vertice destino){
        if(contemVerticePorId(origem) && contemVerticePorId(destino)){
                               
            int i = origem.id();
            int j = destino.id();
                if(matriz[i][j] != 0){
                    return true;
                }
        }
        else {
            throw new IllegalArgumentException("Um ou mais vértices não foram encontrados");

        }
        return false;
    }

    public int grauDoVertice(Vertice vertice) {
        //perguntar pro douglas se precisa do grau de saida tbm
        
        int grau = 0;
        int origem = vertice.id();
        if (contemVerticePorId(vertice)){        //se contem o id do vertice

            for(int i = 0; i < vertices.size(); i++){

                if (matriz[origem][i] != 0){        //se tem um peso diferente de 0

                    grau += 1;
                }
            }
            return grau;
        }
        
        else{
            throw new IllegalArgumentException("O vértice não foi encontrado");
        } 
    } //quantidade de arestas ligadas
    
    public int numeroDeVertices(){
        return vertices.size();
    }  
    
    public int numeroDeArestas(){
        return numArestas;
    }
    
    public ArrayList<Vertice> adjacentesDe(Vertice vertice){

        ArrayList<Vertice> verticesAd = new ArrayList<>();
        if (contemVerticePorId(vertice)){                            
                
            int origem = vertice.id();

            for (int i = 0; i < vertices.size(); i++){

                if(matriz[origem][i] != 0){        //se tem um peso diferente de 0
                    verticesAd.add(this.vertices.get(i)); //guarda o destino
                }

            }
            return verticesAd;
        }
        else{
            throw new IllegalArgumentException("O vértice não foi encontrado");
        }
    
    }

    public void setarPeso(Vertice origem, Vertice destino, double peso){

        if(contemVerticePorId(origem) && contemVerticePorId(destino)){
            
            int i = origem.id();
            int j = destino.id();
            matriz[i][j] = peso;            
        }
        
        else{
            throw new IllegalArgumentException("A aresta não foi encontrada");
        }
    }
    
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino){

        ArrayList<Aresta> arestasEntre = new ArrayList<>();
        
        if(contemVerticePorId(origem) && contemVerticePorId(destino)){

            if (existeAresta(origem, destino)){

               

                        arestasEntre.add(new Aresta(origem, destino, matriz[origem.id()][destino.id()]));
                
            }
        }
        else{
            
            throw new IllegalArgumentException("Um ou mais vértices não foram encontrados");
        }

        return arestasEntre;
    }
    
    public ArrayList<Vertice> vertices(){
        return vertices;
    }
}

//finalizado    

