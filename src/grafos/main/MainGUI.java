package main;
import Construtores.*;
import Interfaces.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;


public class MainGUI extends JFrame {

   
    private final JTextField txtCaminhoArquivo;
    private final JComboBox<TipoDeRepresentacao> comboRepresentacao;
    private final JComboBox<AlgoritmoParaTestar> comboAlgoritmo; 
    private final JTextField txtOrigem;
    private final JTextField txtDestino;
    private final JButton btnExecutar;
    private final JTextArea areaLog; 
    private final JFileChooser fileChooser;
    private final Algoritmos algoritmos;

   
    private enum AlgoritmoParaTestar {
        BASICOS("Informações Básicas"),
        EXISTE_CICLO("Detecção de Ciclo"),
        AGM("Árvore Geradora Mínima (AGM)"),
        CAMINHO_MINIMO_BELLMAN_FORD("Caminho Mínimo (Bellman-Ford)"),
        CAMINHO_MINIMO_DIJKSTRA("Caminho Mínimo (DIJSKTRA)"),        
        BUSCA_EM_LARGURA("Busca em Largura (BFS)"),
        BUSCA_EM_PROFUNDIDADE("Busca em Profundidade (DFS)"),
        FORTEMENTE_CONEXOS("Componentes Fortemente Conexos"),
        FLUXO_MAXIMO("Fluxo Máximo");
        
        private final String label;
        AlgoritmoParaTestar(String label) { this.label = label; }
        @Override public String toString() { return this.label; } // Texto que aparece no dropdown
    }

