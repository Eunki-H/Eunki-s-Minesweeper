import java.util.*;

/** This class represents Minesweeper Object, which is one of the most famous game in the world.
 *  
 *  @author Eunki Hong
 *  @version 9/3/2020
 */
 
public class Minesweeper
{
	/********************* Fields ***********************/
	
	private char[][] keyPlay;
	private char[][] forPlay;
	
	
	/****************** Constructors ********************/
	
	/**
	 * Full constructor, setting the size 10x10  of keyPlay and forPlay array 
	 * and then, creating a key set in a given mode.
	 * 
	 * @param startingX	    The x value of the starting position of the keyPlay array.
	 * @param startingY	    The y value of the starting position of the keyPlay array.
	 * @param difficulty	The level of the game, it is chosen by the player.
	 */
	
	public Minesweeper(int startingX, int startingY, char difficulty)
	{
		this.keyPlay = new char[10][10];
		this.makeKeyPlay(startingX, startingY, difficulty);
		
		this.forPlay = new char[10][10];
		this.initializeForPlay();
		
		//
	}
	
	/**
	 * Default constructor, setting the size 10x10 of keyPlay and forPlay array
	 * and then, creating a key set in easy mode.
	 * 
	 * @param startingX	    The x value of the starting position of the keyPlay array.
	 * @param startingY	    The y value of the starting position of the keyPlay array.
	 */
	
	public Minesweeper(int startingX, int startingY)
	{
		this.keyPlay = new char[10][10];
		this.makeKeyPlay(startingX, startingY, 'e');
		
		this.forPlay = new char[10][10];
		this.initializeForPlay();
	}

	
	/********************* Methods **********************/
	
	/** 
	 * This method makes a keyPlay array be filled with a random key set, 
	 * making the give spot blank one simultaneously.
	 * 
	 * @param x		    The x value of the starting position of the keyPlay array.
	 * @param y		    The y value of the starting position of the keyPlay array.
	 * @param level  	The level of a game the player set.
	 */
	
	public void makeKeyPlay(int x, int y, char level)
	{				
		Random r = new Random();
				
		// Level (= number of bombs) of the game
		int numBombs = 15;
		
		if (level == 'e')
			numBombs = 15;
		
		if (level == 'm')
			numBombs = 20;
		
		if (level == 'h')
			numBombs = 30;
		
		// Bomb - set
		for (int i = 0; i < numBombs; i ++)
		{
			int xBomb, yBomb;
			boolean verifyBlank;
			
			do	// to spread entire bombs evenly
			{
				xBomb = r.nextInt(10);
				yBomb = r.nextInt(10);
				
				verifyBlank = true;			// to verify the first spot the user set is a blank 
				
				for (int j = -1; j <= 1; j++)
				{
					for (int k = -1; k <= 1; k++)
					{
						if (xBomb == x + j && yBomb == y + k)
							verifyBlank = false;
					}
				}
				
			} while (keyPlay[xBomb][yBomb] == 'X' || verifyBlank == false);
			
			keyPlay[xBomb][yBomb] = 'X';
		}
		
		// Number(hint) - set
		for (int row = 0; row < 10; row++)
		{
			for (int col = 0; col < 10; col++)
			{
				if (keyPlay[row][col] != 'X')
				{
					int numBomb = 0;
						
					if (row > 0 && col > 0 && keyPlay[row - 1][col - 1] == 'X')
						numBomb++;
					if (row > 0 && keyPlay[row - 1][col] == 'X')
						numBomb++;	
					if (row > 0 && col < 9 && keyPlay[row - 1][col + 1] == 'X')
						numBomb++;
					if (col > 0 && keyPlay[row][col - 1] == 'X')
						numBomb++;
					if (col < 9 && keyPlay[row][col + 1] == 'X')
						numBomb++;
					if (row < 9 && col > 0 && keyPlay[row + 1][col - 1] == 'X')
						numBomb++;
					if (row < 9 && keyPlay[row + 1][col] == 'X')
						numBomb++;
					if (row < 9 && col < 9 && keyPlay[row + 1][col + 1] == 'X')
						numBomb++;
						
					char hint = (char) (numBomb + 48); 		// to make integer to character directly
									
					if (numBomb > 0)
						keyPlay[row][col] = hint;
					
					else 
						keyPlay[row][col] = ' ';
				}
						
			}
		}
	}
	
	/** 
	 * This method makes a keyPlay array be shown in order on the screen.
	 */
	
	public void showKeyPlay()
	{
		System.out.print("  "); 	
		
		for (int i = 0; i < 10; i++)		// Row #
			System.out.printf(" %d", i);
		
		System.out.println();
		
		for (int i = 0; i < 10; i++)		// Column # and Each Square
		{
			System.out.printf("%d |", i);
			
			for (int k = 0; k < 10; k++)
			{	
				System.out.printf("%c|", keyPlay[i][k]);
			}
			
			System.out.println();
		}		
	}
	
	/** 
	 * This method initializes a forPlay array with '-' masks.
	 */

	public void initializeForPlay()
	{
		forPlay = new char[10][10];
		
		for (int row = 0; row < 10; row++)		  // to make masks '-' for each square
			for (int col = 0; col < 10; col++)
				forPlay[row][col] = '-';		
	}
	
