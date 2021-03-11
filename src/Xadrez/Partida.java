package Xadrez;

import java.util.ArrayList;
import java.util.List;

import Tabuleiro.Peca;
import Tabuleiro.Posicao;
import Tabuleiro.Tabuleiro;
import Xadrez.pecas.King;
import Xadrez.pecas.Rook;

public class Partida {
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
	private List<PecaXadrez>pecasNoTabuleiro = new ArrayList<PecaXadrez>();
	private List<Peca>pecasCapturadas = new ArrayList<Peca>();
	
	public Partida() {
		turno = 1;
		jogadorAtual = Cor.BRANCO; 
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
	
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.paraPosicao();
		validaPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaXadrez PerformaMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoAlvo) {
		Posicao origem = posicaoOrigem.paraPosicao();
		Posicao alvo = posicaoAlvo.paraPosicao();
		validaPosicaoOrigem(origem);
		validaPosicaoDestino(origem, alvo);
		Peca pecaCapturada = fazMovimento(origem, alvo);
		proximoTurno();
		return (PecaXadrez)pecaCapturada;
		
	}
	
	private void validaPosicaoOrigem(Posicao posicao) {
		if(!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("Não existe peca na posicao de origem");
		}
		if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peca escolhida é do seu oponente"); 
		}
		if(!tabuleiro.peca(posicao).temMovimentoPossivel()) {
			throw new XadrezException("Não existe movimentos possiveis para a peca escolhida");
		}
	}
	
	private void validaPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentoEpossivel(destino)){
			throw new XadrezException("A peca escolhida nao pode se mover pra posicao de destino");
		}
	}
	
	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Peca fazMovimento(Posicao origem, Posicao alvo) {
		Peca p = tabuleiro.removePeca(origem);
		Peca pecaCapturada = tabuleiro.removePeca(alvo);
		tabuleiro.colocaPeca(p, alvo);
		if(pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}
	
	private void colocaNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocaPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
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
	//GETTERS
	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	
}


