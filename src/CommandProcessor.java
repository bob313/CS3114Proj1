import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author cdc97 bob313
 * @version 9.5.18
 *          Processes command file and feeds instructions Hash and
 *          MemoryManager.
 * 
 */

public class CommandProcessor {
    private Hash hash;
    private MemoryManager manager;


    /**
     * 
     * Constructor for CommandProcessor. Takes inputs from main method and
     * processes the commands.
     * 
     * @param memorySize
     *            size of MemoryManager received from main method
     * 
     * @param hashSize
     *            size of Hash received from main method
     * 
     * @param file
     *            the input file containing the commands to be read and
     *            processed
     * 
     */

    public CommandProcessor(String memorySize, String hashSize, String file) {
        hash = new Hash(Integer.valueOf(hashSize));
        manager = new MemoryManager(Integer.valueOf(memorySize));
        Scanner scan = null;
        try {
            scan = new Scanner(new File(file));
        }
        catch (FileNotFoundException e) {
            System.out.println("Comman File Not Found");
            e.printStackTrace();
        }
        while (scan.hasNextLine()) {
            processCommand(scan.nextLine());
        }
    }


    /**
     * 
     * Processes a given line of the command file for the command found in the
     * line
     * 
     * @param commandString
     *            the line of the command file being processed
     * 
     * @return true if proper command present false if not
     * 
     */
    private boolean processCommand(String commandString) {
        if (commandString.contains("update")) {
            update(commandString);
            return true;
        }
        if (commandString.contains("add")) {
            add(commandString);
            return true;
        }
        if (commandString.contains("delete")) {
            delete(commandString);
            return true;
        }
        if (commandString.contains("print")) {
            print(commandString);
            return true;
        }
        return false;
    }


    /**
     * 
     * Handles the update add command and update delete command
     * 
     * @param updateCommand
     *            update command string
     * 
     */
    private void update(String updateCommand) {
        String updateName = updateCommand.replace("update", "");
        // boolean check;
        if (updateCommand.contains("add")) {
            this.updateAdd(updateName);
        }
        else {
            updateName = updateName.replace("delete", "");
            updateName = formatString(updateName);
            String[] inputs = updateName.split("<SEP>");
            Handle handle = hash.searchHandle(inputs[0]);
            if (handle != null) {
                String oldRecord = handle.getRecord();
                String[] temp = oldRecord.split("<SEP>");
                int foundKey = -1;
                for (int i = 1; i < temp.length; i += 2) {
                    if (temp[i].equals(inputs[1])) {
                        foundKey = i;
                        break;
                    }
                }
            }
        }
    }

    private void updateAdd(String updateName) {
        updateName = updateName.replace("add", "");
        updateName = formatString(updateName);
        String[] inputs = updateName.split("<SEP>");
        Handle handle = hash.searchHandle(inputs[0]);

        if (handle != null) {
            String oldRecord = handle.getRecord();
            String[] temp = oldRecord.split("<SEP>");
            int foundKey = -1;
            for (int i = 1; i < temp.length; i += 2) {
                if (temp[i].equals(inputs[1])) {
                    foundKey = i;
                    break;
                }
            }
            String newRecord = "";
            if (foundKey != -1) {
                for (int i = 0; i < temp.length; i++) {
                    if (i != foundKey && i != foundKey + 1) {
                        newRecord = newRecord + temp[i];
                        if (i < temp.length) {
                            newRecord = newRecord + "<SEP>";
                        }
                    }
                }
                newRecord = newRecord + "<SEP>" + inputs[1].trim() + "<SEP>"
                    + inputs[2].trim();
            }
            else {
                newRecord = oldRecord + "<SEP>" + inputs[1].trim() + "<SEP>"
                    + inputs[2].trim();
            }
            handle.setRecord(newRecord);
            System.out.println("Updated Record: |" + newRecord + "|");
        }
        else {
            System.out.println("|" + inputs[0]
                + "| not updated because it does not exist.");
        }
    }

    /**
     * handles the print commands
     * 
     * @param printCommand
     *            print command string
     */
    private void print(String printCommand) {
        if (printCommand.contains("blocks")) {
            System.out.println(manager.getPoolSize() + ": 0");
        }
        else {
            hash.print();

        }
    }


    /**
     * Handles the add command
     * 
     * @param addCommand
     *            add command string
     */
    private void add(String addCommand) {
        String name;
        name = addCommand.replace("add", "");
        name = formatString(name);
        boolean check;
        Handle handle = manager.getHandle(name);
        handle.setRecord(name); // for delete
        check = hash.add(name, handle);
        if (check) {
            System.out.println("|" + name
                + "| has been added to the Name database.");
        }
        else {
            System.out.println("|" + name
                + "| duplicates a record already in the Name database.");
        }
    }


    /**
     * Handles the delete command
     * 
     * @param deleteCommand
     *            delete command string
     */
    private void delete(String deleteCommand) {
        String name;
        name = deleteCommand.replace("delete", "");
        name = formatString(name);
        boolean check;
        check = hash.remove(name);
        if (check) {
            System.out.println("|" + name
                + "| has been deleted from the Name database.");
        }
        else {
            System.out.println("|" + name + "| not deleted because it does"
                + " not exist in the Name database.");
        }
    }


    /**
     * Formats input strings to remove excess spaces and command words (i.e.
     * update, add, delete)
     * 
     * @param nameString
     *            unformatted string
     * @return formatted string
     */
    private String formatString(String nameString) {
        StringBuilder newString = new StringBuilder();
        char[] chars = nameString.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!Character.isWhitespace(chars[i])) {
                while (i < chars.length && !Character.isWhitespace(chars[i])) {
                    newString.append(chars[i]);
                    i++;
                }
                newString.append(" ");
            }
        }
        newString.deleteCharAt(newString.length() - 1);
        return newString.toString();
    }
}
