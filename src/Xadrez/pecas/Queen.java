package Xadrez.pecas;

import Tabuleiro.Posicao;
import Tabuleiro.Tabuleiro;
import Xadrez.Cor;
import Xadrez.PecaXadrez;

public class Queen extends PecaXadrez{



	public Queen(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] matriz = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getLinhas()];
		Posicao aux = new Posicao(0, 0);
		//pra cima
		aux.setValores(posicao.getLinha()-1, posicao.getColuna());
		while(getTabuleiro().posicaoExiste(aux)&& !getTabuleiro().temUmaPeca(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
			aux.setLinha(aux.getLinha()-1);
		}
		if(getTabuleiro().posicaoExiste(aux) && temPecaOponente(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
		}
		//pra esquerda
		aux.setValores(posicao.getLinha(), posicao.getColuna()-1);
		while(getTabuleiro().posicaoExiste(aux)&& !getTabuleiro().temUmaPeca(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
			aux.setColuna(aux.getColuna()-1);
		}
		if(getTabuleiro().posicaoExiste(aux) && temPecaOponente(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
		}

		//pra direita
		aux.setValores(posicao.getLinha(), posicao.getColuna()+1);
		while(getTabuleiro().posicaoExiste(aux)&& !getTabuleiro().temUmaPeca(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
			aux.setColuna(aux.getColuna()+1);
		}
		if(getTabuleiro().posicaoExiste(aux) && temPecaOponente(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
		}

		//pra baixo
		aux.setValores(posicao.getLinha()+1, posicao.getColuna());
		while(getTabuleiro().posicaoExiste(aux)&& !getTabuleiro().temUmaPeca(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
			aux.setLinha(aux.getLinha()+1);
		}
		if(getTabuleiro().posicaoExiste(aux) && temPecaOponente(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
		}
		//Diagonais
		//noroeste
		aux.setValores(posicao.getLinha()-1, posicao.getColuna()-1);
		while(getTabuleiro().posicaoExiste(aux)&& !getTabuleiro().temUmaPeca(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
			aux.setValores(aux.getLinha()-1, aux.getColuna()-1);
		}
		if(getTabuleiro().posicaoExiste(aux) && temPecaOponente(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
		}
		//nordeste
		aux.setValores(posicao.getLinha()-1, posicao.getColuna()+1);
		while(getTabuleiro().posicaoExiste(aux)&& !getTabuleiro().temUmaPeca(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
			aux.setValores(aux.getLinha()-1, aux.getColuna()+1);
		}
		if(getTabuleiro().posicaoExiste(aux) && temPecaOponente(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
		}

		//sudeste
		aux.setValores(posicao.getLinha()+1, posicao.getColuna()+1);
		while(getTabuleiro().posicaoExiste(aux)&& !getTabuleiro().temUmaPeca(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
			aux.setValores(aux.getLinha()+1, aux.getColuna()+1);
		}
		if(getTabuleiro().posicaoExiste(aux) && temPecaOponente(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
		}

		//sudoeste
		aux.setValores(posicao.getLinha()+1, posicao.getColuna()-1);
		while(getTabuleiro().posicaoExiste(aux)&& !getTabuleiro().temUmaPeca(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
			aux.setValores(aux.getLinha()+1, aux.getColuna()-1);
		}
		if(getTabuleiro().posicaoExiste(aux) && temPecaOponente(aux)) {
			matriz[aux.getLinha()][aux.getColuna()] = true;
		}
		

		return matriz;
	}


}