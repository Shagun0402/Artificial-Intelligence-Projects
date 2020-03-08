

/*
Let  Probability
	PB = burglary
	PE = earthquake
	PJ_AT = Alarm true given John Calls.
	PJ_AF = Alarm is false given John Calls.
	PM_AT = Alarm true given Mary Calls.
	PM_AF = Alarm is false given Mary Calls.
	PA_BT_ET = Alarm given burglary is true and earthquake is true
	PA_BT_EF = Alarm given burglary is true and earthquake is false
	PA_BF_ET = Alarm given burglary is false and earthquake is true
	PA_BF_EF = Alarm given burglary is false and earthquake is false
*/

public class Bayesian_Network 
{
	Node B, E, A, J, M;
	
	public Bayesian_Network() 
	{
		B = new Node('B', Node.Node_location.INITIAL);
		E = new Node('E', Node.Node_location.INITIAL);
		A = new Node('A', Node.Node_location.INTERMEDIATE);
		J = new Node('J', Node.Node_location.END);
		M = new Node('M', Node.Node_location.END);
	}
	
	public void setval_true(Node tmp, boolean bool) 
	{
		tmp.q_end = 0;
		if (bool == true)
			tmp.provided_end = 0;
	}
	
	public void setval_false(Node tmp, boolean bool) 
	{
		tmp.q_beg = 1;
		if (bool == true)
			tmp.provided_beg = 1;
	}
	
	public void process_Arguments(String[] args) 
	{
		boolean bool = false;		
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("given"))
				bool = true;
			else if (args[i].equalsIgnoreCase("Bf"))
				setval_false(this.B, bool);
			else if (args[i].equalsIgnoreCase("Bt"))
				setval_true(this.B, bool);
			else if (args[i].equalsIgnoreCase("Ef"))
				setval_false(this.E, bool);
			else if (args[i].equalsIgnoreCase("Et"))
				setval_true(this.E, bool);
			else if (args[i].equalsIgnoreCase("Af"))
				setval_false(this.A, bool);
			else if (args[i].equalsIgnoreCase("At"))
				setval_true(this.A, bool);
			else if (args[i].equalsIgnoreCase("Jf"))
				setval_false(this.J, bool);
			else if (args[i].equalsIgnoreCase("Jt"))
				setval_true(this.J, bool);
			else if (args[i].equalsIgnoreCase("Mf"))
				setval_false(this.M, bool);
			else if (args[i].equalsIgnoreCase("Mt"))
				setval_true(this.M, bool);
			
			else {
				System.out.println("OOPS! Incorrect input. Retry again!!!!!!!");
				System.exit(0);
			}
		}
	}
	
	public static double calculate_Probability (Node B, Node E, Node A, Node J, Node M) 
	{
		//FINDING DENOMINATOR 
				double dec = 0;
				//Burglary
				for (int b = B.provided_beg; b <= B.provided_end; b++)
					//Earthquake
					for (int e = E.provided_beg; e <= E.provided_end; e++)
						//Alarm
						for (int a = A.provided_beg; a <= A.provided_end; a++)
							//John calls
							for (int j = J.provided_beg; j <= J.provided_end; j++)
								//Mary calls
								for (int m = M.provided_beg; m <= M.provided_end; m++)
									dec += (Node.calcProbability(B, b, -1, -1) * Node.calcProbability(E, e, -1, -1) * Node.calcProbability(A, a, b, e) * Node.calcProbability(J, j, a, -1) * Node.calcProbability(M, m, a, -1));
		//FINDING NUMERATOR
		double num = 0;
		//Burglary
		for (int b = B.q_beg; b <= B.q_end; b++)
			//Earthquake
			for (int e = E.q_beg; e <= E.q_end; e++)
				//Alarm
				for (int a = A.q_beg; a <= A.q_end; a++)
					//John calls
					for (int j = J.q_beg; j <= J.q_end; j++)
						//Mary calls
						for (int m = M.q_beg; m <= M.q_end; m++)
							num += (Node.calcProbability(B, b, -1, -1) * Node.calcProbability(E, e, -1, -1) * Node.calcProbability(A, a, b, e) * Node.calcProbability(J, j, a, -1) * Node.calcProbability(M, m, a, -1));
		
		return (num/dec);
	}
	
	public static void main(String[] args) 
	{
		// Provide input
		if (args.length == 0) 
		{
			System.out.println("Give in input arguments:");
			System.exit(0);
		}
		if (args.length > 6) 
		{
			System.out.println("Insert input arguments [1 2 3 4 5]");
			System.exit(0);
		}
		// Probability
		Bayesian_Network bnet = new Bayesian_Network();
		bnet.process_Arguments(args);
		// Printing Probability
		System.out.println(" \n\n The Joint Probability is: " + calculate_Probability(bnet.B,bnet.E, bnet.A, bnet.J, bnet.M));
			
	}
}

