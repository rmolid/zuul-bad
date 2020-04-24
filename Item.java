
/**
 * Esta clase representa un objeto que tiene una descripcion y un peso.
 * 
 * @author Raquel Molina Diaz 
 * @version 2020.04.24
 */
public class Item
{

    private String itemDescription;
    private int itemWeight;

    /**
     * Constructor para los objetos de la clase Item
     */
    public Item(String itemDescription, int itemWeight)
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }

    /**
     * Devuelve un String con la descripcion del objeto
     */
    public String getItemDescription(){
        return itemDescription;
    }
    
    /**
    * Devuelve el peso del objeto
    */
    public int getItemWeight(){
        return itemWeight;
    }
}