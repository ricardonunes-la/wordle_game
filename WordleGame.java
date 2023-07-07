class WordleGame {

	// First row of "char [][] matrix" is not used and is shown in black. This is
	// due to
	// implementation changes at the end of the project, since initially the secret
	// word was being stored in the first line of the matrix.
	private char[][] matrix = { { ' ', ' ', ' ', ' ', ' ', ' ' }, { ' ', ' ', ' ', ' ', ' ', ' ' },
			{ ' ', ' ', ' ', ' ', ' ', ' ' }, { ' ', ' ', ' ', ' ', ' ', ' ' }, { ' ', ' ', ' ', ' ', ' ', ' ' },
			{ ' ', ' ', ' ', ' ', ' ', ' ' } };
	private char[][] keyboard;
	private Stats registerStats;
	private String puzzle;
	private int[] stateOfEachLetter;
	private final Dictionary dictionaryPT; // For each game created, create a dictionary.
	private char[] secretWord;
	private int indicatesLines;
	private String lastTry;
	public final ColorImage img;
	public ColorImage imgStats;// It is not final to be able to draw new statistics

	WordleGame() {
		this.dictionaryPT = new Dictionary("pt_br.txt");
		this.img = new ColorImage(Constantes.DEFAULT_WIDTH, Constantes.DEFAULT_HEIGHT, Constantes.DEFAULT_BG);
		this.imgStats = new ColorImage(Constantes.DEFAULT_WIDTH, Constantes.DEFAULT_HEIGHT, Constantes.BLACK);
		this.registerStats = new Stats();
		init();
	}

	WordleGame(Dictionary dictionaryPT) {
		this.dictionaryPT = dictionaryPT;
		this.img = new ColorImage(Constantes.DEFAULT_WIDTH, Constantes.DEFAULT_HEIGHT, Constantes.DEFAULT_BG);
		this.imgStats = new ColorImage(Constantes.DEFAULT_WIDTH, Constantes.DEFAULT_HEIGHT, Constantes.BLACK);
		this.registerStats = new Stats();
		init();
	}

	WordleGame(ColorImage img) {
		this.dictionaryPT = new Dictionary("pt_br.txt");
		this.img = img;
		this.imgStats = new ColorImage(Constantes.DEFAULT_WIDTH, Constantes.DEFAULT_HEIGHT, Constantes.BLACK);
		this.registerStats = new Stats();
		this.init();
	}

	public void restart() {

		for (int i = 0; i < Constantes.DEFAULT_WIDTH; i++) {
			for (int j = 0; j < Constantes.DEFAULT_HEIGHT; j++) {
				if (i >= 0 && i < imgStats.getWidth() && j >= 0 && j < imgStats.getHeight())
					imgStats.setColor(i, j, Constantes.BLACK);
				else
					throw new IllegalArgumentException("Posição não válida.");
			}
		}
		setIndicatesLines(0);
		registerStats.setHelpsCountAttempts(-1);
		registerStats.inicializeCountAttempts(0);
		this.puzzle = randomPuzzleWord();
		this.setSecretWord(puzzle.toCharArray());
		for (int q = 0; q != matrix.length; q++) {
			for (int a = 0; a != matrix[q].length; a++) {
				getMatrix()[q][a] = ' ';
			}
		}
		for (int c = 0; c != this.stateOfEachLetter.length; c++)
			this.stateOfEachLetter[c] = 0;// 0 not tested
	}

	private void init() {
		this.keyboard = Constantes.QWERTY;
		this.stateOfEachLetter = new int[26]; // number of keyboard characters
		this.puzzle = randomPuzzleWord();
		this.lastTry = null;
		this.setSecretWord(puzzle.toCharArray()); // convert secret word to an array of characters to compare
		// It should be used to compare character by character.
		this.indicatesLines = 0;
		for (int f = 0; f != this.stateOfEachLetter.length; f++)
			this.stateOfEachLetter[f] = 0;// Initially each keyboard letter is in state 0, which corresponds to EMPTY_BG
		if (img == null)
			throw new NullPointerException();
		else
			this.draw();
	}

	private void draw() {
		this.stateOfEachLetter = PaintUtil.drawGameGrid(img, getMatrix(), getSecretWord(), stateOfEachLetter);
		this.stateOfEachLetter = PaintUtil.drawKeyboard(img, keyboard, getSecretWord(), stateOfEachLetter);
	}

	public void getWord(String word) {
		lastTry = PaintUtil.transformString(word);// removes accents, cedillas, etc.
		// increments a try for after deciding whether to include in a win or loss
		registerStats.startHelpsCountAttempts();
		registerStats.setcountAttempts(1);
		saveWord();
		this.draw();
		// for the stats, game won
		if (lastTry.equals(puzzle)) {
			registerStats.setnumberOfWins(1);
			registerStats.setDistributionOfAttempts(1, registerStats.getHelpsCountAttempts());
			registerStats.setNumberOfGames(1);
			registerStats.sumWinningStreak(1);
			restart();
		} else if (registerStats.getcountAttempts() == 6) {
			registerStats.setNumberOfGames(1);
			registerStats.setWinningStreak(0);
			registerStats.setDistributionOfAttempts(1, 6);
			restart();
		}
	}

	private void saveWord() {
		char[] charWord;
		charWord = lastTry.toCharArray(); // convert typed word to an array of characters to compare

		for (int q = 0; q != matrix.length; q++) {
			for (int a = 0; a != matrix[q].length; a++) {
				if (q == indicatesLines)
					matrix[q][a] = charWord[a];
			}
		}
		// Used to write on different lines of the matrix.
		// It is necessary to restart this variable, which can be used to find out if
		// the maximum number of attempts has been exhausted or not.
		this.indicatesLines++;
		// The matrix must be drawn first, as it includes the update of the state of
		// each letter, which is used to draw the keyboard.
	}

	// Only supposed to see the statistics after completing a game,
	// otherwise the program itself will give an error when calculating a percentage
	// that gives a number to be divided by zero.
	// To avoid this situation use IllegalStateException
	public void invokeDrawingStatistics() {
		if (registerStats.getNumberOfGames() == 0)
			throw new IllegalStateException();
		else
			registerStats.drawStats(imgStats);// as a new image to not record over the game image
	}

	// get random puzzle word
	private String randomPuzzleWord() {
		return dictionaryPT.generateSecretWord(setWordSize());
	}

	// help set word size
	private int setWordSize() {
		int length = Constantes.WORD_SIZE;
		return length;
	}

	private char[][] getMatrix() {
		return matrix;
	}

	private void setSecretWord(char[] secretWord) {
		this.secretWord = secretWord;
	}

	public char[] getSecretWord() {
		return secretWord;
	}

	private void setIndicatesLines(int number) {
		this.indicatesLines = number;
	}

}
