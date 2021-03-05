package Tabuleiro;

public class Posicao {
	
	private int linha;
	private int coluna;
	
	
	public Posicao(int linha, int coluna) {
		super();
		this.linha = linha;
		this.coluna = coluna;
	}
	
	@Override
	public String toString() {
		return linha + ", " +coluna;
	}
	//GETTERS E SETTERS 
	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
	
	
	
	

}
