package clueGame;

public class BadConfigFormatException extends Exception{
	private String code;
	
	public BadConfigFormatException(String message) {
		super(message);
	}
}
