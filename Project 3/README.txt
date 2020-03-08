Propositional Logic (Wumpus World Problem)

--------------------------------------------------------------------------------------------------------------

1) Programming Language Used : JAVA
2) How is the Code Structured?

Code Structure: Includes 2 Classes
		# CheckTrueFalse
		# LogicalExpression

xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
							Class : CheckTrueFalse

Methods: main(),get_Symbols(),  is_Valid_Input(), readSubexpressions(), read_Expression(), valid_expression(), valid_symbol()
Class: TTEntails, Model

						Explanation to Structure for CheckTrueFalse :-

METHODS:	# valid_symbol() : Checks is the unique symbol is VALID or not.
		# valid_expression() : Checks if lofical expression is VALID or not.
		# readSubexpressions() : Reads all unique symbols of a given sub-expression.
		# read_Expression() : Reads logical expression.

CLASS TTEntails
Methods: ttEntails(), pl_true(), ttCheck_All, get_Symbols(), removeSymbols()

		# ttEntails() : Fetched Knowledge Base, Determines if KB entails statement by calling ttCheck_All.
		# ttCheck_All() : Checks if KB entails statement or not.
		# pl_true() : Evaluates truth value for KB and Statement entails.
		# get_Symbols() : Fetches symbols from list of symbols.
		#removeSymbols() : Removes symbols ffrom list of symbols.

xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
							Class : LogicalExpression

Methods: set_Connective(), set_Unique_Symbol()
					
						Explanation to Structure for LogicalExpression :-

METHODS:	# set_Connective() : Sets connective for logical expression and returns the string to collect the symbols from it.
		# set_Unique_Symbol() : Evaluates for proper semantics of Logical Expression.

xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
3) How to run code?
	# Load CheckTrueFalse.java and LogicalExpression.java onto Omega
	# Load wumpus_rules.txt kb.txt statement.txt onto Omega
	# Compilation : javac *.java
	# Execution : java CheckTrueFalse wumpus_rules.txt [knowledge-base] [statement]
		      For Example : java CheckTrueFalse wumpus_rules.txt kb.txt statement.txt
		      For Example : java CheckTrueFalse a.txt b.txt c.txt   (Runs for the files provided in the assignment too)

4) How is the Knowledge Base Designed?
	[1,1] : No monster, No pit, No stench, No breeze
	[1,2] : No monster, No pit, No stench, No breeze
	[1,3] : No monster, No pit, No stench, breeze
	[1,4] : No monster, pit, No stench, No breeze
	[2,1] : No monster, No pit, No stench, No breeze
	[2,2] : No monster, No pit, No stench, breeze
	[2,3] : No monster, No pit, No stench, No breeze
	[2,4] : No monster, No pit, No stench, breeze
	[3,1] : No monster, No pit, No stench, breeze
	[3,2] : No monster, pit, No stench, No breeze
	[3,3] : No monster, No pit, No stench, breeze
	[3,4] : No monster, No pit, stench, No breeze
	[4,1] : No monster, No pit, No stench, No breeze
	[4,2] : No monster, No pit, No stench, breeze
	[4,3] : No monster, No pit, stench, No breeze
	[4,4] : Monster, No pit, No stench, No breeze

For statement.txt file that holds input like: 

	# M_4_4 : Yields resuults as definitely True
	# B_2_3 : Yields results as definitely True
	# (not B_2_3) : Yields results as definitely False
	# M_4_2 : Yields results as definitely False
	# When we load a.txt b.txt c.txt , it yields results as Possibly True, Possibly False
 	NOTE: We need to input a result in statement.txt file, currently it incorporates (not B_2_3) as input.
