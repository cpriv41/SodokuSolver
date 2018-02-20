package sodoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.math.*;

public class SodokuBoardPrivitera {

	// class specific instance variables
	private int boxWidth;
	private int boxHeight;
	private int boardSize;
	private int boxSize;
	private int numCells;
	// instance variable used outside class
	public int[][] boardCells;

	public SodokuBoardPrivitera(String inputFile) throws FileNotFoundException {

		try {
			// process input file
			File input = new File(inputFile);
			FileReader fileReader = new FileReader(input);
			Scanner board = new Scanner(fileReader);

			Pattern pat = Pattern.compile("c");

			while (board.findInLine(pat) != null) {
				// once at 'c' read line
				board.nextLine();

				// get board dimensions
				boxWidth = board.nextInt();
				boxHeight = board.nextInt();

				// set box size based on dimensions, multiply by 3 to get board
				// size
				setBoxSize(boxWidth * boxHeight);
				boardSize = getBoxSize() * 3;

				// number of cells based on dimensions
				numCells = (int) Math.pow(boardSize, 2);

				// Create array for cells
				boardCells = new int[boxWidth][boxHeight];

				// process data from input file
				for (int row = 0; row < boardSize; row++) {
					for (int column = 0; column < boardSize; column++) {
						boardCells[row][column] = board.nextInt();
					}
				}
			}
		} catch (FileNotFoundException ex) {
			// if no file is found, print error
			System.out.println("File not found.");
			throw (ex);
		}

	}

	/**
	 * row to which the cell belongs in the 2D form of the Sudoku board
	 * @param cell
	 */
	public int getCellRow(int cell) {

		int row = (cell - getCellColumn(cell)) / boardSize;
		return row;
	}

	/**
	 * column to which the cell belongs in the 2D form of the Sudoku board
	 * @param cell
	 */

	public int getCellColumn(int cell) {
		int column = Math.floorMod(cell, boardSize);
		return column;
	}

	/**
	 * value in the cell
	 * @param cell
	 */
	public int getCellValue(int cell) {
		int value = boardCells[getCellRow(cell)][getCellColumn(cell)];
		return value;

	}

	/**
	 * box in which the cell lies in the 2D form of the Sudoku board
	 * @param cell
	 */

	public int getCellBox(int cell) {
		double box = (Math.floor(getCellRow(cell) / boxWidth) + 3 * Math.floor(getCellColumn(cell) / boxWidth));
		return (int) box;

	}

	/**
	 * Prints a representation of board on stdout
	 */
	public void toString() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++)
				System.out.print(boardCells[i][j] + "  ");
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * set the value of a given cell in boardCells to a given integer
	 * @param cell
	 * @param setValue
	 */
	public void setVals(int cell, int setValue) {
		setValue = boardCells[getCellRow(cell)][getCellColumn(cell)];

	}

	/**
	 * getters for: width, height, board size, number of Cells, box size
	 * 
	 */

	public int getWidth() {
		return boxWidth;
	}

	public int getHeight() {
		return boxHeight;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public int getNumCells() {
		return numCells;

	}

	public int getBoxSize() {
		return boxSize;
	}

	/**
	 * 	setter for box size
	 * 	used to get board size
	 * @param boxSize
	 */
	public void setBoxSize(int boxSize) {
		this.boxSize = boxSize;
	}

}
