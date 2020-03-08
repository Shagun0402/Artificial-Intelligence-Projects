Max-Connect 4 Game using Interactive Mode and One-Move Mode
------------------------------------------------------------------------------------------------------------------------------
Code Structure :
Classes : maxconnect4, AiPlayer, GameBoard
 
----------------------------------------------------------------------------
class maxconnect4 :
methods : main(), InteractivePlayComputerMove(), OneMovePlayComputerMove(output), HumanMove(), print_Result(), print_Details(), isValidPlay(playColumn), exit_function(value).

Function Description:
HumanMove() -> input is taken from user and used to make the next move for human player.
InteractivePlayComputerMove() -> used to make a move for an interactive mode game by computer
OneMovePlayComputerMove() -> this function is called when the computer has to make a move for a one-move mode game.
print_Details() -> used to prints the board's current state and the current score. 
print_Result() -> used to prints the final score and the declares winner or tie.
isValidPlay(playColumn) -> used to check if the input from the user is a valid column.

----------------------------------------------------------------------------
class AiPlayer :
methods : findBestPlay(), Min_Util(), Max_Util()

Function Description:
findBestPlay() -> used to make the decision to make a move for the computer using the min and max value from the below given two functions
Calculate_Min_Utility() -> used to calculate the min value.
Calculate_Min_Utility() -> used to calculate the max value.

-----------------------------------------------------------------------------
class GameBoard :
methods : get_Score(), fetchCurrentTurn(), getGameBoard(), GameBoard_print(), is_Board_Full(), get_Player(), set_Player()

Function Description:
getScore() -> used to take the current score and then send it to printBoardAndScore() to print score.
fetchCurrentTurn() -> used to trace the current turn.
getGameBoard() -> used to take the current gameboard state and pass it to printGameBoard() function.
GameBoard_print() -> used to print the current board state.
is_Board_Full() -> used to determine if the board is full.
get_Player() -> used to get the player values.
set_Player() -> used to set the player values.

-----------------------------------------------------------------------------

-> Evaluation function :
       AiPlayer.findBestPlay():
        *This method uses the minmax algorithm along with alpha beta pruning and depth limited search to make the best move the computer can make to win or tie the          game.
        *It takes current game board state as input.
        *For each valid column inputted, it calls the Min_Utility() or Max_Utility() depending on the player.
        *Max_Utility()  and Min_Utility(): takes 4 parameters gameboard, depth, alpha and beta. They run until depth becomes 0. They use alpha beta pruning and each          time reduces depth by 1.
        *The findBestPlay() returns the optimal column number decided by the function.

------------------------------------------------------------------------------
How to run Code :
Compile using :
        javac -classpath . maxconnect4.java

Execute using :
        (for interactive mode) :
        java maxconnect4 interactive [input_file] [computer-next/human-next] [depth]  
        for example: java maxconnect4 interactive input.txt computer-next 8

        (for one-move mode) :
        time java maxconnect4 one-move [input_file] [output_file] [depth]  
        for example: java maxconnect4 one-move input1.txt output.txt 8

Command to retrieve execution time:
        time java maxconnect4 one-move [input_file] [output_file] [depth]  
        for example: java maxconnect4 one-move input1.txt output.txt 8

-------------------------------------------------------------------------------
 The depth and execution time of user results are as below:

         -------------------------------------
        || Depth Level || Execution Time(user) ||
          -------------------------------------
        ||     1       ||        0m0.049s      ||
        ||     2       ||        0m0.092s      ||
        ||     3       ||        0m0.118s      ||
        ||     4       ||        0m0.122s      ||
        ||     5       ||        0m0.138s      ||
        ||     6       ||        0m0.143s      ||
        ||     7       ||        0m0.434s      ||
        ||     8       ||        0m0.649s      ||
        ||     9       ||        0m1.019s      ||
        ||     10      ||        0m2.217s      ||
        ||     11      ||        0m5.239s      ||
        ||     12      ||        1m30.092s     ||
        ||     13      ||        5m51.129s     ||
        ||     14      ||        7m4.619s      ||
         -------------------------------------
-------------------------------------------------------------------------------
