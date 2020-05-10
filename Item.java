/**
 * Esta clase representa un objeto que tiene una descripcion y un peso.
 * 
 * @author Raquel Molina Diaz 
 * @version 2020.04.24
 */
public class Item
{

    private String itemDescription;
    private String id;
    private boolean canTakeItem;

    /**
     * Constructor para los objetos de la clase Item
     */
    public Item(String itemDescription, String id, boolean canTakeItem)
    {
        this.itemDescription = itemDescription;
        this.id = id;
        this.canTakeItem = canTakeItem;
    }

    /**
     * Devuelve un String con la descripcion del objeto
     */
    public String getItemDescription(){
        return itemDescription;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean canTakeItem() {
        return this.canTakeItem;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + "\n" +
                "Descripcion: " + getItemDescription() + "\n";
    }
}