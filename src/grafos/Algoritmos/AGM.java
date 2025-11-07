package Algoritmos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import Construtores.Aresta;
import Construtores.Vertice;
import Interfaces.Grafo;

public class AGM {
    
    public Collection<Aresta> CalcularAGM(Grafo g){

    Collection<Aresta> ramos_da_arvore = new ArrayList<>();
    ArrayList<Aresta> todasArestas = new ArrayList<>();
    Map<Vertice, ArrayList<Vertice>> conjuntoDosVertices = new HashMap<>();

        try {
            for (Vertice u : g.vertices()){
                
                for (Vertice v : g.adjacentesDe(u)){

                    for(Aresta a : g.arestasEntre(u,v)){

                        todasArestas.add(a);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        todasArestas.sort(Comparator.comparingDouble(Aresta::peso));

        

        for (Vertice u : g.vertices()){

            ArrayList<Vertice> lista = new ArrayList<>();
            lista.add(u);
            conjuntoDosVertices.put(u, lista);
        }



        for (Aresta a : todasArestas){

            ArrayList<Vertice> dVertices = null;
            ArrayList<Vertice> oVertices = null;

            for (ArrayList<Vertice> lista : conjuntoDosVertices.values()){

                if (lista.contains(a.origem())) oVertices = lista;

                if (lista.contains(a.destino())) dVertices = lista;

            }

                if (oVertices != null && dVertices != null && oVertices != dVertices){

                    oVertices.addAll(dVertices);

                    for (Vertice v : dVertices) {

                        conjuntoDosVertices.put(v, oVertices);
                    }

                    ramos_da_arvore.add(a);
                

               }

            }
        


        return ramos_da_arvore;
    }

    public double CustoAGM(Grafo g, Collection<Aresta> ramos_da_arvore){ 

        double custo = 0.0;

        if (ramos_da_arvore.isEmpty()) {
            CalcularAGM(g);
        }
        for (Aresta a : ramos_da_arvore){
            custo += a.peso();
        }

            return custo;
    
    }

}