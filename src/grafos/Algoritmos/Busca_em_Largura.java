package Algoritmos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;

import Construtores.*;
import Interfaces.*;

public class Busca_em_Largura {

    public Collection<Aresta> CalcularBFS (Grafo g, Vertice origem){


        
        Queue<Vertice> fila = new LinkedList<>();
        Map<Vertice, Vertice> filhoPai = new HashMap<>();
        Map<Vertice, Double> distancia = new HashMap<>();
        Map<Vertice, String> cores = new HashMap<>();
        ArrayList<Aresta> arestasEncontradas = new ArrayList<>();

        
        for (Vertice v : g.vertices()){

            cores.put(v, "branco");
            distancia.put(v, Double.POSITIVE_INFINITY);
            filhoPai.put(v, null);
        }
        cores.put(origem, "cinza");
        distancia.put(origem, 0.0);
        fila.add(origem);

        while(!fila.isEmpty()){

            Vertice u = fila.remove();
            try{

                for(Vertice v : g.adjacentesDe(u)){

                    if ("branco".equals(cores.get(v))){

                        cores.put(v, "cinza");
                        distancia.put(v, distancia.get(u) + 1);
                        filhoPai.put(v, u);
                        fila.add(v);

                        for (Aresta a : g.arestasEntre(u, v)){

                            arestasEncontradas.add(a);
                        }
                    }
                }
                cores.put(u, "preto");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return arestasEncontradas;
    }
    
    public ArrayList<InformacoesFluxoMaximo> BuscaResidualBFS(Grafo g, Vertice origem, Vertice destino, Map<Aresta, Double> fluxos){

        Map<Vertice, InformacoesFluxoMaximo> caminho = new HashMap<>(); //define qual caminho chegou no vértice
        Queue<Vertice> fila = new LinkedList<>();
        Map<Vertice, String> cores = new HashMap<>();

        for (Vertice v : g.vertices()){

            cores.put(v, "branco");
        }
        cores.put(origem, "cinza");
        fila.add(origem);
        caminho.put(origem, null);

        while(!fila.isEmpty()){

            Vertice u = fila.remove();

            if (u.equals(destino)){     //já achou o destino final
                break;
            }
            try{

                for(Vertice v : g.adjacentesDe(u)){

                    for (Aresta a : g.arestasEntre(u, v)){

                        Double fluxoA = fluxos.get(a);
                        if(fluxoA == null) fluxoA = 0.0;
                        Double capacidadeResidual = a.peso() - fluxoA;

                        if("branco".equals(cores.get(v)) && capacidadeResidual > 0){

                            cores.put(v, "cinza");
                            fila.add(v);
                            caminho.put(v, new InformacoesFluxoMaximo(a, capacidadeResidual, false));
                        }
                    }
                    
                }

                for (Vertice w : g.vertices()){

                    if(w.equals(u)) continue;

                    for(Aresta a : g.arestasEntre(w, u)){

                        Double fluxoA = fluxos.get(a);
                        if(fluxoA == null) fluxoA = 0.0;
                        double capacidadeResidual = fluxoA;

                        if ("branco".equals(cores.get(w)) && capacidadeResidual > 0){

                            cores.put(w, "cinza");
                            fila.add(w);
                            caminho.put(w, new InformacoesFluxoMaximo(a, capacidadeResidual, true));
                        }
                    }
                }
                cores.put(u, "preto");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        if("branco".equals(cores.get(destino))){     //se o destino não foi visitado, retorna null
            return null;
        }

        ArrayList<InformacoesFluxoMaximo> caminhoReconstruido = new ArrayList<>();
        Vertice atual = destino;

        while (!atual.equals(origem)){

            InformacoesFluxoMaximo aresta = caminho.get(atual);
            caminhoReconstruido.add(aresta);

            if (aresta.residualReversa == true){

                atual = aresta.arestaOriginal.destino();
            }
            else{

                atual = aresta.arestaOriginal.origem();
            }
        }

        Collections.reverse(caminhoReconstruido);
        return caminhoReconstruido;
    }
}
