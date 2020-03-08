Posterior Probability and Bayesian Networks

---------------------------------------------------------------------------------------------------------------------

Programming Language used: JAVA

		Code Structure for Task 1:

- The code initially starts with counting number of Lime and Cherry candies.
- P_H_CC is the probability of picking Cherry Candy from the Bag which P_Hypothesis is the probability provided in the task.

Steps to run code:
	> Load Compute_posteriorProb.java onto Omega.
	> javac Compute_posteriorProb.java
	> java Compute_posteriorProb [observations]
	> open result.txt file to check probabilities.

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		Code Structure for Task 2:

-class Bayesian_Network
	# void setval_true: When query is mentioned in end, set value to 0.
	# void setval_false: When query is mentioned in beginning, set value to 1.
	# void process_Arguments: Processes values taken from user and provides to correct input arguments.
-class Node
	# double calculate_Probability: Calculate joint probability

Bayesian_Network takes 6 arguments.
Arguments starts from 1-5 and each argument is used to specify:
	- Burglary
	- Alarm
	- John Calls
	- Mary Calls
	- Value for true
	- Value for false
This program creates a truth table and then calculates joint probability fr every combination of events.

Steps to run code:
	> Load Bayesian_Network.java onto Omega.
	> javac Bayesian_Network.java
	> java Bayesian_Network [input arguments]
