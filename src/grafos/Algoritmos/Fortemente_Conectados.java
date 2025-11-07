package Algoritmos;

import Construtores.*;
import Interfaces.*;
import Metodos.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Fortemente_Conectados{

    private Map<Vertice, String> cores;
    private ArrayList<ArrayList<Vertice>> componentes; 
    private ArrayList<Vertice> componenteAtual;     

    public Grafo CalcularFortementeConectados(Grafo g){

        Busca_em_Profundidade dfsOriginal = new Busca_em_Profundidade();
        ArrayList<Vertice> verticesOrdenados = new ArrayList<>(g.vertices());

        dfsOriginal.CalcularDFS(g);
        verticesOrdenados.sort(Comparator.comparing(dfsOriginal::getEncerramento).reversed());
        Grafo gTransposto = criarGrafoTransposto(g);

        this.cores = new HashMap<>();
        this.componentes = new ArrayList<>();
        
     
        for (Vertice v : gTransposto.vertices()){

            cores.put(v, "branco");
        }


        for (Vertice u : verticesOrdenados){

            if ("branco".equals(cores.get(u))){

                componenteAtual = new ArrayList<>();
                visitaDFS_CFC(gTransposto, u);
                componentes.add(componenteAtual); 
            }
        }


        return construirGrafoReduzido(g, componentes);
    }

    private Grafo criarGrafoTransposto(Grafo g){

        Grafo gTransposto = new Lista_de_Adjacencia(); 
        try{

        for (Vertice v : g.vertices()){

             gTransposto.adicionarVertice(v);
        }
        }
        catch(Exception e){
            e.printStackTrace();
        }
      
        try{
            for (Vertice u : g.vertices()){
             
                for (Vertice v : g.adjacentesDe(u)){

                    for (Aresta a : g.arestasEntre(u, v)){
                       
                        gTransposto.adicionarAresta(v, u, a.peso());
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return gTransposto;
    }

    private void visitaDFS_CFC(Grafo gTransposto, Vertice u){

        cores.put(u, "cinza");
        componenteAtual.add(u); 
        
        try{
            for (Vertice v : gTransposto.adjacentesDe(u)){

                if ("branco".equals(cores.get(v))){

                    visitaDFS_CFC(gTransposto, v);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        
        cores.put(u, "preto");
    }

    private Grafo construirGrafoReduzido(Grafo g, ArrayList<ArrayList<Vertice>> componentes){
        Grafo grafoReduzido = new Lista_de_Adjacencia();
        
        Map<Vertice, Integer> cfcMap = new HashMap<>();
        try{

        
            for (int i = 0; i < componentes.size(); i++){

                Vertice sccVertice = new Vertice(i);
                grafoReduzido.adicionarVertice(sccVertice);
                

                for (Vertice v : componentes.get(i)){

                    cfcMap.put(v, i);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try{
            for (Vertice u : g.vertices()){

                int sccOrigemId = cfcMap.get(u);
                Vertice sccOrigemVertice = grafoReduzido.vertices().get(sccOrigemId);
                
                for (Vertice v : g.adjacentesDe(u)){

                    int sccDestinoId = cfcMap.get(v);
                    

                    if (sccOrigemId != sccDestinoId){

                        Vertice sccDestinoVertice = grafoReduzido.vertices().get(sccDestinoId);
                        if (!grafoReduzido.existeAresta(sccOrigemVertice, sccDestinoVertice)){

                            grafoReduzido.adicionarAresta(sccOrigemVertice, sccDestinoVertice, 1.0);
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return grafoReduzido;
    }
}