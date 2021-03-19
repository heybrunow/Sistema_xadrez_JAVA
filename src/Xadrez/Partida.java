package Xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Tabuleiro.Peca;
import Tabuleiro.Posicao;
import Tabuleiro.Tabuleiro;
import Xadrez.pecas.Bishop;
import Xadrez.pecas.King;
import Xadrez.pecas.Knight;
import Xadrez.pecas.Pawn;
import Xadrez.pecas.Queen;
import Xadrez.pecas.Rook;

public class Partida {

	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaXadrez vulneravelEnPassant;
	private PecaXadrez promovida;

	private List<Peca> pecasNoTabuleiro = new ArrayList<Peca>();
	private List<Peca> pecasCapturadas = new ArrayList<Peca>();

	public Partida() {
		turno = 1;
		jogadorAtual = Cor.BRANCO;
		tabuleiro = new Tabuleiro(8, 8);
		iniciaPartida();
	}

	public PecaXadrez[][] getPecasPartida() {
		PecaXadrez[][] partida = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];

		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				partida[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return partida;
	}

	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
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

		if (testaCheck(jogadorAtual)) {
			desfazMovimento(origem, alvo, pecaCapturada);
			throw new XadrezException("Voc� nao pode se colocar em check");
		}
		PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(alvo);

		//movimento especial promo��o
		promovida = null;
		if(pecaMovida instanceof Pawn) {
			if(pecaMovida.getCor() == Cor.BRANCO && alvo.getLinha() == 0 || pecaMovida.getCor() == Cor.PRETO && alvo.getLinha() == 0 ) { 
				promovida =  (PecaXadrez)tabuleiro.peca(alvo);
				promovida = repoePecaPromovida("Q");

			}
		}



		check = (testaCheck(oponente(jogadorAtual))) ? true : false;

