import java.lang.Math;

class Stats {

	private int numberOfWins;
	private int numberOfGames;
	private int winningStreak;
	private int bestSequel;
	private int[] distributionOfAttempts;
	private int helpsCountAttempts;
	private int countAttempts;

	Stats() {
		this.numberOfWins = 0;
		this.numberOfGames = 0;
		this.winningStreak = 0;
		this.bestSequel = 0;
		this.distributionOfAttempts = new int[7];
		this.helpsCountAttempts = -1;
		this.countAttempts = 0;
	}

	void restart() { // to use as a button
		setnumberOfWins(0);
		setNumberOfGames(0);
		sumWinningStreak(0);
		setBestSequel(0);
		setHelpsCountAttempts(-1); //to start counting at index 0
		setcountAttempts(0);
		setWinningStreak(0);
		for (int c = 0; c != this.distributionOfAttempts.length; c++) {
			this.distributionOfAttempts[c] = 0;
		}
	}

	void setWinningStreak(int num) {
		this.winningStreak = num;
	}

	int getcountAttempts() {
		return this.countAttempts;
	}

	void inicializeCountAttempts(int num){
		this.countAttempts=num;
	}
	
	void setcountAttempts(int num) {
		this.countAttempts = this.countAttempts + num;
	}

	void setHelpsCountAttempts(int num) {
		this.helpsCountAttempts = num;
	}

	void startHelpsCountAttempts() {
		this.helpsCountAttempts++;
	}

	int getHelpsCountAttempts() {
		return this.helpsCountAttempts;
	}

	private long calculatePercentage() {
		double result = (double)this.numberOfWins / this.numberOfGames;
		result = result * 100;
		return Math.round(result);
	}

	void setnumberOfWins(int wins) {
		this.numberOfWins = this.numberOfWins + wins;
	}

	int getNumberOfGames() {
		return numberOfGames;
	}

	void setNumberOfGames(int numberOfGames) {
		this.numberOfGames = this.numberOfGames + numberOfGames;
	}

	private int getWinningStreak() {
		return this.winningStreak;
	}

	void sumWinningStreak(int num) {
		this.winningStreak = this.winningStreak + num;
	}

	private int getBestSequel() {
		return this.bestSequel;
	}

	private void setBestSequel(int winningStreak) {
		if (this.bestSequel < this.winningStreak)
			this.bestSequel = this.winningStreak;
	}

	private int getDistributionOfAttempts(int indexStats) {
		int result = 0;
		for (int u = 0; u != distributionOfAttempts.length; u++) {
			if (u == indexStats) {
				result = distributionOfAttempts[indexStats];
				break;
			}
		}
		return result;
	}

	// Updates record due to victory and defeat.
	void setDistributionOfAttempts(int incrementVictory, int line) {
		for (int c = 0; c != this.distributionOfAttempts.length; c++) {
			if (c == line)
				this.distributionOfAttempts[c] = this.distributionOfAttempts[c] + incrementVictory;
		}
	}