	/** 
	 * This method shows a forPlay array in order.
	 */
	
	public void showForPlay()
	{
		System.out.print("  "); 	
		
		for (int i = 0; i < 10; i++)		// Row #
			System.out.printf(" %d", i);
		
		System.out.println();
		
		for (int i = 0; i < 10; i++)		// Column # and Each Square
		{
			System.out.printf("%d |", i);
			
			for (int k = 0; k < 10; k++)
			{	
				System.out.printf("%c|", forPlay[i][k]);
			}
			
			System.out.println();
		}		
	}
	

	/**
	 * This method helps show the result when the user clicks blank ' ' spot.
	 * It makes every blank touched by the spot open, and it also opens the all # hints surrounded by those blanks.
	 * 
	 * @param x The x coordinate of each choice, which is blank, the user made.
	 * @param y The y coordinate of each choice, which is blank, the user made.
	 */	
	
	public void doBlankDfsSearch(int x, int y)			// Got helped from Bro. Matt
	{
		if (x > -1 && y > -1 && x < 10 && y < 10 && forPlay[x][y] == '-')
		{
				if (keyPlay[x][y] == ' ')
				{
					forPlay[x][y] = keyPlay[x][y];
					doBlankDfsSearch(x - 1, y - 1);
					doBlankDfsSearch(x - 1, y + 1);
					doBlankDfsSearch(x - 1, y);
					doBlankDfsSearch(x, y + 1);
					doBlankDfsSearch(x, y - 1);
					doBlankDfsSearch(x + 1, y - 1);
					doBlankDfsSearch(x + 1, y + 1);
					doBlankDfsSearch(x + 1, y);
				}
				
				else if (keyPlay[x][y] != 'X')
					forPlay[x][y] = keyPlay[x][y];
		}		
	}

	/** 
	 * This method shows the result of a player's choice, one by one.
	 * 
	 * @param x The x coordinate of each choice the user made.
	 * @param y The y coordinate of each choice the user made.
	 */
	
	public void showByOne(int x, int y)
	{
		if (forPlay[x][y] != '-')
		{
			System.out.println();
			System.out.println("You alreday had that spot.");
			System.out.println("Try again!");
		}
			
		else if (keyPlay[x][y] == ' ')
		{	
			doBlankDfsSearch(x,y);	// Continuous reveal of the spots around that blank!
			
			clearScreen(50);
			
			showForPlay();		// to be cont'd 
		}		
		
		else if (keyPlay[x][y] == 'X')
		{
			forPlay[x][y] = keyPlay[x][y]; 		// Bomb!!!
			
			clearScreen(50);
			
			showForPlay();	
			
			// The game ends w/ a question for the ask of the key
			
			Scanner console = new Scanner(System.in);
			
			System.out.println();
			System.out.println("YOU FAILED - Do you want the rest to be opened? (y/n)");
			char reply = console.next().charAt(0);
			
			if (reply == 'y')
			{
				clearScreen(50);
				
				showKeyPlay();
				System.out.println("\n      - Game Over - ");
				System.exit(-1);
			}
			else
			{
				System.exit(-1);	// to terminate the program
				System.out.println("\n      - Game Over - ");
			}
		}		
		
		else if (keyPlay[x][y] > 0 + 48 && keyPlay[x][y] < 9 + 48)
		{
			forPlay[x][y] = keyPlay[x][y]; 		// to reveal that hint #
			
			clearScreen(50);
			
			showForPlay();		// to be cont'd
		}		
	}
	
	/** 
	 * This method put a flag into the given spot of the forPlay array.
	 * -> flag = bomb (the player thinks)
	 * 
	 * @param x The x coordinate for the flag spot.
	 * @param y The y coordinate for the flag spot.
	 */
	
	public void doFlag(int x, int y)
	{
		if (forPlay[x][y] == '-')
		{
			forPlay[x][y] = 'p';
			
			clearScreen(50);
			
			showForPlay();		// to be cont'd
		}
		
		else
		{
			System.out.println();
			System.out.println("You alreday done that spot.");
			System.out.println("Try again!");
		}
		
	}
	
	/** 
	 * This method make a decision for the player about if the player wins a game,
	 * when the player finds out all of the bombs.
	 * 
	 * @return The decision if the user finished the game with one's success.
	 */
	
	public boolean winOrNot()
	{
		boolean win = false;
		int verifytTotal = 0;
		
		for (int row = 0; row < 10; row++)
	    {
			for (int col = 0; col < 10; col++)
			{
				if (forPlay[row][col] == keyPlay[row][col] && keyPlay[row][col] != 'X')
					verifytTotal++;
			}
	 	}
		
		if (verifytTotal == 100 - 15)
			win = true;
		
		return win;
	}
	
	/** 
	 * This method sends the player the winner's message!
     * and then, the program ends.
	 */
	
	public void winningMessage()
	{
		System.out.println();
		System.out.println("Congratulations!! You won the game ~ :)");
		System.out.println("\n      - Game Over - ");
		System.exit(-1);
	}
	
	/** 
	 * This method performs like clearing all the contents in the screen.
	 *  - it is used when the next behavior the player takes.
	 *  
	 * @param num The number of rows taking part of the screen.
	 */
	
	public void clearScreen(int num)
	{
		for (int i = 0; i < num; i++)
			System.out.println();
	}
	
}