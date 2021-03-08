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
		colocaNovaPeca('c', 1, new Rook(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('c', 2, new Rook(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('d', 2, new Rook(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 2, new Rook(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 1, new Rook(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('d', 1, new King(tabuleiro, Cor.BRANCO));


		colocaNovaPeca('c', 7, new Rook(tabuleiro, Cor.PRETO));
		colocaNovaPeca('c', 8, new Rook(tabuleiro, Cor.PRETO));
		colocaNovaPeca('d', 7, new Rook(tabuleiro, Cor.PRETO));
		colocaNovaPeca('e', 7, new Rook(tabuleiro, Cor.PRETO));
		colocaNovaPeca('e', 8, new Rook(tabuleiro, Cor.PRETO));
		colocaNovaPeca('d', 8, new King(tabuleiro, Cor.PRETO));
	}
}
