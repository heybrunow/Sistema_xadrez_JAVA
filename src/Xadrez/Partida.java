package Xadrez;

import Tabuleiro.Posicao;
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
	
	public void iniciaPartida() {
		tabuleiro.colocaPeca(new Rook(tabuleiro, Cor.BRANCO), new Posicao(2, 1));
		tabuleiro.colocaPeca(new King(tabuleiro, Cor.PRETO), new Posicao(0, 4));
		tabuleiro.colocaPeca(new King(tabuleiro, Cor.BRANCO), new Posicao(7, 4));
	}
}
