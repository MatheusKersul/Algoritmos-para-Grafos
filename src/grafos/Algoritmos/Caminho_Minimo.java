package Algoritmos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Construtores.Aresta;
import Construtores.Vertice;
import Interfaces.Grafo;

public class Caminho_Minimo {

    
    public ArrayList<Aresta> CMBellmanFord(Grafo g, Vertice origem, Vertice destino) {

        Map<Vertice, Double> distancias = new java.util.HashMap<>();
        Map<Vertice, Vertice> paiFilho = new java.util.HashMap<>();
        ArrayList<Aresta> caminhoMinimo = new java.util.ArrayList<>();
        List<Aresta> arestasDoGrafo = new java.util.ArrayList<>();

        for (Vertice v : g.vertices()) {

            distancias.put(v, Double.POSITIVE_INFINITY);
            paiFilho.put(v, null);
        }

        distancias.put(origem, 0.0);

        try {
            for (Vertice u : g.vertices()){
                
                for (Vertice v : g.adjacentesDe(u)){

                    for(Aresta a : g.arestasEntre(u,v)){

                        arestasDoGrafo.add(a);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


        for (int i = 1; i < g.numeroDeVertices(); i++){

            for (Aresta a : arestasDoGrafo){

                Vertice pai = a.origem();
                Vertice filho = a.destino();
                double peso = a.peso();

                if (distancias.get(pai) + peso < distancias.get(filho)){ //pode relaxar

                    distancias.put(filho, distancias.get(pai) + peso);
                    paiFilho.put(filho,pai);
                }
            }
        }

            for (Aresta a: arestasDoGrafo){

                Vertice pai = a.origem();
                Vertice filho = a.destino();
                double peso = a.peso();

                if (distancias.get(pai) + peso < distancias.get(filho)){ //ciclo negativo

                    return null;
                }
            }


            Vertice atual = destino;

            while (atual != null && paiFilho.get(atual) != null) {

                Vertice anterior = paiFilho.get(atual);
                try {

                    for (Aresta a : g.arestasEntre(anterior, atual)) {
                        caminhoMinimo.add(0, a); // insere no início da lista
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
                atual = anterior;
        }
        return caminhoMinimo; // Retornar a lista de arestas que compõem o caminho mínimo
    }

    public ArrayList<Aresta> CMDijkstra(Grafo g, Vertice origem, Vertice destino) {

        Map<Integer, Double> distancias = new java.util.HashMap<>();
        Map<Vertice, Vertice> paiFilho = new java.util.HashMap<>();
        ArrayList<Aresta> caminhoMinimo = new ArrayList<>();
        ArrayList<Vertice> naoVisitados = new ArrayList<>();

        

        for (Vertice v : g.vertices()) {

            distancias.put(v.id(), Double.POSITIVE_INFINITY);
            paiFilho.put(v, null);
        }

        distancias.put(origem.id(), 0.0);

        for (Vertice v : g.vertices()) {

            naoVisitados.add(v);
        }
        while (naoVisitados.size() > 0){

            Vertice verticeTemp = null;
            double menorDistancia = Double.POSITIVE_INFINITY;

            for (Vertice v : naoVisitados) {

                if (distancias.get(v.id()) <= menorDistancia){

                    menorDistancia = distancias.get(v.id());
                    verticeTemp = v;
                }
            }
            naoVisitados.remove(verticeTemp);
            try{   

                for (Vertice v : g.adjacentesDe(verticeTemp)){

                    if (naoVisitados.contains(v)){

                        for (Aresta a : g.arestasEntre(verticeTemp, v)){

                        double peso = a.peso();

                            if (distancias.get(verticeTemp.id()) + peso < distancias.get(v.id())){ //pode relaxar

                                distancias.put(v.id(), distancias.get(verticeTemp.id()) + peso);
                                paiFilho.put(v, verticeTemp);

                            }
                        }
                    }
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
    }


        Vertice atual = destino;

        while (atual != null && paiFilho.get(atual) != null) {

            Vertice anterior = paiFilho.get(atual);
            try {

                for (Aresta a : g.arestasEntre(anterior, atual)) {
                    caminhoMinimo.add(0, a); // insere no início da lista
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            atual = anterior;
        }
        return caminhoMinimo; 
    }

    public double CustoDoCaminhoMinimo (Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino){

        double pesototal = 0;

            if (arestas == null || arestas.isEmpty()) {

                throw new IllegalArgumentException("Caminho inexistente entre os vértices informados.");
            }

            if (!g.vertices().contains(origem) || !g.vertices().contains(destino)) {

                throw new IllegalArgumentException("Origem ou destino não encontrados no grafo.");
            }
            
            for (Aresta a : arestas){

                pesototal += a.peso();
            }

        

        return pesototal;
    }
}
