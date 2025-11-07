package Metodos;
import java.util.ArrayList;
import java.util.Map;

import Construtores.Aresta;
import Construtores.Vertice;
import Interfaces.Grafo;

public class Lista_de_Adjacencia implements Grafo {
 
    private Map<Integer, ArrayList<Aresta>> listaDeAdjacencia;
    private int numArestas;
    private ArrayList<Vertice> vertices;
   
    public Lista_de_Adjacencia(){
    
        this.listaDeAdjacencia = new java.util.HashMap<>();
        this.vertices = new ArrayList<>();
        this.numArestas = 0;
    }

    public void adicionarVertice(Vertice vertice){
        int id = vertice.id();
        if(listaDeAdjacencia.containsKey(id) == false){
            listaDeAdjacencia.put(id, new ArrayList<Aresta>());
            vertices.add(vertice);
        }
        else{
            throw new IllegalArgumentException("O vértice já foi adicionado");
        }
    }

    public void adicionarAresta(Vertice origem, Vertice destino){
        if(listaDeAdjacencia.containsKey(origem.id())  && listaDeAdjacencia.containsKey(destino.id())){

            adicionarAresta(origem, destino, 1.0);
        }
        else{
            throw new IllegalArgumentException("Um ou mais vértices não foram encontrados");
        }  
    }
    
    public void adicionarAresta(Vertice origem, Vertice destino, double peso){
        if(listaDeAdjacencia.containsKey(origem.id())  && listaDeAdjacencia.containsKey(destino.id())){

            numArestas += 1;
            listaDeAdjacencia.get(origem.id()).add(new Aresta(origem, destino, peso));
        } 
        else{
            throw new IllegalArgumentException("Um ou mais vértices não foram encontrados");
        }
    }
    
    public boolean existeAresta(Vertice origem, Vertice destino){
        if(listaDeAdjacencia.containsKey(origem.id())  && listaDeAdjacencia.containsKey(destino.id())){
                               
            ArrayList<Aresta> arestasIndice = listaDeAdjacencia.get(origem.id());
            for (Aresta a : arestasIndice) {

                if(a.destino().id() == destino.id()){

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

        int id = vertice.id();

        if (listaDeAdjacencia.containsKey(id)){  
            
            return listaDeAdjacencia.get(id).size();
            }
               
        else{
            throw new IllegalArgumentException("O vértice não foi encontrado");
        } 
    } //quantidade de arestas ligadas
    
    public int numeroDeVertices(){
        return listaDeAdjacencia.size();
    }  
    
    public int numeroDeArestas(){
        return numArestas;
    }
    
    public ArrayList<Vertice> adjacentesDe(Vertice vertice){

        int id = vertice.id();

        if (listaDeAdjacencia.containsKey(id)){                            
                
            ArrayList<Vertice> verticesAd = new ArrayList<>();
           
            ArrayList<Aresta> arestasIndice = listaDeAdjacencia.get(id);

                for(Aresta a : arestasIndice){

                        verticesAd.add(a.destino());
                }
            
            return verticesAd;
        }
        else{
            throw new IllegalArgumentException("O vértice não foi encontrado");
        }
    
    }

    public void setarPeso(Vertice origem, Vertice destino, double peso){

        if(listaDeAdjacencia.containsKey(origem.id())  && listaDeAdjacencia.containsKey(destino.id())){
            
            ArrayList<Aresta> arestasIndice = listaDeAdjacencia.get(origem.id());

            for (Aresta a : arestasIndice) {

                if(a.destino().id() == destino.id()){

                    a.setarPeso(peso);
                }
            }       
        }
        
        else{

            throw new IllegalArgumentException("A aresta não foi encontrada");
        }
    }
    
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino){

        ArrayList<Aresta> arestasEntre = new ArrayList<>();
        
        if(listaDeAdjacencia.containsKey(origem.id())  && listaDeAdjacencia.containsKey(destino.id())){

            if (existeAresta(origem, destino)){

                ArrayList<Aresta> arestasIndice = listaDeAdjacencia.get(origem.id());

                for(Aresta a :arestasIndice){

                        if(a.destino().id() == destino.id()){

                            arestasEntre.add(a);
                        }
                }
                
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