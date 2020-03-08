/**
 * CSE 5360-002 Artificial Intelligence-1
 * Name: Shagun Paul
 * Student Id #: 1001557958
 * @author Shagun
 */

import java.io.*;

/**
 * This is the Gameboard class. It implements a two dimension array that
 * represents a connect four gameboard. It keeps track of the player making
 * the next play based on the number of pieces on the game board. It provides
 * all of the methods needed to implement the playing of a max connect four
 * game.
 *
 */

public class GameBoard 
{
	// class fields
	private int[][] play_Board;
	private int piece_Count;
	private int turn;
	private String move_first;
	private String game_mode;
	private String player_1, player_2;

	/**
	 * This constructor creates a GameBoard object based on the input file
	 * given as an argument. It reads data from the input file and provides
	 * lines that, when uncommented, will display exactly what has been read
	 * in from the input file.  You can find these lines by looking for 
	 * 
	 * @param input_file the path of the input file for the game
	 */
	public GameBoard( String input_file ) 
	{
		this.play_Board = new int[6][7];
		this.piece_Count = 0;
		int flag = 0;
		BufferedReader input = null;
		String gamedata = null;

		// open the input file
		try 
		{
			input = new BufferedReader( new FileReader( input_file ) );
		} 
		catch( IOException e ) 
		{
			System.out.println("\nProblem opening the input file!\nTry again." +
					"\n");
			e.printStackTrace();
		}

		//read the game data from the input file
		for(int i = 0; i < 6; i++) 
		{
			try 
			{
				gamedata = input.readLine();
				// read each piece from the input file
				for( int j = 0; j < 7; j++ ) 
				{
					this.play_Board[ i ][ j ] = gamedata.charAt( flag++ ) - 48;

					// sanity check
					if( !( ( this.play_Board[ i ][ j ] == 0 ) ||
							( this.play_Board[ i ][ j ] == 1 ) ||
							( this.play_Board[ i ][ j ] == 2 ) ) ) 
					{
						System.out.println("\n Oops!\n--The piece reads " +
								"from the input file was not a 1, a 2 or a 0" );
						this.exit_function( 0 );
					}

					if( this.play_Board[ i ][ j ] > 0 )
					{
						this.piece_Count++;
					}
				}
			} 
			catch( Exception e ) 
			{
				System.out.println("\n Oops! Problem in reading the input file!\n" +
						"Please Try again....\n");
				e.printStackTrace();
				this.exit_function( 0 );
			}

			//reset the counter
			flag = 0;

		} // end for loop

		// read one more line to get the next players turn
		try 
		{
			gamedata = input.readLine();
		} 
		catch( Exception e ) 
		{
			System.out.println("\n Oops!Problem in reading the next turn!\n" +
					"-- Please Try again.....\n");
			e.printStackTrace();
		}

		this.turn = gamedata.charAt( 0 ) - 48;

		//testing-uncomment the next 2 lines to see which current turn was read
		//System.out.println(" The current turn i read was->" +
		//		this.currentTurn );

		// make sure the turn corresponds to the no of pcs played already
		if(!( ( this.turn == 1) || ( this.turn == 2 ) ) ) 
		{
			System.out.println(" Oops! Problem!!! \n the Current Turn Read is not a " +
					"1 or a 2!");
			this.exit_function( 0 );
		} 
		else if ( this.fetchCurrentTurn() != this.turn ) 
		{
			System.out.println(" Oops Problems!\n the Current Turn Read does not " +
					"corresponds to the no of pieces played!");
			this.exit_function( 0 );			
		}
	} // end GameBoard( String )


	/**
	 * This constructor creates a GameBoard object from another double
	 * indexed array.
	 * 
	 * @param master_Game a dual indexed array
	 */
	public GameBoard( int master_Game[][] ) 
	{

		this.play_Board = new int[6][7];
		this.piece_Count = 0;

		for( int i = 0; i < 6; i++ ) 
		{
			for( int j = 0; j < 7; j++) 
			{
				this.play_Board[ i ][ j ] = master_Game[ i ][ j ];

				if( this.play_Board[i][j] > 0 )
				{
					this.piece_Count++;
				}
			}
		}
	} // end GameBoard( int[][] )

