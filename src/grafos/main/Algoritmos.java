package main;


import java.util.Collection;

import Algoritmos.AGM;
import Algoritmos.Busca_em_Largura;
import Algoritmos.Busca_em_Profundidade;
import Algoritmos.Caminho_Minimo;
import Algoritmos.Fluxo_Maximo;
import Algoritmos.Fortemente_Conectados;
import Construtores.Aresta;
import Construtores.TipoDeRepresentacao;
import Construtores.Vertice;
import Interfaces.AlgoritmosEmGrafos;
import Interfaces.Grafo;
import Metodos.Matriz_de_Adjacencia;
import Metodos.Matriz_de_Incidencia;
import Metodos.Lista_de_Adjacencia;

import java.util.ArrayList;

public class Algoritmos implements AlgoritmosEmGrafos {

    public Grafo carregarGrafo(String path, TipoDeRepresentacao t) throws Exception {

        FileManager fileManager = new FileManager();
        ArrayList<String> todasLinhas = fileManager.stringReader(path);

        if (todasLinhas == null || todasLinhas.isEmpty()) {
            throw new Exception("Erro: Arquivo está vazio ou não foi encontrado.");
        }

        boolean isMatrixFormat = false;
        String firstLine = todasLinhas.get(0).trim();

        if (firstLine.contains("-") || firstLine.contains(";")) {
            isMatrixFormat = true;
        }

        try {
            int numeroDeVertices;
            int loopStartIndex; 

            if (isMatrixFormat) {
                numeroDeVertices = todasLinhas.size();
                loopStartIndex = 0;
            } else {
                numeroDeVertices = Integer.parseInt(firstLine);
                loopStartIndex = 1;
            }

            Grafo grafo = null;
            if (t == TipoDeRepresentacao.MATRIZ_DE_ADJACENCIA) {
                grafo = new Matriz_de_Adjacencia(numeroDeVertices);
            } 
            else if (t == TipoDeRepresentacao.MATRIZ_DE_INCIDENCIA) {
                grafo = new Matriz_de_Incidencia(numeroDeVertices); 
            } 
            else if (t == TipoDeRepresentacao.LISTA_DE_ADJACENCIA) {
                grafo = new Lista_de_Adjacencia();
                for (int i = 0; i < numeroDeVertices; i++) {
                    grafo.adicionarVertice(new Vertice(i));
                }
            } 
            else {
                throw new IllegalArgumentException("Tipo de representação inválida.");
            }

            for (int i = loopStartIndex; i < todasLinhas.size(); i++) {
                
                String linha = todasLinhas.get(i).trim();
                if (linha.isEmpty()) continue; 

                int idOrigem;
                String arestasString;

                if (isMatrixFormat) {
                    idOrigem = i;
                    arestasString = linha;
                } else {
                    String[] partes = linha.split(" ", 2);
                    
                    idOrigem = Integer.parseInt(partes[0]);
                    
                    if (partes.length < 2) {
                        continue; 
                    }
                    arestasString = partes[1];
                }

                String arestasStringLimpa = arestasString.replaceAll("\\s+", "");
                String[] arestas = arestasStringLimpa.split(";");

                for (String aresta : arestas) {
                    if (!aresta.isEmpty()) {
                        String[] destinoPeso = aresta.split("-");
                        int idDestino = Integer.parseInt(destinoPeso[0]);

                        if (idOrigem >= numeroDeVertices || idDestino >= numeroDeVertices) {
                            System.err.println("Aviso: Vértice inválido na linha " + (i+1) + ". Aresta ("+idOrigem+"->"+idDestino+") ignorada.");
                            continue;
                        }

                        Vertice vOrigem = grafo.vertices().get(idOrigem);
                        Vertice vDestino = grafo.vertices().get(idDestino);

                        if (destinoPeso.length > 1) {
                            double peso = Double.parseDouble(destinoPeso[1]);
                            grafo.adicionarAresta(vOrigem, vDestino, peso);
                        } else {
                            grafo.adicionarAresta(vOrigem, vDestino);
                        }
                    }
                }
            } 

            return grafo;

        } catch (NumberFormatException e) {

            throw new Exception("Erro de formato numérico ao carregar o grafo: " + e.getMessage(), e);
        } catch (Exception e) {

            throw new Exception("Erro ao carregar o grafo: " + e.getMessage(), e);
        }
    }
    private Busca_em_Profundidade DFS;

