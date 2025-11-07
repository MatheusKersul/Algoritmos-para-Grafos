package Algoritmos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import Construtores.*;
import Interfaces.*;


public class Busca_em_Profundidade {

    Map<Vertice , Integer> descoberta = new HashMap<>();
    Map<Vertice , Integer> encerramento = new HashMap<>();
    Map<Vertice , String> cores = new HashMap<>();
    ArrayList<Aresta> todasArestas = new ArrayList<>();
    Integer tempo = null;
    ArrayList<Aresta> arestasDaArvore = new ArrayList<>();
    ArrayList<Aresta> arestasDeRetorno = new ArrayList<>();
    ArrayList<Aresta> arestasDeAvanco = new ArrayList<>();
    ArrayList<Aresta> arestasDeCruzamento = new ArrayList<>();
    Boolean ciclo = false;
    Map<Vertice, Vertice> antecessores = new HashMap<>();


    public Collection<Aresta> CalcularDFS (Grafo g){

        for (Vertice v : g.vertices()){

            cores.put(v, "branco");
        }

        tempo = 0;

        for (Vertice u : g.vertices()){

            if ("branco".equals(cores.get(u))){

                VisitaDFS(u, g);
            }
        }

        return arestasDaArvore;
    }

    private void VisitaDFS(Vertice u, Grafo g){

        cores.put(u, "cinza");
        tempo += 1;
        descoberta.put(u, tempo);

        try{

            for(Vertice v : g.adjacentesDe(u)){

                if ("branco".equals(cores.get(v))){

                    for (Aresta a : g.arestasEntre(u, v)){

                        arestasDaArvore.add(a);
                    }

                    VisitaDFS(v, g);
                }


                else if("cinza".equals(cores.get(u)) && "cinza".equals(cores.get(v))){

                    ciclo = true;
                    for(Aresta a : g.arestasEntre(u, v)){

                        arestasDeRetorno.add(a);
                    }
                }

                else if("cinza".equals(cores.get(u)) && "preto".equals(cores.get(v))){

                    if(descoberta.get(u) < descoberta.get(v)){

                        for(Aresta a : g.arestasEntre(u, v)){

                            arestasDeAvanco.add(a);
                        }
                    }

                    else{

                        for(Aresta a : g.arestasEntre(u, v)){

                            arestasDeCruzamento.add(a);
                        }
                    }
                }
            }
        }
        catch (Exception e){

            e.printStackTrace();
        }

        tempo += 1;
        cores.put(u,"preto");
        encerramento.put(u, tempo);
    }

    public Integer getDescoberta(Vertice v){

        return descoberta.get(v);
    }

    public Integer getEncerramento(Vertice v){

        return encerramento.get(v);
    }
 
    public Collection<Aresta> ArestasDaArvore(Grafo g){

        return this.arestasDaArvore;
    }

    public Collection<Aresta> ArestasDeAvanco(Grafo g){

        return this.arestasDeAvanco;
    }

    public Collection<Aresta> ArestasDeRetorno(Grafo g){

        return this.arestasDeRetorno;
    }

    public Collection<Aresta> ArestasDeCruzamento(Grafo g){

        return this.arestasDeCruzamento;
    }

    public boolean existeCiclo(){

        return ciclo;
    }
    //método para achar o fortemente conectado
    public Collection<Aresta> CalcularDFS (Grafo g, ArrayList<Vertice> verticesOrdenados){

        descoberta.clear();
        encerramento.clear();
        cores.clear();
        arestasDaArvore.clear();
        arestasDeRetorno.clear();
        arestasDeAvanco.clear();
        arestasDeCruzamento.clear();
        antecessores.clear();
        ciclo = false;
        tempo = 0;

        for (Vertice v : verticesOrdenados){

            cores.put(v, "branco");
        }

        tempo = 0;

        for (Vertice u : verticesOrdenados){

            if ("branco".equals(cores.get(u))){

                VisitaDFS(u, g);
            }
        }

        return arestasDaArvore;
    }
}
