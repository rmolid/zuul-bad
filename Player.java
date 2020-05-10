import java.util.ArrayList;
import java.util.Iterator;
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
    private int pesoMax;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room currentRoom, int pesoMax)
    {
        // initialise instance variables
        this.currentRoom = currentRoom;
        this.visitadas = new Stack<Room>();
        this.items = new ArrayList<>();
        this.pesoMax = pesoMax;
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
     * Metodo que permite coger objetos en una habitacion
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
                if(objeto.canTakeItem()){
                    int peso = pesoAcumulado() + objeto.getItemWeight();
                    if(peso <= this.pesoMax ){
                        //Lo puedo coger
                        this.items.add(objeto);
                        currentRoom.removeItem(idItem);
                    }else{
                        System.out.println("Llevas mucho peso.");
                    }
                }else{
                    System.out.println("No se puede coger ese objeto");
                }
            }else {
                System.out.println("No existe ese objeto en la sala");
            }
        }
    }


    /**
     * Muestra los objetos que hay en la mochila
     */
    public void items(){
        if(items.isEmpty()){
            System.out.println("No llevas objetos!");
        }else{
            String itemDescription = "Mis objetos son: \n";
            for(Item item : this.items){
                itemDescription += item.toString();
            }

            itemDescription += "El peso acumulado es: " + pesoAcumulado() + " gr. \n";
            System.out.println(itemDescription);

        }

    }

    /**
     * Muestra el peso total de los objetos de la mochila
     */
    public int pesoAcumulado() {
        int pesoAcumulado = 0;
        for (Item item : this.items) {
            pesoAcumulado += item.getItemWeight();
        }

        return pesoAcumulado;
    }

        /**
     * Metodo que permite dejar un objeto cuando este este en la mochila.
     * Si el objeto no existe o no esta en la mochila se muestra un mensaje por pantalla
     */
    public void drop(Command comando) {
        if(!comando.hasSecondWord()) {
            System.out.println("Introduce un Id correcto");
            return;
        }

        String idItem = comando.getSecondWord();

        if(!this.items.isEmpty()){
            //Tengo objetos
            if(existItem(idItem)){
                //Si existe el objeto lo borro y lo agrego a la habitacion actual
                Item item = removeItem(idItem);
                currentRoom.addItem(item);
            }else{
                //Si NO existe el objeto
                System.out.println("No existe el objeto que quieres dejar.");
            }
        }else {
            System.out.println("No tienes objetos en la mochila.");
        }

    }

    /**
    * Metodo que nos dice que el objeto existe conociendo su id.
    */
    public boolean existItem(String id) {
        boolean existItem = false;
        for(Item objeto : this.items){
            if(objeto.getId().equals(id)){
                existItem = true;
            }
        }
        return existItem;
    }

    /** 
     * Elimina un objeto de la mochila 
     * Devuelve null si no existe el objeto
     */
    public Item removeItem(String id){
        Iterator<Item> iterator = this.items.iterator();
        boolean borrado = false;
        Item currentItem = null;
        while(iterator.hasNext() && !borrado){
            currentItem = iterator.next();
            if(currentItem.getId().equals(id)){
                iterator.remove();
                borrado = true;
            }
        }

        return currentItem;
    }
}