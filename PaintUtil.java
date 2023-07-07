class PaintUtil {
	static String transformString(String m) {
		// "j" must exist outside the for loop in order not to be set to zero.
		int j = 0;
		// used to replace accents and cedillas
		char newChar1 = 'A', newChar2 = 'C', newChar3 = 'E', newChar4 = 'I', newChar5 = 'O', newChar6 = 'U';
		String mTransformed = new String(); // final string to return
		// Copy to first remove accents and cedillas and later change everything to
		// uppercase
		mTransformed = m;
		char[] k = new char[Constantes.WORD_SIZE]; // used to change everything to uppercase
		// -there is an alternative way to remove accents and cedillas using
		// "normalize",
		// which was not given in class
		// -check character by character
		// -see ascii code here: https://www.ime.usp.br/~glauber/html/acentos.htm
		for (int i = 0; i < mTransformed.length(); i++) {
			char aux = mTransformed.charAt(i);
			if (aux == 199 || aux == 231) { // C
				// "mTransformed=" to save the replaced value
				mTransformed = mTransformed.replace(aux, newChar2);
				continue;
			} else if (aux >= 192 && aux <= 197 || aux >= 224 && aux <= 229) { // A
				mTransformed = mTransformed.replace(aux, newChar1);
				continue;
			} else if (aux >= 200 && aux <= 203 || aux >= 232 && aux <= 235) { // E
				mTransformed = mTransformed.replace(aux, newChar3);
				continue;
			} else if (aux >= 204 && aux <= 207 || aux >= 236 && aux <= 239) { // I
				mTransformed = mTransformed.replace(aux, newChar4);
				continue;
			} else if (aux >= 210 && aux <= 214 || aux >= 242 && aux <= 246) { // O
				mTransformed = mTransformed.replace(aux, newChar5);
				continue;
			} else if (aux >= 217 && aux <= 220 || aux >= 249 & aux <= 252) { // U
				mTransformed = mTransformed.replace(aux, newChar6);
				continue;
			} else if (aux >= 65 && aux <= 90 || aux >= 97 & aux <= 122) // A-Z or a-z
				continue;
			else
				throw new IllegalArgumentException("Caractere inválido.");
		}
		// Passes each character to an array of characters. And convert everything to
		// uppercase.
		for (int i = 0; i < mTransformed.length(); i++) {
			char c = mTransformed.charAt(i);
			if (c >= 'a' && c <= 'z') {
				c = (char) (c - 32);
			}
			// Passes each character to a position in the array of characters. Increments
			// "j" to move to the next position in the array.
			k[j] = c;
			j++;
		}
		j = 0; // Restarts for next word entry
		// Pass the character array to a string.
		mTransformed = String.valueOf(k);
		return mTransformed;
	}

	// -draw letters and there is a method already done in a previous practical
	// class
	// -given an image, draw a letter, receive the ColorImage for example 600 by 700
	// -a point is given to draw a square with a certain color with the size of the
	// icon that is in the constant
	// -draw letter in this square (drawTest with centering method and that's all
	static void drawLetter(ColorImage img, int x, int y, int color, char letter) {
		String c = String.valueOf(letter); // to use "img.drawCenteredText"
		// The idea here is when drawing each character in the matrix, it is compared w
		// the letter exists, does not exist, whether the position was correct or not.
		// And so you can draw correctly character by character already with the proper
		// color using the "int color".
		Color result = new Color(0, 0, 0);
		result = checkColor(color, result);

		for (int i = 0; i < Constantes.ICON_SIZE; i++) {
			for (int j = 0; j < Constantes.ICON_SIZE; j++) {
				if (i + x >= 0 && i + x < img.getWidth() && j + y >= 0 && j + y < img.getHeight())// Validating
																									// position
																									// could be in a
					// ColorImage method.
					img.setColor(x + i, y + j, result); // Take care not to draw outside the image
				else
					throw new IllegalArgumentException("Posição não válida.");
			}
		}
		// -If the square starts to be drawn at the position (50,50), then the letter to
		// be in the center of that square must have the "drawCenteredText" method with
		// the coordinates (70,70).
		img.drawCenteredText(x + 20, y + 20, c, Constantes.SIZE_TEXT, Constantes.WHITE);
	}

	// checks the color to apply in the letter design
	static Color checkColor(int color, Color result) {
		if (color == 0)
			result = Constantes.EMPTY_BG;
		else if (color == 1)
			result = Constantes.EXISTS_BG;
		else if (color == 2)
			result = Constantes.NOT_IN_WORD_BG;
		else if (color == 3)
			result = Constantes.BLACK;
		else
			result = Constantes.CORRECT_BG;
		return result;
	}

	// -calculate the total size of the icons and draw in the center of the image
	// after find the center (icon+space+icon) and move to the bottom line
	// -(before making the game class, this function will have to be changed after
	// it is done)
	// -confirm if the letter is in the word and if it is, the letter is in green
	public static int[] drawGameGrid(ColorImage img, char[][] matrix, char[] secretWord, int[] stateOfEachLetter) {
		int yImageCenterOnTop = 100; // to fit the keyboard in the picture
		char[][] attempts = new char[Constantes.MAX_LINES][Constantes.WORD_SIZE];
		boolean drawMatrix = true;

		// Prepare the matrix to draw.
		for (int q = 0; q != attempts.length; q++) {
			for (int a = 0; a != attempts[q].length; a++) {
				attempts[q][a] = matrix[q][a];
			}
		}
		stateOfEachLetter = drawCommonProcess(img, matrix, yImageCenterOnTop, secretWord, drawMatrix,
				stateOfEachLetter);
		return stateOfEachLetter;
	}

	public static int[] drawKeyboard(ColorImage img, char[][] m, char[] secretWord, int[] stateOfEachLetter) {
		int yImageCenterBelow = (int) (img.getHeight() * 3.0 / 4); // to fit the keyboard in the picture
		// Use "drawCommonProcess" to draw the keyboard, but first prepare the matrix
		// you want to draw.
		boolean drawMatrix = false;

		stateOfEachLetter = drawCommonProcess(img, m, yImageCenterBelow, secretWord, drawMatrix, stateOfEachLetter);
		return stateOfEachLetter;
	}

	// It receives a given image and a matrix, which it draws.
	public static int[] drawCommonProcess(ColorImage img, char[][] matrix, int yImageCenter2, char[] word,
			boolean drawMatrix, int[] stateOfEachLetter) {
		int color = 0; // to use with "drawLetter", 0 corresponds to EMPTY_BG as the background color.
		int xImageCenterEven, xImageCenterOdd, yImageCenter;
		yImageCenter = yImageCenter2;
		// to save used coordinates for the next line
		int atualY = 0;
		// to advance the matrix grid and draw on the next line
		int incrementLineForDrawing = 0;
		// to advance the matrix grid and draw on the next column
		int incrementColumnForDrawing = 1; // to not draw the second column on top of the first
		boolean moreThanOneCharacter = false;
		int xImageCenter = 0; // will receive the value of xImageCenterEven or xImageCenterOdd
		// To assign correct sizes and spaces if it were a universal method, maybe a
		// future implementation
		int iconSpacing = Constantes.ICON_SPACING;
		int iconSize = Constantes.ICON_SIZE;
		xImageCenterEven = calculateCenterEven(img, matrix, iconSpacing, iconSize);// To be used also for
		// drawing Statistics.
		for (int i = 0; i != matrix.length; i++) {
			// Determines whether the number of columns in a given row is odd or even to
			// center correctly.
			if (matrix[i].length % 2 == 0) // even
				xImageCenter = xImageCenterEven;
			else if (matrix[i].length % 2 != 0 && i >= 1) { // to draw more than one line
				xImageCenterOdd = img.getWidth() / 2 - iconSpacing * ((matrix[i].length - 1) / 2) - iconSize / 2
						- iconSize * ((matrix[i].length - 1) / 2);
				// -1 "(m[0].length - 1)" because I'm counting only the evens and the odd is
				// split into two "Constants.ICON_SIZE / 2"
				xImageCenter = xImageCenterOdd;
			}
			for (int j = 0; j != matrix[i].length; j++) {
				// calculates coordinates to draw each icon
				if (i == 0) {
					// In the case that the keyboard is drawn for the first time, before any
					// attempts and there is no state recorded.
					if (assistsStateFilling(matrix[i][j]) == 26)
						color = 2;
					else
						color = stateOfEachLetter[assistsStateFilling(matrix[i][j])];
					// draw on the left
					if (moreThanOneCharacter == false) {
						if (drawMatrix) {
							color = compareCharacters(word, matrix[i][j], j);
							// To draw the matrix.
							int indexLetter = assistsStateFilling(matrix[i][j]);
							if (indexLetter <= 25)// It only records the state corresponding to a keyboard character.
								stateOfEachLetter[indexLetter] = color;
						}
						PaintUtil.drawLetter(img, xImageCenter, yImageCenter - iconSpacing - iconSize, color,
								matrix[i][j]);
						moreThanOneCharacter = true;
						atualY = yImageCenter;
						// Does not increment "Constants.ICON SPACING" to "xImageCenter" because there
						// would be a larger spacing than desired between the first and second columns.
						continue; // to move to the correct drawing position of the next letter
					}
					// draw on the right
					else {// to draw more than one character

						if (drawMatrix) {
							color = compareCharacters(word, matrix[i][j], j);
							// To draw the matrix.
							int indexLetter = assistsStateFilling(matrix[i][j]);
							if (indexLetter <= 25)// It only records the state corresponding to a keyboard character.
								stateOfEachLetter[indexLetter] = color;
						}
						PaintUtil.drawLetter(img,
								xImageCenter + iconSize * incrementColumnForDrawing
										+ iconSpacing * incrementColumnForDrawing,
								yImageCenter - iconSpacing - iconSize, color, matrix[i][j]);
						incrementColumnForDrawing++;// to draw characters on the first line
						if (matrix[i].length == j + 1) {// if there are no more characters to be drawn
							// +1 because j starts counting at 0
							// allows you to draw more than one character per line
							// prepare to draw the next line
							moreThanOneCharacter = false;
							incrementColumnForDrawing = 1;
						}
					}
				} else { // for other lines
					// draw on the left

					// Decide what color to use for the character.
					if (drawMatrix) {
						color = compareCharacters(word, matrix[i][j], j);
						// To draw the matrix.
						int indexLetter = assistsStateFilling(matrix[i][j]);
						if (indexLetter <= 25)// It only records the state corresponding to a keyboard character.
							stateOfEachLetter[indexLetter] = color;
					}
					// When drawing the keyboard, it only displays letters with states after the
					// first attempt through compareCharacters(word, matrix[i][j], j)
					else if (!drawMatrix) {// To draw the keyboard
						// Send character index.
						int indexLetter = assistsStateFilling(matrix[i][j]);
						color = stateOfEachLetter[indexLetter];
					}
					if (moreThanOneCharacter == false) {
						PaintUtil.drawLetter(img, xImageCenter,
								atualY + iconSize * incrementLineForDrawing + iconSpacing * incrementLineForDrawing,
								color, matrix[i][j]);
						moreThanOneCharacter = true;
						// signals to pass to the else with the value of "moreThanOneCharacter" to true
						continue; // to move to the correct drawing position of the next letter
					}
					// draw on the right
					else {// means that a character has already been drawn
						PaintUtil.drawLetter(img,
								xImageCenter + iconSize * incrementColumnForDrawing
										+ iconSpacing * incrementColumnForDrawing,
								atualY + iconSize * incrementLineForDrawing + iconSpacing * incrementLineForDrawing,
								color, matrix[i][j]);
						incrementColumnForDrawing++;
						if (matrix[i].length == j + 1) {
							// +1 because j starts counting at 0
							// allows you to draw more than one character per column
							// prepare to draw the next column
							incrementColumnForDrawing = 1;
							// otherwise characters on the same line could be drawn on top of each other
							// prepare to draw the next line
							moreThanOneCharacter = false;
							// otherwise characters from the same initial line could change lines
							incrementLineForDrawing++;
						}
					}
				}
			}
		}
		return stateOfEachLetter;
	}

	// "Constantes.ICON_SPACING/2" corresponds to the middle spacing.
	// The other spacing is already included in the previous code.
	// to choose between xImageCenterEven or xImageCenterOdd=0
	static int calculateCenterEven(ColorImage img, char[][] matrix, int iconSpacing, int iconSize) {
		return img.getWidth() / 2 - iconSpacing * ((matrix[0].length / 2) - 1) - iconSpacing / 2
				- iconSize * (matrix[0].length / 2);
	}

	// Returns the index of each keyboard letter to be used in
	// setStateOfEachLetter() of the Game class.
	private static int assistsStateFilling(char letter) {

		if (letter == 'Q')
			return 0;
		else if (letter == 'W')
			return 1;
		else if (letter == 'E')
			return 2;
		else if (letter == 'R')
			return 3;
		else if (letter == 'T')
			return 4;
		else if (letter == 'Y')
			return 5;
		else if (letter == 'U')
			return 6;
		else if (letter == 'I')
			return 7;
		else if (letter == 'O')
			return 8;
		else if (letter == 'P')
			return 9;
		else if (letter == 'A')
			return 10;
		else if (letter == 'S')
			return 11;
		else if (letter == 'D')
			return 12;
		else if (letter == 'F')
			return 13;
		else if (letter == 'G')
			return 14;
		else if (letter == 'H')
			return 15;
		else if (letter == 'J')
			return 16;
		else if (letter == 'K')
			return 17;
		else if (letter == 'L')
			return 18;
		else if (letter == 'Z')
			return 19;
		else if (letter == 'X')
			return 20;
		else if (letter == 'C')
			return 21;
		else if (letter == 'V')
			return 22;
		else if (letter == 'B')
			return 23;
		else if (letter == 'N')
			return 24;
		else if (letter == 'M')
			return 25;
		else
			return 26;// Indicates that there are no recorded states yet.
	}

	// It compares each character that is drawn in the matrix with each character of
	// a string. This is needed to decide which color to use for the state. To draw
	// the matrix with the trials, the string is the secret word.
	private static int compareCharacters(char[] word, char letter, int j) {
		if (word[j] == letter)
			return 4;// CORRECT_BG
		else {
			for (int g = 0; g != word.length; g++) {
				if (word[g] == letter)
					return 1;// EXISTS_BG
			}
		}
		return 2;// NOT_IN_WORD_BG
		// 0 EMPTY_BG
	}

}