	// based on the construction of "drawCommonProcess()"
	void drawStats(ColorImage img) {
		boolean zeroDistributionValue = true;// Initially the "trial distribution" is completely zero, so the value is
												// true.
		int indexDistributionOfAttempts = 0;// also used to change coordinates
		int identifierIndex = 0; // Used to identify the right size of each graph line, based on information
									// stored in a vector.
		int incrementHeight = 0; // to use with assistDrawingLines()
		int yImageCenter = 100;
		char[][] statsMatrix = Constantes.STATS_TEXT;
		int xImageCenterEven = PaintUtil.calculateCenterEven(img, statsMatrix, Constantes.ICON_SPACING,
				Constantes.ICON_SIZE);
		char[] c = new char[30];

		for (int z = 0; z != statsMatrix.length; z++) {
			int x = 40;
			for (int h = 0; h != statsMatrix[z].length; h++) {
				if (z == 0) { // Estatísticas
					c[h] = statsMatrix[z][h];
					String convertedString = String.valueOf(c[h]); // Convert character to string
					img.drawCenteredText(xImageCenterEven + 20, yImageCenter, convertedString, Constantes.SIZE_TEXT,
							Constantes.WHITE);
					// + 20 is still needed to center
					xImageCenterEven = xImageCenterEven + 44;
				}
				if (z == 1) { // Jogados
					// zeroDistributionValue has no use up to and including "Distribution Trials" as
					// it is only used for the graph.
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					if (h == 6) {
						int numberOfGames = getNumberOfGames();
						String s = Integer.toString(numberOfGames);
						img.drawCenteredText(x, yImageCenter + 40, s, Constantes.SIZE_TEXT_MIN, Constantes.CORRECT_BG);
						incrementHeight++;// Implemented here so that the letters are not drawn diagonally, but on the
											// same line
					}
				}
				if (z == 2) { // % Vitórias
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					if (h == 9) {
						long percentage = this.calculatePercentage();

						String s = Long.toString(percentage);
						
						//String s = Integer.toString(percentage);
						s = "    " + s;
						img.drawCenteredText(x, yImageCenter + 80, s, Constantes.SIZE_TEXT_MIN, Constantes.CORRECT_BG);
						incrementHeight++;
					}
				}
				if (z == 3) { // Sequência de vitórias
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					if (h == 20) {
						int winningStreak = getWinningStreak();
						String s = Integer.toString(winningStreak);
						img.drawCenteredText(x, yImageCenter + 120, s, Constantes.SIZE_TEXT_MIN, Constantes.CORRECT_BG);
						incrementHeight++;
					}
				}
				if (z == 4) { // Melhor sequência
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					if (h == 15) {
						setBestSequel(getWinningStreak());
						int bestSequel = getBestSequel();
						String s = Integer.toString(bestSequel);
						img.drawCenteredText(x, yImageCenter + 160, s, Constantes.SIZE_TEXT_MIN, Constantes.CORRECT_BG);
						incrementHeight++;
					}
				}
				if (z == 5) { // Distribuição de tentativas
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					if (h == 25)
						incrementHeight++;
				}
				if (z == 6) { // 1
					identifierIndex = 2;// indices are stored in odd positions in the array that will be used with this
										// variable
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					int distributionOfAttempts = getDistributionOfAttempts(indexDistributionOfAttempts);
					zeroDistributionValue = compareValuesToChart(distributionOfAttempts);
					drawRectangle(img, yImageCenter + 6 * 40 - 17, zeroDistributionValue, identifierIndex);
					assistDrawingGraph(img, x, yImageCenter, indexDistributionOfAttempts);
					indexDistributionOfAttempts++;
					incrementHeight++;
				}
				if (z == 7) { // 2
					identifierIndex = 4;
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					int distributionOfAttempts = getDistributionOfAttempts(indexDistributionOfAttempts);
					zeroDistributionValue = compareValuesToChart(distributionOfAttempts);
					drawRectangle(img, yImageCenter + 7 * 40 - 17, zeroDistributionValue, identifierIndex);
					assistDrawingGraph(img, x, yImageCenter, indexDistributionOfAttempts);
					indexDistributionOfAttempts++;
					incrementHeight++;
				}
				if (z == 8) { // 3
					identifierIndex = 6;
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					int distributionOfAttempts = getDistributionOfAttempts(indexDistributionOfAttempts);
					zeroDistributionValue = compareValuesToChart(distributionOfAttempts);
					drawRectangle(img, yImageCenter + 8 * 40 - 17, zeroDistributionValue, identifierIndex);
					assistDrawingGraph(img, x, yImageCenter, indexDistributionOfAttempts);
					indexDistributionOfAttempts++;
					incrementHeight++;
				}
				if (z == 9) { // 4
					identifierIndex = 8;
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					int distributionOfAttempts = getDistributionOfAttempts(indexDistributionOfAttempts);
					zeroDistributionValue = compareValuesToChart(distributionOfAttempts);
					drawRectangle(img, yImageCenter + 9 * 40 - 17, zeroDistributionValue, identifierIndex);
					assistDrawingGraph(img, x, yImageCenter, indexDistributionOfAttempts);
					indexDistributionOfAttempts++;
					incrementHeight++;
				}
				if (z == 10) { // 5
					identifierIndex = 10;
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					int distributionOfAttempts = getDistributionOfAttempts(indexDistributionOfAttempts);
					zeroDistributionValue = compareValuesToChart(distributionOfAttempts);
					drawRectangle(img, yImageCenter + 10 * 40 - 17, zeroDistributionValue, identifierIndex);
					assistDrawingGraph(img, x, yImageCenter, indexDistributionOfAttempts);
					indexDistributionOfAttempts++;
					incrementHeight++;
				}
				if (z == 11) { // 6
					identifierIndex = 12;
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					int distributionOfAttempts = getDistributionOfAttempts(indexDistributionOfAttempts);
					zeroDistributionValue = compareValuesToChart(distributionOfAttempts);
					drawRectangle(img, yImageCenter + 11 * 40 - 17, zeroDistributionValue, identifierIndex);
					assistDrawingGraph(img, x, yImageCenter, indexDistributionOfAttempts);
					indexDistributionOfAttempts++;
					incrementHeight++;
				}
				if (z == 12) { // X
					identifierIndex = 14;
					assistDrawingLines(img, c, h, z, x, yImageCenter, statsMatrix, incrementHeight);
					x = x + 20;
					int distributionOfAttempts = getDistributionOfAttempts(indexDistributionOfAttempts);
					zeroDistributionValue = compareValuesToChart(distributionOfAttempts);
					drawRectangle(img, yImageCenter + 12 * 40 - 17, zeroDistributionValue, identifierIndex);
					assistDrawingGraph(img, x, yImageCenter, indexDistributionOfAttempts);
					indexDistributionOfAttempts++;
					// does not increment "incrementHeight++;", because there are no more lines
				}
			}
		}
	}

