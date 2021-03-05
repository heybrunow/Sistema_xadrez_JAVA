package Xadrez;

import Tabuleiro.Tabuleiro;
import Xadrez.pecas.King;
import Xadrez.pecas.Rook;

public class Partida {
	
	private Tabuleiro tabuleiro;

	
	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
		iniciaPartida();
	}
	
	public PecaXadrez[][] getPecasPartida(){
		PecaXadrez[][] partida =  new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		
		for(int i=0; i< tabuleiro.getLinhas(); i++) {
			for(int j=0; j<tabuleiro.getColunas(); j++) {
				partida[i][j] =  (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return partida;
	} 
	
	private void colocaNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocaPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
	}
	
	public void iniciaPartida() {
		colocaNovaPeca('b', 6, new Rook(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 8,new King(tabuleiro, Cor.PRETO));
		colocaNovaPeca('e', 1,new King(tabuleiro, Cor.BRANCO));
	}
}
