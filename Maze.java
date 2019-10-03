import java.util.Scanner;
import java.util.Stack;

/**
 * 
 * @author Andrew Pressman
 * @author Tyler Ramdass
 * 
 * @source Andrew Pressman in 2015
 */

public class Maze {

	public static int StartX;	//Variable used to store the X coordinate of the start point
	public static int StartY;	//Variable used to store the Y coordinate of the start point
	public static Stack<Character> BestRoute = new Stack<Character>();	//stack to track more efficent route the algorithm finds

	public static char[][] maze = {			//The maze itself
			{'#','#','#','#','#','#','#','#','#','#','#','#'},
			{'#','.','.','.','#','.','.','.','.','.','.','#'},
			{'.','.','#','.','#','.','#','#','#','#','.','#'},
			{'#','#','#','.','#','.','.','.','.','#','.','#'},
			{'#','.','.','.','.','#','#','#','.','#','.','.'},
			{'#','#','#','#','.','#','.','#','.','#','.','#'},
			{'#','.','.','#','.','#','.','#','.','#','.','#'},
			{'#','#','.','#','.','#','.','#','.','#','.','#'},
			{'#','.','.','.','.','.','.','.','.','#','.','#'},
			{'#','#','#','#','#','#','.','#','#','#','.','#'},
			{'#','.','.','.','.','.','.','#','.','.','.','#'},
			{'#','#','#','#','#','#','#','#','#','#','#','#'}};


	/**
	 * Method located the start position for the maze, indicated by
	 * a '.' character on the left edge of the maze
	 * @return true if start point is found
	 */
	public static boolean FindStart()
	{

		for(int i = 0; i < maze.length; i++)
		{
			if(maze[i][0] == '.')
			{
				maze[i][0] = 'x';
				StartX = 0;
				StartY = i;
				return true;
			}
		}
		System.out.print("The start could not be found");
		return false;
	}

	//postcondition- if passed in value is less than the length of the maze, return true, else return false
	/**
	 * 
	 * @param index
	 * 
	 * @return true if the passed location(index) is within the bounds of the maze
	 */
	public static boolean IsValid( int index )
	{
		return (index >= 0) && (index < maze.length);
	}