	// assists in the distribution of attempts
	private void assistDrawingGraph(ColorImage img, int x, int yImageCenter, int indexDistributionOfAttempts) {
		int distributionOfAttempts = getDistributionOfAttempts(indexDistributionOfAttempts);
		String s = Integer.toString(distributionOfAttempts);
		img.drawCenteredText(x, yImageCenter + 240 + indexDistributionOfAttempts * 40, s, Constantes.SIZE_TEXT_MIN,
				Constantes.WHITE);
	}

	private void assistDrawingLines(ColorImage img, char[] c, int h, int z, int x, int yImageCenter,
			char[][] statsMatrix, int incrementHeight) {
		c[h] = statsMatrix[z][h];
		String convertedString = String.valueOf(c[h]);
		img.drawCenteredText(x, yImageCenter + 40 + 40 * incrementHeight, convertedString, Constantes.SIZE_TEXT_MIN,
				Constantes.WHITE);
	}

	// The idea is to assign the maximum length of the rectangle, to be drawn, to
	// the
	// largest number. Then that length can be divided into parts, depending on the
	// number you have.
	private void drawRectangle(ColorImage img, int y, boolean zeroDistributionValue, int identifierIndex) {
		int[] copyDistributionOfAttempts2 = new int[16];
		Color colorToZero = new Color(160, 160, 160);
		Color colorBlue = new Color(0, 128, 255);
		// final values
		int finalWidth = Constantes.SIZE_WIDTHRECTANGLE;
		int finalHeight = Constantes.MAX_SIZE_LENGTHRECTANGLE;
		int x = 50; // Defines the position to start drawing the rectangle.
		int highestValue = 0;
		copyDistributionOfAttempts2 = getValuesOfDistribution();
		double result = 0.0;

		// if 0
		if (zeroDistributionValue == true) {
			finalHeight = Constantes.MAX_SIZE_LENGTHRECTANGLEZERO;
			for (int i = 0; i < finalHeight; i++) {
				for (int j = 0; j < finalWidth; j++) {
					// Validating position could be in a ColorImage method.
					if (i + x >= 0 && i + x < img.getWidth() && j + y >= 0 && j + y < img.getHeight()) {
						img.setColor(x + i, y + j, colorToZero);
					} else
						throw new IllegalArgumentException("Posição não válida.");
				}
			}
		}

		// to apply rule of 3 simple
		highestValue = copyDistributionOfAttempts2[0];
		if (copyDistributionOfAttempts2[identifierIndex] != 0)
			result = copyDistributionOfAttempts2[identifierIndex] * finalHeight / highestValue;// does not do casting

		if (zeroDistributionValue == false) {
			// applies the largest length size
			if (copyDistributionOfAttempts2[identifierIndex] == highestValue) {
				for (int i = 0; i < finalHeight; i++) {
					for (int j = 0; j < finalWidth; j++) {
						// Validating position could be in a ColorImage method.
						if (i + x >= 0 && i + x < img.getWidth() && j + y >= 0 && j + y < img.getHeight()) {
							img.setColor(x + i, y + j, colorBlue);
						} else
							throw new IllegalArgumentException("Posição não válida.");
					}
				}
			} else {
				// apply rule of 3 simple
				for (int i = 0; i < result; i++) {
					for (int j = 0; j < finalWidth; j++) {
						// Validating position could be in a ColorImage method.
						if (i + x >= 0 && i + x < img.getWidth() && j + y >= 0 && j + y < img.getHeight()) {
							img.setColor(x + i, y + j, colorBlue);
						} else
							throw new IllegalArgumentException("Posição não válida.");
					}
				}
			}
		}
	}

	// It compares whether the value in the distribution of attempts is zero or not,
	// in order to choose the appropriate color and size for each rectangle, which
	// is drawn on the graph.
	private boolean compareValuesToChart(int distributionOfAttempts) {
		boolean zeroDistributionValue = true;
		if (distributionOfAttempts != 0)
			zeroDistributionValue = false;
		return zeroDistributionValue;
	}

	// Get vector equal to distributionOfAttempts[]
	// and the largest number in it
	private int[] getValuesOfDistribution() {
		int[] copyDistributionOfAttempts = new int[16];
		int higherNumber = 0;
		int indexHigherNumber = 0;
		int inc1 = 1;
		int inc2 = 0;

		// The highest number is stored in the first position of
		// "copyDistributionOfAttempts[]".
		// The values of "getDistributionOfAttempts()" are stored in even positions. And
		// in the odd positions the indices are kept.
		for (int u = 2; u != copyDistributionOfAttempts.length; u++) {
			if (u % 2 == 0) {
				copyDistributionOfAttempts[u] = getDistributionOfAttempts(inc2);
				inc2++;
			} else if (u % 2 != 0) {
				copyDistributionOfAttempts[u] = inc1;
				inc1++;
			}
			if (copyDistributionOfAttempts[u] > higherNumber) {
				higherNumber = copyDistributionOfAttempts[u];
				indexHigherNumber = u;
				copyDistributionOfAttempts[0] = higherNumber;
				copyDistributionOfAttempts[1] = indexHigherNumber;
			}
		}
		return copyDistributionOfAttempts;
	}
}