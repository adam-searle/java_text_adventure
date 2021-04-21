import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "CAPTUED BY SQOGS" application. 
 * "CAPTURED BY SQOGS" is a simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room. Information is also stored 
 * on the lock status of an exit. A room can be unlocked with an item.
 * There are switches to unlock exits. A room can contain items to be
 * collected by the player 
 * 
 * @author  162225
 * @version 1
 */

public class Room 
{
    private String description;                 // description of a room
    private HashMap<String, Room> exits;        // stores exits of this room.
    private HashMap<String, Boolean> unlocked;  // stores each exits lock state.
    private HashMap<String, String> lockItem;   // stores information for lock items, e.g keys.
    private HashMap<Room, String> switches;     // stores information on switches
    private HashMap<String, Boolean> itemUse;   // checking if a given has/can been used in this room    
    private List<String> items;                 // items in the room
    private Boolean searched;                   // whether a room has been searched or not
    
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        searched = false;
        this.description = description;
        items = new ArrayList<String>();
        exits = new HashMap<String, Room>();
        unlocked = new HashMap<String, Boolean>();
        lockItem = new HashMap<String, String>();         
        switches = new HashMap<Room, String>();
        itemUse = new HashMap<String, Boolean>();        
    }
        
   /**
    * Set a room as searched
    */
   public void setSearched(){
       searched = true;
    }
 
    /**
     * Checks to see if the room has been searched
     * @return a boolen true if the room has been searched, false if not
     */
    public Boolean searched(){
        return searched;
    }
    
    /**
     * Adds a switch to the room
     * @param The Room which holds the exit to be unlocked by the switch 
     * @param The exit in given room to be linked to the switch
     */ 
    public void addSwitch(Room room, String exit){
        switches.put(room, exit);
    }
    
    /**
     * Check if room has any switches.
     * @return a boolean, true if the room contains switches, else false.
     */
    public Boolean checkSwitches(){
        return !switches.isEmpty();
    }
    
    /**
     * Returns Room and Direction of switches in the room as a Set.
     * @return returns a Set of switches in the room.
     */
    public Set getSwtiches(){
        return switches.entrySet();
    }
    
    /**
     * Returns Room of swith N in the list of switches
     * @param N The number switch in the list to access.
     * @return returns a Room that the specified switch is linked to.
     */
    public Room getSwitchRoom(int N){
        List<Room> keys = new ArrayList<Room>(switches.keySet());
        return keys.get(N);
    }
    
    /**
     * Returns which exit a switch is linked to regarding corresponding Room.
     * @return a direction as a string for which exit a switch opens.
     */
    public String getSwitchExit(Room room){
        return switches.get(room);
    }
    
    /**
     * Adding an item to be used in this room
     * @param i item to be used
     * @param bool if item can be used
     */
    public void addItemUse(String i, Boolean bool){
        itemUse.put(i, bool);
    }
    
    /**
     * Check if an item can be used in this room
     * @param i The item to check
     * @return a boolean, true if an item can be used in the current room other wise false.
     */
    public Boolean getItemUse(String i){
        if (itemUse.get(i) == null){
            return false;
        }
        return itemUse.get(i);
    }
    
    /**
     * For setting item use status
     * @param i item to toggle
     */
    public void setItemUse(String i, Boolean bool){
        itemUse.put(i,bool);
    }
    
    /**
     * Add an item to the room.
     * @param i The item to be added.
     */
    public void addItem(String i) {
        items.add(i);
    }
    
    /**
     * Gets the specified item if it exists.
     * other wise returns null.
     * @param i The item requested.
     * @return the specified item, or null if it doesnt exist. 
     */
    public String getItem(String i){
        if (items.contains(i))
        {
           return i; 
        }
        return null;   
    }
    
    /**
     * Returns list of items in room, or if empty, returns empty list.
     * @return a list of items in the room
    */
   public List<String> searchRoom(){
       return items;
   }
   
   /**
    * Clears the room of items
    */
   public void emptyRoom(){
       items.clear();
    }
   
   /**
    * Sets the item required to unlock an exit
    * @param direction The exit to be linked to the item
    * @param lockItem The item required to unlock the exit
    */
   public void setLockItem(String direction, String lockitem){
       lockItem.put(direction, lockitem); 
    }
    
    /**
     * For checking if item required to unlock the given exit is in the inventory
     * @param direction The exit to be checked
     * @return a boolean, true item to unlock exit is in inventory, false otherwise.
     */
    public Boolean getUnlock (String direction, List inventory){
        if (inventory.contains(lockItem.get(direction))){
            return true;
        }
        else{
            return false;
        }
    }
  
    
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     * @param unLocked If the exit is unLocked.
     */    
    public void setExit(String direction, Room neighbor, Boolean unLocked) 
    {
        exits.put(direction, neighbor);
        unlocked.put(direction, unLocked);        
    }
    
    /**
     * Returns the current status of an exit (e.g locked or unlocked).
     * @param direction The direction to check.
     * @return a boolean, true if given exit is unlocked, false otherwise.
     */
    public Boolean getExitStatus(String direction)
    {
        return unlocked.get(direction);
    }
    
    /**
     * Set an exit to be locked or unlocked
     * @param direction The direction to be set.
     * @param unLocked The status of the lock, true of false.
     */
    public void setExitStatus(String direction, Boolean unLocked)
    {
        if (unLocked == true)
        {
            unlocked.put(direction, true);
        }
        
        else
        {
            unlocked.put(direction, false);
        }
    }
    
    /**
     * Toggle the lock status of an exit
     * @param The direction of the exit to toggle
     */
    public void toggleExitStatus(String direction)
    {
        if (unlocked.get(direction) == true)
        {
            unlocked.put(direction, false);
        }
        else
        {
            unlocked.put(direction, true);
        }
    }
    
    /**
     * Set the description of a room
     * @param Description The new description
     */    
    public void setDescription(String Description)
    {
        this.description = Description; 
    }
    
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     * 
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    
    

}

