/**
 * CSE 5360-002 Artificial Intelligence- 1
 * Name: Shagun Paul
 * Student ID # : 1001557958
 *
 * @author Shagun
 */

import java.util.Scanner;

/**
 * This class controls the game play for the Max Connect-Four game. 
 * To compile the program, use the following command from the maxConnectFour directory:
 * javac *.java
 */

public class maxconnect4
{

	private static GameBoard current_game;
	private static AiPlayer calculon;
	private static Scanner instr;

	public static void main(String[] args) 
	{
		// check for the correct number of arguments
		if( args.length != 4 ) 
		{
			System.out.println("Four command-line arguments are needed:\n"
					+ "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
					+ " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

			exit_function( 0 );
		}

		// parse the input arguments
		String game_mode = args[0].toString();				// the game mode
		String input = args[1].toString();					// the input game file
		int dept_limit = Integer.parseInt( args[3] );  		// the depth level of the ai search

		// create and initialize the game board
		current_game = new GameBoard( input );

		// create the Ai Player
		calculon = new AiPlayer(dept_limit, current_game);

		if( game_mode.equalsIgnoreCase( "interactive" ) ) 
		{
			current_game.setGameMode("interactive");
			if (args[2].toString().equalsIgnoreCase("computer-next") || args[2].toString().equalsIgnoreCase("C")) {
				// if it is computer next, make the computer make a move
				current_game.set_FirstMove("computer");
				InteractivePlayComputerMove();
			} else if (args[2].toString().equalsIgnoreCase("human-next") || args[2].toString().equalsIgnoreCase("H")){
				current_game.set_FirstMove("human");
				HumanMove();
			} else {
				System.out.println("\n" + "value for 'next turn' doesn't recognized.  \n try again. \n");
				exit_function(0);
			}

			if (current_game.is_Board_Full()) {
				System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
				exit_function(0);
			}
		} 

		else if(game_mode.equalsIgnoreCase("one-move")) 
		{
			// /////////// one-move mode ///////////
			current_game.setGameMode("one-move");
			String output_FileName = args[2].toString(); // the output game file
			OneMovePlayComputerMove(output_FileName);
		}
		else {
			System.out.println( "\n" + game_mode + " is an unrecognized game mode \n try again. \n" );
			return;
		}
	} // end of main()

	private static void InteractivePlayComputerMove() {
		// TODO Auto-generated method stub
		print_Details();

		System.out.println("\n Computer's turn:\n");

		int playColumn = 99; // the players choice of column to play

		// AI play - random play
		playColumn = calculon.findBestPlay(current_game);

		if (playColumn == 99) {
			System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
			return;
		}

		// play the piece
		current_game.playPiece(playColumn);

		System.out.println("move: " + current_game.get_PieceCount() + " , Player: Computer , Column: " + (playColumn + 1));

		current_game.gameBoardToFile_Print("computer.txt");

		if (current_game.is_Board_Full()) {
			print_Details();
			print_Result();
		} else {
			HumanMove();
		}
	}
	private static void HumanMove() {
		// TODO Auto-generated method stub
		print_Details();
		System.out.println("\n Human Turn:\n Kindly choose your move here(1-7):");

		instr = new Scanner(System.in);

		int playColumn = 99;

		do {
			playColumn = instr.nextInt();
		} while (!isValidPlay(playColumn));

		// play the piece
		current_game.playPiece(playColumn - 1);

		System.out.println("move: " + current_game.get_PieceCount() + " , Player: Human , Column: " + playColumn);

		current_game.gameBoardToFile_Print("human.txt");

		if (current_game.is_Board_Full()) {
			print_Details();
			print_Result();
		} else {
			InteractivePlayComputerMove();
		}
	}
	private static void OneMovePlayComputerMove(String output) {
		// TODO Auto-generated method stub

		// variables to keep up with the game
		int playColumn = 99; // the players choice of column to play
		
		System.out.print("\nMaxConnect-4 game\n");
		System.out.print("game state before move:\n");

		//print the current game board
		current_game.gameBoard_Print();
		// print the current scores
		System.out.println( "Score: Player 1 = " + current_game.get_Score( 1 ) +
				", Player2 = " + current_game.get_Score( 2 ) + "\n " );

		// Check if board is full!
		if (current_game.is_Board_Full()) {
			System.out.println("\n Sorry!The Board is Full. I canont play!!\n Game Over.");
			return;
		}
		// ****************** this chunk of code makes the computer play
		int current_player = current_game.fetchCurrentTurn();
		playColumn = calculon.findBestPlay(current_game);

		if(playColumn == 99) {
			System.out.println("\n The Board is Full.I canont play!!\nGame Over.");
			return;
		}

		current_game.playPiece(playColumn);

		System.out.println("move " + current_game.get_PieceCount() 
		+ ": Player " + current_player
		+ ", column " + playColumn);
		System.out.print("State of game after move:\n");

		// dispaly the current game board state
		current_game.gameBoard_Print();

		// display the current scores
		System.out.println( "Score: Player 1 = " + current_game.get_Score( 1 ) +
				", Player2 = " + current_game.get_Score( 2 ) + "\n " );

		current_game.gameBoardToFile_Print( output );

	}
	private static boolean isValidPlay(int playColumn) {
		if (current_game.isValidPlay(playColumn - 1)) {
			return true;
		}
		System.out.println("Sorry!!..Invalid column , Kindly choose column value between 1 to 7.");
		return false;
	}

	private static void print_Result() {
		// TODO Auto-generated method stub
		int h = 0 ,c = 0;
		String player = current_game.get_Player();
		if(player.equals("human"))
		{
			h = 1;
			c = 2;
		}
		else {
			h = 2;
			c = 1;
		}
		int humanScore = current_game.get_Score(h);
		int computerScore = current_game.get_Score(c);
	
		System.out.println("\n Result:");
		if(humanScore > computerScore){
			System.out.println("\n Congratulations!! You WON!!!"); 
		} else if (humanScore < computerScore) {
			System.out.println("\n Whoopsie!You lost!! Better Luck Next Time.");
		} else {
			System.out.println("\n Whoa! The Game is Tie!!");
		}
	}

	/**
	 * This method is used when to exit the program prematurly.
	 * @param value an integer that is returned to the system when the program exits.
	 */
	private static void print_Details() {
		// TODO Auto-generated method stub
		System.out.print("Game state :\n");

		// print the current game board
		current_game.gameBoard_Print();

		// print the current scores
		System.out.println("Score: Player-1 = " + current_game.get_Score(1) + ", Player-2 = " + current_game.get_Score(2)
		+ "\n ");
	}

	
	private static void exit_function( int value )
	{
		System.out.println("exiting from MaxConnectFour.java!\n\n");
		System.exit( value );
	}
} // end of class connectFour