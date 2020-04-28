import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael K�lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> visitadas;
    private ArrayList<Item> items;
    private int pesoMax;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        items = new ArrayList<Item>();
        pesoMax = 1250;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room comedor, cocina,dormitorio, cuartel, armeria, establo, corral;

        // create the rooms
        comedor = new Room("Comedor del castillo, encuentra a Bucefalo");
        cocina = new Room("Cocina");
        cuartel = new Room("Cuartel");
        armeria = new Room("Armeria");
        establo = new Room("Establo, Bucefalo!!");
        dormitorio = new Room("Dormitorio");
        corral = new Room ( "Corral");

        cocina.addItem(new Item("Frutero con manzanas", 200 , "frutero",true));
        corral.addItem(new Item("Cubo con agua", 50, "cubo", false));
        corral.addItem(new Item("Herradura de caballo", 10, "herradura", true));
        dormitorio.addItem(new Item("Cofre de oro", 500, "cofre", true));
        cuartel.addItem(new Item("Casco de acero", 120,"casco", false));
        armeria.addItem(new Item("Espada del rey", 1100, "espada", true));

        // initialise room exits        
        comedor.setExit("east", cuartel);
        comedor.setExit("south", dormitorio);
        comedor.setExit("west", cocina);
        comedor.setExit("southEast", armeria);

        cocina.setExit("east", comedor);
        cocina.setExit("abajo", corral);

        corral.setExit ("arriba", cocina);

        dormitorio.setExit("north", comedor);

        cuartel.setExit("south", armeria);
        cuartel.setExit("west", comedor);

        armeria.setExit("north", cuartel);
        armeria.setExit("south", establo);

        establo.setExit("north", armeria);

        currentRoom = comedor;  // start game outside
        visitadas = new Stack<>();
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
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")){
            eat();
        }
        else if (commandWord.equals("back")){           
            back();
        }
        else if (commandWord.equals("take")){
            take(command);
        }else if (commandWord.equals("drop")){
            drop(command);
        }else if (commandWord.equals("items")){
            items();
        }

        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * Muestra los objetos que hay en la mochila
     */
    private void items(){
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
    private void drop(Command comando) {
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

    /**
     * Metodo que permite coger objetos en una habitacion
     * @param comando
     */
    private void take(Command comando) {
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
                    if(peso <= pesoMax ){
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
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
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
        else {
            visitadas.push(currentRoom);
            currentRoom = nextRoom;
            printLocationInfo();

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

    private void back() 
    {
        if(visitadas.empty()){
            System.out.println("Est�s al inicio del juego");
        }else{
            currentRoom = visitadas.pop();
            printLocationInfo();
        }

    }

    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    private void eat() {
        System.out.println("You have eaten now and you are not hungry any more");
    }

    private void printLocationInfo()
    {        
        System.out.println(currentRoom.getLongDescription());

        System.out.println();
    }
}