	/**
	 * this method returns the score for the player given as an argument.
	 * it checks horizontally, vertically, and each direction diagonally.
	 * currently, it uses for loops, but i'm sure that it can be made 
	 * more efficient.
	 * 
	 * @param player the player whose score is being requested.  valid
	 * values are 1 or 2
	 * @return the integer of the players score
	 */
	public int get_Score( int player ) 
	{
		//reset the scores
		int player_Score = 0;

		//check horizontally
		for( int i = 0; i < 6; i++ ) 
		{
			for( int j = 0; j < 4; j++ ) 
			{
				if( ( this.play_Board[ i ][j] == player ) &&
						( this.play_Board[ i ][ j+1 ] == player ) &&
						( this.play_Board[ i ][ j+2 ] == player ) &&
						( this.play_Board[ i ][ j+3 ] == player ) ) 
				{
					player_Score++;
				}
			}
		} // end horizontal

		//check vertically
		for( int i = 0; i < 3; i++ ) {
			for( int j = 0; j < 7; j++ ) {
				if( ( this.play_Board[ i ][ j ] == player ) &&
						( this.play_Board[ i+1 ][ j ] == player ) &&
						( this.play_Board[ i+2 ][ j ] == player ) &&
						( this.play_Board[ i+3 ][ j ] == player ) ) {
					player_Score++;
				}
			}
		} // end vertical

		//check diagonally - backs lash ->	\
		for( int i = 0; i < 3; i++ ){
			for( int j = 0; j < 4; j++ ) {
				if( ( this.play_Board[ i ][ j ] == player ) &&
						( this.play_Board[ i+1 ][ j+1 ] == player ) &&
						( this.play_Board[ i+2 ][ j+2 ] == player ) &&
						( this.play_Board[ i+3 ][ j+3 ] == player ) ) {
					player_Score++;
				}
			}
		}

		//check diagonally - forward slash -> /
		for( int i = 0; i < 3; i++ ){
			for( int j = 0; j < 4; j++ ) {
				if( ( this.play_Board[ i+3 ][ j ] == player ) &&
						( this.play_Board[ i+2 ][ j+1 ] == player ) &&
						( this.play_Board[ i+1 ][ j+2 ] == player ) &&
						( this.play_Board[ i ][ j+3 ] == player ) ) {
					player_Score++;
				}
			}
		}// end player score check

		return player_Score;
	} // end getScore

	/**
	 * the method gets the current turn
	 * @return an int value representing whose turn it is.  either a 1 or a 2
	 */
	public int get_PieceCount() 
	{
		return this.piece_Count;
	}

	/**
	 * this method returns the whole gameboard as a dual indexed array
	 * @return a dual indexed array representing the gameboard
	 */
	public int fetchCurrentTurn() 
	{
		return ( this.piece_Count % 2 ) + 1 ;
	} // end getCurrentTurn


	/**
	 * this method returns the number of pieces that have been played on the
	 * board 
	 * 
	 * @return an int representing the number of pieces that have been played
	 * on board alread
	 */
	
	public int[][] getGameBoard() 
	{
		return this.play_Board;
	}

	/**
	 * a method that determines if a play is valid or not. It checks to see if
	 * the column is within bounds.  If the column is within bounds, and the
	 * column is not full, then the play is valid.
	 * @param column an int representing the column to be played in.
	 * @return true if the play is valid<br>
	 * false if it is either out of bounds or the column is full
	 */
	public boolean isValidPlay( int column ) {

		if ( !( column >= 0 && column <= 7 ) ) {
			// check the column bounds
			return false;
		} else if( this.play_Board[0][ column ] > 0 ) {
			// check if column is full
			return false;
		} else {
			// column is NOT full and the column is within bounds
			return true;
		}
	}

