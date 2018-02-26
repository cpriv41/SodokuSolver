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
	private Writer writer;
	public int boxWidth;
	public int boxHeight;
	public int numCells;
	boolean[] variables;
	boolean[] boxes;
	SodokuBoardPrivitera sodokuBoard;
	// final private static int[] DEFAULT_VALUE = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	public SodokuToSatReducerPrivitera(File inputFile) throws Exception {

		createBoard(inputFile);
		// 729 variables
		variables = new boolean[sodokuBoard.getNumberOfCells() * 9];
		boxes = new boolean[sodokuBoard.getBoardSize()];
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

	public void initialzeBoxStatus() {
		boolean[] boxStatus = new boolean[sodokuBoard.getBoardSize()];
		// Check all boxes on board
		for (int i = 0; i < sodokuBoard.getBoardSize(); i++) {
			// Assume that the box has all required digits
			boxes[i] = true;
			// Check every digit in the box
			for (int j = 0; j < sodokuBoard.getBoxHeight(); j++) {
				for (int k = 0; k < sodokuBoard.getBoxWidth(); k++) {
					// Tick the appropriate indicator for every non-zero value
					if (getBoxValue(i, j, k) != 0)
						boxStatus[getBoxValue(i, j, k)] = true;
				}
			}
			// If any digits are missing, set the box status to false
			for (int x = 0; x < boxStatus.length; x++) {
				if (boxStatus[x] != true)
					boxes[i] = false;
			}
			// reset indicators for next box analysis
			Arrays.fill(boxStatus, false);
		}
		return;
	}

	public int getBoxValue(int boxNum, int row, int column) {

		final int[] box = new int[sodokuBoard.getBoardSize()];
		final int rowLocation = (row - 1) / sodokuBoard.getBoxWidth();
		final int colLocation = (column - 1) / sodokuBoard.getBoxHeight();
		// int counter = 0;
		// REGION_SIZE (3) Rows/Columns from rowBase/ColumnBase
		for (int i = rowLocation; i < sodokuBoard.getBoxWidth() + rowLocation; i++) {
			for (int j = colLocation; j < sodokuBoard.getBoxWidth() + colLocation; j++) {
				box[boxNum] = (boxHeight * (sodokuBoard.getCellRow(i) / boxHeight)
						+ (sodokuBoard.getCellColumn(j) / boxWidth));
			}
		}
		return box[boxNum];
	}

	public void initializeBoxes(int row, int col) {

		return;
	}

private void reduceBoard(SodokuBoardPrivitera board){
	atLeastOneInRow();
	atLeastOneInColumn();
	atLeastOneInBox();
	atMostOneInRow();
	atMostOneInColumn();
	atMostOneInBox();
	return;
}
/**
 * 
 *	Converts it into one number using the i * 9^2 + j * 9 + k
 *	converting a cell from the sudoku board into one variable. 
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

	public boolean atLeastOneInRow(int row, int value) {

		for (int j = 0; j < sodokuBoard.getBoardSize(); j++) {
			if (variables[getVariableIndex(row, j, value)] == true && isValid(value) == true)

				return true;

		}
		return false;

	}

	private boolean isValid(int value) {
		return (value > 0 && value < 10);
	}

	private boolean isValidMove(int row, int value) {
		while (atLeastOneInRow(row, value) == true) {
			if (sodokuBoard.getCellRow(value) != sodokuBoard.getCellColumn(value)
					|| (sodokuBoard.getCellRow(value)) != sodokuBoard.getCellBox(value)
					|| sodokuBoard.getCellColumn(value) != sodokuBoard.getCellBox(value)) {
				return true;
			}
		}
		return false;
	}

	private void atMostOneInRow(int row, int value) {

		for (int i = 0; i < sodokuBoard.getBoardSize(); i++) {
			for (int j = i + 1; j < sodokuBoard.getBoardSize(); j++) {
				if (variables[getVariableIndex(i, j, value)] == true) {
					System.out.println();
				}
			}
		}

	}

	public boolean atLeastOneInColumn(int column, int value) {
		for (int i = 0; i < sodokuBoard.getBoardSize(); i++) {
			if (variables[getVariableIndex(i, column, value)] == true && isValid(value) == true)
				return true;
		}
		return false;

	}

	public boolean atMostOneInColumn(int column, int value) {
		return false;

	}

	public boolean atLeastOneInBox(int box, int value) {
		return false;

	}

	public boolean atMostOneInBox(int box, int value) {
		return false;

	}

	public boolean atLeastAtMostValue(int row, int column) {
		return false;

	}

	private int numberOfVariables() {
		return sodokuBoard.getBoardSize() * sodokuBoard.getBoardSize() * sodokuBoard.getBoardSize();
	}

	private int numberOfClauses() {
		return sodokuBoard.getBoardSize() * sodokuBoard.getBoardSize() * sodokuBoard.getBoardSize()
				* sodokuBoard.getNumberOfCells();
	}

	private void output(String inputFile) throws Exception {
		cnfOut = new File(inputFile + ".cnf");

		try {
			writer = new PrintWriter(cnfOut);
		} catch (Exception e) {
			throw e;
		}
	}

	private void writeToFile(String write) throws Exception {

		try {
			writer.write(write);
			// writer.append(c)
			writer.flush();
		} catch (Exception e) {
			throw e;
		}
	}

}
