
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
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
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
        Room roomDevuelta = null;
        if (salida.equals("north")) 
            roomDevuelta = exits.get("northExit");
        if (salida.equals("south")) 
            roomDevuelta = exits.get("southExit");
        if (salida.equals("east")) 
            roomDevuelta = exits.get("eastExit");
        if (salida.equals("west")) 
            roomDevuelta = exits.get("westExit");
        if (salida.equals("southEast")) 
            roomDevuelta = exits.get("southEastExit");
        return roomDevuelta;
    }

    /**
     * Devuelve la información de las salidas existentes
     * Por ejemplo: "Exits: north east west" o "Exits: south" 
     * o "Exits: " si no hay salidas disponibles
     *
     * @return Una descripción de las salidas existentes.
     */
    public String getExitString() {
        String aDevolver = "Exits: ";
        if(exits.get("northExit") != null)
            aDevolver += "north ";
        if(exits.get("eastExit") != null)
            aDevolver += "east ";
        if(exits.get("southExit") != null)
            aDevolver += "south ";
        if(exits.get("southEastExit") != null)
            aDevolver += "southEast ";
        if(exits.get("westExit") != null)
            aDevolver += "west ";
 
        return aDevolver;
    }

    /**
     * Define una salida para esta sala
     * 
     * @param direccion La direccion de la salida (por ejemplo "north" o "southEast")
     * @param sala La sala que se encuentra en la direccion indicada
     */
    public void setExit(String direccion, Room sala) {
        if(direccion.equals("north"))
            exits.put("northExit" ,sala);
        else if(direccion.equals("east"))
            exits.put("eastExit" ,sala);
        else if(direccion.equals("south"))
            exits.put("southExit" ,sala);
        else if(direccion.equals("west"))
            exits.put("westExit" ,sala);
        else if(direccion.equals("southEast"))
            exits.put("southEastExit" ,sala);

    }

}
