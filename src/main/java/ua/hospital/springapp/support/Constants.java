package ua.hospital.springapp.support;

public class Constants {
	public static final String LOCAL_TIME_DATE_PATTERN = "MM-dd-yyyy HH:mm";
	public static final String LOCAL_DATE_PATTERN = "MM-dd-yyyy";
	public static final int PAGE_SIZE = 5;
	
	public static final String LATIN_NAME_REGEX = "[A-Z][a-z]*";
	public static final String CYRILLIC_NAME_REGEX = "[А-ЯІЇЄҐ][а-яіїєґ]*";
	public static final String PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}";
	public static final String LOGIN_REGEX = "[a-zA-Z0-9._-]{3,}";
	public static final String PHRASE_EN_REGEX = "[A-Z][a-z\\d\\s,-]*";
	public static final String PHRASE_UK_REGEX = "[А-ЯІЇЄҐ][а-яіїєґ\\d\\s,-]*";

}
