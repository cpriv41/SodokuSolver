package sodoku;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SodokuToSatReducerPrivitera {

	private File cnfOut;
	private PrintWriter writer;
	public int boxWidth;
	public int boxHeight;
	public int numCells;
	boolean[] variables;
	LinkedList<String> clauses = new LinkedList<>();
	// boolean[] boxes;
	SodokuBoardPrivitera sodokuBoard;
	int rowcount, mrowcount;
	int colcount, mcolcount;
	int boxcount, mboxcount;
	int cellcount, mcellcount;
	// final private static int[] DEFAULT_VALUE = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	public SodokuToSatReducerPrivitera(File inputFile) throws Exception {

		createBoard(inputFile);
		// 729 variables
		variables = new boolean[sodokuBoard.getNumberOfCells() * sodokuBoard.getBoardSize()];
		// boxes = new boolean[sodokuBoard.getBoardSize()];
		initializeBoard();
		reduceBoard();

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
				board.nextLine();

			// get board dimensions
			boxWidth = board.nextInt();
			boxHeight = board.nextInt();

			sodokuBoard = new SodokuBoardPrivitera(boxWidth, boxHeight);

			// process data from input file
			for (int cell = 1; cell <= sodokuBoard.getNumberOfCells(); cell++) {
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

	private int getVariableIndex(int row, int column, int value) {

		return (value - 1) * sodokuBoard.getNumberOfCells() + 
				row * sodokuBoard.getBoardSize() + column;
	}
	
	public void initializeBoard() {
		for (int i = 0; i < sodokuBoard.getNumberOfCells(); i++) {
			if (sodokuBoard.getCellValue(i) != 0) {
				variables[getVariableIndex(sodokuBoard.getCellRow(i), sodokuBoard.getCellColumn(i),
						sodokuBoard.getCellValue(i))] = true;			}
		}
		return;

	}

	public void reduceBoard() {
		TimerPrivitera timer = new TimerPrivitera();

		System.out.println("Processing input file: ");
		timer.start();
		reducer();
		timer.stop();
		System.out.println("Time elapsed: " + timer.getDuration() + "ms");
		System.out.flush();
	}

	public void reducer() {
		try {

			for (int i = 0; i < sodokuBoard.getBoardSize(); i++) {
				for (int k = 1; k <= sodokuBoard.getBoardSize(); k++) {
					atLeastOneInRow(i, k);
					atMostOneInRow(i, k);
				}
			}
			//clauses.add("EndRows\n");
			for (int j = 0; j < sodokuBoard.getBoardSize(); j++) {
				for (int k = 1; k <= sodokuBoard.getBoardSize(); k++) {
					atLeastOneInColumn(j, k);
					atMostOneInColumn(j, k);
				}
			}
			//clauses.add("EndCols\n");
			for (int i = 0; i < sodokuBoard.getBoardSize(); i++) {
				for (int k = 1; k <= sodokuBoard.getBoardSize(); k++) {
					atLeastOneInBox(i, k);
					atMostOneInBox(i, k);
				}
			}
			//clauses.add("EndBox\n");
			for (int i = 0; i < sodokuBoard.getBoardSize(); i++) {
				for (int j = 0; j < sodokuBoard.getBoardSize(); j++) {
					atLeastOneInCell(i, j);
					atMostOneInCell(i, j);
				}
			}
			createOutput("output");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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

	/**
	 * 
	 * @param row
	 * @param value
	 */
	public void atLeastOneInRow(int row, int value) {
		StringBuffer clause = new StringBuffer();

		for (int col = 0; col < sodokuBoard.getBoardSize(); col++) {
			clause.append(Integer.toString(convert(row, col, value)) + " ");
		}
		clause.append("0\n");
		clauses.add(clause.toString());
		rowcount++;
	}

	private void atMostOneInRow(int row, int value) {
		for (int j = 0; j < sodokuBoard.getBoardSize(); j++) {
			for (int k = j + 1; k < sodokuBoard.getBoardSize(); k++) {
				clauses.add(Integer.toString(-1 * convert(row, j, value)) + " "
						+ Integer.toString(-1 * convert(row, k, value)) + " 0\n");
				mrowcount++;
			}
		}
	}

	public void atLeastOneInColumn(int column, int value) {
		StringBuffer clause = new StringBuffer();

		for (int row = 0; row < sodokuBoard.getBoardSize(); row++) {
			clause.append(Integer.toString(convert(row, column, value)) + " ");
		}
		clause.append("0\n");
		clauses.add(clause.toString());
		colcount++;
	}

	public void atMostOneInColumn(int column, int value) {
		for (int j = 0; j < sodokuBoard.getBoardSize(); j++) {
			for (int k = j + 1; k < sodokuBoard.getBoardSize(); k++) {
				clauses.add(Integer.toString(-1 * convert(j, column, value)) + " "
						+ Integer.toString(-1 * convert(k, column, value)) + " 0\n");
				mcolcount++;
			}
		}
	}

	public void atLeastOneInBox(int box, int value) {
		StringBuffer clause = new StringBuffer();
		for (int row = 0, cell = 0; row < sodokuBoard.getBoardSize(); row++) {
			for (int column = row + 1; column < sodokuBoard.getBoardSize(); column++) {
				if (sodokuBoard.getCellBox(cell++) != box)
					continue;
				clause.append(Integer.toString(convert(row, column, value)) + " ");
			}
		}
		clause.append("0\n");
		clauses.add(clause.toString());
		boxcount++;
	}

	public void atMostOneInBox(int box, int value) {
		for (int row = 0; row < sodokuBoard.getBoardSize(); row++) {
			for (int column = row + 1; column < sodokuBoard.getBoardSize(); column++) {
				clauses.add(Integer.toString(-1 * convert(row, column, value)) + " "
						+ Integer.toString(-1 * convert(row, column, value)) + " 0\n");
				mboxcount++;
			}
		}
	}

	public void atLeastOneInCell(int row, int column) {
		StringBuffer clause = new StringBuffer();
		for (int k = 1; k <= sodokuBoard.getBoardSize(); k++) {
			clause.append(Integer.toString(convert(row, column, k)) + " ");
		}
		clause.append("0\n");
		clauses.add(clause.toString());
		cellcount++;

	}

	private void atMostOneInCell(int row, int column) {
		for (int x = 1; x <= sodokuBoard.getBoardSize(); x++) {
			for (int y = x + 1; y <= sodokuBoard.getBoardSize(); y++) {
				clauses.add(Integer.toString(-1 * convert(row, column, x)) + " "
						+ Integer.toString(-1 * convert(row, column, y)) + " 0\n");
				mcellcount++;
			}
		}
	}

	private int numberOfVariables() {
		return sodokuBoard.getBoardSize() * sodokuBoard.getBoardSize() * sodokuBoard.getBoardSize();
	}

	private int numberOfClauses() {
		return clauses.size();
	}

	private void printFirstLine() {
		writer.println("p cnf " + numberOfVariables() + " " + numberOfClauses());
	}

	private void createOutput(String outFile) throws Exception {
		cnfOut = new File(outFile + ".cnf");
		System.out.println("Output " + numberOfClauses() + " clauses");
		System.out.println("Row Clauses " + rowcount + "/" + mrowcount);
		System.out.println("Col Clauses " + colcount + "/" + mcolcount);
		System.out.println("Box Clauses " + boxcount + "/" + mboxcount);
		System.out.println("Cell Clauses " + cellcount + "/" + mcellcount);
		try {
			writer = new PrintWriter(cnfOut);
			printFirstLine();
			for (String clause : clauses) {
				writer.print(clause);
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw e;

		}
	}

}