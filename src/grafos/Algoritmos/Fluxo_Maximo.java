package Algoritmos;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import Construtores.*;
import Interfaces.*;

public class Fluxo_Maximo {
    
    private Map<Aresta, Double> fluxos = new HashMap<>();
    private double fluxoMaximo = 0.0;
    Busca_em_Largura BFS = new Busca_em_Largura();

    public Double CalcularFLuxoMaximo(Grafo g, Vertice origem, Vertice destino){


        SetarFluxo(g);

        while (true){

            ArrayList<InformacoesFluxoMaximo> caminho = BFS.BuscaResidualBFS(g, origem, destino, fluxos);

            if(caminho == null){
                break;
            }

            double gargalo = Double.POSITIVE_INFINITY;
            for (InformacoesFluxoMaximo aresta : caminho){

                if(aresta.capacidadeResidual < gargalo){  //pode aumentar

                    gargalo = aresta.capacidadeResidual;
                }
            }
            for (InformacoesFluxoMaximo aresta : caminho){

                Aresta arestaOriginal = aresta.arestaOriginal;
                Double fluxoAtual = fluxos.get(arestaOriginal);

                if(aresta.residualReversa == true){

                    fluxos.put(arestaOriginal, fluxoAtual - gargalo);
                }
                else{

                    fluxos.put(arestaOriginal, fluxoAtual + gargalo);
                }
            }
            fluxoMaximo += gargalo;
        }

        return fluxoMaximo;
    }

    private void SetarFluxo (Grafo g){

        try{
            for(Vertice v : g.vertices()){

                for(Vertice u : g.vertices()){

                    for(Aresta a: g.arestasEntre(u,v)){
                        
                        fluxos.put(a, 0.0);

                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }    

    }

}
