package Xadrez;

import Tabuleiro.Peca;
import Tabuleiro.Tabuleiro;

public class PecaXadrez extends Peca{
	
	private Cor cor;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	//GET
	public Cor getCor() {
		return cor;
	}


	
	
	
	


	
	
	

}
