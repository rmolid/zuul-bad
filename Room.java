
import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
    }

    /**
     * Add item to the room
     * @param item
     */
    public void addItem(Item item)
    {
        this.items.add(item);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Devuelve la sala vecina a la actual que esta ubicada en la direccion indicada como parametro.
     *
     * @param salida Un String indicando la direccion por la que saldriamos de la sala actual
     * @return La sala ubicada en la direccion especificada o null si no hay ninguna salida en esa direccion
     */
    public Room getExit(String salida) {
        return exits.get(salida);
    }

    /**
     * Devuelve la informacion de las salidas existentes
     * Por ejemplo: "Exits: north east west" o "Exits: south" 
     * o "Exits: " si no hay salidas disponibles
     *
     * @return Una descripcion de las salidas existentes.
     */
    public String getExitString() {
        String aDevolver = "Exits: ";
        for (String descripcion : exits.keySet()) {
            aDevolver += descripcion + " ";
        }

        return aDevolver;
    }

    /**
     * Define una salida para esta sala
     * 
     * @param direccion La direccion de la salida (por ejemplo "north" o "southEast")
     * @param sala La sala que se encuentra en la direccion indicada
     */
    public void setExit(String direccion, Room sala) {
        exits.put(direccion, sala);
    }

    /**
     * Devuelve un texto con la descripcion completa de la habitacion, que 
     * incluye la descripcion corta de la sala, las salidas de la misma as� como
     * la descripcion del objerto y peso en gramos. Por ejemplo:
     *     You are in the lab
     *     Exits: north west southwest
     *     Objeto: Espada de acero 1200gr
     * @return Una descripcion completa de la habitacion incluyendo sus salidas
     */
    public String getLongDescription(){
        String aDevolver = "You are in the " + getDescription() + "\n" + getExitString();


        //Mi sala contiene objetos
        if(this.items.size() > 0 ){
            for(Item item : this.items) {
                aDevolver = aDevolver + "\nObjeto: " + item.getItemDescription()+ "\nPeso del objeto: " + item.getItemWeight() + "gr";
            }
        }else { //Mi sala no contiene objetos
            aDevolver = aDevolver + "\nObjeto: " + "Esta sala no tiene ningun objeto.";
        }
        
        
        return aDevolver; 
    } 
}
