import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * 
 *  * This class is part of the "CAPTUED BY SQOGS" application. 
 * "CAPTURED BY SQOGS" is a simple, text based adventure game.
 * 
 * An inventory of items 
 * @author 162225 
 */
public class Inventory
{
    private List<String> inventory;
     /**
     * Constructor for Inventory
     * constructs an empty inventory
     */
    public Inventory()
    {
        inventory = new ArrayList<String>();
    }

    /**
     * Adds a list of passed items to the inventory
     * a list is used to be more flexible.
     * @param i The list of items to be added
     */
    public void addItems(List<String> items)
    {  
       Iterator<String> it = items.iterator();
       while(it.hasNext()){
           String take = it.next();
           inventory.add(take);           
           System.out.println(take + " added to inventory");
       }
    }
    
    /**
     * Prints a list of items in the inventory
     */
    public void listItems(){
       Iterator<String> it = inventory.iterator();
       System.out.println("Inventory contains: ");
       while(it.hasNext()){
           System.out.println(it.next() + " ");
       }
    }
    
    /**
     * Returns an ArrayList of the inventory items
     * @return a list of strings, being the inventory items.
     */ 
    public List getItemList(){
        return inventory;//.toArray();
    }
   
    /**
     * Checks if given item is in inventory, returns true or false.
     * @param i The item to check
     * @return a boolean
     */
    public Boolean containsItem(String i)
    {
        return inventory.contains(i);
    }
    
}
