import java.util.ArrayList;
import java.util.StringTokenizer;

class TextFormatter {

	static int maxLineLength_;
	static int letterCounter = 0;
	static String formattedText = "";
	static ArrayList<String> words;

	private static final String text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy "
			+ "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et "
			+ "accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem "
			+ "ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod "
			+ "tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et "
			+ "justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est "
			+ "Lorem ipsum dolor sit amet.";

	public static void main(String[] args) {
		TextFormatter formatter = new TextFormatter(30);
		formatter.print(text);
	}

	// Konstruktor
	public TextFormatter(int maxLineLength) {
		maxLineLength_ = maxLineLength;
	}

	// Ausgabe
	public void print(String text) {
		saveWordsInList(text);
		formatText();
		System.out.println(formattedText);
	}

	private void saveWordsInList(String text) {
		words = new ArrayList<String>();
		StringTokenizer toki = new StringTokenizer(text, " ");
		while (toki.hasMoreTokens()) {
			words.add(toki.nextToken());
		}
	}

	private void formatText() {
		for (int i=0; i<words.size(); i++){ //iterate over all words
			int wordLength=words.get(i).length();
			if(wordLength>maxLineLength_){ //word is longer then the maximum linelength
				splitWord(words.get(i));
			}else if (letterCounter + wordLength <= maxLineLength_){ //word does fit in line 
				formattedText += words.get(i);
				letterCounter += wordLength;
			} else { //word doesn´t fit in line anymore
				letterCounter = wordLength;
				formattedText += "\n" + words.get(i);
			}
			addSpaceIfNeeded();
		}
	}

	private void splitWord(String word) {
		formattedText += "\n";
		letterCounter = 0;
		for (int i = 0; i < word.length(); i++) {
			if (letterCounter == maxLineLength_ - 1) {
				addHyphen(i, word);
			}
			formattedText += word.charAt(i);
			letterCounter++;
		}
	}

  private void addHyphen(int i, String word) {
		if (word.length() != i) {
			formattedText += "-\n";
			letterCounter = 0;
		}
	}
	
	private void addSpaceIfNeeded(){
	  if(letterCounter <= maxLineLength_){
	    formattedText+= " ";
      letterCounter++;
	  }
  }
}