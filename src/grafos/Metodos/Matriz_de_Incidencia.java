package Metodos;

import java.util.ArrayList;

import Construtores.Aresta;
import Construtores.Vertice;
import Interfaces.Grafo;

public class Matriz_de_Incidencia implements Grafo {


    //matriz[origem][aresta]; exemplo

    private double matriz[][];
    private ArrayList<Vertice> vertices;
    private ArrayList<Aresta> arestas;

    private boolean contemVerticePorId(Vertice vertice) {
        if (vertice == null) return false;
        for (Vertice v : this.vertices) {
            if (v.id() == vertice.id()) {
                return true;
            }
        }
        return false;
    }
    
    public Matriz_de_Incidencia(int numeroDeVertices){

        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();

        for (int i = 0; i < numeroDeVertices; i++){

            vertices.add(new Vertice(i));       //id do vertice
        }
    }

    public void adicionarVertice(Vertice vertice){
        
    }

    public void novaMatriz(){

        if (matriz == null){   //matriz ainda não foi iniciada

            this.matriz = new double[vertices.size()][1];
        }
        else{

            double novaMatriz[][] = new double[vertices.size()][arestas.size() + 1];
            for(int i = 0; i < matriz.length; i++){ //roda a matriz

                System.arraycopy(matriz[i], 0, novaMatriz[i], 0, matriz[i].length);
            }
            this.matriz = novaMatriz;
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

            novaMatriz();
            Aresta a = new Aresta(origem, destino, peso);
            arestas.add(a);
            matriz[origem.id()][arestas.size() -1] = peso; 
            matriz[destino.id()][arestas.size() -1] = -peso; 
        } 
        else{
            throw new IllegalArgumentException("Um ou mais vértices não foram encontrados");
        }
    }
    
    public boolean existeAresta(Vertice origem, Vertice destino){
        if(contemVerticePorId(origem) && contemVerticePorId(destino)){        //roda as posições das arestas para determinada origem
                               
            int orig = origem.id();
            int dest = destino.id();
            for (Aresta a : this.arestas) {

                if (a.origem().id() == orig && a.destino().id() == dest) {
                    
                    return true;
                }
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

            for(int i = 0; i < arestas.size(); i++){

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
    }  //tera que retornar a primeira linha do arquivo
    
    public int numeroDeArestas(){
        return arestas.size();
    }
    
    public ArrayList<Vertice> adjacentesDe(Vertice vertice){

        ArrayList<Vertice> verticesAd = new ArrayList<>();

        if (contemVerticePorId(vertice)){                            
                
            int origem = vertice.id();
            
            for (Aresta a : this.arestas) {
 
            if (a.origem().id() == origem) {
                    
                    
                    verticesAd.add(this.vertices.get(a.destino().id()));
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
            
      
            for(int count1 = 0; count1 < arestas.size(); count1++){

                if(matriz[origem.id()][count1] > 0 && matriz[destino.id()][count1] < 0){
                    matriz[origem.id()][count1] = peso;
                    matriz[destino.id()][count1] = -peso;
                }
            }
                        
        }
        
        else{
            throw new IllegalArgumentException("A aresta não foi encontrada");
        }
    }
    
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino){

        ArrayList<Aresta> arestasEntre = new ArrayList<>();
        
        if(contemVerticePorId(origem) && contemVerticePorId(destino)){

            int oID = origem.id();
            int dID = destino.id();
            
            
            for (Aresta a : this.arestas) {

                if (a.origem().id() == oID && a.destino().id() == dID) {
                    arestasEntre.add(a);
                }
            }
            return arestasEntre;
        }
        else{
            
            throw new IllegalArgumentException("Um ou mais vértices não foram encontrados");
        }

        
    }
    
    public ArrayList<Vertice> vertices(){
        return vertices;
    }
}

//finalizado
