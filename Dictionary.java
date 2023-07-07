import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class Dictionary {

	private Map<String, String> words;
	private Map<Integer, List<String>> wordsByLength;

	public Dictionary(String filename) {
		loadWords(filename);
	}
	
	public String generateSecretWord(int length) {
		if(!wordsByLength.containsKey(length))
			throw new IllegalStateException("Invalid length (" + length + ").");
		
		List<String> wordsList = wordsByLength.get(length);
		int randomIndex = (int) (Math.random() * wordsList.size());
		return wordsList.get(randomIndex).toUpperCase();
	}
	
	public boolean exists(String noSpecialCharactersWord) {
		if(noSpecialCharactersWord == null)
			throw new NullPointerException("The given argument can't be null.");
		
		String s = new String(noSpecialCharactersWord);
		s = s.toLowerCase();
		
		return words.containsKey(s);
	}

	public String getOriginalWord(String noSpecialCharactersWord) {
		if(noSpecialCharactersWord == null)
			throw new NullPointerException("The given argument can't be null.");
		
		String key = new String(noSpecialCharactersWord);
		key = key.toLowerCase();
		String possibleSpecialCharactersWord = words.get(key);
		if(possibleSpecialCharactersWord == null) {
			return key.toUpperCase();
		} else {
			return possibleSpecialCharactersWord.toUpperCase();
		}
	}

	private void addWordByLength(String word) {
		int length = word.length();
		if(!wordsByLength.containsKey(length)) {
			wordsByLength.put(length, new ArrayList<String>());
		}
		
		wordsByLength.get(length).add(word);
	}

	private void loadWords(String filename) {
		words = new HashMap<>();
		wordsByLength = new HashMap<Integer, List<String>>();
		
		try {
			Scanner s = new Scanner(new File(filename), "UTF-8");

			while (s.hasNextLine()) {
				String line = s.nextLine();
				if(line.contains(";")) {
					String[] v = line.split(";");
					String originalWord = v[0];
					String noSpecialCharactersWord = v[1];
					words.put(noSpecialCharactersWord, originalWord);
					addWordByLength(noSpecialCharactersWord);
					
				} else {
					String word = line;
					words.put(word, null);
					addWordByLength(word);
				}
			}

			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
