/**
 *  * This class is part of the "CAPTUED BY SQOGS" application. 
 * "CAPTURED BY SQOGS" is a simple, text based adventure game.
 * 
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author 162225
 * @version 1
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"), SEARCH("search"), USE("use"), INVENTORY("inventory"), QUIT("quit"), HELP("help"), UNKNOWN("?");
    
    // The command string.
    private String commandString;
    
    /**
     * Initialise with the corresponding command word.
     * @param commandWord The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}
