import java.io.*;
import java.util.*;
import java.util.Map.Entry;
/**
 * @author Shagun Paul
 *
 */
public class CheckTrueFalse {

	static HashSet<String> list_symbols = new HashSet<String>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		if( args.length != 3){
			// Takes three command-line arguments
			System.out.println("Usage: " + args[0] +  " [wumpus-rules-file] [additional-knowledge-file] [input_file]\n");
			exit_function(0);
		}
		//Creating Buffered IO streams
		String br;
		BufferedReader input_Stream;
		BufferedWriter output_Stream;
		// Creating Knowledge Base and Statement
		LogicalExpression knowledge_base = new LogicalExpression();
		LogicalExpression statement_1 = new LogicalExpression();
		LogicalExpression statement_2 = new LogicalExpression();
		TTEntails ttEntails = new TTEntails();
		TTEntails.Model model = ttEntails.new Model();

		//Opening wumpus_rules text file
		try {
			input_Stream = new BufferedReader( new FileReader( args[0] ) );
			//Loading Wumpus Rules
			System.out.println("\n Please Wait!............ Loading Wumpus World Rules.......");
			knowledge_base.set_Connective("and");
			while(  ( br = input_Stream.readLine() ) != null ) 
			{
				if( !(br.startsWith("#") || (br.equals( "" )) )) 
				{
					// The line is not a statement
					LogicalExpression sub_Expression = read_Expression( br );
					knowledge_base.setSubexpression( sub_Expression );
				}
			}		
			// Closing Input File i.e. wumpus_rules.txt
			input_Stream.close();
		} catch(Exception e) 
		{
			System.out.println("\n OOPS! ........ Failed to Open............ " + args[0] );
			e.printStackTrace();
			exit_function(0);
		}
		// End of reading wumpus rules input file
		// Reading additional knowledge base text file
		try {
			input_Stream = new BufferedReader( new FileReader( args[1] ) );
			// Loading the additional knowledge base text file
			System.out.println("\n Please Wait!.........Loading the additional Knowledge Base..............");
			// We don't need to set the connectives for knowledge base text file because already set.
			// We may need LogicalExpression.setConnective() method to check whether connective set or not.
			// knowledge_base.setConnective("and");
			while(  ( br = input_Stream.readLine() ) != null) 
			{
				if( !(br.startsWith("#") || (br.equals("") ))) 
				{
					String tmp_br = br;
					if(tmp_br.contains("not")) {
						String[] temp_split = tmp_br.split(" ");
						temp_split[1] = temp_split[1].substring(0, temp_split[1].length()-1);
						model.hashMap.put(temp_split[1], false);
					}
					else {
						tmp_br = tmp_br.trim();
						model.hashMap.put(tmp_br, true);
					}
					LogicalExpression sub_Expression = read_Expression( br );
					knowledge_base.setSubexpression( sub_Expression );
				}
			}
			// Closing input text file i.e. kb.txt
			input_Stream.close();
		} catch(Exception e) {
			System.out.println("\n OOPS!...............Failed to Open................. " + args[1] );
			e.printStackTrace();
			exit_function(0);
		}
		// Done reading additional Knowledge Base
		// Checking for a valid additional Knowledge Base
		if( !valid_expression( knowledge_base ) ) {
			System.out.println("\n OOPS!............INVALID Knowledge Base...........................");
			exit_function(0);
		}
		// Printing Knowledge Base
		// knowledge_base.print_expression("\n");

		String a1 = "", a2 = "";
		// Reading Statement File that takes user input
		try {
			input_Stream = new BufferedReader( new FileReader( args[2] ) );
			System.out.println("\n Please Wait!....................Loading Statement File.................");
			get_Symbols(knowledge_base);
			Set<String> unique_Set_Symbol = list_symbols;
			// br = inputStream.readLine();
			// Actual Reading command for Statement text file
			// We assume that the statement file comprises of ONLY one line
			while( ( br = input_Stream.readLine() ) != null ) {
				if( !br.startsWith("#") ) {
					if(br.contains("not")) {
						a1 = br;
						String[] split_buffer = br.split(" ");
						a2 = split_buffer[1].substring(split_buffer[1].length() - 1);
					}
					else {
						a1 = br;
						a2 = "(not " + br + ")";
					}
					// The line is not a comment
					statement_1 = read_Expression( a1 );
					statement_2 = read_Expression( a2 );
					if (valid_expression(statement_1)&& !is_Valid_Input(a1, unique_Set_Symbol)) {
						System.out.println("\n OOPS!...........INVALID Statement"); 
						return;
					}
					break;
				} 
			}
			// Closing input text file i.e. statement.txt
			input_Stream.close();
		} catch(Exception e) {
			System.out.println("\n OOPS!.............. Failed to Open............... " + args[2] );
			e.printStackTrace();
			exit_function(0);
		}
		// Done reading statement text file
		// Now evaluating to check if statement valid or not.
		if( !valid_expression( statement_1 ) ) {
			System.out.println("\n OOPS!............INVALID Statement....................");
			exit_function(0);
		}
		