    public Collection<Aresta> buscaEmProfundidade(Grafo g) { //finalizado

        this.DFS = new Busca_em_Profundidade();
        this.DFS.CalcularDFS(g);

        return this.DFS.ArestasDaArvore(g);
    }

    public Collection<Aresta> arestasDeArvore(Grafo g) { //finalizado

        if(this.DFS == null){
            this.DFS = new Busca_em_Profundidade();
            this.DFS.CalcularDFS(g);
        }

        return this.DFS.ArestasDaArvore(g);
    }

    public Collection<Aresta> arestasDeRetorno(Grafo g) { //finalizado
        
        if(this.DFS == null){
            this.DFS = new Busca_em_Profundidade();
            this.DFS.CalcularDFS(g);
        }

        return this.DFS.ArestasDeRetorno(g);
    }

    public Collection<Aresta> arestasDeAvanco(Grafo g) { //finalizado
        if(this.DFS == null){
            this.DFS = new Busca_em_Profundidade();
            this.DFS.CalcularDFS(g);
        }

        return this.DFS.ArestasDeAvanco(g);
    }

    public Collection<Aresta> arestasDeCruzamento(Grafo g) { //finalizado
        if(this.DFS == null){
            this.DFS = new Busca_em_Profundidade();
            this.DFS.CalcularDFS(g);
        }

        return this.DFS.ArestasDeCruzamento(g);
    }

    public Integer getTempoDeDescoberta(Vertice v) { //finalizado

        if(this.DFS == null){
            return null;
        }

        return this.DFS.getDescoberta(v);
    }

    public Integer getTempoDeEncerramento(Vertice v) { //finalizado

        if(this.DFS == null){
            return null;
        }

        return this.DFS.getEncerramento(v);
    }

    public Collection<Aresta> buscaEmLargura(Grafo g, Vertice origem) { //finalizado

        Busca_em_Largura func = new Busca_em_Largura();
        Collection<Aresta> arvoreResultante = func.CalcularBFS(g, origem);
        return arvoreResultante;
    }

    public boolean existeCiclo(Grafo g) { //finalizado

        if (this.DFS == null){
            this.DFS = new Busca_em_Profundidade();
            this.DFS.CalcularDFS(g);
        }
        return this.DFS.existeCiclo();
    }

    public Collection<Aresta> arvoreGeradoraMinima(Grafo g) { //finalizado
        
        AGM func = new AGM();
        Collection<Aresta> AGM = func.CalcularAGM(g);
        
        return AGM;
    }

    public double custoDaArvoreGeradora(Grafo g, Collection<Aresta> arestas) {//finalizado
        
        AGM func = new AGM();
        return func.CustoAGM(g, arestas);
    }

    public ArrayList<Aresta> caminhoMinimo(Grafo g, Vertice origem, Vertice destino) {  //finalizado

        Caminho_Minimo func = new Caminho_Minimo();
        ArrayList<Aresta> CaminhoMinimo = func.CMDijkstra(g, origem, destino);
        return CaminhoMinimo;
       
    }

    public ArrayList<Aresta> caminhoMinimoBellmanFord(Grafo g, Vertice origem, Vertice destino) { //finalizado

        Caminho_Minimo func = new Caminho_Minimo();
        ArrayList<Aresta> CaminhoMinimo = func.CMBellmanFord(g, origem, destino);
        return CaminhoMinimo;
       
    }

    public double custoDoCaminhoMinimo(Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino) { //finalizado

        Caminho_Minimo func = new Caminho_Minimo();
        return func.CustoDoCaminhoMinimo(g, arestas, origem, destino);
    }   

    public double fluxoMaximo (Grafo g, Vertice origem, Vertice destino) { //finalizado

        Fluxo_Maximo func = new Fluxo_Maximo();
        Double fluxoMaximo = func.CalcularFLuxoMaximo(g, origem, destino);
        return fluxoMaximo;
    }

    public Grafo componentesFortementeConexos(Grafo g) { //finalizado

        Fortemente_Conectados func = new Fortemente_Conectados();
        Grafo grafoReduzido = func.CalcularFortementeConectados(g);
        return grafoReduzido;
    }
}
