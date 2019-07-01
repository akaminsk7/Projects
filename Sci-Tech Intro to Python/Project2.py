num_1 = input("What is the first number you would like to operate on: ")
num_2 = input("What is the second number you would like to operate on: ")

sum = num_1 + num_2		#Addition
diff = num_1 - num_2 	#Subtraction
prod = num_1 * num_2 	#Multiply
quot = num_1 / num_2 	#Divide
pow = num_1 ** num_2 	#Power
mod = num_1 % num_2  	#Remainder

print("Here are the following results for the inputs: " + num_1 + " and " + num_2 
		+ "\nAddition:\t" + sum + "\nSubtraction:\t" + diff + "\nMultiplication:\t" + prod 
		+ "\nDivision:\t" + quot + )