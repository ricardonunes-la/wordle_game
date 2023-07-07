
public class Constantes {

	// Color pallete

	static final Color CORRECT_BG = new Color(59, 163, 148);
	static final Color EXISTS_BG = new Color(212, 173, 106);
	static final Color NOT_IN_WORD_BG = new Color(48, 42, 44);
	static final Color EMPTY_BG = new Color(96, 84, 88);
	static final Color DEFAULT_BG = new Color(111, 92, 98);
	static final Color ERROR_BG = new Color(0, 154, 254);
	static final Color WHITE = new Color(255, 255, 255);
	static final Color BLACK = new Color(0, 0, 0);

	// Size defaults

	static final int ICON_SIZE = 40;
	static final int ICON_SPACING = 4;
	static final int ICON_SIZE_STATS = 10;
	static final int SIZE_TEXT = 40;
	static final int SIZE_TEXT_MIN = 30;
	static final int SIZE_TEXT_STATS = 50;
	static final int MAX_SIZE_LENGTHRECTANGLE = 400;
	static final int MAX_SIZE_LENGTHRECTANGLEZERO = 25;
	static final int SIZE_WIDTHRECTANGLE = 30;

	// Coding

	static final int CORRECT_POSITION = 3;
	static final int EXISTS = 2;
	static final int NOT_EXISTS = 1;
	static final int UNTESTED = 0;

	// Game setup

	static final int WORD_SIZE = 6; // 6 character word game
	static final int MAX_LINES = 6;
	static final int DEFAULT_WIDTH = 700;
	static final int DEFAULT_HEIGHT = 600;

	static final char[][] QWERTY = { { 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P' },
			{ 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L' }, { 'Z', 'X', 'C', 'V', 'B', 'N', 'M' } };

	static final char[][] STATS_TEXT = { { 'E', 'S', 'T', 'A', 'T', 'Í', 'S', 'T', 'I', 'C', 'A', 'S' },
			{ 'J', 'o', 'g', 'a', 'd', 'o', 's' }, { '%', ' ', 'V', 'i', 't', 'ó', 'r', 'i', 'a', 's' },
			{ 'S', 'e', 'q', 'u', 'ê', 'n', 'c', 'i', 'a', ' ', 'd', 'e', ' ', 'v', 'i', 't', 'ó', 'r', 'i', 'a', 's' },
			{ 'M', 'e', 'l', 'h', 'o', 'r', ' ', 's', 'e', 'q', 'u', 'ê', 'n', 'c', 'i', 'a' },
			{ 'D', 'i', 's', 't', 'r', 'i', 'b', 'u', 'i', 'ç', 'ã', 'o', ' ', 'd', 'e', ' ', 't', 'e', 'n', 't', 'a',
					't', 'i', 'v', 'a', 's' },
			{ '1' }, { '2' }, { '3' }, { '4' }, { '5' }, { '6' }, { 'X' } };
}