	//precondition- maze exists startX and StartY are set
	//postcondition- maze is solved
	/**
	 * 
	 * @param arrayX - current x coordinate
	 * @param arrayY - current y coordinate
	 * @param direction - current direction of solution
	 * 
	 * checks if location has reached endpoint of maze marked by a '.' on the right end of the maze
	 * algorithm is designed to always keep a wall to its right, and stick to that route
	 * For each incoming direction, maze will check all 4 directions, prioritizing the wall it is following
	 * once a direction is chosen, that direction is added to the solving stack
	 * then swap() is called to either add to the trail, or remove from the trail if a trail already exists at the current location
	 * 
	 * @return next spot in array and direction until the end of the maze is reached
	 * @throws InterruptedException
	 */
	public static boolean SolveMaze(int arrayX, int arrayY, char direction) throws InterruptedException
	{
		if(IsValid( arrayX )){
			if(IsValid( arrayY )){
				DisplayMaze();
				Thread.sleep(200);
				if(arrayX == maze.length-1 && maze[arrayY][arrayX] != '#')
				{
					System.out.println("You have found the EXIT!!!");
					return true;
				}
				if(maze[arrayY][arrayX] != '#'){	
					if( !(arrayX == StartX && arrayY == StartY  && direction != 'w') )
						//						coming from east				
						if(direction == 'e')
						{
							if(IsValid( arrayY-1) && (maze[arrayY - 1][arrayX] == '.' || maze[arrayY - 1][arrayX] == '0'))			
							{
								swap(maze, arrayX , arrayY, arrayX, arrayY-1);
								BestRoute.push('s');
								return SolveMaze(arrayX,arrayY-1,'s');
							}

							if(IsValid(arrayX-1) &&  (maze[arrayY][arrayX - 1] == '.' || maze[arrayY][arrayX - 1] == '0'))
							{
								swap(maze, arrayX , arrayY, arrayX-1, arrayY);
								BestRoute.push('e');
								return SolveMaze(arrayX-1,arrayY,'e');
							}
							if(IsValid(arrayY+1) && (maze[arrayY + 1][arrayX] == '.' || maze[arrayY + 1][arrayX] == '0'))
							{
								swap(maze, arrayX , arrayY, arrayX, arrayY+1);
								BestRoute.push('n');
								return SolveMaze(arrayX,arrayY+1,'n');
							}
							if(IsValid(arrayX+1) && (maze[arrayY][arrayX + 1] == '.' || maze[arrayY][arrayX + 1] == '0'))
							{
								swap(maze, arrayX , arrayY, arrayX+1, arrayY);
								BestRoute.push('w');
								return SolveMaze(arrayX+1,arrayY,'w');
							}				
						}

					//					coming from west
					if(direction == 'w')
					{
						if(IsValid(arrayY+1) && (maze[arrayY+1][arrayX] == '.' || maze[arrayY+1][arrayX] == '0'))
						{
							swap(maze, arrayX , arrayY, arrayX, arrayY+1);
							BestRoute.push('n');
							return SolveMaze(arrayX,arrayY+1,'n');
						}
						if(IsValid( arrayX+1) && (maze[arrayY][arrayX + 1] == '.' || maze[arrayY][arrayX + 1] == '0'))
						{
							swap(maze, arrayX , arrayY, arrayX+1, arrayY);
							BestRoute.push('w');
							return SolveMaze(arrayX+1,arrayY,'w');
						}				
						if(IsValid( arrayY-1) && (maze[arrayY - 1][arrayX] == '.' || maze[arrayY - 1][arrayX] == '0'))			
						{
							swap(maze, arrayX , arrayY, arrayX, arrayY-1);
							BestRoute.push('s');
							return SolveMaze(arrayX,arrayY-1,'s');
						}

						if(IsValid(arrayX-1) &&   (maze[arrayY][arrayX - 1] == '.' || maze[arrayY][arrayX - 1] == '0'))
						{
							swap(maze, arrayX , arrayY, arrayX-1, arrayY);
							BestRoute.push('e');
							return SolveMaze(arrayX-1,arrayY,'e');
						}		
					}
					//					coming from South
					if(direction == 's')
					{
						if(IsValid( arrayX+1) && (maze[arrayY][arrayX + 1] == '.' || maze[arrayY][arrayX + 1] == '0'))
						{
							swap(maze, arrayX , arrayY, arrayX+1, arrayY);
							BestRoute.push('w');
							return SolveMaze(arrayX+1,arrayY,'w');
						}
						if(IsValid( arrayY-1) && (maze[arrayY - 1][arrayX] == '.' || maze[arrayY - 1][arrayX] == '0'))			
						{
							swap(maze, arrayX , arrayY, arrayX, arrayY-1);
							BestRoute.push('s');
							return SolveMaze(arrayX,arrayY-1,'s');
						}
						if(IsValid(arrayX-1) &&   (maze[arrayY][arrayX - 1] == '.' || maze[arrayY][arrayX - 1] == '0'))
						{
							swap(maze, arrayX , arrayY, arrayX-1, arrayY);
							BestRoute.push('e');
							return SolveMaze(arrayX-1,arrayY,'e');
						}

						if(IsValid(arrayY+1) && (maze[arrayY + 1][arrayX] == '.' || maze[arrayY + 1][arrayX] == '0'))
						{
							swap(maze, arrayX , arrayY, arrayX, arrayY+1);
							BestRoute.push('n');
							return SolveMaze(arrayX,arrayY+1,'n');
						}
					}
				}

				//				coming from North				
				if(direction == 'n')
				{
					if(IsValid(arrayX-1) &&   (maze[arrayY][arrayX - 1] == '.' || maze[arrayY][arrayX - 1] == '0'))
					{
						swap(maze, arrayX , arrayY, arrayX-1, arrayY);
						BestRoute.push('e');
						return SolveMaze(arrayX-1,arrayY,'e');
					}
					if(IsValid(arrayY+1) && (maze[arrayY + 1][arrayX] == '.' || maze[arrayY + 1][arrayX] == '0'))
					{
						swap(maze, arrayX , arrayY, arrayX, arrayY+1);
						BestRoute.push('n');
						return SolveMaze(arrayX,arrayY+1,'n');
					}
					if(IsValid(arrayX+1) && (maze[arrayY][arrayX + 1] == '.' || maze[arrayY][arrayX + 1] == '0'))
					{
						swap(maze, arrayX , arrayY, arrayX+1, arrayY);
						BestRoute.push('w');
						return SolveMaze(arrayX+1,arrayY,'w');
					}			
					if(IsValid( arrayY-1) && (maze[arrayY - 1][arrayX] == '.' || maze[arrayY - 1][arrayX] == '0'))			
					{
						swap(maze, arrayX , arrayY, arrayX, arrayY-1);
						BestRoute.push('s');
						return SolveMaze(arrayX,arrayY-1,'s');
					}
				}
			}
		}	
		return false;
	}

	/**
	 * 
	 * @param maze
	 * @param col1
	 * @param row1
	 * @param col2
	 * @param row2
	 * 
	 * accepts a maze, and the coordinated of the two points to edit
	 * if point2(row2,col2) is marked as a '.', a '0' is placed onto the maze to denote the path of the solver
	 * if point2 is marked as a '0' then point2 has already been encountered by the maze, the direction is then removed from the stack and the point is converted back to a '.'
	 * 
	 */
	static void swap(char[][] maze, int col1, int row1, int col2, int row2){
		char temp = maze[row1][col1];
		//char temp2 = maze[row2][col2];
		if(maze[row2][col2] == '0')
		{
			maze[row1][col1] = '.';
			BestRoute.pop();
		}
		else
		{
			maze[row1][col1] = '0';
		}

		maze[row2][col2] = temp;


	}


