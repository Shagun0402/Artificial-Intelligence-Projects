import java.util.*;
/**
 * @author Shagun Paul
 *
 */
public class LogicalExpression {

	private String unique_Symbol = null; 	//  Returns null if sentence is a complex expression
	private String connective = null; 		// Returns null if sentence is  _UNIQUE_ symbol
	private Vector<LogicalExpression> subexpressions = null;   // a vector of LogicalExpressions i.e vector of unique symbols and subexpressions

	// Default Constructor
	public LogicalExpression()
	{
		//this.uniqueSymbol = "0";
		//this.connective = "0";
		this.subexpressions = new Vector<LogicalExpression>();
	}

	// Parameterized Constructor
	// A new logical Expression, makes a copy
	public LogicalExpression( LogicalExpression old_Expression ) {

		if( old_Expression.getUniqueSymbol() == null) {
			this.unique_Symbol = old_Expression.getUniqueSymbol();
		} else {
			// Create a new logical expression from the one passed to it
			this.connective = old_Expression.getConnective();
			// gets all subExpressions
			// Enumerate the subexpression vector of newExpression
			for( Enumeration<LogicalExpression> e = old_Expression.getSubexpressions().elements(); e.hasMoreElements(); ) {
				LogicalExpression nextExpression = (LogicalExpression)e.nextElement();
				this.subexpressions.add( nextExpression );
			}
		}
	}
	/* This method is used to replace _part_ of read_word() from the example code 
	 * Sets the connective for this LogicalExpression
	 * 
	 * Returns the rest of the string to collect the symbols for it
	 */
	public String set_Connective( String ip_str ) {
		String connect;
		// System.out.println("setConnective() - beginning -" + inputString + "-");
		// Trimming whitespace at the beginning of the string if there is any
		// Note: There exists no whitespace
		ip_str.trim();

		// When first character of the inputString is a '('
		// - remove the ')' and the ')' and any whitespace after it.
		if( ip_str.startsWith("(") ) {
			ip_str = ip_str.substring( ip_str.indexOf('('), ip_str.length() );
			// Trims the whitespace
			ip_str.trim();
		}
		// System.out.println("here: setConnective1- inputString:" + inputString + "--");
		if( ip_str.contains( " " ) ) {
			// Remove the connective out of the string
			connect = ip_str.substring( 0, ip_str.indexOf( " " )) ;
			ip_str = ip_str.substring( ( connect.length() + 1 ), ip_str.length() );
			// inputString.trim();
			// System.out.println("I have the connective -" + connect + "- and i've got symbols -" + inputString + "-");
		} else {
			// just set to get checked and empty the inputString
			// huh?
			connect = ip_str;
			ip_str = "";
		}

		// if connect is a proper connective
		if ( connect.equalsIgnoreCase( "if" ) ||
				connect.equalsIgnoreCase( "iff" ) ||
				connect.equalsIgnoreCase( "and" ) ||
				connect.equalsIgnoreCase("or") ||
				connect.equalsIgnoreCase("xor") || 
				connect.equalsIgnoreCase( "not" ) ) {
			// First word in the string is a valid connective
			// Setting the connective
			this.connective = connect;
			//System.out.println( "setConnective(): I have just set the connective\n->" + connect + "<-\nand i'm returning\n->" + inputString + "<-" );
			return ip_str;
		} else {
			System.out.println( " Unexpected Character!!! : Invalid Connective!! - setConnective():-" + ip_str );
			LogicalExpression.exit_function( 0 );
		}
		// Invalid Connective
		System.out.println(" Invalid Connective! : setConnective:-" + ip_str );
		return ip_str;
	}

	/* This method is used to replace _part_ of read_word()
	 * Sets the symbol for the LogicalExpression
	 * Checks if starts with one of the appropriate letters,
	 * Checks that the rest of the string is either digits or '_'
	 */
	public void set_Unique_Symbol( String new_Symbol ) 
	{
		boolean validity = true;
		// Removes preceding whitespace
		new_Symbol.trim();
		if( this.unique_Symbol != null ) 
		{
			System.out.println(" setUniqueSymbol(): - this Logical Expression already has a unique symbol!!!" +
					"\n Swapping........ :->" + this.unique_Symbol + "<- for ->" + new_Symbol +"<-\n");
		} 
		else if( validity ) 
		{
			this.unique_Symbol = new_Symbol;
			// System.out.println(" setUniqueSymbol() - added-" + newSymbol + "- to the LogicalExpression! ");
		} 
	}

		public void setSubexpression( LogicalExpression new_Sub ) {
		this.subexpressions.add(new_Sub);
	}
	
	public String getConnective() {
		return this.connective;
	}
	public void setSubexpressions( Vector<LogicalExpression> symbols ) {
		this.subexpressions = symbols;

	}
	
	public LogicalExpression getNextSubexpression() {
		return this.subexpressions.lastElement();
	}
	public String getUniqueSymbol(){
		return this.unique_Symbol;
	}


	public Vector<LogicalExpression> getSubexpressions() {
		return this.subexpressions;
	}

	/************************* end getters and setters *************/

	public void print_expression( String separator ) {

		if ( !(this.unique_Symbol == null) )
		{
			System.out.print( this.unique_Symbol.toUpperCase() );
		} else {
			// else the symbol is a nested logical expression not a unique symbol
			LogicalExpression nextExpression;

			// Prints the connective
			System.out.print( "(" + this.connective.toUpperCase() );

			// Enumerates over the 'symbols' ( LogicalExpression objects ) and print them
			for( Enumeration<LogicalExpression> e = this.subexpressions.elements(); e.hasMoreElements(); ) {
				nextExpression = ( LogicalExpression )e.nextElement();

				System.out.print(" ");
				nextExpression.print_expression("");
				System.out.print( separator );
			}

			System.out.print(")");
		}
	}

	private static void exit_function(int val) {
		System.out.println("...................Exiting from LogicalExpression....................");
		System.exit(val);
	}
}