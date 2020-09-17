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
	 * Full constructor...
	 */
	
	/**
	 * Default constructor, setting the size 10x10 of keyPlay and forPlay array
	 * and then, creating a key set.
	 */
	
	public Minesweeper()
	{
		this.keyPlay = new char[10][10];
		this.makeKeyPlay();
		
		this.forPlay = new char[10][10];
	}

	
	/********************* Methods **********************/
	
	/** 
	 * This method makes a keyPlay array be filled with a random key set.
	 */
	
	public void makeKeyPlay()
	{				
		Random r = new Random();
				
		// Bomb - set
		for (int i = 0; i < 15; i ++)
		{
			int xBomb, yBomb;
			
			do	// to spread entire 15 bombs evenly
			{
				xBomb = r.nextInt(10);
				yBomb = r.nextInt(10);
				
			} while (keyPlay[xBomb][yBomb] == 'X');
			
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
	 * This method analyzes the coordinates with a specific coordinate surrounded by them,
	 * and then, open those coordinates of forPlay under the specific conditions.
	 * 
	 * @param x		    The x value of the starting position of the keyPlay array
	 * @param y		    The y value of the starting position of the keyPlay array
	 */

	public void analyzeSurrounding1(int x, int y)
	{
		for (int i = -1; i <= 1; i++)		// (1) Nested for - loops to apply if - statements to the surrounding of a point
		{
			for (int k = -1; k <= 1; k++) 	// (2)
			{
				if (i != 0 || k != 0) 	    // (3) , except for the point itself
				{
					if (keyPlay[x + i][y + k] != ' ') 	// this case means, hint #s!
						forPlay[x + i][y + k] = keyPlay[x + i][y + k];
				}
			}
		}
	}
	
	/** 
	 * This second method analyzes the coordinates with a specific coordinate surrounded by them,
	 * and then, save all important information to the hybrid array.
	 * 
	 * @param x		    The x value of the starting position of the keyPlay array
	 * @param y		    The y value of the starting position of the keyPlay array
	 * @return The indicators of the number of blanks with x, y coordinates
	 */

	public int[][][] analyzeSurrounding2(int x, int y)
	{
		int[][][] hybrid = new int[2][9][2];	// x, y coordinates - number of blanks - mode 0/1 (general/num_blanks)
		int numBlanks = 0;
		
		for (int i = -1; i <= 1; i++)		
		{
			for (int k = -1; k <= 1; k++) 	
			{
				if (i != 0 || k != 0) 	  
				{
					if (keyPlay[x + i][y + k] == ' ') 
					{	
						hybrid[0][numBlanks][0]= x + i;		// to save x coordinates
						hybrid[1][numBlanks][0]= y + k;		// to save y coordinates
						numBlanks++;
					}
				}
				
				else	// *** to save one's own
				{
					hybrid[0][8][0] = x;
					hybrid[1][8][0] = y;
				}
			}
		}
		
		hybrid[1][1][1]= numBlanks;			// to save the number of blanks information
		
		return hybrid;
	}
	
	public int[][][] doBlanks(int x, int y)
	{
		int[][][] indicators = new int[2][9][2];
		
		forPlay[x][y] = keyPlay[x][y]; 	
		analyzeSurrounding1(x, y);	
		indicators = analyzeSurrounding2(x, y); 		 	
	
			/*
			for (int i = 0; i < indicators[1][1][1]; i++)
			{
				int newX = indicators[0][i][0], newY = indicators[1][i][0];		
						
				if (forPlay[newX][newY] == '-')
				{	
					forPlay[newX][newY] = keyPlay[newX][newY]; 			// (1)
					analyzeSurrounding1(newX, newY);		     	    // (2)
					
				}
			}
			*/
		
		return indicators;
	}
	/*
		analyzeSurrounding1(x, y);
		int[][][] indicators = analyzeSurrounding2(x, y);
		int numBlanks = indicators[1][1][1];
		
		boolean forWhile = false;
		
		if (numBlanks == 0) 	// (1) to terminate when the number of blanks is 0
			forWhile = true;
		
		else
		{
			int preX = -1, preY = -1;	// impossible #s, -1
			
			do
			{	
				if (indicators[0][9][0] == preX && indicators[1][9][0] == preY)     // (2) to terminate when all blanks are opened
					forWhile = true;
				
				else	
				{	
					numBlanks = indicators[1][1][1];
				
					for(int i = 0; i < numBlanks; i++)
						analyzeSurrounding1(indicators[0][i][0], indicators[1][i][0]);
					
					preX = indicators[0][9][0];
					preY = indicators[1][9][0];
					
					// new 
					if (numBlanks - 1 == )
					indicators = analyzeSurrounding2(indicators[0][numBlanks - 1][0], indicators[1][numBlanks - 1][0]);
				}
				
			} while (forWhile == false);
		} 
	*/
	
	/** 
	 * This method shows the result of a player's choice, one by one.
	 * 
	 * @param x The x coordinate of each choice the user made.
	 * @param y The y coordinate of each choice the user made.
	 */
	
	public void showByOne(int x, int y)
	{
		if (keyPlay[x][y] == ' ')
		{
			int[][][] reference = doBlanks(x, y);
			
			for (int i = 0; i < reference[1][1][1]; i++)
			{
				int newX = reference[0][i][0], newY = reference[1][i][0];
				
				if (forPlay[newX][newY] == '-')
				{
					int[][][] reference2 = doBlanks(newX, newY);
				}
			}
						
			
			/*	
			forPlay[x][y] = keyPlay[x][y]; 		// (1) to reveal that blank
			analyzeSurrounding1(x, y);			// (2) to open only the hint #s around the given position 
			int[][][] indicators = analyzeSurrounding2(x, y); 		// (3) to get information of blanks around it
			
			int i = 0;
			
			for (int k = 0; k < indicators[1][1][1]; k++)
			{
				int newX = indicators[0][i][0], newY = indicators[1][i][0];		
					
				if (forPlay[newX][newY] == '-')
				{	
					forPlay[newX][newY] = keyPlay[newX][newY]; 			// (1)
					analyzeSurrounding1(newX, newY);					// (2)
					int[][][] dup = analyzeSurrounding2(newX, newY);	// (3)
					
					for (int q = 0; q < 2; q++)
					{
						for (int w = 0; w < 9; w++)
						{
							for (int e = 0; e < 2; e++)
								indicators[q][w][e] = dup[q][w][e];
						}
					}
					
					k = 0;
					i = 0;
				}
				
				else i++;
			}
			*/
		
			showForPlay();		// to be cont'd with forPlay array
		}		
		
		else if (keyPlay[x][y] == 'X')
		{
			forPlay[x][y] = keyPlay[x][y]; 		// Bomb!!
			
			showForPlay();		
			
			// The game ends w/ a question for the ask of the key
			
			Scanner console = new Scanner(System.in);
			
			System.out.println();
			System.out.println("YOU FAILED - Do you want the rest to be opened? (y/n)");
			String reply = console.next();
			
			if (reply == "y")
			{
				showKeyPlay();
				System.exit(-1);
			}
			else
				System.exit(-1);	// to terminate the program
		}		
		
		else
		{
			forPlay[x][y] = keyPlay[x][y]; 		// to reveal that hint #
			
			showForPlay();		
			
		}		
	}
	

	
	
	
	
	
	
	
	
	
}