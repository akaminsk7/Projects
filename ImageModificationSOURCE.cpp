/*
BMPmodSOURCE.cpp
A BMP file starts with a header that contain various info at specified positions
based on the number of bytes, because it is a binary file.
using the fstream functions, the program will read in a bitmap file, this
must be in 24-bit truecolor in order to work. The end result of the program is
to convert a regular image into it's negative. It can also change a negative
into the positive proof converting the RGB values.
*/
#include <iostream>
#include <fstream>
#include <cstdlib>
#include <string>
#include <cstring>

using namespace std;

void process( int& , int& , int&, int, int, int, int, int );
int getInt(fstream& , int );

int main()
{
	int fileSize;
	int start;
	int width, height;
	int scanLineSize;
	int padding;
	int pos;
	int blue, green, red;
	int tempblue = 0, tempgreen = 0, tempred = 0;
	int t;
	int tpos;
	
	cout << "Please enter the file name: "; //Prompts user for the name of the file
	string fileName;
	cin >> fileName; 
	cout << "\n0 is Negative\n1 is Sunset\n2 is grayscale\n3 is Checker Board\n4 is Mirror\n5 is Upside Down\n6 is a custom filter\nWhich filter do you want to use: "; //Prompts user for the filter they want
	int filter;
	cin >> filter;
	cin.ignore();

	fstream stream;
	//open as a binary file
	stream.open(fileName.c_str(), ios::in | ios::out | ios::binary);
	if( stream.fail() )
	{
		cout << "File not found." << endl;
		system("pause");
		return 1;
	}
	fileSize = getInt( stream, 2);
	start = getInt( stream, 10);
	width = getInt( stream, 18 );
	height = getInt( stream, 22 );
	_sleep(600);

	//scan lines must occupy multiples of four bytes
	scanLineSize = width * 3;
	padding = 0;
	if( scanLineSize % 4 != 0)
	{
		padding = 4 - scanLineSize % 4;
	}
	if( fileSize != start + (scanLineSize + padding) * height )
	{
		cout << "Not a 24-bit true color image file." << endl;
		system("pause");
		return 1;
	}
	pos = start;//go to the start of the pixels

	if( filter == 4) //Mirror Image
	{

		for( int i = 0; i < height; i++ ) //for each scan line
		{
			tpos = pos + scanLineSize; //Finds the position of the other sets of pixels
			for( int k = 0; k < width; k++ ) //for each pixel
			{
				if( k - 1 < (width / 2) ) //This runs half the code
				{
					stream.seekg( pos );
					blue = stream.get();//read the pixel
					green = stream.get();
					red = stream.get();

					stream.seekg( tpos );
					tempblue = stream.get();//read the pixel
					tempgreen = stream.get();
					tempred = stream.get();

					t = blue; //This switches blue
					blue = tempblue;
					tempblue = t;

					t = red; //This switches red
					red = tempred;
					tempred = t;

					t = green; //This switches green
					green = tempgreen;
					tempgreen = t;


					stream.seekp( pos ); //go back to the start of the pixel
					stream.put( blue ); //write out the new pixel
					stream.put( green );
					stream.put( red );

					stream.seekp( tpos ); //go back to the start of the pixel
					stream.put( tempblue ); //write out the new pixel
					stream.put( tempgreen );
					stream.put( tempred );

					pos = pos + 3; //Moves it over 3 positions
					if( tpos - 3 > start ) //This will move the second set of pixels back 3 positions
					{
						tpos = tpos - 3;
					} 
				}
				else //This runs after the line is switched
				{
					pos = pos + 3;
				}
			}
			stream.seekg( padding, ios::cur); //skip the padding
		}
	}
	else if( filter == 5 ) //This will only run half the code
	{
		for( int i = 0; i < (height / 2); i++ ) //for each scan line
		{
			tpos = ((height - (i + 1)) * scanLineSize) + start; //This will find the position of the other sets of pixels
			for( int k = 0; k < width; k++ ) //for each pixel
			{
					stream.seekg( pos );
					blue = stream.get();//read the pixel
					green = stream.get();
					red = stream.get();

					stream.seekg( tpos );
					tempblue = stream.get();//read the pixel
					tempgreen = stream.get();
					tempred = stream.get();

					t = blue; //This switches blue
					blue = tempblue;
					tempblue = t;

					t = red; //This switches red
					red = tempred;
					tempred = t;

					t = green; //This switches green
					green = tempgreen;
					tempgreen = t;


					stream.seekp( pos ); //go back to the start of the pixel
					stream.put( blue ); //write out the new pixel
					stream.put( green );
					stream.put( red );

					stream.seekp( tpos ); //go back to the start of the pixel
					stream.put( tempblue ); //write out the new pixel
					stream.put( tempgreen );
					stream.put( tempred );

					pos = pos + 3; //Moves to the next pixel
					tpos = tpos + 3; //Moves to the next pixel
			}
			stream.seekg( padding, ios::cur); //skip the padding
		}
	}
	else if( filter == 6 ) //Custom Filter
	{
		for( int i = 0; i < height; i++ ) //for each scan line
		{
			tpos = pos + scanLineSize; //Finds the second set of pixel
			for( int k = 0; k < width; k++ ) //for each pixel
			{
				if( k - 1 < (width / 2) )
				{
					stream.seekg( pos );
					blue = stream.get();//read the pixel
					green = stream.get();
					red = stream.get();

					stream.seekg( tpos );
					tempblue = stream.get();//read the pixel
					tempgreen = stream.get();
					tempred = stream.get();

					blue = tempblue; //makes right pixel equal to the left
					red = tempred;
					green = tempgreen;

					stream.seekp( pos ); //go back to the start of the pixel
					stream.put( blue ); //write out the new pixel
					stream.put( green );
					stream.put( red );

					stream.seekp( tpos ); //go back to the start of the pixel
					stream.put( tempblue ); //write out the new pixel
					stream.put( tempgreen );
					stream.put( tempred );

					pos = pos + 3; //Moves it over 3 positions
					if( tpos - 3 > start ) //This will move the second set of pixels back 3 positions
					{
						tpos = tpos - 3;
					} 
				}
				else //This runs after the line is switched
				{
					pos = pos + 3;
				}
			}
			stream.seekg( padding, ios::cur); //skip the padding
		}
	}
	else
	{
		for( int i = 0; i < height; i++ ) //for each scan line
		{
			for( int k = 0; k < width; k++ ) //for each pixel
			{
				stream.seekg( pos );
				blue = stream.get();//read the pixel
				green = stream.get();
				red = stream.get();
				process( blue, green, red, filter, i, k, height, width );
				stream.seekp( pos ); //go back to the start of the pixel
				stream.put( blue ); //write out the new pixel
				stream.put( green );
				stream.put( red );
				pos = pos + 3;
			}
			stream.seekg( padding, ios::cur); //skip the padding
		}
	}


	cout << endl;
	system("pause");
	return EXIT_SUCCESS;
}

