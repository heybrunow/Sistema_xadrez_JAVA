package Xadrez.pecas;

import Tabuleiro.Tabuleiro;
import Xadrez.Cor;
import Xadrez.PecaXadrez;

public class Rook extends PecaXadrez{

	
	
	public Rook(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] partida = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getLinhas()];
		return partida;
	}
	
	
}