	/**
	 * prints current maze
	 */
	public static void DisplayMaze()
	{
		for(int i = 0;i<maze.length;i++)
			System.out.println(maze[i]);
		System.out.println();
	}

	/**
	 * Prompts the use to choose 1 of 4 preset mazes
	 * Note: maze #1 is preset as the default maze and not initalized in this method
	 */
	public static void ChooseMaze()
	{
		char[][] maze2 = {		
				{'#','#','#','#','#','#','#','#','#','#','#','#'},
				{'#','.','.','.','.','.','#','.','.','.','.','#'},
				{'#','#','#','#','.','#','.','#','.','#','.','#'},
				{'#','.','#','#','.','#','.','#','.','#','.','#'},
				{'#','.','#','#','#','#','.','.','.','#','.','#'},
				{'#','.','.','.','.','#','.','#','.','#','.','#'},
				{'#','#','.','#','.','.','.','#','.','#','.','#'},
				{'#','#','.','#','.','#','#','#','.','#','.','.'},
				{'#','#','.','#','.','#','.','.','.','#','.','#'},
				{'#','#','.','#','.','#','.','#','#','#','.','#'},
				{'.','.','.','#','.','#','.','.','.','.','.','#'},
				{'#','#','#','#','#','#','#','#','#','#','#','#'}};


		char[][] maze3 = {	
				{'#','#','#','#','#','#','#','#','#','#','#','#'},
				{'.','.','#','.','.','.','#','#','.','.','.','.'},
				{'#','.','#','.','#','.','#','#','#','.','#','#'},
				{'#','.','.','.','#','.','#','.','#','.','#','#'},
				{'#','#','#','#','#','.','#','.','#','.','#','#'},
				{'#','#','#','#','#','.','.','.','#','.','#','#'},
				{'#','#','.','#','#','.','#','.','#','.','#','#'},
				{'#','#','.','.','.','.','#','.','#','.','.','#'},
				{'#','#','.','#','#','#','#','.','#','.','#','#'},
				{'#','#','.','#','#','#','#','.','.','.','.','#'},
				{'#','#','.','.','#','#','#','#','#','#','.','#'},
				{'#','#','#','#','#','#','#','#','#','#','#','#'}};

		char[][] maze4 = {	
				{'#','#','#','#','#','#','#','#','#','#','#','#'},
				{'#','#','.','.','.','#','.','.','.','.','.','#'},
				{'#','.','.','#','#','#','#','#','.','#','.','#'},
				{'#','#','.','#','#','#','.','#','.','#','.','#'},
				{'#','#','.','.','.','#','.','#','.','#','.','#'},
				{'#','.','.','#','.','#','.','#','.','#','.','.'},
				{'#','#','.','#','.','.','.','#','.','#','.','#'},
				{'#','#','.','#','#','.','#','#','.','#','.','#'},
				{'#','#','.','#','#','.','#','#','.','#','.','#'},
				{'#','.','.','#','#','.','#','#','.','#','.','#'},
				{'.','.','#','#','#','.','.','.','.','#','.','#'},
				{'#','#','#','#','#','#','#','#','#','#','#','#'}};

		Scanner SC = new Scanner(System.in);
		System.out.println("Choose a maze by entering a number 1 to 4");
		boolean cont = true;
		int selection = 0;
		while(cont)
		{
			selection = 0;
			try
			{
				selection = Integer.parseInt(SC.nextLine());
				switch(selection)
				{
				case 1:
					cont = false;
					break;
				case 2:
					maze = maze2;
					cont = false;
					break;
				case 3:
					maze = maze3;
					cont = false;
					break;
				case 4:
					maze = maze4;
					cont = false;
					break;
				default:
					System.out.println("Invalid input, try again");
					break;
				}
			}
			catch(Exception E)
			{
				System.out.println("Invalid input, try again");
			}
		}
		System.out.println("You have chosen: Maze #" + selection);
	}

	/**
	 * 
	 * @param args
	 * @throws InterruptedException
	 * 
	 * Main method
	 * user is prompted to choose from 1 of 3 different mazes to solve
	 * then finds and sets the values for the start point of the maze
	 * Maze is then solved, using W as the starting direction
	 * then prints the best possible route
	 * 
	 */
	public static void main(String [] args) throws InterruptedException
	{
		ChooseMaze();	
		DisplayMaze();	
		FindStart();
		SolveMaze(StartX, StartY, 'w');
		System.out.println(BestRoute);


	}
}