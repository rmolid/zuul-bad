import java.util.Stack;
/**
 * La clase Plater representa a un jugador del juego.
 * 
 * @author Raquel Molina Diaz
 * @version 2020.04.30
 */
public class Player
{
    private Room currentRoom;
    private Stack<Room> visitadas;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room currentRoom)
    {
        // initialise instance variables
        this.currentRoom = currentRoom;
        visitadas = new Stack<Room>();
    }

    /**
     * Habitacion actual del jugador
     * @return Room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
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
        else {
            visitadas.push(currentRoom);
            currentRoom = nextRoom;
            printLocationInfo();

        }
    }

    public void back() 
    {
        if(visitadas.empty()){
            System.out.println("Estás al inicio del juego");
        }else{
            currentRoom = visitadas.pop();
            printLocationInfo();
        }

    }

    public void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    public void eat() {
        System.out.println("You have eaten now and you are not hungry any more");
    }

    public void printLocationInfo()
    {        
        System.out.println(currentRoom.getLongDescription());

        System.out.println();
    }
}