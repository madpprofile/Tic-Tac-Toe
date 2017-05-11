package br.cefet.aps.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GameContext {
	int[] s = new int[9];
	Strategy difficulty;
	boolean vsCPU;
	
	GameContext(Strategy strategy){
		this.difficulty = strategy;
		this.vsCPU = true;
		
	}
	
	GameContext(){
		this.vsCPU = false;
	}
	
	public int[] getContext(){
		return this.s;
	}
	
	public void run(){
		int player = 1;
		Scanner sc = new Scanner(System.in);
		while(player != 0){
			play(sc, player);
			int winner = isTerminal();
			if(winner == 1){
				System.out.println("Player X wins!");
				sc.close();
				return;
				
			} else if(winner == 2){
				System.out.println("Player O wins!");
				sc.close();
				return;
				
			} else if(winner == -1){
				System.out.println("Draw!");
				sc.close();
				return;
				
			}
			
			//alternate between players
			if(player == 1)
					player = 2;
			else
				player = 1;
		}
	}
	
	public void play(Scanner sc, int player){
		switch(player){
			//if it's player's turn
			case 1:
				playerMove(sc, player);
				break;
			//if it's CPU's turn
			default:
				if(vsCPU)
					cpuMove();
				else
					playerMove(sc, 2);
		}
		
	}
	
	private void playerMove(Scanner sc, int player){
		System.out.println("Select a spot to play: ");
		int spot = sc.nextInt();
		if(isLegal(spot)){
			s[spot] = player;
			draw();
		} else {
			System.out.print("Illegal movement!\nAvailable movements: ");
			for(int i : getLegalMoves()){
				System.out.print(i + " ");
				playerMove(sc, player);
			}
		}
	}
	
	private void cpuMove(){
		int move = difficulty.play(s);
		if(isLegal(move)){
			s[move] = 2;
		//if strategy is broken
		} else{
			System.err.println("Your strategy sucks!");
			System.exit(1);
		}
		draw();
		
	}
	
	public boolean isLegal(int i){
		List<Integer> legalMoves = getLegalMoves();
		if(legalMoves.contains(i))
			return true;
		return false;
	}
	
	private List<Integer> getLegalMoves(){
		List<Integer> l = new ArrayList<Integer>();
		for(int i = 0; i < s.length; i++){
			if(s[i] == 0)
				l.add(i);
		}
		return l;
	}
	
	public int isTerminal(){
		//horizontals
		if(s[0] != 0 && s[0] == s[1] && s[1] == s[2])
			return s[0];
		else if(s[3] != 0 && s[3] == s[4] && s[4] == s[5])
			return s[3];
		else if(s[6] != 0 && s[6] == s[7] && s[7] == s[8])
			return s[6];
		//verticals
		else if(s[0] != 0 && s[0] == s[3] && s[3] == s[6])
			return s[0];
		else if(s[1] != 0 && s[4] == s[7] && s[4] == s[5])
			return s[1];
		else if(s[2] != 0 && s[2] == s[5] && s[5] == s[8])
			return s[2];
		//diagonals
		else if(s[0] != 0 && s[0] == s[4] && s[4] == s[8])
			return s[0];
		else if(s[2] != 0 && s[2] == s[4] && s[4] == s[6])
			return s[2];
		
		//no more places
		boolean isOver = true;
		for(int i = 0; i < s.length; i++){
			if(s[i] != 0)
				isOver = false;
		}
		if(isOver)
			return -1;
		
		//not terminal
		return 0;
	}
	
	public void draw(){
		System.out.println(
				"-------------\n" +
				"| " + drawValue(s[0]) + " | " +  drawValue(s[1]) + " | " + drawValue(s[2]) + " |\n" +
				"-------------\n" +
				"| " + drawValue(s[3]) + " | " +  drawValue(s[4]) + " | " + drawValue(s[5]) + " |\n" +
				"-------------\n" +
				"| " + drawValue(s[6]) + " | " +  drawValue(s[7]) + " | " + drawValue(s[8]) + " |\n" +
				"-------------\n"
				);
	}
	
	private String drawValue(int player) {
		switch (player) {
		case 1:
			return "X";
		case 2:
			return "O";
		default:
			return " ";
		}
	}

}
