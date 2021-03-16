package Xadrez;

import Tabuleiro.Peca;
import Tabuleiro.Posicao;
import Tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca{

	private Cor cor;
	private int contadorMovimentos;
	

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	protected boolean temPecaOponente(Posicao posicao) {
		PecaXadrez peca = (PecaXadrez) getTabuleiro().peca(posicao);

		return peca != null && peca.getCor() != cor;
	}

	public void incrementaMovimentosContados() {
		contadorMovimentos++;
	}
	
	public void decrementaMovimentosContados() {
		contadorMovimentos--;
	}

	//GETTER
	public Cor getCor() {
		return cor;
	}

	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.daPosicao(posicao);
	}
	
	public int getContadorMovimentos() {
		return contadorMovimentos;
	}











}
