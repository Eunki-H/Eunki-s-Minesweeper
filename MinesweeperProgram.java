import java.util.*;

public class MinesweeperProgram 
{
	public static void main(String[] args) 
	{
		// Let's just play a game!
	
		
		// (0) Ask of a game difficulty
		Scanner console = new Scanner(System.in);
		
		System.out.println(" <Eunki's Minesweeper Game>");
		System.out.println();
		System.out.println("\t Easy - e");
		System.out.println("\t Medium - m");
		System.out.println("\t Hard - h");
		System.out.println();
		System.out.println(" Enter a game mode you want");
		System.out.print(" >> ");
		char mode = console.next().charAt(0);
		
		while (mode != 'e' && mode != 'm' && mode != 'h') // input validation
		{
			System.out.println();
			System.out.println("Error - The mode you type should be \'e\', \'m\', or \'h\'");
			System.out.print("Try again >> ");
			mode = console.next().charAt(0);
		}
		
		clearScreen(50);
		
		// (1) Show the first figure of a game to the player
		char[][] forPlay = new char[10][10]; 		// just for this time
		
		initializeForPlay(forPlay);
		showForPlay(forPlay);		
		
		// (2) The 1st. User Input
		int Xi, Yi;
		
		System.out.println();
		System.out.println("Enter a (x, y) location.");
		
		System.out.print("x >> ");
		Xi = console.nextInt();
		
		System.out.print("y >> ");
		Yi = console.nextInt();
		
		// User Input Validation
		while (Xi < 0 || Xi > 9 || Yi < 0 || Yi > 9)
		{
			// Error Message
			System.out.println();
			System.out.println("Error -- The x, y inputs should be 0 ~ 9.");
			System.out.println("Try again!");
			
			System.out.print("x >> ");
			Xi = console.nextInt();
			
			System.out.print("y >> ");
			Yi = console.nextInt();
		}
		
		System.out.println();
		
		// (3) Make the key of the game & Make a game Object for the player
		Minesweeper play1 = new Minesweeper(Xi, Yi, mode);		
		
		play1.clearScreen(50);
		
		play1.showByOne(Xi, Yi);
		// play1.showKeyPlay();
		
		// (4) The Game mode!         - w/ the user -
		while (play1.winOrNot() == false)
		{
			System.out.println();
			System.out.println("Flag or Open? (\"p\" for Flag)");
			char flagOrOpen = console.next().charAt(0);
			
			if (flagOrOpen == 'p')		// Flag
			{
				int Xp, Yp;
				
				System.out.println();
				System.out.println("Enter P(x, y) location.");
				
				System.out.print("x >> ");
				Xp = console.nextInt();
				
				System.out.print("y >> ");
				Yp = console.nextInt();
				
				// User Input Validation
				while (Xp < 0 || Xp > 9 || Yp < 0 || Yp > 9)
				{
					// Error Message
					System.out.println();
					System.out.println("Error -- The x, y inputs should be 0 ~ 9.");
					System.out.println("Try again!");
					
					System.out.print("x >> ");
					Xp = console.nextInt();
					
					System.out.print("y >> ");
					Yp = console.nextInt();
				}
				
				System.out.println();
				
				play1.doFlag(Xp, Yp);
			}
				
			else						// Open
			{
				int x, y;
			
				System.out.println();
				System.out.println("Enter a (x, y) location.");
				
				System.out.print("x >> ");
				x = console.nextInt();
				
				System.out.print("y >> ");
				y = console.nextInt();
				
				// User Input Validation
				while (x < 0 || x > 9 || y < 0 || y > 9)
				{
					// Error Message
					System.out.println();
					System.out.println("Error -- The x, y inputs should be 0 ~ 9.");
					System.out.println("Try again!");
					
					System.out.print("x >> ");
					x = console.nextInt();
					
					System.out.print("y >> ");
					y = console.nextInt();
				}
				
				System.out.println();
				
				play1.showByOne(x, y);
			}
			
		}
		
		// Winner's Message
		play1.winningMessage();
		
	}

	public static void clearScreen(int num)
	{
		for (int i = 0; i < num; i++)
			System.out.println();
	}
	
	public static void initializeForPlay(char[][] forPlay)
	{		
		for (int row = 0; row < 10; row++)		  
			for (int col = 0; col < 10; col++)
				forPlay[row][col] = '-';		
	}
	
	public static void showForPlay(char[][] forPlay)
	{
		System.out.print("  "); 	
		
		for (int i = 0; i < 10; i++)		
			System.out.printf(" %d", i);
		
		System.out.println();
		
		for (int i = 0; i < 10; i++)		
		{
			System.out.printf("%d |", i);
			
			for (int k = 0; k < 10; k++)
			{	
				System.out.printf("%c|", forPlay[i][k]);
			}
			
			System.out.println();
		}		
	}
	
}
