 import java.util.Scanner;

/**
 *  This class is the main class of the "CAPTURED BY SQOGS" application. 
 *  "CAPTURED BY SQOGS" is a  simple, text based puzzle/adventure game.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser, creates the inventory and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *  
 * 
 * @author  162225
 * @version 1
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Inventory inventory;
    private Room cell = new Room("in a Cell");   
    private Room cavernousHallway = new Room("in a Cavernous Hallway");        
    private Room stupidSqog = new Room("in an open cavern shaped like a room.\nThere is a ledge up to your left,a SQOG is throwing rocks at you from\nthe ledge making it impossible for you to get any further into the room");
    private Room switch1Room = new Room("in a dead end room, roughly cut into rock.");
    private Room stoneHallway = new Room("in a large Stoney Hallway ");
    private Room deadEnd = new Room("in a dead end, looks like someone else got stuck here, you notice some human skeleton remains");
    private Room riverRoom = new Room("in a long under ground cavern. A river runs northward lighting the cave with a strange magical glow");
    private Room riddleRoom = new Room("in a large open room. This room is different from what you\nhave seen so far.Crystals of all colours are growing out of the walls\nand a strange energy permiates the air"); 
    private Room ladderRoom = new Room("in a simple section of cave. There is a ledge ahead with a ladder hanging above it");
    private Room holeRoom = new Room("in an open area of cavern, well lit from what appears to be natural light to the north.\n There is a deep hole in the ground...you can't see anything in it...");
    private Room exit = new Room("in a corridor section of cave, you are so close to freedom you can almost smell it");
    private Room bedRoom = new Room("in what appears to be a bedroom for SQOGS, it's very rocky.\nThere are several sleeping SQOGS, aswell as many boxes and lots of junk\nYou can't go back the way you came in. The only way out is a\nwinding river, burrowing it's way through the rocky walls.");
    private Room outside = new Room("in the open air! You made it, you have escaped the SQOG cave.\n Now what...?");
    private Room stillOutside = new Room("still outside...");
        
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        inventory = new Inventory();
        createRooms();        
    }

    /**
     * Set up all the rooms initial states.
     * Such as items, the rooms items can be used in and exits 
     */
    private void createRooms()
    {
        cell.setExit("north", cavernousHallway, false);        
        cell.addItem("CellKey");        
        cell.setLockItem("north", "CellKey");
                
        cavernousHallway.setExit("south", cell, true);
        cavernousHallway.setExit("west", stupidSqog, true);
        cavernousHallway.setExit("east", switch1Room, false);
        cavernousHallway.setExit("north", stoneHallway, false);
        cavernousHallway.addItem("SolidStick");
        cavernousHallway.setLockItem("east", "ShinyKey");
                      
        stupidSqog.setExit("east", cavernousHallway, true);
        stupidSqog.addItemUse("SolidStick", true);
        
        switch1Room.setExit("west", cavernousHallway, true);
        switch1Room.addSwitch(cavernousHallway, "north");
        
        stoneHallway.setExit("south", cavernousHallway, true);
        stoneHallway.setExit("west", deadEnd, true);
        stoneHallway.setExit("east", riverRoom, true);

        deadEnd.setExit("east", stoneHallway, true);
        deadEnd.addItem("Trumpet");
        deadEnd.addItem("GrappleHook");
        
        riverRoom.setExit("west", stoneHallway, true);
        riverRoom.setExit("north", riddleRoom, true);
        riverRoom.addItem("FishHead");
        
        
        riddleRoom.setExit("south", riverRoom, true);
        riddleRoom.addItem("MagicCrystal");
        riddleRoom.addItemUse("MagicCrystal", true);
        
        ladderRoom.setExit("west", riddleRoom, true);
        ladderRoom.setItemUse("GrappleHook", true);
        
        holeRoom.setExit("down", bedRoom, true);
        holeRoom.setExit("south", ladderRoom, true);
        holeRoom.setExit("north", exit, true);
        
        bedRoom.setExit("south", riverRoom, true); 
        bedRoom.addItem("MasterKey");
        bedRoom.addItemUse("Trumpet", true);
        
        exit.setExit("north", outside, false);
        exit.setExit("south", holeRoom, true);
        exit.setLockItem("north", "MasterKey");
        
        outside.setExit("south", exit, true);
        outside.setExit("north", stillOutside, true);
        outside.setExit("east", stillOutside, true);
        outside.setExit("west", stillOutside, true);
        
        stillOutside.setExit("south", stillOutside, true);
        stillOutside.setExit("north", stillOutside, true);
        stillOutside.setExit("east", stillOutside, true);
        stillOutside.setExit("west", stillOutside, true);                      

        currentRoom = cell;  // start game in the cell
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to CAPTURED BY SQOGS!\n");
        System.out.println("You have been CAPTURED BY SQOGS!");
        System.out.println("I wouldn't worry about that much, SQOGS are pretty stupid.");
        System.out.println("They usually make their basses in old abandonded dungeons.");
        System.out.println("There must be an exit somewhere...\n");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());        
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }
        //List current inventory items if player inputs 'inventory'
        if (commandWord == CommandWord.INVENTORY) {
            inventory.listItems();
        }
        
        if (commandWord == CommandWord.USE) {
            use(command);
        }
        
        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        
        if (commandWord == CommandWord.SEARCH) {
            currentRoom.setSearched();
            if (!(currentRoom.searchRoom()).isEmpty()){
                System.out.println("You search around, and notice something");                
                inventory.addItems(currentRoom.searchRoom()); 
                currentRoom.emptyRoom();
            }
            else if(currentRoom.checkSwitches()) {
                System.out.println("You notice something small, it's a switch");                
            }            
            else{
                System.out.println("You search, but there is nothing to be found");
            }
        }        
        
        if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        
        if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone.");
        System.out.println("You have been CAPTURED BY SQOGS");
        System.out.println("There must be an exit somewhere...");
        System.out.println("\nYour command words are:");
        parser.showCommands();
    }
    
    /**
     * Try to use the specified command. If valid command, enact specific instructions else 
     * print an error message.
     */
    private void use(Command command)
    {        
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to use...
            System.out.println("Use what?");
            return;
        }
        String used = command.getSecondWord();
        //tries to use a switch if there is one
        if (used.equals("switch")){       
            if (currentRoom.checkSwitches() && currentRoom.searched()){ 
                (currentRoom.getSwitchRoom(0)).toggleExitStatus(currentRoom.getSwitchExit(currentRoom.getSwitchRoom(0)));
                System.out.println("You hear a distant clicking sound");
                return;
            }                                           
        
            else if (!currentRoom.checkSwitches()){
                System.out.println("You can't see a switch");
                return;
            }
        }
            
        if (!inventory.containsItem(used)) {
            System.out.println("You don't have one of those...");
            return;
        }
        
        else if (!currentRoom.getItemUse(used)){
            System.out.println("That has no use here");
            return;
        }
        
        //at this point we know an item is used by the current room, has not already been used(will be set to used shortly)
        //and that it is in the inventory. So now we can define what to do given the use of an item, as we will already
        //be in required room. We also know the user isn't trying to use a switch.
        //So here you can essentially add scripts for the game to run given an item use.
        
        if (used.equals("SolidStick")){
            System.out.println("\nAs the SQOG aims his next throw you line yourself up."); 
            System.out.println("A rock comes hurtling towards you with surprising accuracy,");
            System.out.println("considering it was thrown by a SQOG.");
            System.out.println("You swing the SolidStick stricking the rock directly back at the SQOG.");
            System.out.println("The rock hits the SQOG square in the face.");
            System.out.println("\nThe SQOG turns and runs down a tunnel behind it");
            System.out.println("dropping a shiny object from the ledge as it scarpers");
            currentRoom.addItem("ShinyKey");
            currentRoom.setItemUse("SolidStick", false);     
            currentRoom.setDescription("in an open cavern shaped like a room");
        }
        
        if (used.equals("MagicCrystal")){
            System.out.println("You give the MagicCrystal a rub");
            System.out.println("There is a blinding flash of light and then before you know it...");
            System.out.println("A Riddle Master appears, a mystical creature from stories you heard as a child");
            System.out.println("In a booming voice which seems to come from inside your mind the Riddle Mmaster speaks\n");
            System.out.println("'A princess is as old as the prince will be when the princess is twice as old as the ");
            System.out.println("prince was when the princess' age was half the sum of their present age.\n");
            System.out.println("Which of the following, then, could be true?");
            System.out.println("A - The Prince is 30 and the Princess is 40");
            System.out.println("B - The Prince is 40 and the Princess is 30");
            System.out.println("C - They are both the same age");
            System.out.println("Answer simply, A, B or C");                        
            Scanner sc = new Scanner(System.in);
            String answer = sc.nextLine();
            if (answer.equals("A")){
                System.out.println("'So, you have figured it out, well done. I have opened the way forward to you'");
                System.out.println("There is another flash of light and poof, the Riddle Master is gone");                
                currentRoom.setItemUse("MagicCrystal", false);
                riddleRoom.setExit("east", ladderRoom, true);
                System.out.println(currentRoom.getLongDescription());
            }
            else{
                System.out.println("Well, that's not quite right is it");
                currentRoom = cell;
                System.out.println(currentRoom.getLongDescription());
            }
        }
        
        if (used.equals("GrappleHook")){
            System.out.println("You throw your GrappleHook at the ladder");
            System.out.println("Good shot! The ladder falls down granting access to the ledge above");
            ladderRoom.setExit("north", holeRoom, true);
            ladderRoom.setDescription("You are in a simple section of cave. There is a ladder onto a ledge ahead"); 
            System.out.println(currentRoom.getLongDescription());
            
        }
        
        if (used.equals("Trumpet")){
            System.out.println("You blow hard on your Trumpet");
            System.out.println("The SQOGS wake up...obviously. I'm starting to see how you got caught");
            System.out.println("in the first place...");
            currentRoom = cell;
            cell.setExit("north", cavernousHallway, false);
            System.out.println(currentRoom.getLongDescription());
        }          
    } 
    /** 
     * Try to go to one direction. If there is an unlocked and valid exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else if (!currentRoom.getExitStatus(direction)){ //checking if exit is locked            
            if (currentRoom.getUnlock(direction, inventory.getItemList())) {
                currentRoom.setExitStatus(direction, true);
                System.out.println("The key opened the door!");
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());                
            }
            else{
                System.out.println("That exit is locked!");
            }
        }        
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
