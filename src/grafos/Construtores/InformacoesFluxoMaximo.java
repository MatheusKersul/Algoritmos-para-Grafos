package Construtores;

public class InformacoesFluxoMaximo {
    
    public Aresta arestaOriginal; 
    public Double capacidadeResidual;
    public boolean residualReversa; 

    public InformacoesFluxoMaximo(Aresta a, Double capacidade, boolean reversa) {

        this.arestaOriginal = a;
        this.capacidadeResidual = capacidade;
        this.residualReversa = reversa;
    }
}
