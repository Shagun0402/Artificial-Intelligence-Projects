/**
 * CSE 5360-002 Artificial Intelligence- 1
 * Name: Shagun Paul
 * Student ID # : 1001557958
 * @author Shagun
 *
 * This is the AiPlayer class.  It simulates a minimax player for the max
 * MAX Connect Four Game. 
 */

public class AiPlayer 
{
	public int depth;
	public GameBoard gameBoard;

	/**
	 * The constructor instantiates an AiPlayer object and it's attributes.
	 */
	public AiPlayer(int depth, GameBoard currGameBoard) {
		this.depth = depth;
		this.gameBoard = currGameBoard;
	}

	
		/**
	 * This method calculates MAX value.
	 */
	private int Max_Util(GameBoard gameBoard, int depth, int alpha, int beta) {
		// TODO Auto-generated method stub
		if(!gameBoard.is_Board_Full() && depth > 0) {
			int value = Integer.MIN_VALUE;
			int utility = 0;
			GameBoard nextGameBoard;
			for(int i = 0; i < 7; i++) {
				if(gameBoard.isValidPlay(i)) {
					nextGameBoard = new GameBoard(gameBoard.getGameBoard());
					nextGameBoard.playPiece(i);
					utility = Min_Util(nextGameBoard, depth-1, alpha, beta);
					value = Math.max(value, utility);
					if (value >= beta)
						return value;
					alpha = Math.max(value, alpha);
				}
			}
			return value;
		}
		else {
			// terminal state
			int value = gameBoard.get_Score(2) - gameBoard.get_Score(1);
			return value;
		}		
	}
	/**
	 * This method calculates MIN value.
	 */
	private int Min_Util(GameBoard gameBoard, int depth, int alpha, int beta) {
		// TODO Auto-generated method stub
		if(!gameBoard.is_Board_Full() && depth > 0) {
			int value = Integer.MAX_VALUE;
			int utility = 0;
			GameBoard nextGameBoard;
			for(int i = 0; i < 7; i++) {
				if(gameBoard.isValidPlay(i)) {
					nextGameBoard = new GameBoard(gameBoard.getGameBoard());
					nextGameBoard.playPiece(i);
					utility = Max_Util(nextGameBoard, depth-1, alpha, beta);
					value = Math.max(value, utility);
					if (value <= alpha)
						return value;
					beta = Math.max(value, beta);
				}
			}
			return value;
		}
		else {
			// terminal state
			int value = gameBoard.get_Score(2) - gameBoard.get_Score(1);
			return value;
		}		
	}


	/**
	 * This method plays a piece on the board. It employs the MINMAX algorithm/
	 * 
	 * @param currentGame The GameBoard object that is currently being used to
	 * play the game.
	 * @return an integer indicating which column the AiPlayer would like
	 * to play in.
	 */
	
	public int findBestPlay(GameBoard current_game) {
		{
			int playChoice = 0;
			int value = 0, utility = 0;
			GameBoard nextGameBoard;
			if( current_game.fetchCurrentTurn() == 1) {
				value = Integer.MAX_VALUE;
				for(int i = 0; i < 7; i++) {
					if(current_game.isValidPlay(i)) {
						nextGameBoard = new GameBoard(current_game.getGameBoard());
						nextGameBoard.playPiece(i);
						utility = Max_Util(nextGameBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
						if(value > utility) {
							playChoice = i;
							value = utility;
						}
					}
				}
			}
			else {
				value = Integer.MIN_VALUE;
				for(int i = 0; i < 7; i++) {
					if(current_game.isValidPlay(i)) {
						nextGameBoard = new GameBoard(current_game.getGameBoard());
						nextGameBoard.playPiece(i);
						utility = Min_Util(nextGameBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
						if(value < utility) {
							playChoice = i;
							value = utility;
						}
					}
				}
			}
			return playChoice;
		}

	}
}