	/**
	 * This method plays a piece on the game board.
	 * @param column the column where the piece is to be played.
	 * @return true if the piece was successfully played<br>
	 * false otherwise
	 */
	public boolean playPiece( int column ) {

		// check if the column choice is a valid play
		if( !this.isValidPlay( column ) ) {
			return false;
		} else {

			//starting at the bottom of the board,
			//place the piece into the first empty spot
			for( int i = 5; i >= 0; i-- ) {
				if( this.play_Board[i][column] == 0 ) {
					this.play_Board[i][column] = fetchCurrentTurn();
					this.piece_Count++;
					return true;
				}
			}
			//the pgm shouldn't get here
			System.out.println("Something went wrong with playPiece()");

			return false;
		}
	} //end playPiece

	/***************************  solution methods **************************/

	/**
	 * this method removes the top piece from the game board
	 * @param column the column to remove a piece from 
	 */
	public void remove_Piece( int column ) {

		// starting looking at the top of the game board,
		// and remove the top piece
		for( int i = 0; i < 6; i++ ) {
			if( this.play_Board[ i ][ column ] > 0 ) {
				this.play_Board[ i ][ column ] = 0;
				this.piece_Count--;

				break;
			}
		}

		//testing
		//WARNING: uncommenting the next 3 lines will potentially
		//produce LOTS of output
		//System.out.println("gameBoard.removePiece(). I am removing the " +
		//		"piece in column ->" + column + "<-");
		//this.printGameBoard();
		//end testing

	} // end remove piece	

	/************************  end solution methods **************************/
	/**
	 * this method prints the GameBoard to an output file to be used for
	 * inspection or by another running of the application
	 * @param output_file the path and file name of the file to be written
	 */
	public void gameBoardToFile_Print( String output_file ) {
		try {
			BufferedWriter output = new BufferedWriter(
					new FileWriter( output_file ) );

			for( int i = 0; i < 6; i++ ) {
				for( int j = 0; j < 7; j++ ) {
					output.write( this.play_Board[i][j] + 48 );
				}
				output.write("\r\n");
			}

			//write the current turn
			output.write( this.fetchCurrentTurn() + "\r\n");
			output.close();

		} catch( IOException e ) {
			System.out.println("\nProblem writing to the output file!\n" +
					"Please Try again.......");
			e.printStackTrace();
		}
	} // end printGameBoardToFile()

	/**
	 * this method prints the GameBoard to the screen in a nice, pretty,
	 * readable format
	 */
	public void gameBoard_Print() 
	{
		System.out.println(" -----------------");

		for( int i = 0; i < 6; i++ ) 
		{
			System.out.print(" | ");
			for( int j = 0; j < 7; j++ ) 
			{
				System.out.print( this.play_Board[i][j] + " " );
			}

			System.out.println("| ");
		}

		System.out.println(" -----------------");
	} // end printGameBoard


	private void exit_function( int val ){
		System.out.println("exiting from GameBoard.java!\n\n");
		System.exit( val );
	}


	public boolean is_Board_Full() {
		// TODO Auto-generated method stub
		return (this.get_PieceCount() >= 42);
	}


	public void setGameMode(String mode) {
		// TODO Auto-generated method stub
		game_mode = mode; 
	}
	
	public void set_FirstMove(String player1) {
		// TODO Auto-generated method stub
		set_Player(player1);
		move_first = player1;
		System.out.println("Player 1 is : "+ player_1 +" ; Player 2 is : "+ player_2);
	}
	
	public String getGameMode() {
		return game_mode;
	}
	public void set_Player(String player1) {
		this.player_1 = player1;
		this.player_2 = (player1.equals("human"))?"computer":"human";
		
	}
	public String get_Player() {
		return this.player_1;
	}

}  // end GameBoard class