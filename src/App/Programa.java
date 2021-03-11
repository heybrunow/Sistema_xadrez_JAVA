package App;

import java.util.InputMismatchException;
import java.util.Scanner;

import Xadrez.Partida;
import Xadrez.PecaXadrez;
import Xadrez.PosicaoXadrez;
import Xadrez.XadrezException;

public class Programa {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Partida partida = new Partida();
		
		while(true) {
			try {
				UI.mostraPartida(partida);
				System.out.println();
				System.out.println("Origem:");
				PosicaoXadrez origem = UI.lePosicaoXadrez(sc);
				System.out.println("Você pode mover sua peca para as posicoes marcadas com X");
				boolean[][] moivmentosPossiveis = partida.movimentosPossiveis(origem);
				UI.mostraTabuleiro(partida.getPecasPartida(), moivmentosPossiveis);
				
				System.out.println();
				System.out.println("Destino:");
				PosicaoXadrez destino = UI.lePosicaoXadrez(sc);
				
				PecaXadrez pecaCapturada = partida.PerformaMovimentoXadrez(origem, destino);
				
			} catch (XadrezException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		
		}
		
	}

}
