print("Regestration Form\n")
user_name = raw_input("Please create a username: ")
password = raw_input("Please create a password: ")
balance = 500
transaction_fail = False
cont = True


print("Welcome to the ATM, Please login!")

for x in range(5):
	print("Attempt Number " + str(x + 1))
	login_user_name = raw_input("Please enter a username: ")
	login_password = raw_input("Please enter a password: ")

	# Username and verification 
	if login_user_name == user_name and login_password == password:
		print("Login complete\nYou have a balance of $" + str(balance) )
		while cont:
			answer = raw_input("Would you like to withdrawl(w) or deposit(d): ")
			if answer == "w" or answer == "withdraw":
				repeat = True
				while repeat:
					amount = input("How much would you like to withdraw? ");
					if amount <= balance:
						repeat = False
						print("You withdrew $" + str(amount) )
					else:
						print("Please enter the an amount less than " + str(balance))
			else:
				amount = raw_input("How much would you like to deposit?");
				print("You deposited $" + str(amount) )
			answer = raw_input("Would you like to continue operations: ")
			cont = (answer == "yes") or (answer == "y")
	elif login_user_name != user_name:
		print("The Username is incorrect.")
		transaction_fail = True
	else:
		print("The Password is incorrect.")
		transaction_fail = True
		if x == 4:
			print("You have failed too many logins. Have a nice day!")
if not transaction_fail:
	print("Thank you for using the ATM")
		