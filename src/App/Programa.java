package App;

import Tabuleiro.Posicao;
import Tabuleiro.Tabuleiro;
import Xadrez.Partida;

public class Programa {
	public static void main(String[] args) {
		
		Partida p1 = new Partida();
		UI.mostraTabuleiro(p1.getPecasPartida());
	}

}
