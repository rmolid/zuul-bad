import java.util.ArrayList;
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
    private ArrayList<Item> items;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room currentRoom)
    {
        // initialise instance variables
        this.currentRoom = currentRoom;
        this.visitadas = new Stack<Room>();
        this.items = new ArrayList<>();
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
            System.out.println("Estas al inicio del juego");
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

   /**
     * Metodo que permite coger objetos de una habitacion
     * @param comando
     */
    public void take(Command comando) {
        //el comando introducido no es valido
        if(!comando.hasSecondWord()) {
            System.out.println("Introduce un Id correcto");
            return; //temina la ejecucion de la funcion take
        }

        String idItem = comando.getSecondWord();

        if(currentRoom.getItems().isEmpty()) {
            System.out.println("La sala no tiene objetos");
        }else{
            //Si existe el objeto en la sala
            Item objeto = currentRoom.find(idItem);
            if(objeto != null){
                this.items.add(objeto);
                currentRoom.removeItem(idItem);
            }else {
                System.out.println("No existe ese objeto en la sala");
            }
        }
    }
}