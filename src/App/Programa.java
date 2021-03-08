package App;

import java.util.Scanner;

import Xadrez.Partida;
import Xadrez.PecaXadrez;
import Xadrez.PosicaoXadrez;

public class Programa {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Partida partida = new Partida();
		
		while(true) {
			UI.mostraTabuleiro(partida.getPecasPartida());
		System.out.println();
		System.out.println("Origem:");
		PosicaoXadrez origem = UI.lePosicaoXadrez(sc);
		
		System.out.println();
		System.out.println("Destino:");
		PosicaoXadrez destino = UI.lePosicaoXadrez(sc);
		
		PecaXadrez pecaCapturada = partida.PerformaMovimentoXadrez(origem, destino);
		
		
		}
		
	}

}
