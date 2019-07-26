import random
question = raw_input("What would you like to ask the Magic Eight Ball? ")
print("Your question is: " + question)
num = random.randint(1, 20)
if num == 1:
	print("Your answer is: " + "It is certain")
elif num == 2:
	print("Your answer is: " + "It is decidedly so")
elif num == 3:
	print("Your answer is: " + "Without a doubt")
elif num == 4:
	print("Your answer is: " + "Yes - definitely")
elif num == 5:
	print("Your answer is: " + "You may rely on it.")
elif num == 6:
	print("Your answer is: " + "As I see it, yes")
elif num == 7:
	print("Your answer is: " + "Most likely")
elif num == 8:
	print("Your answer is: " + "Outlook good")
elif num == 9:
	print("Your answer is: " + "Yes")
elif num == 10:
	print("Your answer is: " + "Signs point to yes")
elif num == 11:
	print("Your answer is: " + "Reply hazy, try again")
elif num == 12:
	print("Your answer is: " + "Ask again later")
elif num == 13:
	print("Your answer is: " + "Better not tell you now")
elif num == 14:
	print("Your answer is: " + "Cannot predict now")
elif num == 15:
	print("Your answer is: " + "Concentrate and ask again")
elif num == 16:
	print("Your answer is: " + "Don't count on it")
elif num == 17:
	print("Your answer is: " + "My reply is no")
elif num == 18:
	print("Your answer is: " + "My sources say no")
elif num == 19:
	print("Your answer is: " + "Outlook not so good")
elif num == 20:
	print("Your answer is: " + "Very doubtful")
else:
	print("ERROR")