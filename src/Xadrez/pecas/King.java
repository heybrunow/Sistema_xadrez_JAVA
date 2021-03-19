package Xadrez.pecas;

import Tabuleiro.Posicao;
import Tabuleiro.Tabuleiro;
import Xadrez.Cor;
import Xadrez.Partida;
import Xadrez.PecaXadrez;

public class King extends PecaXadrez{

	private Partida partida;

	public King(Tabuleiro tabuleiro, Cor cor, Partida partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean testeRookCastling(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Rook && p.getCor() == getCor() && p.getContadorMovimentos() == 0;
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor();
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// above
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// below
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// left
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// right
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nw
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// ne
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sw
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// se
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		//#moivmento especial castling 
		if(getContadorMovimentos()==0 && !partida.getCheck()) {
			//castling lado do rei
			Posicao posicaoT1 = new Posicao(posicao.getLinha(), posicao.getColuna()+3);
			if(testeRookCastling(posicaoT1)) {
				Posicao p1 =  new Posicao(posicao.getLinha(), posicao.getColuna()+1);
				Posicao p2 =  new Posicao(posicao.getLinha(), posicao.getColuna()+2);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna()+2] = true;
				}
			}
			//castling lado rainha
			if(getContadorMovimentos()==0 && !partida.getCheck()) {
				//castling lado do rei
				Posicao posicaoT2 = new Posicao(posicao.getLinha(), posicao.getColuna()-4);
				if(testeRookCastling(posicaoT2));
				Posicao p1 =  new Posicao(posicao.getLinha(), posicao.getColuna()-1);
				Posicao p2 =  new Posicao(posicao.getLinha(), posicao.getColuna()-2);
				Posicao p3 =  new Posicao(posicao.getLinha(), posicao.getColuna()-3);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna()-2] = true;
				}
			}
		}


		return mat;
	}




}




