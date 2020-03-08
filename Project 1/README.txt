Uninformed Search

Implement a search algorithm that can find a route between any two cities. Your program will be called
find_route, and will take exactly three commandline arguments, as follows:

find_route input_filename origin_city destination_city

Example: find_route input.txt Munich Berlin
---------------------------------------------------------------------------------------------------------------------
Instructions:

Programming Language used: Python 2.4.3
Structure of Code:

1) Main Class-  * Used the concept of graphs to manage the nodes and its edges.
		* This class is used to maintain the list of nodea dnthe edges associated with it along with their costs.
		* This class handles functionalities like:
			# Adding Nodes
			# Adding Edges
			# Fetching Nodes
			# Fetching Nodes
			# Fetching Neighbours i.e, it helps in finding adjacent nodes
2) Functions Used- Used several functions that handle the below mentioned funcionalities:
			# Reading input arguments
			# Reading data from a file uptil END OF INPUT
			# Creating a graph to handle nodes and its edges
			# FInding the shortest path betweeen nodes and its neighbours
3) Steps to run the code-
	# Please note that the input file MUST be in the same directory as the source code.
	# Login into Omega using NetID using SSh Secure Shell or puTTY.
	# Enter into the directory in which the find_route.py and input file is stored.
	# Follow the below written instructions:
		(i) Type: chmod a+x find_route.py
		(ii) Type: python find_route.py <input_filename.txt> <Source_City> <Destination_City>

