package Tabuleiro;

public abstract class Peca {

	protected Posicao posicao;
	private Tabuleiro tabuleiro;

	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	public abstract boolean[][] movimentosPossiveis();

	public  boolean movimentoEpossivel(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}

	public boolean temMovimentoPossivel() {
		boolean[][] partida = movimentosPossiveis();
		for(int i = 0; i<partida.length; i++) {
			for(int j = 0; j<partida.length; j++) {
				if(partida[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	//GETTER
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}








}
