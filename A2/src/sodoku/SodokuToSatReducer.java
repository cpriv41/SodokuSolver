package sodoku;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SodokuToSatReducerPrivitera {

	public int boxWidth;
	public int boxHeight;
	public int numCells;
	public int boardSize;
	SodokuBoardPrivitera input;
	SodokuBoardPrivitera boardOutput;
	final private static int[] DEFAULT_VALUE = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	public SodokuToSatReducerPrivitera(File inputFile) {

		input = new SodokuBoardPrivitera(inputFile);

	}

	public SodokuBoardPrivitera createBoard(File inputFile) {

		Scanner board = null;

		try {
			FileReader fileReader =new FileReader(inputFile);
			board = new Scanner(fileReader);
		} catch (Exception e) {
			System.out.print(e.getClass());
			System.exit(1);
		}

		Pattern pat = Pattern.compile("c");

		while (board.findInLine(pat) != null) {
			// once at 'c' read line
			board.nextLine();

			// get board dimensions
			boxWidth = board.nextInt();
			boxHeight = board.nextInt();

			int boardWidth = boxWidth * 3;
			int boardHeight = boxHeight * 3;

			boardOutput = new SodokuBoardPrivitera(boardWidth, boardHeight);

			// process data from input file
			for (int cell = 0; cell < boardOutput.getNumberOfCells(); cell++) {
				boardOutput.setCellValue(cell, board.nextInt());
			}
		}
		return boardOutput;
	}

	public void reduceBoard(int row, int column, int value) {

		return;

	}

	public boolean atLeastOneInRow(int[] row, int value) {

		for (int i : row) {
			if (i == value && value < 10 && value != 0)
				return true;
			else
				return false;
		}
		return false;

	}

	public boolean atMostOneInRow(int[] row, int value) {

		int[] check = null;
		int count = 0;

		for (int i = 0; i < row.length; i++) {
			if (i == value)
				check[i] += check[value];
		}
		for (int i = 0; i < check.length; i++) {
			if (i == value)
				count++;
		}
		if (count > 1)
			return false;
		else
			return true;

	}

	public boolean atLeastOneInColumn() {

	}

	public boolean atMostOneInColumn() {

	}

	public boolean atLeastOneInBox() {

	}

	public boolean atMostOneInBox() {

	}

	public boolean atLeastAtMostValue(int row, int column) {

	}

}
