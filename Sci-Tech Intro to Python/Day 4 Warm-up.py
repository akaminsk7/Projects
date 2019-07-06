'''
	WARM-UP
	What is the output of the following code when:
	1) x = 5
	2) x = 6
	3) x = -1
	4) x = 10
'''
x = input("What value of x would you like to test: ")
if x > 5:
	print(str(x) + " is greater than 5")
elif x >= 10:
	print(str(x) + " is greater than or equal to 10")
elif x < 5:
	print(str(x) + " is less than 5")
else:
	print("Mah Rabu!!")