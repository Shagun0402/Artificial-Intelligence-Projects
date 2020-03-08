import java.text.DecimalFormat;
import java.util.*;
import java.io.*;


public class Task4 {
	static String current_line;

	// Calculates the probability of the given event
	public static double calculate(ArrayList<String> Event, int prob)
	{
		Iterator<String> itr = Event.iterator();
		int i = 0, j = 0;
		while(itr.hasNext())
		{
			String line = (String)itr.next();
			if(line.equals("0"))
				j++;
			else
				i++;
		}
		return((double)i/prob);
	}

	public static void main(String[] args) throws Exception
	{
		/*
		Let A:baseball game on TV.
		    W:George watches TV.
			O:George is out of cat food.
			F:George feeds the cat.
			W_when_A :George watches TV given that baseball game is on TV.
			W_when_A_false:George watches TV given that baseball game is not on TV.
			F_when_W_false_O:George feeds the cat given George does not watch TV and is out of cat food.
			F_when_W_false_O_false:George feeds the cat given George does not watch TV and is not out of cat food.
			F_when_W_O:George feeds the cat given George watches TV and is out of cat food.
			F_when_W_O_false:George feeds the cat given George watches TV and is not out of cat food.
		*/
		ArrayList<String> A = new ArrayList<String>();
		ArrayList<String> W = new ArrayList<String>();
		ArrayList<String> O = new ArrayList<String>();
		ArrayList<String> F = new ArrayList<String>();		
		ArrayList<String> W_when_A = new ArrayList<String>();
		ArrayList<String> W_when_A_false = new ArrayList<String>();
		ArrayList<String> F_when_W_O = new ArrayList<String>();
		ArrayList<String> F_when_W_O_false = new ArrayList<String>();
		ArrayList<String> F_when_W_false_O = new ArrayList<String>();
		ArrayList<String> F_when_W_false_O_false = new ArrayList<String>();

		//Decimal Format	
		DecimalFormat decimal = new DecimalFormat("#.#####");
		// File Processing
		File f1 = new File(args[0]);
		BufferedReader B = new BufferedReader(new FileReader(f1));
		int no_of_lines = 0;
		while((current_line = B.readLine()) != null)
		{
			String [] line = current_line.split("     ");
			A.add(line[1]);
			if(line[1].equals("1"))	
				W_when_A.add(line[2]);
			else
				W_when_A_false.add(line[2]);
			W.add(line[2]);
			O.add(line[3]);
			F.add(line[4]);
			if(line[2].equals("1"))
			{
				if(line[3].equals("1"))
					F_when_W_O.add(line[4]);
				else
					F_when_W_O_false.add(line[4]);
			}
			else
			{
				if(line[3].equals("1"))
					F_when_W_false_O.add(line[4]);	
				else
					F_when_W_false_O_false.add(line[4]);
			}
			no_of_lines++;
		}
	
		
		// Printing Probability Tables 
		
		// Probability baseball_game_on_TV
		System.out.println("**********************************************");
		double baseball_game_on_TV = calculate(A, no_of_lines);
		System.out.println("P(A)");
		System.out.println("T= " + decimal.format(baseball_game_on_TV));
		System.out.println("F= " + decimal.format(1-baseball_game_on_TV));
		System.out.println("**********************************************");
		
		//Probability George_watches_TV
		System.out.println("**********************************************");
		System.out.println("P(W): ");
		System.out.println("P(W|A) = T= " + decimal.format(calculate(W_when_A,W_when_A.size())));
		System.out.println("P(W|A) = F= " + decimal.format(calculate(W_when_A_false,W_when_A_false.size())));

		System.out.println("***********************************************");
		
		// Probability out_of_cat_food
		System.out.println("*********************************************");
		double out_of_cat_food = calculate(O, no_of_lines);
		System.out.println("P(O)");
		System.out.println("T= " + decimal.format(out_of_cat_food));
		System.out.println("F= " + decimal.format(1-out_of_cat_food));
		System.out.println("**********************************************");
		
		// Probability George_feeds_cat
		System.out.println("**********************************************");
		System.out.println("P(F) : ");
		System.out.println("P(F|W = F and O = T ) = " + decimal.format(calculate(F_when_W_false_O,F_when_W_false_O.size())));		
		System.out.println("P(F|W = F and O = F) = " + decimal.format(calculate(F_when_W_false_O_false,F_when_W_false_O_false.size())));
		System.out.println("P(F|W = T  and O = T ) = " + decimal.format(calculate(F_when_W_O,F_when_W_O.size())));
		System.out.println("P(F|W = T  and O = F) = " + decimal.format(calculate(F_when_W_O_false,F_when_W_O_false.size())));
		System.out.println("**************************************************");
		
	}
}