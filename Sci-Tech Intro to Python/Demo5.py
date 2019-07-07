'''
	WARM-UP:
	Name and fix the 5 syntax errors in the following code:
	Hint 1: name should be a string
	Hint 2: number should be an int
	Hint 3: Everything that belongs in an if statement needs to be indented
	Hint 4: if statements end with a :
	Hint 5: You need to use a + in order to concatinate strings together

	name = input("What is your name: ")
	number = raw_input("Please enter your favorite number: ")
	if number > 5 and number < 20:
	print(name " + wants you to say Mah Rabu!")
	elif number < 50 and number > 20
		print( name + " wants you to say Goodnight Moon")
	else:
		print( "Can " + name " get a wow?")

'''
#'''
import random
x = input("Pick a number between 1 and 10: ")
count = 0
not_equal = False
while not not_equal:
	count += 1
	rand_num = random.randint(1, 11)
	print(str(count) + ". " + str(rand_num))
	not_equal = rand_num == x
print("It took " + str(count) + " tries to get number " + str(x))
#'''
# =========================================================================================
#'''
import random
print("Welcome to the Dice Roller")
sides = input("How many sides would you like for the die to have: ")
rolls = input("How many times would you like to roll the die: ")
for x in range(rolls):
	result = random.randint(1, sides)
	print( "Roll Number " + str(x + 1) + ". " + str(result))
#'''	