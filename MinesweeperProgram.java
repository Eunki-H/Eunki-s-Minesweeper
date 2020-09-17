import java.util.*;

public class MinesweeperProgram 
{
	public static void main(String[] args) 
	{
		// Let's just play a game!
		
		Minesweeper play1 = new Minesweeper();		// A key set is already made for play1
		
		play1.initializeForPlay();
		play1.showForPlay();
		
		Scanner console = new Scanner(System.in);
		
		play1.showKeyPlay();
		
		// User Input
		
		int x, y;
		
		System.out.println();
		System.out.println("Enter a (x, y) location.");
		
		System.out.print("x >> ");
		x = console.nextInt();
		
		// input validation, x
		
		System.out.print("y >> ");
		y = console.nextInt();
		
		// input validation, y
		
		// Link 
			
		play1.showByOne(x, y);
		
		
	}

}