		if (testaCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}
		// movimento especial en passant
		if (pecaMovida instanceof Pawn
				&& (alvo.getLinha() == origem.getLinha() - 2 || alvo.getLinha() == origem.getLinha() + 2)) {
			vulneravelEnPassant = pecaMovida;
		} else {
			vulneravelEnPassant = null;
		}
		return (PecaXadrez) pecaCapturada;

	}

	public PecaXadrez repoePecaPromovida(String tipo) {
		if(promovida == null) {
			throw new IllegalStateException("Nao h� peca pra ser promovida");
		}
		if(!tipo.equals("B") && !tipo.equals("N") && !tipo.equals("R") && !tipo.equals("B") && !tipo.equals("Q")) {
			return promovida;
		}
		Posicao pos = promovida.getPosicaoXadrez().paraPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);

		PecaXadrez pecaNova = novaPeca(tipo,  promovida.getCor());
		tabuleiro.colocaPeca(pecaNova, pos);
		pecasNoTabuleiro.add(pecaNova);
		return pecaNova;

	}

	private PecaXadrez novaPeca(String tipo, Cor cor) {
		if(tipo.equals("B"))return new Bishop(tabuleiro, cor);
		if(tipo.equals("N"))return new Knight(tabuleiro, cor);
		if(tipo.equals("Q"))return new Queen(tabuleiro, cor);
		return new Rook(tabuleiro, cor);
	}


	private void validaPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("N�o existe peca na posicao de origem");
		}
		if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("A peca escolhida � do seu oponente");
		}
		if (!tabuleiro.peca(posicao).temMovimentoPossivel()) {
			throw new XadrezException("N�o existe movimentos possiveis para a peca escolhida");
		}
	}

	private void validaPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoEpossivel(destino)) {
			throw new XadrezException("A peca escolhida nao pode se mover pra posicao de destino");
		}
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private Peca fazMovimento(Posicao origem, Posicao alvo) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(origem);
		p.incrementaMovimentosContados();
		Peca pecaCapturada = tabuleiro.removePeca(alvo);
		tabuleiro.colocaPeca(p, alvo);
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		// movimento especial castling lado do rei
		if (p instanceof King && alvo.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.colocaPeca(torre, alvoT);
			torre.incrementaMovimentosContados();

		}
		// movimento especial castling lado rainha
		if (p instanceof King && alvo.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.colocaPeca(torre, alvoT);
			torre.incrementaMovimentosContados();
		}
		// movimento especial en passant
		if (p instanceof Pawn) {
			if (origem.getColuna() != alvo.getColuna() && pecaCapturada == null) {
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(alvo.getLinha() + 1, alvo.getColuna());
				} else {
					posicaoPeao = new Posicao(alvo.getLinha() - 1, alvo.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(posicaoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
		return pecaCapturada;
	}

	private void desfazMovimento(Posicao origem, Posicao alvo, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez) tabuleiro.removePeca(alvo);
		p.decrementaMovimentosContados();
		tabuleiro.colocaPeca(p, origem);
		if (pecaCapturada != null) {
			tabuleiro.colocaPeca(pecaCapturada, alvo);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
		// movimento especial castling lado do rei
		if (p instanceof King && alvo.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(alvoT);
			tabuleiro.colocaPeca(torre, origemT);
			torre.decrementaMovimentosContados();

		}
		// movimento especial castling lado rainha
		if (p instanceof King && alvo.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao alvoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabuleiro.removePeca(alvoT);
			tabuleiro.colocaPeca(torre, origemT);
			torre.decrementaMovimentosContados();
		}
		// movimento especial en passant
		if (p instanceof Pawn) {
			if (origem.getColuna() != alvo.getColuna() && pecaCapturada == vulneravelEnPassant) {
				PecaXadrez peao = (PecaXadrez) tabuleiro.removePeca(alvo);
				Posicao posicaoPeao;
				if (p.getCor() == Cor.BRANCO) {
					posicaoPeao = new Posicao(3, alvo.getColuna());
				} else {
					posicaoPeao = new Posicao(4, alvo.getColuna());
				}
				tabuleiro.colocaPeca(peao, posicaoPeao);
			}
		}
	}

	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}

	private PecaXadrez king(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca peca : lista) {
			if (peca instanceof King) {
				return (PecaXadrez) peca;
			}
		}
		throw new IllegalStateException("N�o existe rei " + cor + " nesse tabuleiro");
	}

	private boolean testaCheck(Cor cor) {
		Posicao posicaoKing = king(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOponentes) {
			boolean[][] matriz = p.movimentosPossiveis();
			if (matriz[posicaoKing.getLinha()][posicaoKing.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testaCheckMate(Cor cor) {
		if (!testaCheck(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca peca : lista) {
			boolean[][] matriz = peca.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Posicao origem = ((PecaXadrez) peca).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazMovimento(origem, destino);
						boolean auxTestaCheck = testaCheck(cor);
						desfazMovimento(origem, destino, pecaCapturada);
						if (!auxTestaCheck) {
							return false;
						}
					}
				}
			}

		}
		return true;
	}

	private void colocaNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocaPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}

	public void iniciaPartida() {

		colocaNovaPeca('a', 1, new Rook(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('b', 1, new Knight(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('c', 1, new Bishop(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('d', 1, new Queen(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 1, new King(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('f', 1, new Bishop(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('g', 1, new Knight(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('h', 1, new Rook(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('a', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('b', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('c', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('d', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('e', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('f', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('g', 2, new Pawn(tabuleiro, Cor.BRANCO, this));
		colocaNovaPeca('h', 2, new Pawn(tabuleiro, Cor.BRANCO, this));

		colocaNovaPeca('a', 8, new Rook(tabuleiro, Cor.PRETO));
		colocaNovaPeca('b', 8, new Knight(tabuleiro, Cor.PRETO));
		colocaNovaPeca('c', 8, new Bishop(tabuleiro, Cor.PRETO));
		colocaNovaPeca('d', 8, new Queen(tabuleiro, Cor.PRETO));
		colocaNovaPeca('e', 8, new King(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('f', 8, new Bishop(tabuleiro, Cor.PRETO));
		colocaNovaPeca('g', 8, new Knight(tabuleiro, Cor.PRETO));
		colocaNovaPeca('h', 8, new Pawn(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('a', 7, new Pawn(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('b', 7, new Pawn(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('c', 7, new Pawn(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('d', 7, new Pawn(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('e', 7, new Pawn(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('f', 7, new Pawn(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('g', 7, new Pawn(tabuleiro, Cor.PRETO, this));
		colocaNovaPeca('h', 7, new Pawn(tabuleiro, Cor.PRETO, this));

	}

	// GETTERS
	public int getTurno() {
		return turno;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecaXadrez getVulneravelEnPassant() {
		return vulneravelEnPassant;
	}

	public PecaXadrez getPromovida() {
		return promovida;

	}
}
