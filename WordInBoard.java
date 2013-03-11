package com.guidewire.wordy.impl;

import com.guidewire.wordy.IBoard;
import com.guidewire.wordy.IWordInBoardValidator;

public class WordInBoardValidatorImpl implements IWordInBoardValidator {

	@SuppressWarnings("static-access")
	@Override
	public boolean isWordInBoard(IBoard board, String word) {
		
		boolean[][] bitFlags = new boolean[board.BOARD_ROWS][board.BOARD_COLUMNS];
		boolean wordFound = false;
		boolean letterFound = false;
		StringBuilder wordBuilder = new StringBuilder(0);
		
		if (word.length() < 1)
			return false;
		
		word = word.toUpperCase();
		for (int i = 0; i < board.BOARD_ROWS; i++)
			for (int j = 0; j < board.BOARD_COLUMNS; j++) {
				if(board.getCell(i, j) == word.charAt(0)) {
					wordBuilder.append(board.getCell(i, j));
					wordFound = wordValidator(board, bitFlags, word, wordBuilder, letterFound, i, j);
				}
				if(wordFound) // if word found with the first letter match in board, return true from this function
				{
					return true;
				}
				else //keep looking on the board for search word's next start letter; reset the bitFlags
				{
					bitFlags = new boolean[board.BOARD_ROWS][board.BOARD_COLUMNS];
				}
			}
		return false;
	}
	
	//function to find if the letters in the word are adjacently placed
	@SuppressWarnings("static-access")
	public boolean wordValidator(IBoard board, boolean[][] flags, String word, StringBuilder wordBuilder, boolean letterFound, int x, int y) {
		//mark this letter on board, as visited
		flags[x][y] = true;
		
		wordBuilder.trimToSize();
		//if the word has been found, return true
		if(wordBuilder.toString().equals(word))
			return true;
		
		if(wordBuilder.length() < word.length()) {
			//top cell
			if(x != 0 && !flags[x - 1][y] && (board.getCell(x - 1, y) == word.charAt(wordBuilder.length())))
			{
				wordBuilder.append(board.getCell(x - 1, y)); //append the letter found to wordBuilder
				letterFound = wordValidator(board, flags, word, wordBuilder, letterFound, x - 1, y); //call recursively to find next letter
				if(letterFound) //if prev call didn't find letter, don't return , keep looking, else return true and stop the search
					return true;
			}
			//diagonal top-right cell
			if(x != 0 && y != board.BOARD_COLUMNS - 1 && !flags[x - 1][y + 1] && (board.getCell(x - 1, y + 1) == word.charAt(wordBuilder.length()))) 
			{
				wordBuilder.append(board.getCell(x - 1, y + 1)); 
				letterFound = wordValidator(board, flags, word, wordBuilder, letterFound, x - 1, y + 1); 
				if(letterFound) 
					return true;
			}
			//right cell
			if(y != board.BOARD_COLUMNS - 1 && !flags[x][y + 1] && (board.getCell(x, y + 1) == word.charAt(wordBuilder.length())))
			{
				wordBuilder.append(board.getCell(x, y + 1));
				letterFound = wordValidator(board, flags, word, wordBuilder, letterFound, x, y + 1);
				if(letterFound)
					return true;
			}
			
			//diagonal bottom-right cell
			if(x != board.BOARD_ROWS - 1 && y != board.BOARD_COLUMNS - 1 && !flags[x + 1][y + 1] && (board.getCell(x + 1, y + 1) == word.charAt(wordBuilder.length())))
			{
				wordBuilder.append(board.getCell(x + 1, y + 1));
				letterFound = wordValidator(board, flags, word, wordBuilder, letterFound, x + 1, y + 1);
				if(letterFound)
					return true;
			}
			//bottom cell
			if(x != board.BOARD_ROWS - 1 && !flags[x + 1][y] && (board.getCell(x + 1, y) == word.charAt(wordBuilder.length())))
			{
				wordBuilder.append(board.getCell(x + 1, y));
				letterFound = wordValidator(board, flags, word, wordBuilder, letterFound, x + 1, y);
				if(letterFound)
					return true;
			}
			
			//diagonal bottom-left cell
			if(x != board.BOARD_ROWS - 1 && y != 0 && !flags[x + 1][y - 1] && (board.getCell(x + 1, y - 1) == word.charAt(wordBuilder.length())))
			{
				wordBuilder.append(board.getCell(x + 1, y - 1));
				letterFound = wordValidator(board, flags, word, wordBuilder, letterFound, x + 1, y - 1);
				if(letterFound)
					return true;
			}
			//left cell
			if(y != 0 && !flags[x][y - 1] && (board.getCell(x, y - 1) == word.charAt(wordBuilder.length())))
			{
				wordBuilder.append(board.getCell(x, y - 1));
				letterFound = wordValidator(board, flags, word, wordBuilder, letterFound, x, y - 1);
				if(letterFound)
					return true;
			}
			//diagonal top-left
			if(x != 0 && y != 0 && !flags[x - 1][y - 1] && (board.getCell(x - 1, y - 1) == word.charAt(wordBuilder.length())))
			{
				wordBuilder.append(board.getCell(x - 1, y - 1));
				letterFound = wordValidator(board, flags, word, wordBuilder, letterFound, x - 1, y - 1);
				if(letterFound)
					return true;
			}
			else
			{
				//if next letter in string not found, delete this letter, and start the search from any other equal adjacent letter
				wordBuilder.deleteCharAt(wordBuilder.length() - 1);
				flags[x][y] = false;
				return false;
			}
		}
		return false;
	}
}