import java.util.ArrayList;
import java.util.StringTokenizer;

class TextFormatter {

	private int maxLineLength;
  private static int form;
	private int letterCounter = 0;
	private ArrayList<String> words;
  private String right = "";

	private static final String text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy "
			+ "eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et "
			+ "accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem "
			+ "ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod "
			+ "tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et "
			+ "justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est "
			+ "Lorem ipsum dolor sit amet.";

	public static void main(String[] args) {
    if(args.length == 0){
      form = 0;
    }else if(args[0].toLowerCase().equals("left")){
      form = 0;
    }else if(args[0].toLowerCase().equals("right")){
      form = 1;
    }else if(args[0].toLowerCase().equals("block")){
      form = 2;
    }else{
      System.out.println("Please enter one of the following commands: \"left\", \"right\" or \"block\".");
      return;
    }

    	//0=left, 1=right, 2=block
		TextFormatter formatter = new TextFormatter(30, form);
		formatter.print(text);
	}

	// Konstruktor
	public TextFormatter(int maxLineLength, int form) {
		this.form = form;
    if(form != 3){
      this.maxLineLength=maxLineLength;
    }else{
    this.maxLineLength = maxLineLength-1;
    }
	}

	// Ausgabe
	public void print(String text) {
		saveWordsInList(text);
		System.out.println(formatText());
	}

	private void saveWordsInList(String text) {
		words = new ArrayList<String>();
		StringTokenizer toki = new StringTokenizer(text, " ");
		while (toki.hasMoreTokens())
			  words.add(toki.nextToken());
	}

  private String addWordWithHyphen(String word){
		String temporary = "";
		for (int letterPos = 0; letterPos < word.length(); letterPos++) {
			if (letterCounter == maxLineLength){ 
				temporary += addHyphen(letterPos, word);
      }
			temporary += word.charAt(letterPos);
			letterCounter++;
    }
    return temporary;
  }

  private String addWordWithoutHyphen(String word){
    letterCounter+=word.length();
    return word;
  }

  private String format(String word){
    if(form>0){
      return buildNewLine(word);
    }else{
    return word+addSpaceIfNeeded();
    }
  }

private String buildNewLine(String word){
  if(letterCounter+word.length() < maxLineLength){
    letterCounter+=word.length();
    right+=word+addSpaceIfNeeded();
  return "";
  }
  letterCounter = 0;
  String newLine = right;
  right = word+" ";
  letterCounter+=word.length();
  if(form == 1){
  return changeLetterPositions(newLine);
  }else{
    return changeToBlock(newLine);
  }
}

 //Umordnen
 private String changeLetterPositions(String newLine){
    String temp = "";
    for(int letterPos = -1; letterPos < (maxLineLength-newLine.length()); letterPos++){
      temp+=" ";
    }
    temp+=newLine;
    return temp + "\n";
  }

  //Block
  private String changeToBlock(String newLine){
    int stringLength = newLine.length();
    int currentWord = 0;
    while(stringLength+currentWord<maxLineLength){
    StringTokenizer toki = new StringTokenizer(newLine.trim(), " ", true);
    String temp="";
    while(toki.hasMoreTokens()){
      temp+=toki.nextToken();
      if(stringLength+currentWord<maxLineLength && toki.hasMoreTokens()){
        temp+=" ";
        currentWord++;
      }
    }
    newLine=temp;
    }
    return newLine + "\n";
  }

  private String formatText(){
    String formattedText = "";
    for (int wordPos=0; wordPos<words.size(); wordPos++){ //iterate over all words
      String word = words.get(wordPos);
      int wordLength=word.length();
      if(form == 2 || form == 1){
        formattedText += format(word);
      }else if(wordLength>maxLineLength){
        formattedText += "\n" + format(addWordWithHyphen(word));
      }else{
        if((wordLength+letterCounter)>maxLineLength){
        formattedText += "\n";
        letterCounter = 0;
        }
        formattedText += format(addWordWithoutHyphen(word));
      }
    }
    if(form == 2){
    formattedText+=changeToBlock(right);
    }else if(form ==1){
      formattedText+=changeLetterPositions(right);
    }
    return formattedText;
}

private String addHyphen(int letterPos, String word){
  			letterCounter = 0;
		if (word.charAt(0)==word.charAt(letterPos)){
      return "\n";
    }
    return "-\n";
  }

  private String addSpaceIfNeeded(){
	  if(letterCounter < maxLineLength){
      letterCounter++;
      return " ";
    }
      return "";
  }
}