/**
Processes a pixel by forming the negative.
@param blue the blue value of the pixel
@param green the green value of the pixel
@param red the red value of the pixel
*/
void process( int& blue, int& green, int& red, int f, int line, int pos, int height, int width )
{
	int blockl = width / 6;
	int blockk = height / 12;
	int posw = 99;
	int posl = 99;
	int tempblue, tempgreen, tempred;
	int newwidth;
	int t;


	switch( f )
	{
	case 0: //Negative
		blue = 255 - blue;
		green = 255 - green;
		red = 255 - red;
		break;

	case 1: //Sunset
		blue = blue * 0.7;
		green = green * 0.7;
		red = red;
		break;

	case 2: //Grayscale
		t = (blue + green + red) / 3; //This averages the values of the pixel
		blue = t;
		green = t;
		red = t;
		break;

	case 3: //Checker
		if( pos < blockl ) //This tests for the first block
		{
			posw = 0;
		}
		else //If it is not in the first block then it runs through this
		{
			for( int i = 1; i < 6; i++)
			{
				if( (pos - (blockl * i)) < blockl) //If the width - length of block * block number < block length
				{
					posw = i; //this is the width of the block
					i = 600;
				}
			}
		}
		 
		if( line < blockk ) //Tests for first block
		{
			posl = 0;
		}
		else //If not, then it runs through to find the block number
		{
			for( int i = 1; i < 12; i++)
			{
				if( (line - (blockk * i)) < blockk) //If the line number - block length * the block number < block length
				{
					posl = i; //Finds block the length is
					i = 600;
				}
			}
		}


		switch( posl ) //This tests for the block number of the length
		{
		case 0: 
		case 2:
		case 4:
		case 6:
		case 8:
		case 10:
			switch( posw ) //This tests for the block number of the width
			{
			case 0:
			case 2:
			case 4:
				blue = blue * 0.55;
				green = green * 0.55;
				red = red;
				break;
			case 1:
			case 3:
			case 5:
				blue = blue * 0.55;
				green = green * 0.55;
				red = red * 0.55;
				break;
			default:
				cout << "\n\nIt broke around 346\n\n";
				break;
			}
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 9:
		case 11:
			switch( posw ) //This tests for the block number of the width
			{
			case 0:
			case 2:
			case 4:
				blue = blue * 0.55;
				green = green * 0.55;
				red = red * 0.55;
				break;
			case 1:
			case 3:
			case 5:
				blue = blue * 0.55;
				green = green * 0.55;
				red = red;
				break;
			default:
				cout << "\n\nIt broke around 373\n\n";
				break;
			}
			break;
		default:
			cout << "\n\nIt broke around 378\n\n";
			break;
		}
		break;

	case 6: //Other
	case 5: //Upside Down
	case 4: //Mirror
	default:
		break;
	}

}
/**
Gets an integer from a binary stream
@param stream the stream
@param offset the offest at which to read the integer
@return the integer starting at the given offset
*/
int getInt(fstream& stream, int offset )
{
	stream.seekg( offset );
	int result = 0;
	int base = 1;
	for( int i = 0; i < 4; i++)
	{
		result = result + stream.get() * base;
		base = base * 256;
	}
	return result;
}