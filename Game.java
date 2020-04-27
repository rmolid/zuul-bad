import java.util.Stack;
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

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room comedor, cocina,dormitorio, cuartel, armeria, establo, corral;

        // create the rooms
        comedor = new Room("Comedor del castillo, encuentra a Buc�falo");
        cocina = new Room("Cocina");
        cuartel = new Room("Cuartel");
        armeria = new Room("Armeria");
        establo = new Room("Establo, Buc�falo!!");
        dormitorio = new Room("Dormitorio");
        corral = new Room ( "Corral");

        cocina.addItem(new Item("Frutero", 200));
        corral.addItem(new Item("Cubo", 50));
        corral.addItem(new Item("Herradura", 10));
        dormitorio.addItem(new Item("Cofre de oro", 500));
        cuartel.addItem(new Item("Casco", 120));
        armeria.addItem(new Item("Espada", 1100));

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

        return wantToQuit;
    }

    // implementations of user commands:

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