		boolean out_1 = ttEntails.ttEntails(knowledge_base, statement_1, model);
		boolean out_2 = ttEntails.ttEntails(knowledge_base, statement_2, model);

		try {
			output_Stream = new BufferedWriter(new FileWriter(new File("result.txt")));
			if (out_1 != out_2) {
				System.out.println(" Definitely " + out_1);
				output_Stream.write(" Definitely " + out_1);
			} else if (out_1 == out_2 && out_1 == false) {
				System.out.println(" Possibly TRUE, Possibly FALSE");
				output_Stream.write(" Possibly TRUE, Possibly FALSE");
			} else if (out_1 == out_2 && out_1 == true) {
				System.out.println(" Both True and False");
				output_Stream.write(" Both True and False");
			}
			output_Stream.close();
		} catch (IOException e) {
			System.out.println("Error Message : " + e.getMessage());
			e.printStackTrace();
		}
	} // END OF MAIN FUNCTION
	private static void get_Symbols(LogicalExpression log_Ex) {
		// TODO Auto-generated method stub
		if(!(log_Ex.getUniqueSymbol() == null))
			list_symbols.add(log_Ex.getUniqueSymbol());
		else {
			for(int i = 0; i < log_Ex.getSubexpressions().size(); i++) {
				LogicalExpression logEx1 = log_Ex.getSubexpressions().get(i);
				get_Symbols(logEx1);
				if(!(logEx1.getUniqueSymbol() == null))
					list_symbols.add(logEx1.getUniqueSymbol());
			}
		}
	}
	private static boolean is_Valid_Input(String alpha, Set<String> set) {
		// TODO Auto-generated method stub
		Iterator<String> i = set.iterator();
		boolean b = false;
		while(i.hasNext()) {
			if(i.next().equals(alpha))
				b = true;
		}
		if(alpha.contains("(or") || alpha.contains("(and") || alpha.contains("(xor") || alpha.contains("(not") || alpha.contains("(if")	|| alpha.contains("(iff"))
			b = true;
		return b;
	}

	
	/* This method is used to read all the unique symbols of a sub-expression
	 * It is called is by read_expression(String, long)((  It is the only read_expression that actually does something ));
	 * 
	 * Each of the string defined is EITHER:
	 * - a unique Symbol
	 * - a sub-expression
	 * - Delineated by spaces, and parenthesis pairs
	 * 
	 * This method returns a vector of logicalExpressions
	 * 
	 * 
	 */

	public static Vector<LogicalExpression> readSubexpressions( String ip_str ) {

		Vector<LogicalExpression> symbol_List = new Vector<LogicalExpression>();
		LogicalExpression new_Expression;// = new LogicalExpression();
		String new_Symbol = new String();
		ip_str.trim();

		while( ip_str.length() > 0 ) {
			new_Expression = new LogicalExpression();
			if( ip_str.startsWith( "(" ) ) {
				// It is a sub-expression.
				// Uses readExpression to parse it into a LogicalExpression object
				// Used to find the matching ')'
				int paren_Count = 1;
				int match_Index = 1;
				while( ( paren_Count > 0 ) && ( match_Index < ip_str.length() ) ) {
					if( ip_str.charAt( match_Index ) == '(') {
						paren_Count++;
					} else if( ip_str.charAt( match_Index ) == ')') {
						paren_Count--;
					}
					match_Index++;
				}
				// Used to read until matching ')' into a new string
				new_Symbol = ip_str.substring( 0, match_Index );
				// Passing new string to readExpression,
				new_Expression = read_Expression( new_Symbol );
				// Adding LogicalExpression to the new string and returning to the vector symbol_List
				symbol_List.add( new_Expression );
				// Trimming the logicalExpression from the input_string for more processing
				ip_str = ip_str.substring( new_Symbol.length(), ip_str.length() );
			} else {
				//setUniqueSymbol() notifies if not a unique symbol. Here, it is unique.
				// We only require first symbol hence creating a LogicalExpression object and
				// Adding the object to the Vector
				if( ip_str.contains( " " ) ) {
					// Removal of first string from the string.
					new_Symbol = ip_str.substring( 0, ip_str.indexOf( " " ) );
					ip_str = ip_str.substring( (new_Symbol.length() + 1), ip_str.length() );
				} else {
					new_Symbol = ip_str;
					ip_str = "";
				}
				new_Expression.set_Unique_Symbol( new_Symbol );
				symbol_List.add( new_Expression );
			}
			ip_str.trim();
			if( ip_str.startsWith( " " )) {
				// Removing preceding whitespace
				ip_str = ip_str.substring(1);
			}
		}
		return symbol_List;
	}


	/* This method is used to read a Logical Expression
	 * if the next string is a:
	 * - '(' => then the next 'symbol' is a Sub Expression
	 * - else => it must be a unique_symbol
	 * 
	 * Returns a Logical Expression
	 * 
	 * Note: Please note that it is still a conflict whether we need a counter or no.
	 * 
	 */
	public static LogicalExpression read_Expression( String ip_str ) 
	{
		LogicalExpression res = new LogicalExpression();
		// Trimming the whitespace off
		ip_str = ip_str.trim();
		if( ip_str.startsWith("(") ) 
		{
			// When a sub expression
			String symbol_String = "";
			// Removing '(' from the I/P String
			symbol_String = ip_str.substring( 1 );
			if( !symbol_String.endsWith(")" ) ) 
			{
				// If missing parenthesis ')' then Invalid Expression
				System.out.println(" OOPS! Missing ')' !!! - Invalid Expression!!! - readExpression():-" + symbol_String );
				exit_function(0);
			}
			else 
			{
				// Removing last ')'
				// Must be at the end
				symbol_String = symbol_String.substring( 0 , ( symbol_String.length() - 1 ) );
				symbol_String.trim();
				// Reading connective into the Result LogicalExpression Object					  
				symbol_String = res.set_Connective( symbol_String );
			}
			// Reading the sub-expressions into a Vector and calling setSubExpressions( Vector );
			res.setSubexpressions( readSubexpressions( symbol_String ) );
		} 
		else 
		{   	
			// The next symbol must be UNIQUE
			// setUniqueSymbol notifies if unique symbol NOT VALID
			res.set_Unique_Symbol( ip_str );
		}
		return res;
	}

		/* This method used to check if logical expression is valid or not 
	 * An expression is valid when:
	 * ( this is an XOR )
	 * - is a unique_symbol
	 * - has:
	 *  -- a connective
	 *  -- a vector of logical expressions
	 *  
	 * */
	public static boolean valid_expression(LogicalExpression expression)
	{
		// Checking for an empty symbol
		// Id symbol !EMPTY, then Check
		// return the truth of the validity of that symbol
		if ( !(expression.getUniqueSymbol() == null) && ( expression.getConnective() == null ) ) {
			// If we have unique symbol, Check for Validity
			return valid_symbol( expression.getUniqueSymbol() );
		}
		// When Symbol is empty, so
		// Check if connective is valid
		// check for 'if / iff'
		if ( ( expression.getConnective().equalsIgnoreCase("if") )  ||
				( expression.getConnective().equalsIgnoreCase("iff") ) ) {
			// The connective is either 'if' or 'iff' - so check the number of connectives
			if (expression.getSubexpressions().size() != 2) {
				System.out.println(" Error: Connective \"" + expression.getConnective() +
						"\" with " + expression.getSubexpressions().size() + " Arguments\n" );
				return false;
			}
		}
		// ending checking 'if / iff'
		// Checking for 'not'
		else   if ( expression.getConnective().equalsIgnoreCase("not") ) {
			// The connective is NOT - There exists only one symbol/sub-expression
			if ( expression.getSubexpressions().size() != 1)
			{
				System.out.println(" Error: Connective \""+ expression.getConnective() + "\" with "+ expression.getSubexpressions().size() +" arguments\n" ); 
				return false;
			}
		}
		// Ending check for 'not'
		// check for 'and / or / xor'
		else if ( ( !expression.getConnective().equalsIgnoreCase("and") )  &&
				( !expression.getConnective().equalsIgnoreCase( "or" ) )  &&
				( !expression.getConnective().equalsIgnoreCase("xor" ) ) ) {
			System.out.println(" Error: Unknown Connective " + expression.getConnective() + "\n" );
			return false;
		}
		// Ending checking for 'and / or / not'
		// End connective check
		// Checks for validity of the logical_expression 'symbols' that go with the connective
		for( Enumeration<LogicalExpression> e = expression.getSubexpressions().elements(); e.hasMoreElements(); ) {
			LogicalExpression testExpression = (LogicalExpression)e.nextElement();
			// For each subExpression in expression,
			// Check to see if the subexpression is valid
			if( !valid_expression( testExpression ) ) {
				return false;
			}
		}
		// If the method made it here, the expression must be valid
		return true;
	}

	/** this function checks to see if a unique symbol is valid */
	//////////////////// this function should be done and complete
	// originally returned a data type of long.
	//public long valid_symbol( String symbol ) {
	public static boolean valid_symbol( String sym ) {
		if (  sym == null || ( sym.length() == 0 )) {
			return false;
		}

		for ( int count = 0; count < sym.length(); count++ ) {
			if ( (sym.charAt( count ) != '_') &&
					( !Character.isLetterOrDigit( sym.charAt( count ) ) ) ) {
				System.out.println(" String: " + sym + " is INVALID! UNNECESSARY Character:---" + sym.charAt( count ) + "---\n");
				return false;
			}
		}
		// Characters of the symbol string are either a letter or a digit or an underscore,
		// Return true
		return true;
	}

	private static void exit_function(int value) {
		System.out.println(" \n ....................Exiting from checkTrueFalse...............");
		System.exit(value);
	}	
}

