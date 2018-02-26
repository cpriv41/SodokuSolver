package sodoku;

/**
 * 
 * @author Caleb Privitera Class 406.02 Assignment 2 Resubmit 02/23/2018
 * 
 */

public class SodokuBoardPrivitera {

	// class specific instance variables
	private int boxWidth;
	private int boxHeight;
	private int numCells;
	private int[] boardCells;

	/**
	 * Constructor to take input parameters which is used to create a sudoku
	 * board as a linear array, called boardCells
	 * 
	 * @param boxWidth
	 * @param boxHeight
	 */

	public SodokuBoardPrivitera(int boxWidth, int boxHeight) {

		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		this.numCells = (boxWidth * boxWidth) * (boxHeight * boxHeight);
		boardCells = new int[numCells];
	}

	/**
	 * row to which the cell belongs in the 2D form of the Sudoku board
	 * 
	 * @param cell
	 */
	public int getCellRow(int cell) {
		int row = (cell / (boxWidth * boxHeight));
		return row;
	}

	/**
	 * column to which the cell belongs in the 2D form of the Sudoku board
	 * 
	 * @param cell
	 */
	public int getCellColumn(int cell) {
		int column = (cell % (boxWidth * boxHeight));
		return column;
	}

	/**
	 * value in the cell
	 * 
	 * @param cell
	 */
	public int getCellValue(int cell) {
		int value = boardCells[cell];
		return value;
	}

	/**
	 * box in which the cell lies in the 2D form of the Sudoku board
	 * 
	 * @param cell
	 */
	public int getCellBox(int cell) {
		int box = (boxHeight * (getCellRow(cell) / boxHeight) + (getCellColumn(cell) / boxWidth));
		return box;
	}

	/**
	 * Prints a representation of board on stdout
	 */
	public String toString() {

		String board = "";

		for (int i = 0; i < numCells; i++) {
			// if value in box is not 0, add it
			if (((i) % (boxWidth * boxHeight)) != 0) {
				board += (boardCells[i]) + " ";
			} else {
				board += " \n" + boardCells[i] + " ";
			}
		}
		return board;
	}

	/**
	 * set the value of a given cell in boardCells to a given integer
	 * 
	 * @param cell
	 * @param value
	 */
	public void setCellValue(int cell, int value) {
		boardCells[cell] = value;
	}

	/**
	 * getters for: width, height, board size, number of Cells, box sizeF
	 * 
	 */

	public int getBoxWidth() {
		return boxWidth;
	}

	public int getBoxHeight() {
		return boxHeight;
	}

	public int getBoardSize() {
		return boxWidth * boxHeight;
	}

	public int getNumberOfCells() {
		return numCells;
	}

}
