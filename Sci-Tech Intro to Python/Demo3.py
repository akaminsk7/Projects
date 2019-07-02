'''
	WARM-UP:
	What is the value of output for the following snippets of code:
	x = 2
	y = 3
	1) output = x + y
	2) output = y - x 
	3) output = x ** y 
	4) output = y % x 
	5) output = x * y 
	6) output = y / x
'''
#Number inputs
num_1 = input("What is the first number you would like to operate on: ")
num_2 = input("What is the second number you would like to operate on: ")
function = raw_input("What function would you like to preform?\nAddition(a), Subtraction(s), Multiplication(m), Division(d), remainder(r) or power(p):")

message = "";
if function == "Addition" or function == "a":
	output = num_1 + num_2		#Addition
	message = str(num_1) + " + " + str(num_2) + " = " + str(output)	#Message creation
elif function == "Subtraction" or function == "s":
	output = num_1 - num_2 		#Subtraction
	message = str(num_1) + " - " + str(num_2) + " = " + str(output)	#Message creation
elif function == "Multiplication" or function == "m":
	output = num_1 * num_2 		#Multiply
	message = str(num_1) + " * " + str(num_2) + " = " + str(output)	#Message creation
elif function == "Division" or function == "d":
	output = num_1 / num_2 		#Divide
	message = str(num_1) + " / " + str(num_2) + " = " + str(output)	#Message creation
elif function == "remainder" or function == "r":
	output = num_1 % num_2  	#Remainder
	message = str(num_1) + " % " + str(num_2) + " = " + str(output)	#Message creation
elif function == "power" or function == "p":
	output = num_1 ** num_2 	#Power
	message = str(num_1) + " ^ " + str(num_2) + " = " + str(output)	#Message creation
else:
	message = "An incorrect function was inputted."

print(message)