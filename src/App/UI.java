package App;

import java.util.InputMismatchException;
import java.util.Scanner;

import Xadrez.PecaXadrez;
import Xadrez.PosicaoXadrez;

public class UI {
	
	
	public static PosicaoXadrez lePosicaoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		} 
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro lendo posicao do xadrez: valores validos sao de a1 até h8");
		}
	}
	
	public static void mostraTabuleiro(PecaXadrez[][] pecas) {
		for(int i=0; i<pecas.length; i++) {
			System.out.print((8-i)+" ");
			for(int j=0; j<pecas.length; j++) {
				mostraPeca(pecas[i][j]);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}
	
	private static void mostraPeca(PecaXadrez peca) {
		if(peca ==null) {
			System.out.print("-");
		}
		else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}

}