    public MainGUI() {
        setTitle("Testador de Algoritmos de Grafos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        this.algoritmos = new Algoritmos();
        this.fileChooser = new JFileChooser("."); 

        JPanel panelControles = new JPanel();
        panelControles.setLayout(new GridBagLayout());
        panelControles.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.gridx = 0;
        panelControles.add(new JLabel("Arquivo do Grafo:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0; 
        txtCaminhoArquivo = new JTextField(40);
        txtCaminhoArquivo.setEditable(false);
        panelControles.add(txtCaminhoArquivo, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        JButton btnProcurar = new JButton("Procurar...");
        panelControles.add(btnProcurar, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        panelControles.add(new JLabel("Representação:"), gbc);
        gbc.gridx = 1;
        comboRepresentacao = new JComboBox<>(TipoDeRepresentacao.values());
        panelControles.add(comboRepresentacao, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panelControles.add(new JLabel("Algoritmo:"), gbc);
        gbc.gridx = 1;
        comboAlgoritmo = new JComboBox<>(AlgoritmoParaTestar.values());
        panelControles.add(comboAlgoritmo, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panelControles.add(new JLabel("Origem / Destino:"), gbc);
        
        JPanel panelOrigDest = new JPanel(new GridLayout(1, 2, 5, 0));
        txtOrigem = new JTextField("0");
        txtDestino = new JTextField("4");
        panelOrigDest.add(txtOrigem);
        panelOrigDest.add(txtDestino);
        gbc.gridx = 1;
        panelControles.add(panelOrigDest, gbc);

        gbc.gridy = 4; 
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        btnExecutar = new JButton("EXECUTAR ALGORITMO"); 
        btnExecutar.setFont(new Font("Arial", Font.BOLD, 14));
        panelControles.add(btnExecutar, gbc);

        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollLog = new JScrollPane(areaLog); 

        add(panelControles, BorderLayout.NORTH);
        add(scrollLog, BorderLayout.CENTER);


        btnProcurar.addActionListener(e -> onProcurarArquivo());
        
  
        btnExecutar.addActionListener(e -> onExecutarTesteSelecionado()); 

  
        comboAlgoritmo.addActionListener(e -> onAlgoritmoSelecionado());


        onAlgoritmoSelecionado();

    
        setVisible(true);
    }

    private void onProcurarArquivo() {
        int retorno = fileChooser.showOpenDialog(this);
        if (retorno == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            txtCaminhoArquivo.setText(arquivo.getAbsolutePath());
            log("Arquivo selecionado: " + arquivo.getName());
        }
    }
 
    private void onAlgoritmoSelecionado() {
        AlgoritmoParaTestar selecionado = (AlgoritmoParaTestar) comboAlgoritmo.getSelectedItem();
        
        boolean habilitar = (selecionado == AlgoritmoParaTestar.CAMINHO_MINIMO_BELLMAN_FORD || selecionado == AlgoritmoParaTestar.CAMINHO_MINIMO_DIJKSTRA
                            || selecionado == AlgoritmoParaTestar.BUSCA_EM_LARGURA || selecionado == AlgoritmoParaTestar.BUSCA_EM_PROFUNDIDADE
                            || selecionado == AlgoritmoParaTestar.FLUXO_MAXIMO);
        txtOrigem.setEnabled(habilitar);
        txtDestino.setEnabled(habilitar);
    }

    private void onExecutarTesteSelecionado() {
       
        areaLog.setText("");
        
       
        String path = txtCaminhoArquivo.getText();
        TipoDeRepresentacao tipo = (TipoDeRepresentacao) comboRepresentacao.getSelectedItem();
        AlgoritmoParaTestar algoritmo = (AlgoritmoParaTestar) comboAlgoritmo.getSelectedItem();
        
   
        if (path.isEmpty()) {

            log("!!! ERRO: Por favor, selecione um arquivo de grafo primeiro.");
            return;
        }

        try {
       
            log(">>> Carregando grafo com representação: " + tipo + " <<<");
            Grafo g = algoritmos.carregarGrafo(path, tipo);
            log("Grafo carregado com sucesso.");
            int numVertices = g.numeroDeVertices();
            log("\n[INICIANDO ALGORITMO: " + algoritmo + "]");

            switch (algoritmo) {
                
                case BASICOS:
                    log("Numero de Vértices: " + numVertices);
                    log("Numero de Arestas: " + g.numeroDeArestas());
                    if (numVertices > 0) {
                        Vertice v0 = g.vertices().get(0);
                        log("Grau de Saída do Vértice 0: " + g.grauDoVertice(v0));
                        ArrayList<Vertice> adjV0 = g.adjacentesDe(v0);
                        String adjStr = "";
                        for(Vertice v : adjV0) adjStr += v.id() + " ";
                        log("Adjacentes de 0: " + adjStr);
                    }
                    break;

                case AGM:
                    Collection<Aresta> agm = algoritmos.arvoreGeradoraMinima(g);
                    log("Arestas da AGM encontradas (" + agm.size() + " arestas):");
                    log(formatarArestas(agm)); 
                    double custoAGM = algoritmos.custoDaArvoreGeradora(g, agm);
                    log("Custo total da AGM: " + custoAGM);
                    break;

                case CAMINHO_MINIMO_DIJKSTRA:
                    int idOrigemD = Integer.parseInt(txtOrigem.getText());
                    int idDestinoD = Integer.parseInt(txtDestino.getText());

                    if (idOrigemD < 0 || idOrigemD >= numVertices || idDestinoD < 0 || idDestinoD >= numVertices) {
                        log("!!! ERRO: IDs de Origem ou Destino inválidos.");
                        break;
                    }
                    Vertice vOrigemD = g.vertices().get(idOrigemD);
                    Vertice vDestinoD = g.vertices().get(idDestinoD);
                    log("Calculando caminho de Vértice " + idOrigemD + " para Vértice " + idDestinoD + "...");
                    
                    ArrayList<Aresta> caminhoD = algoritmos.caminhoMinimo(g, vOrigemD, vDestinoD);

                    if (caminhoD == null) {
                        log("Resultado: Foi detectado um ciclo negativo!");
                    } else if (caminhoD.isEmpty()) {
                        log("Resultado: Não há caminho entre " + idOrigemD + " e " + idDestinoD + ".");
                    } else {
                        log("Caminho encontrado:");
                        log(formatarArestas(caminhoD));
                        double custoCaminho = algoritmos.custoDoCaminhoMinimo(g, caminhoD, vOrigemD, vDestinoD);
                        log("Custo total do Caminho: " + custoCaminho);
                    }
                    break;


                case CAMINHO_MINIMO_BELLMAN_FORD:
                    int idOrigem = Integer.parseInt(txtOrigem.getText());
                    int idDestino = Integer.parseInt(txtDestino.getText());

                    if (idOrigem < 0 || idOrigem >= numVertices || idDestino < 0 || idDestino >= numVertices) {
                        log("!!! ERRO: IDs de Origem ou Destino inválidos.");
                        break;
                    }
                    
                    Vertice vOrigem = g.vertices().get(idOrigem);
                    Vertice vDestino = g.vertices().get(idDestino);
                    log("Calculando caminho de Vértice " + idOrigem + " para Vértice " + idDestino + "...");
                    
                    ArrayList<Aresta> caminho = algoritmos.caminhoMinimoBellmanFord(g, vOrigem, vDestino);

                    if (caminho == null) {
                        log("Resultado: Foi detectado um ciclo negativo!");
                    } else if (caminho.isEmpty()) {
                        log("Resultado: Não há caminho entre " + idOrigem + " e " + idDestino + ".");
                    } else {
                        log("Caminho encontrado:");
                        log(formatarArestas(caminho));
                        double custoCaminho = algoritmos.custoDoCaminhoMinimo(g, caminho, vOrigem, vDestino);
                        log("Custo total do Caminho: " + custoCaminho);
                    }
                    break;

                case BUSCA_EM_PROFUNDIDADE:
                    log("Executando Busca em Profundidade (DFS)...");
                    Collection<Aresta> dfs = algoritmos.buscaEmProfundidade(g);

                        for(Vertice v : g.vertices()){
                            log(String.format("Descoberta do Vértice %d: %d // Finalização do Vértice %d: %d", v.id(), (algoritmos.getTempoDeDescoberta(v)), v.id(), (algoritmos.getTempoDeEncerramento(v))));
                        }

                        log("===============Arestas resultantes da DFS===============");
                            log(formatarArestas(dfs));

                        log("===============Arestas de Avanço===============");
                            log(formatarArestas(algoritmos.arestasDeAvanco(g)));


                        log("===============Arestas de Retorno===============");
                            log(formatarArestas(algoritmos.arestasDeRetorno(g)));
        
                        log("===============Arestas de Cruzamento===============");
                            log(formatarArestas(algoritmos.arestasDeCruzamento(g)));
           
                    break;

                case EXISTE_CICLO:
                    log("Verificando existência de ciclo no grafo...");
                    boolean temCiclo = algoritmos.existeCiclo(g);
                    if (temCiclo) {
                        log("Resultado: O grafo possui ciclo(s).");
                    } else {
                        log("Resultado: O grafo NÃO possui ciclos.");
                    }
                    break;

                case BUSCA_EM_LARGURA:
                    int idOrigemBFS = Integer.parseInt(txtOrigem.getText());
                    Vertice origemBFS = g.vertices().get(idOrigemBFS);
                        log("Executando Busca em Largura (BFS)...");
                        Collection<Aresta> bfs = algoritmos.buscaEmLargura(g, origemBFS);
                            log("BFS encontrou " + bfs.size() + " arestas de árvore:");
                            log(formatarArestas(bfs));
                        
                        break;
                
                case FORTEMENTE_CONEXOS:
                    log("Executando Componentes Fortemente Conexos...");

                    Grafo gReduzido = algoritmos.componentesFortementeConexos(g);

                    if (gReduzido == null) {
                        log("!!! ERRO: O algoritmo não retornou um grafo reduzido.");
                        break;
                    }

                    log("===== RESULTADO =====");
                    log("Número de vértices no grafo reduzido: " + gReduzido.numeroDeVertices());
                    log("Número de arestas no grafo reduzido: " + gReduzido.numeroDeArestas());

                    Collection<Aresta> arestasReduzidas = new ArrayList<>();

                    for(Vertice u : gReduzido.vertices()){
                        for(Vertice v : gReduzido.vertices()){
                            arestasReduzidas.addAll(gReduzido.arestasEntre(u, v));
                        }
                    }
                    log("\nArestas do grafo reduzido:");
                    log(formatarArestas(arestasReduzidas));

                    log("\nVértices do grafo reduzido:");
                    for (Vertice v : gReduzido.vertices()) {
                        log("Vértice: " + v.id());
                    }

                    break;

                case FLUXO_MAXIMO:
                    int idOrigemFM = Integer.parseInt(txtOrigem.getText());
                    int idDestinoFM = Integer.parseInt(txtDestino.getText());
                    Vertice origemFM = g.vertices().get(idOrigemFM);
                    Vertice destinoFM = g.vertices().get(idDestinoFM);
                    log("Executando Fluxo Máximo...");
                    double fluxo = algoritmos.fluxoMaximo(g, origemFM, destinoFM);
                    if (fluxo == 0.0) {
                         log("Resultado: 0.0 (Não existe caminho)");
                    } else {
                        log("Resultado (Valor do Fluxo): " + fluxo);
                    }
                    break;
            }

        } catch (NumberFormatException nfe) {
            log("\n!!!! FALHA CRÍTICA !!!!");
            log("ERRO: Origem e Destino devem ser números inteiros válidos.");
        } catch (Exception e) {
            log("\n!!!! FALHA CRÍTICA !!!!");
            log("ERRO: " + e.getMessage());
            e.printStackTrace(); 
        }
        
        log("\n>>> FIM DO TESTE <<<");
    }

    private void log(String mensagem) {
        areaLog.append(mensagem + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    private String formatarArestas(Collection<Aresta> arestas) {
        if (arestas == null || arestas.isEmpty()) {
            return " (Nenhuma aresta na coleção)";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Aresta a : arestas) {
            if (a != null) {
                sb.append(
                    String.format("  De: %d  -> Para: %d  (Peso: %.1f)\n", 
                        a.origem().id(), 
                        a.destino().id(), 
                        a.peso())
                );
            }
        }
        return sb.toString().trim(); 
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}