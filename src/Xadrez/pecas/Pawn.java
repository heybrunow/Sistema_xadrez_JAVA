package Xadrez.pecas;

import Tabuleiro.Posicao;
import Tabuleiro.Tabuleiro;
import Xadrez.Cor;
import Xadrez.Partida;
import Xadrez.PecaXadrez;

public class Pawn extends PecaXadrez{

	private Partida partida;

	public Pawn(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;

	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);
		//BRANCO
		if(getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getContadorMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}			
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			} 
			//movimento especial en passant branco
			if(posicao.getLinha()==3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(esquerda) && temPecaOponente(esquerda) && getTabuleiro().peca(esquerda)==partida.getVulneravelEnPassant()) {
					matriz[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(direita) && temPecaOponente(direita) && getTabuleiro().peca(direita)==partida.getVulneravelEnPassant()) {
					matriz[esquerda.getLinha() - 1][direita.getColuna()] = true;
				}
			}
		}
		else {
			//PRETO
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().temUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temUmaPeca(p2) && getContadorMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && temPecaOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			//movimento especial en passant preto
			if(posicao.getLinha()==4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(esquerda) && temPecaOponente(esquerda) && getTabuleiro().peca(esquerda)==partida.getVulneravelEnPassant()) {
					matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(direita) && temPecaOponente(direita) && getTabuleiro().peca(direita)==partida.getVulneravelEnPassant()) {
					matriz[esquerda.getLinha() + 1][direita.getColuna()] = true;
				}
			}

		}
		return matriz;
	}

	@Override
	public String toString() {
		return "P";
	}


}
