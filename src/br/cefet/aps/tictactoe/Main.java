package br.cefet.aps.tictactoe;

public class Main {

	public static void main(String[] args) {
		if(args.length < 1 || args.length > 2){
			usage();
			return;
		}
		if(!args[0].equals("1") && !args[0].equals("2")){
			usage();
			return;
		}
		try{
			newGame(args).run();
		} catch(NullPointerException e){
			System.out.println("Strategy " + args[1] + " unknown");
			usage();
			return;
		}

	}
	
	public static GameContext newGame(String[] args){
		if(args[0].equals("2"))
			return new GameContext();
		else{
			switch(args[1]){
				case "random":
					return new GameContext(new StrategyRandom());
				case "impossible":
					return new GameContext(new StrategyImpossible());
				default:
					return null;
			}
			
		}
	}
	
	public static void usage(){
		System.out.println(
					"1 player game:\n" +
					"progname 1 [strategy]\n" +
					"strategy values: impossible or random" +
					"2 players game:\n" +
					"progname 2"
				);
	}

}
