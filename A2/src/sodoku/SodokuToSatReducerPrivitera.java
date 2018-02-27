package sodoku;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SodokuToSatReducerPrivitera {

	private File cnfOut;
	private PrintWriter writer;
	private PrintWriter pw;
	public int boxWidth;
	public int boxHeight;
	public int numCells;
	boolean[] variables;
	// boolean[] boxes;
	SodokuBoardPrivitera sodokuBoard;
	// final private static int[] DEFAULT_VALUE = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	public SodokuToSatReducerPrivitera(File inputFile) throws Exception {

		createBoard(inputFile);
		// 729 variables
		variables = new boolean[sodokuBoard.getNumberOfCells() * 9];
		// boxes = new boolean[sodokuBoard.getBoardSize()];
		initializeBoard();

	}

	public void createBoard(File inputFile) throws Exception {

		Scanner board = null;

		try {

			FileInputStream inFile = new FileInputStream(inputFile);
			board = new Scanner(inFile);
			System.out.println("File " + inputFile + " has been openned");

			Pattern pat = Pattern.compile("c");

			while (board.findInLine(pat) != null)
				// skip comments
				// once at 'c' read line
				board.nextLine();

			// get board dimensions
			boxWidth = board.nextInt();
			boxHeight = board.nextInt();

			sodokuBoard = new SodokuBoardPrivitera(boxWidth, boxHeight);

			// process data from input file
			for (int cell = 1; cell <= sodokuBoard.getNumberOfCells() - 1; cell++) {
				sodokuBoard.setCellValue(cell - 1, board.nextInt());

			}
			System.out.println(sodokuBoard.toString());
			// int[] clauses =
			// Math3.binomialCoefficientLog(sodokuBoard.getBoardSize(), 2);

		} catch (Exception e) {
			throw e;
		} finally {
			board.close();
		}

		return;

	}

	public void initializeBoard() {

		for (int i = 0; i < sodokuBoard.getNumberOfCells(); i++) {
			if (sodokuBoard.getCellValue(i) != 0)
				variables[getVariableIndex(sodokuBoard.getCellRow(i), sodokuBoard.getCellColumn(i),
						sodokuBoard.getCellValue(i))] = true;
		}
		return;

	}
	
	static void reduceBoard(){
		reducer();
	}

	public void reducer() {
		try {
			createOutput("output");
		
		printFirstLine();
		for (int i = 0; i < sodokuBoard.getBoardSize(); i++) {
			for (int k = 1; k <= sodokuBoard.getBoardSize(); k++) {
				atLeastOneInRow(i, k);
				atMostOneInRow(i, k);
			}
		}
		for (int j = 0; j < sodokuBoard.getBoardSize(); j++) {
			for (int k = 1; k <= sodokuBoard.getBoardSize(); k++) {
				atLeastOneInColumn(j, k);
				atMostOneInColumn(j, k);
			}
		}
		for (int i = 0; i < sodokuBoard.getBoardSize(); i++) {
			for (int k = 1; k <= sodokuBoard.getBoardSize(); k++) {
				atLeastOneInBox(i, k);
				atMostOneInBox(i, k);
			}
		}
		for (int i = 0; i < sodokuBoard.getBoardSize(); i++) {
			for (int j = 0; j < sodokuBoard.getBoardSize(); j++) {
				atLeastOneInCell(i, j);
				atMostOneInCell(i, j);

			}

		}
		writer.flush();
		writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//
	// public void initialzeBoxStatus() {
	// boolean[] boxStatus = new boolean[sodokuBoard.getBoardSize()];
	// // Check all boxes on board
	// for (int i = 0; i < sodokuBoard.getBoardSize(); i++) {
	// // Assume that the box has all required digits
	// boxes[i] = true;
	// // Check every digit in the box
	// for (int j = 0; j < sodokuBoard.getBoxHeight(); j++) {
	// for (int k = 0; k < sodokuBoard.getBoxWidth(); k++) {
	// // Tick the appropriate indicator for every non-zero value
	// if (getBoxValue(i, j, k) != 0)
	// boxStatus[getBoxValue(i, j, k)] = true;
	// }
	// }
	// // If any digits are missing, set the box status to false
	// for (int x = 0; x < boxStatus.length; x++) {
	// if (boxStatus[x] != true)
	// boxes[i] = false;
	// }
	//
	// // reset indicators for next box analysis
	// Arrays.fill(boxStatus, false);
	// }
	// return;
	// }
	//
	// public int getBoxValue(int boxNum, int row, int column) {
	//
	// int cellIndex = (boxNum/boxWidth * sodokuBoard.getBoardSize() *
	// boxHeight) + (boxNum % boxWidth)*3 + (row * sodokuBoard.getBoardSize()) +
	// column;
	// return sodokuBoard.getCellValue(cellIndex);
	// }
	//

	/**
	 * 
	 * Converts it into one number using the i * 9^2 + j * 9 + k converting a
	 * cell from the sudoku board into one variable.
	 * 
	 * @param row
	 * @param column
	 * @param value
	 * @return
	 */
	public int convert(int row, int column, int value) {

		return (row * sodokuBoard.getBoardSize() * sodokuBoard.getBoardSize() + column * sodokuBoard.getBoardSize()
				+ value);

	}

	private int getVariableIndex(int row, int column, int value) {

		return (value - 1) + (sodokuBoard.getNumberOfCells()) + row * (sodokuBoard.getBoardSize() + column);
	}

	public void atLeastOneInRow(int row, int value) {

		for (int col = 0; col < sodokuBoard.getBoardSize(); col++) {
			//if (variables[getVariableIndex(row, col, value)] == true)
				writer.print(Integer.toString(convert(row, col, value)) + " ");

		}
		writer.println("0");

	}

	private void atMostOneInRow(int row, int value) {

		for (int j = 0; j < sodokuBoard.getBoardSize(); j++) {
			for (int k = j + 1; k < sodokuBoard.getBoardSize(); k++) {
				// if (variables[getVariableIndex(j, k, value)] == true) {
				writer.println(Integer.toString(-1 * convert(row, j, value)) + " "
						+ Integer.toString(-1 * convert(row, k, value)) + " 0");

				// }
			}
		}

	}

	public void atLeastOneInColumn(int column, int value) {
		for (int row = 0; row < sodokuBoard.getBoardSize(); row++) {
			//if (variables[getVariableIndex(row, column, value)] == true)
				writer.print(Integer.toString(convert(row, column, value)) + " ");

		}
		writer.println("0");

	}

	public void atMostOneInColumn(int column, int value) {

		for (int j = 0; j < sodokuBoard.getBoardSize(); j++) {
			for (int k = j + 1; k < sodokuBoard.getBoardSize(); k++) {
				writer.println(Integer.toString(-1 * convert(j, column, value)) + " "
						+ Integer.toString(-1 * convert(k, column, value)) + " 0");

			}
		}
	}

	public void atLeastOneInBox(int box, int value) {

		for (int row = 0; row < sodokuBoard.getBoardSize(); row++) {
			for (int column = row + 1; column < sodokuBoard.getBoardSize(); column++) {
				if (sodokuBoard.getCellBox(value) != box)
					continue;
				//if (variables[getVariableIndex(row, column, value)] == true)
					writer.print(Integer.toString(convert(row, column, value)) + " ");

			}
		}
		writer.println("0");

	}

	public void atMostOneInBox(int box, int value) {

		for (int row = 0; row < sodokuBoard.getBoardSize(); row++) {
			for (int column = row + 1; column < sodokuBoard.getBoardSize(); column++) {
				if (sodokuBoard.getCellBox(value) != box)
					continue;

				writer.println(Integer.toString(-1 * convert(row, column, value)) + " "
						+ Integer.toString(-1 * convert(row, column, value)) + " 0");

			}
		}

	}

	public void atLeastOneInCell(int row, int column) {

		for (int k = 1; k < sodokuBoard.getBoardSize(); k++) {
			// if (variables[getVariableIndex(row, column, k)] == true)
			writer.print(Integer.toString(convert(row, column, k)) + " ");

		}
		writer.println("0");

	}

	private void atMostOneInCell(int row, int column) {

		for (int x = 0; x < sodokuBoard.getBoardSize(); x++) {
			for (int y = x + 1; y < sodokuBoard.getBoardSize(); y++)
				writer.println(Integer.toString(-1 * convert(row, column, x)) + " "
						+ Integer.toString(-1 * convert(row, column, y)) + " 0");

		}

	}

	private int numberOfVariables() {
		return sodokuBoard.getBoardSize() * sodokuBoard.getBoardSize() * sodokuBoard.getBoardSize();
	}

	private int numberOfClauses() {
		return sodokuBoard.getBoardSize() * sodokuBoard.getBoardSize() * sodokuBoard.getBoardSize()
				* sodokuBoard.getNumberOfCells();
	}

	private void printFirstLine() {
		writer.println("p cnf " + numberOfVariables() + " " + numberOfClauses());
	}

	private void createOutput(String outFile) throws Exception {
		cnfOut = new File(outFile + ".cnf");

		try {
			writer = new PrintWriter(cnfOut);
		} catch (Exception e) {
			throw e;
		
		}
	}

}