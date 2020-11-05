import java.util.ArrayList;
import java.util.StringTokenizer;

class TextFormatter {

	private int maxLineLength = 30;
	private static String insert = "";
  private String form = "";

	private static final String text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy "
			+ "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et "
			+ "accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem "
			+ "ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod "
			+ "tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et "
			+ "justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est "
			+ "Lorem ipsum dolor sit amet.";

	public static void main(String[] args) {
    insert = args[0].toLowerCase();
		TextFormatter formatter = new TextFormatter(20, checkForm(insert));
		formatter.print(text);
	}

	// Konstruktor
	public TextFormatter(int maxLineLength, String form) {
		this.maxLineLength = maxLineLength;
		this.form = form;
	}

	// Ausgabe
	public void print(String text) {
	    ArrayList<String> words = saveWordsInList(text);
			String formattedText = formattedText(words, form);
			System.out.println(formattedText);
		}

  private static String checkForm(String insert){
    if(insert.equals("left") || insert.equals("right") || insert.equals("block"))
    return insert;
    System.out.println("Please enter some of the following: \"left\", \"right\" or \"block\"");
    System.exit(0);
    return null;
  }

	private String formattedText(ArrayList<String> words, String form) {
		String formattedText = "";
		int charsInLine = 0;
		ArrayList<String> wordsInLine = new ArrayList<String>();

		for (int i = 0; i < words.size(); i++) {
			int newCharsInLine = charsInLine + words.get(i).length();
			if (i!=0) newCharsInLine+=1; //space distance between two words

			if (newCharsInLine <= maxLineLength) { // add a new word to the line
				wordsInLine.add(words.get(i));
				charsInLine = newCharsInLine;

			} else { // add the line to the text and add the word to a new line
				formattedText += buildLine(wordsInLine, charsInLine) + "\n";
				wordsInLine.clear();
				wordsInLine.add(words.get(i));
				charsInLine = words.get(i).length();
			}
		}
    formattedText += buildLine(wordsInLine, charsInLine);
		return formattedText;
	}

	private ArrayList<String> saveWordsInList(String text) {
		ArrayList<String> words = new ArrayList<String>();
		StringTokenizer toki = new StringTokenizer(text, " ");
		while (toki.hasMoreTokens()) {
			String word = toki.nextToken();
			if (word.length() <= maxLineLength)
				words.add(word);
			else {
				words.addAll(splittedWords(word));
			}
		}
		return (words);
	}

	private ArrayList<String> splittedWords(String word) {
		ArrayList<String> splittedWords = new ArrayList<String>();
    String splittedWord = "";
		for (int i = 0; i < word.length(); i++) {
			if (i % (maxLineLength-1) == 0 && i != 0) {
        splittedWords.add(splittedWord+"-"); 
				splittedWord = "";
			}
			splittedWord += word.charAt(i);
		}
		splittedWords.add(splittedWord);
		return (splittedWords);
	}

	private String buildLine(ArrayList<String> wordsInLine, int charsInLine) {
	  switch (form) {
		case ("left"):
			return(buildLineLeft(wordsInLine));
		case ("right"):
			return(buildLineRight(wordsInLine, charsInLine));
		case ("block"):
			return(buildLineBlock(wordsInLine, charsInLine));
		}
		return null;
	}

  private String buildLineLeft(ArrayList<String> wordsInLine) {
		String line="";
		for (int i = 0; i < wordsInLine.size(); i++) {
			line += wordsInLine.get(i);
			if (i != wordsInLine.size() - 1)
				line += " ";
		}
		return line;
	}

  private String buildLineRight(ArrayList<String> wordsInLine, int charsInLine) {
		String line="";
		int additionalSpaces = maxLineLength - charsInLine;
		for (int i = 0; i < additionalSpaces; i++) {
			line += " ";
		}
		for (int i = 0; i < wordsInLine.size(); i++) {
			if (i != 0) line += " ";
			line += wordsInLine.get(i);
		}
		return line;
	}

	private String buildLineBlock(ArrayList<String> wordsInLine, int charsInLine) {
		String line="";

		if(wordsInLine.size()>1){
			int additionalSpaces = maxLineLength - charsInLine;
			int spacesPerGap = additionalSpaces/(wordsInLine.size()-1);
			int leftoverSpaces = additionalSpaces%(wordsInLine.size()-1);

			for (int i = 0; i < wordsInLine.size(); i++) {
				line += wordsInLine.get(i);
        
				if (i != wordsInLine.size() - 1){
					for(int j=0; j<=spacesPerGap; j++){
						line += " ";
					}
					if(leftoverSpaces>0){
						line += " ";
						leftoverSpaces--;
					}
				}	
			}
		} else {
			return wordsInLine.get(0);
		}
		return line;	
	}
}