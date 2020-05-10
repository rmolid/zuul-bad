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
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player(createRooms(), 700);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
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

        return comedor;  // start game outside
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
        player.look();
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
            player.goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            player.look();
        }
        else if (commandWord.equals("eat")){
            player.eat();
        }
        else if (commandWord.equals("back")){           
            player.back();
        }
        else if(commandWord.equals("take")){
            player.take(command);
        }
        else if(commandWord.equals("items")){
            player.items();
        }
        else if(commandWord.equals("drop")){
            player.drop(command);
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



}
