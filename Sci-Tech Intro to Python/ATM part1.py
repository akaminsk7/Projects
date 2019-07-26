print("Regestration Form\n")
user_name = raw_input("Please create a username: ")
password = raw_input("Please create a password: ")
balance = 500
transaction_fail = False

print("Welcome to the ATM, Please login!")

login_user_name = raw_input("Please enter a username: ")
login_password = raw_input("Please enter a password: ")

# Username and verification 
if login_user_name == user_name and login_password == password:
	print("Login complete\nYou have a balance of $" + str(balance) )
	answer = raw_input("Would you like to withdrawl(w) or deposit(d): ")
	if answer == "w" or answer == "withdraw":
		amount = input("How much would you like to withdraw? ");
		if amount <= balance:
			print("You withdrew $" + str(amount) )
		else:
			transaction_fail = True
			print("You do not have enough money to complete the transaction")
	else:
		amount = raw_input("How much would you like to deposit?");
		print("You deposited $" + str(amount) )
elif login_user_name != user_name:
	print("The Username is incorrect.")
	transaction_fail = True
else:
	print("The Password is incorrect.")
	transaction_fail = True
if not transaction_fail:
	print("Thank you for using the ATM")
	