class Node 
{
	char id;
	Node_location destination;
	int q_beg, q_end, provided_beg, provided_end;
	
	static final double PB = 0.001;
	static final double PE = 0.002;
	static final double PJ_AT = 0.90;
	static final double PJ_AF = 0.05;
	static final double PM_AT = 0.70;
	static final double PM_AF = 0.01;
	static final double PA_BT_ET = 0.95;
	static final double PA_BT_EF = 0.94;
	static final double PA_BF_ET = 0.29;
	static final double PA_BF_EF = 0.001;

	public enum Node_location 
	{
		INITIAL,
		INTERMEDIATE, 
		END, 
		NONE
	}
	
	public Node(char C, Node_location location) 
	{
		id = C;
		destination = location;
		q_beg = 0;
		q_end = 1;
		provided_beg = 0; 
		provided_end = 1; 
	}
	
	public static double calcProbability(Node tmp, int flag, int p1, int p2) 
	{
		double probab= 0;
		if (tmp.destination == Node_location.INITIAL)
		{
			if (tmp.id == 'B')
			{ 
				if (flag == 1)
					probab = PB;
				else
					probab = 1 - PB;
			}  
			else
			{ 
				if (flag == 1) 
					probab = PE;
				else
					probab = 1 - PE;
			}
		} 
		else if (tmp.destination == Node_location.INTERMEDIATE) 
		{ 
			if ((p1 == 1) && (p2 == 1))
			{ 
				if (flag == 1)
					probab = PA_BT_ET;
				else
					probab = 1 - PA_BT_ET;
			}
			else if ((p1 == 1) && (p2 == 0)) 
			{
				if (flag == 1)
					probab = PA_BT_EF;
				else
					probab = 1 - PA_BT_EF;
			} 
			else if ((p1 == 0) && (p2 == 1)) 
			{ 
				if (flag == 1)
					probab = PA_BF_ET;
				else
					probab = 1 - PA_BF_ET;
			}
			else if ((p1 == 0) && (p2 == 0)) 
			{ 
				if (flag == 1)
					probab = PA_BF_EF;
				else
					probab = 1 - PA_BF_EF;
			} 
		} 
		else if (tmp.destination == Node_location.END)
		{ 
			if (tmp.id == 'J')
			{
				if (p1 == 1)
				{ 
					if (flag == 1)
						probab = PJ_AT;
					else
						probab = 1 - PJ_AT;
				} 
				else
				{ 
					if (flag == 1)
						probab = PJ_AF;
					else
						probab = 1 - PJ_AF;
				} 
			}
			else if (tmp.id == 'M')
			{
				if (p1 == 1) 
				{ 
					if (flag == 1)
						probab = PM_AT;
					else
						probab = 1 - PM_AT;
				} 
				else 
				{ 
					if (flag == 1)
						probab = PM_AF;
					else
						probab = 1 - PM_AF;
				} 
			}
		}
		return probab;
	}
}