class TTEntails {
	Set<String> symbol_List = new HashSet<String>();	
	int count1 = 0;

	public boolean ttEntails(LogicalExpression knowledge_base, LogicalExpression statement, Model model) {
		List<String> symbol_List = getSymbols(knowledge_base, statement);
		symbol_List = removeSymbols(model,symbol_List);
		return ttCheck_All(knowledge_base, statement, symbol_List, model);
	}

	private List<String> removeSymbols(Model model, List<String> symbol_List2) {
		Iterator<Entry<String,Boolean>> i = model.hashMap.entrySet().iterator();
		while (i.hasNext()) {	    	
			Entry<String,Boolean> p = (Entry<String,Boolean>)i.next();
			symbol_List2.remove(p.getKey());	       
		}
		return symbol_List2;
	}
	public boolean ttCheck_All(LogicalExpression kb, LogicalExpression alpha,	List<String> symbols, Model model) {		
		if (symbols.isEmpty()) {			
			boolean pl_true = pl_True(kb, model);			
			if(pl_true)
				return pl_True(alpha, model);				
			else
				return true;		
		} else {
			String P = (String)symbols.get(0);			
			List<String> rest = symbols.subList(1, symbols.size());			
			Model trueModel = model.extend(P, true);
			Model falseModel = model.extend(P, false);
			return (ttCheck_All(kb, alpha, rest, trueModel) && (ttCheck_All(kb, alpha, rest, falseModel)));
		}		
	}
	void get_Symbols(LogicalExpression logEx){
		if(!(logEx.getUniqueSymbol() == null))
			symbol_List.add(logEx.getUniqueSymbol());
		else {
			for(int i = 0 ; i < logEx.getSubexpressions().size(); i++ ){
				LogicalExpression logEx1 = logEx.getSubexpressions().get(i);
				get_Symbols(logEx1);
				if(!(logEx1.getUniqueSymbol() == null))
					symbol_List.add(logEx1.getUniqueSymbol());			
			}
		}
	}		
	boolean pl_True(LogicalExpression kb, Model model){
		Vector<LogicalExpression> vector = kb.getSubexpressions();
		Boolean b1 = false;

		if( kb.getConnective() == null ) {						
			return model.hashMap.get(kb.getUniqueSymbol());			
		}
		else if(kb.getConnective()!=null && kb.getConnective().equalsIgnoreCase("not")){			
			return !(pl_True(kb.getNextSubexpression(),model));
		}
		else if(kb.getConnective()!=null && kb.getConnective().equalsIgnoreCase("or")){			
			for(int i=0;i<vector.size();i++){
				b1 = b1 || pl_True(vector.get(i),model);
			}
			return b1;		
		}
		else if(kb.getConnective()!=null && kb.getConnective().equalsIgnoreCase("if")){		
			b1 = !(b1 && !(pl_True(vector.get(1),model)));
			return b1;			
		}
		else if(kb.getConnective()!=null && kb.getConnective().equalsIgnoreCase("iff")){			
			return b1 == pl_True(vector.get(1),model);
		}
		else if(kb.getConnective()!=null && kb.getConnective().equalsIgnoreCase("and")){			
			b1 = true;
			for(int i = 0; i < vector.size(); i++){				
				b1 = b1 && pl_True(vector.get(i),model);
				if(b1==false){	
					return b1;
				}
			}
			return b1;
		}
		else if(kb.getConnective()!=null && kb.getConnective().equalsIgnoreCase("xor")){			
			int truthCounter=0;
			for(int i = 0; i < vector.size(); i++){
				boolean retrieved = pl_True(vector.get(i),model);
				if(retrieved==true)
					truthCounter++;
				if(truthCounter>1)
					return false;
				b1 = ((b1||retrieved) && !(b1 && retrieved));
			}
			return b1;
		}
		return true;
	}

		List<String> getSymbols(LogicalExpression kb, LogicalExpression alpha){		
		get_Symbols(kb);
		get_Symbols(alpha);		
		List<String> list = new ArrayList<String>(symbol_List);
		return list;
	}

		class Model{
		public HashMap<String,Boolean> hashMap = new HashMap<String,Boolean>();
		public Model extend(String symbol, boolean b) {
			Model model = new Model();
			model.hashMap.putAll(this.hashMap);
			model.hashMap.put(symbol, b);
			return model;
		}
	}
}