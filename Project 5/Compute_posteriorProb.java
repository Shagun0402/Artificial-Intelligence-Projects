
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class Compute_posteriorProb 
{

	public static void main(String[] args) 
	{

		double [] P_Hypothesis = {0.1, 0.2, 0.4, 0.2, 0.1};
		
		double [] P_H_CC = {1, 0.75, 0.5, 0.25, 0};
		
		String Obs = "";
		if (args.length > 0)
			Obs = args[0];
		
		print_result(" Observation sequence Q: " + Obs);
		print_result("\n Length of Q: " + Obs.length() + "\n");
		
		double P_CC = 0;
		for (int i = 0; i < P_Hypothesis.length; i++) 
		{
			P_CC += ( P_H_CC[i] * P_Hypothesis[i] );
		}

		if (args.length < 1) 
		{

			for (int i = 0; i < P_Hypothesis.length; i++) 
			{
				print_result("P(h" + (i + 1) + " | Q) = " + round_off(P_Hypothesis[i], 5) + "\n");
			}
			
			print_result("\n Probability that the next candy we pick will be C, given Q: " + round_off(P_CC, 5));
			print_result("\n Probability that the next candy we pick will be L, given Q: " + round_off(1 - P_CC, 5) + "\n\n");
			
			return;
		}
		
		calc_probability(P_Hypothesis, P_H_CC, P_CC, (1 - P_CC), Obs);
	}
	
	private static void calc_probability(double[] P_HCC, double[] P_H_CC, double P_CC, double P_H_LC, String obs_sequence) 
	{
		
		if (obs_sequence != null && obs_sequence.isEmpty())
			return;
		
		String seq = obs_sequence.substring(0, 1);
		double [] P_HCC_next = new double[P_HCC.length];
		print_result("\n After Observation " + obs_sequence.charAt(0) + " = " + obs_sequence.substring(1, obs_sequence.length()) + "\n\n");
		
		for (int i = 0; i < P_HCC.length; i++) 
		{
			
			P_HCC_next[i] = ((seq.equals("C") ? P_H_CC[i] : ( 1 - P_H_CC[i] )) * P_HCC[i]) / (seq.equals("C") ? P_CC : P_H_LC);
			
			print_result("P(h" + (i + 1) + " | Q) = " + round_off(P_HCC_next[i], 5) + "\n");
		}
		
		double P_CC_next = 0, P_LC_next = 0;
		for (int i = 0; i < P_HCC.length; i++) 
		{
			
			P_CC_next += P_H_CC[i] * P_HCC_next[i];
			P_LC_next += (1 - P_H_CC[i]) * P_HCC_next[i];
		}
		
		print_result("\n Probability that the next candy we pick will be C, given Q: " + round_off(P_CC_next, 5));
		print_result("\n Probability that the next candy we pick will be L, given Q: " + round_off(P_LC_next, 5) + "\n\n");
		
		calc_probability(P_HCC_next, P_H_CC, P_CC_next, P_LC_next, obs_sequence.substring(1, obs_sequence.length()));
	}
	
	private static void print_result(String seq_stmt) 
	{
		
		FileWriter fout = null;
		BufferedWriter bout = null;
		PrintWriter pout = null;
		
		try 
		{
			
			fout = new FileWriter("result.txt", true);
		    bout = new BufferedWriter(fout);
			pout = new PrintWriter(bout);
			pout.write(seq_stmt);
						
		} catch (Exception e) 
		{
			System.out.println(e);
		} 
		finally 
		{
			
			try {
				
				if (pout != null)
					pout.close();
				
			} 
			catch (Exception e1)
			{
				System.out.println(e1);
			}
		}
		
	}
	
	private static String round_off(double no, int precision) 
	{
		
		int precision_scale = 5;

		return BigDecimal.valueOf(no).setScale(precision_scale, BigDecimal.ROUND_HALF_UP).toString();
		
	}
	
}