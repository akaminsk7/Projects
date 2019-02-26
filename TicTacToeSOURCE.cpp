/*

Name:Aaron Kaminsky
Purpose of Program:

*/
#include <cstdlib> 
#include <iostream>
#include <iomanip>
#include <string>
#include <Windows.h>
#include <ctime>
#include <cmath>
#include <cctype>
#include "Conio.h"

using namespace std;

void display(int *, int);
void playerturn(int *, int);
void CPU(int *, int);
int strat(int *, int, int);
void getPos(int *, int);
int winning(int *, int);
void stratA(int *, int);
bool playAgn(int *);

int main()
{
	//0 means empty, 1 means player, 2 means CPU
	int board[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	int arrsize = 9;
	bool playagain = true;
	int win = 0;
	int gamewon[] = { 0, 0, 0 };
	do
	{
		textcolor(15);
		//This resets the winner
		win = 0;
		//This resets the array
		for (int index = 0; index < arrsize; index++)
		{
			board[index] = 0;
		}
		for (int index = 0; index < 4; index++)
		{
			//this will display the board
			display(board, arrsize);
			//This will prompt the user for a position to go
			getPos(board, arrsize);
			//This tests to see if the User beat the computer
			if (index > 1)
			{
				win = winning(board, arrsize);
			}
			if (win != 1)
			{
				//The CPU goes
				CPU(board, arrsize);
			}
			//After each person went 3 times, it tests for a win
			if (index > 1)
			{
				//This tests for three in a row
				win = winning(board, arrsize);
				switch (win)
				{
					//If the user won
				case 1:
					display(board, arrsize);
					index = 99;
					gamewon[0]++;
					cout << "\n\n YOU BEAT THE COMPUTER!\n";
					break;
					//If the CPU won
				case 2:
					display(board, arrsize);
					index = 99;
					gamewon[1]++;
					cout << "\n\n YOU LOST TO THE COMPUTER!\n";
					break;
				default:
					break;
				}

			}
		}
		if (win == 0)
		{
			display(board, arrsize);
			//This will prompt the user for a position to go
			getPos(board, arrsize);
			win = winning(board, arrsize);
			if (win == 0)
			{
				display(board, arrsize);
				gamewon[2]++;
				cout << "\n\nYOU TIED THE COMPUTER!\n";
			}
			else
			{
				display(board, arrsize);
				gamewon[0]++;
				cout << "\n\nYOU BEAT THE COMPUTER!\n";
			}
		}

		playagain = playAgn(gamewon);
	} while (playagain == true);
	cout << "Thanks for playing!" << endl;

	cout << endl;
	system("pause");
	return EXIT_SUCCESS;
}
void display(int *arr, int size)
{
	int indexx = 0;
	cout << "======================\n\n";
	for (int index = 0; index < size; index++)
	{
		textcolor(15);
		//This will print out the screen based on the player's input or the computer's
		if (*(arr + index) == 0)
		{
			cout << index + 1;
		}
		else if (*(arr + index) == 1)
		{
			textcolor(11);
			cout << "X";
		}
		else
		{
			textcolor(14);
			cout << "O";
		}
		//This prints out the rest of the board
		textcolor(15);
		indexx = index + 1;
		if (indexx % 3 == 0 && index != (size - 1))
		{
			cout << "\n-----\n";
		}
		else if (index != (size - 1))
		{
			cout << "|";
		}
		else
		{
			cout << "\n";
		}
	}
}
void getPos(int *barr, int size)
{
	//This will repeat the do-while loop
	bool rep = false;
	//This is my counter array
	int arr[] = { 0, 0, 0 };
	//This is the string that is inputed
	string input = "9999";
	//This will become the size of the string
	int xx = 9999;
	//This will become the ascii value of the string
	int stringint = 9999;
	//This is the total number in each counter
	int total = 9999;
	//This is the converted integer
	int x = 999;
	do
	{
		do
		{
			//This will reset the counter array in case the loop goes again
			for (int index = 0; index < 3; index++)
			{
				arr[index] = 0;
			}
			cout << "\n\nPlease enter a Position: ";
			textcolor(10);
			getline(cin, input);
			textcolor(15);
			xx = input.size();
			for (int index = 0; index < xx; index++)
			{
				//This creates a certain character into the int value
				stringint = (int)input[index];
				switch (stringint)
				{
					//This tests for all numbers
				case 48:
				case 49:
				case 50:
				case 51:
				case 52:
				case 53:
				case 54:
				case 55:
				case 56:
				case 57:
					arr[1]++;
					break;
				default:
					break;
				}
			}
			//This adds all numbers in the counter
			total = arr[0] + arr[1] + arr[2];

			//The loop will repeat when all of the numbers in the array do not equal the total number of eligible characters
		} while (xx != total);

		//This turns the string into numbers
		x = atoi(input.c_str());
		if (x < 0 || x > 10)
		{
			rep = true;
		}
		else
		{
			rep = false;
		}

		x--;
		if (*(barr + x) != 0)
		{
			rep = true;
		}
		else
		{
			rep = false;
		}
	} while (rep == true);

	*(barr + x) = 1;
}
void CPU(int *arr, int size)
{
	int comp = 0;
	bool cpu = true;
	int recomend = 0;
	for (int index = 0; index < size; index++)
	{
		if (*(arr + index) == 2)
		{
			comp++;
		}
	}
	switch (comp)
	{
	case 0:
		if (*(arr + 4) == 0)
		{
			*(arr + 4) = 2;
		}
		else
		{
			*arr = 2;
		}
		break;
	case 1:
		recomend = strat(arr, size, 1);
		if (recomend == 20)
		{
			//1 3
			if (*arr == 1 && *(arr + 2) == 1 && *(arr + 4) == 0)
			{
				*(arr + 4) = 2;
			}
			//1 7
			else if (*arr == 1 && *(arr + 6) == 1 && *(arr + 4) == 0)
			{
				*(arr + 4) = 2;
			}
			//3 9
			else if (*(arr + 8) == 1 && *(arr + 2) == 1 && *(arr + 4) == 0)
			{
				*(arr + 4) = 2;
			}
			//7 9
			else if (*(arr + 6) == 1 && *(arr + 8) == 1 && *(arr + 4) == 0)
			{
				*(arr + 4) = 2;
			}
			//5 9
			else if(*(arr + 4) == 1 && *(arr + 8) == 1 && *(arr + 6) == 0)
			{
				*(arr + 6) = 2;
			}
			//1 8 7
			else if(*arr == 1 && *(arr + 7) == 1 && *(arr + 6) == 0)
			{
				*(arr + 6) = 2;
			}
			//3 8 9
			else if(*(arr + 2) == 1 && *(arr + 7) == 1 && *(arr + 8) == 0)
			{
				*(arr + 8) = 2;
			}
			else
			{
				stratA(arr, size);
			}
		}
		else
		{
			*(arr + recomend) = 2;
		}
		break;
	case 2:
		recomend = strat(arr, size, 2);
		if (recomend == 20)
		{
			recomend = strat(arr, size, 1);
			if (recomend == 20)
			{
				stratA(arr, size);
			}
			else
			{
				*(arr + recomend) = 2;
			}
		}
		else
		{
			*(arr + recomend) = 2;
		}
		break;
	case 3:
		recomend = strat(arr, size, 2);
		if (recomend == 20)
		{
			recomend = strat(arr, size, 1);
			if (recomend == 20)
			{
				stratA(arr, size);
			}
			else
			{
				*(arr + recomend) = 2;
			}
		}
		else
		{
			*(arr + recomend) = 2;
		}
		break;
	case 4:
		recomend = strat(arr, size, 2);
		if (recomend == 20)
		{
			recomend = strat(arr, size, 1);
			if (recomend == 20)
			{
				stratA(arr, size);
			}
			else
			{
				*(arr + recomend) = 2;
			}
		}
		else
		{
			*(arr + recomend) = 2;
		}
		break;
	default:
		cout << "This shouldn't happen" << endl;
		break;
	}
}
int strat(int *arr, int size, int test)
{
	int pos = 0;

	// 1X 2 3X
	if (*arr == test && test == *(arr + 2) && *(arr + 1) == 0)
	{
		pos = 2 - 1;
	}
	// 1X 2X 3
	else if (*arr == test && test == *(arr + 1) && *(arr + 2) == 0)
	{
		pos = 3 - 1;
	}
	//1 2X 3X
	else if (*(arr + 1) == test && *(arr + 2) == test && *arr == 0)
	{
		pos = 1 - 1;
	}
	//4X 5X 6
	else if (*(arr + 3) == test && *(arr + 4) == test && *(arr + 5) == 0)
	{
		pos = 6 - 1;
	}
	//4X 5 6X
	else if (*(arr + 3) == test && *(arr + 5) == test && *(arr + 4) == 0)
	{
		pos = 5 - 1;
	}
	//4 5X 6X
	else if (*(arr + 5) == test && *(arr + 4) == test && *(arr + 3) == 0)
	{
		pos = 4 - 1;
	}
	//7X 8X 9
	else if (*(arr + 6) == test && *(arr + 7) == test && *(arr + 8) == 0)
	{
		pos = 9 - 1;
	}
	//7X 8 9X
	else if (*(arr + 6) == test && *(arr + 8) == test && *(arr + 7) == 0)
	{
		pos = 8 - 1;
	}
	//7 8X 9X
	else if (*(arr + 7) == test && *(arr + 8) == test && *(arr + 6) == 0)
	{
		pos = 7 - 1;
	}
	//1 4X 7X
	else if (*(arr + 3) == test && *(arr + 6) == test && *arr == 0)
	{
		pos = 1 - 1;
	}
	// 1X 4 7X
	else if (*arr == test && *(arr + 6) == test && *(arr + 3) == 0)
	{
		pos = 4 - 1;
	}
	// 1X 4X 7
	else if (*(arr + 3) == test && *arr == test && *(arr + 6) == 0)
	{
		pos = 7 - 1;
	}
	//2X 5X 8
	else if (*(arr + 1) == test && *(arr + 4) == test && *(arr + 7) == 0)
	{
		pos = 8 - 1;
	}
	//2X 5 8X
	else if (*(arr + 1) == test && *(arr + 7) == test && *(arr + 4) == 0)
	{
		pos = 5 - 1;
	}
	//2 5X 8X
	else if (*(arr + 7) == test && *(arr + 4) == test && *(arr + 1) == 0)
	{
		pos = 2 - 1;
	}
	//3X 6X 9
	else if (*(arr + 2) == test && *(arr + 5) == test && *(arr + 8) == 0)
	{
		pos = 9 - 1;
	}
	//3X 6 9X
	else if (*(arr + 2) == test && *(arr + 8) == test && *(arr + 5) == 0)
	{
		pos = 6 - 1;
	}
	//3 6X 9X
	else if (*(arr + 8) == test && *(arr + 5) == test && *(arr + 2) == 0)
	{
		pos = 3 - 1;
	}
	//1X 5X 9
	else if (*arr == test && *(arr + 4) == test && *(arr + 8) == 0)
	{
		pos = 9 - 1;
	}
	//1X 5 9X
	else if (*arr == test && *(arr + 8) == test && *(arr + 4) == 0)
	{
		pos = 5 - 1;
	}
	//1 5X 9X
	else if (*(arr + 4) == test && *(arr + 8) == test && *arr == 0)
	{
		pos = 1 - 1;
	}
	//7X 5X 3
	else if (*(arr + 6) == test && *(arr + 4) == test && *(arr + 2) == 0)
	{
		pos = 3 - 1;
	}
	//7X 5 3X
	else if (*(arr + 6) == test && *(arr + 2) == test && *(arr + 4) == 0)
	{
		pos = 5 - 1;
	}
	//7 5X 3X
	else if (*(arr + 4) == test && *(arr + 2) == test && *(arr + 6) == 0)
	{
		pos = 7 - 1;
	}
	//2X 4X 1
	else if (*(arr + 1) == 1 && *(arr + 3) == 1 && *arr == 0)
	{
		pos = 1 - 1;
	}
	//2X 6X 3
	else if (*(arr + 1) == 1 && *(arr + 5) == 1 && *(arr + 2) == 0)
	{
		pos = 3 - 1;
	}
	//4X 8X 7
	else if (*(arr + 3) == 1 && *(arr + 7) == 1 && *(arr + 6) == 0)
	{
		pos = 7 - 1;
	}
	//6X 8X 9
	else if (*(arr + 5) == 1 && *(arr + 7) == 1 && *(arr + 8) == 0)
	{
		pos = 9 - 1;
	}
	else
	{
		pos = 20;
	}
	return pos;
}
int winning(int *arr, int size)
{
	int win = 0;
	//1 2 3
	if (*arr == *(arr + 1) && *(arr + 2) == *arr && *(arr + 2) != 0)
	{
		if (*(arr + 1) == 1)
		{
			win = 1;
		}
		else if (*(arr + 2) == 2)
		{
			win = 2;
		}
		else
		{
			cout << "Something is wrong in winning" << endl;
		}
	}
	//4 5 6
	else if (*(arr + 3) == *(arr + 4) && *(arr + 4) == *(arr + 5) && *(arr + 5) != 0)
	{
		if (*(arr + 3) == 1)
		{
			win = 1;
		}
		else if (*(arr + 3) == 2)
		{
			win = 2;
		}
		else
		{
			cout << "Something is wrong in winning" << endl;
		}
	}
	//7 8 9
	else if (*(arr + 6) == *(arr + 7) && *(arr + 7) == *(arr + 8) && *(arr + 7) != 0)
	{
		if (*(arr + 6) == 1)
		{
			win = 1;
		}
		else if (*(arr + 6) == 2)
		{
			win = 2;
		}
		else
		{
			cout << "Something is wrong in winning" << endl;
		}
	}
	//1 4 7
	else if (*arr == *(arr + 3) && *(arr + 3) == *(arr + 6) && *(arr + 6) != 0)
	{
		if (*(arr + 3) == 1)
		{
			win = 1;
		}
		else if (*(arr + 3) == 2)
		{
			win = 2;
		}
		else
		{
			cout << "Something is wrong in winning" << endl;
		}
	}
	else if (*(arr + 1) == *(arr + 4) && *(arr + 4) == *(arr + 7) && *(arr + 7) != 0)
	{
		if (*(arr + 1) == 1)
		{
			win = 1;
		}
		else if (*(arr + 1) == 2)
		{
			win = 2;
		}
		else
		{
			cout << "Something is wrong in winning" << endl;
		}
	}
	else if (*(arr + 2) == *(arr + 5) && *(arr + 5) == *(arr + 8) && *(arr + 5) != 0)
	{
		if (*(arr + 2) == 1)
		{
			win = 1;
		}
		else if (*(arr + 2) == 2)
		{
			win = 2;
		}
		else
		{
			cout << "Something is wrong in winning" << endl;
		}
	}
	//1 5 9
	else if (*arr == *(arr + 4) && *(arr + 4) == *(arr + 8) && *(arr + 4) != 0)
	{
		if (*(arr + 4) == 1)
		{
			win = 1;
		}
		else if (*(arr + 4) == 2)
		{
			win = 2;
		}
		else
		{
			cout << "Something is wrong in winning" << endl;
		}
	}
	//3 5 7
	else if (*(arr + 2) == *(arr + 4) && *(arr + 4) == *(arr + 6) && *(arr + 4) != 0)
	{
		if (*(arr + 4) == 1)
		{
			win = 1;
		}
		else if (*(arr + 4) == 2)
		{
			win = 2;
		}
		else
		{
			cout << "Something is wrong in winning" << endl;
		}
	}
	else
	{
		win = 0;
	}
	return win;
}
bool playAgn(int *arr)
{
	int color = 9;
	//This is the string that is inputed
	string input = "9999";
	//This will become the size of the string
	int xx = 9999;
	int x = 9;
	bool play = false;
	do
	{
		cout << "\n\n==============================\n\n";
		cout << "\nYou have beaten the computer ";
		textcolor(color);
		cout << *arr;
		textcolor(15);
		cout << " time(s)\n";
		cout << "You have lost to the computer ";
		textcolor(color);
		cout << *(arr + 1);
		textcolor(15);
		cout<< " time(s)\n";
		cout << "You have tied the computer ";
		textcolor(color);
		cout << *(arr + 2);
		textcolor(15);
		cout << " time(s)\n";
		cout << "\n===================================\n";
		cout << "\nWould you like to play again: ";
		textcolor(10);
		getline(cin, input);
		textcolor(15);
		xx = input.size();
		for (int index = 0; index < xx; index++)
		{
			input[index] = tolower(input[index]);
		}
		//This tests the string for the wanted words
		if (input == "yes" || input == "yeah" || input == "y" || input == "yup")
		{
			x = 1;
		}
		else if (input == "no" || input == "nope" || input == "n" || input == "naw")
		{
			x = 2;
		}
		else
		{
			x = 9;
		}
		//The loop will repeat when the string isnt one of the eligible answers or when the length of the eligible characters does not equal the length of the string
	} while (x == 9);
	if (x == 1)
	{
		play = true;
	}
	else if (x == 2)
	{
		play = false;
	}
	else
	{
		cout << "Something is broken in play again\n";
	}
	//This returns the value of the temp
	return play;
}
void stratA(int *arr, int size)
{
	if (*(arr + 1) == 0)
	{
		*(arr + 1) = 2;
	}
	else if (*(arr + 3) == 0)
	{
		*(arr + 3) = 2;
	}
	else if (*(arr + 4) == 0)
	{
		*(arr + 4) = 2;
	}
	else if (*(arr + 5) == 0)
	{
		*(arr + 5) = 2;
	}
	else if (*(arr + 7) == 0)
	{
		*(arr + 7) = 2;
	}
	else if (*(arr + 2) == 0)
	{
		*(arr + 2) = 2;
	}
	else if (*(arr + 6) == 0)
	{
		*(arr + 6) = 2;
	}
	else if (*(arr + 8) == 0)
	{
		*(arr + 8) = 2;
	}
	else if (*arr == 0)
	{
		*arr = 2;
	}
	else
	{
		cout << "Your code is borken" << endl;
	}
}