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
            System.out.println("Command File Not Found");
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
        String[] inputs = commandString.trim().split("\\s+");
        if (inputs[0].equals("update")) {
            update(commandString);
            return true;
        }
        if (inputs[0].equals("add")) {
            add(commandString);
            return true;
        }
        if (inputs[0].equals("delete")) {
            delete(commandString);
            return true;
        }
        if (inputs[0].equals("print")) {
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
        String updateName = updateCommand.replaceFirst("update", "");
        // boolean check;
        String[] inputs = updateName.trim().split("\\s+");
        if (inputs[0].equals("add")) {
            this.updateAdd(updateName);
        }
        else {
            this.updateDelete(updateName);
        }
    }


    /**
     * add a field and value
     * (if that field is not already part of the record),
     * or else we will replace an existing value.
     * 
     * @param updateName
     *            is the inputs to add
     */
    private void updateAdd(String updateName) {
        updateName = updateName.replaceFirst("add", "");
        updateName = formatString(updateName);
        String[] inputs = updateName.split("<SEP>");
        for (int i =0; i < inputs.length; i++) {
            inputs[i] = inputs[i].trim();
        }
        Handle handle = hash.searchHandle(inputs[0].trim());
        if (handle != null && inputs.length > 1) {
            String oldRecord = manager.getRecord(handle);
            String[] temp = oldRecord.split("<SEP>");
            int foundKey = -1;
            for (int i = 1; i < temp.length; i += 2) {
                if (temp[i].equals(inputs[1].trim())) {
                    foundKey = i;
                    break;
                }
            }
            String newRecord = "";
            if (foundKey != -1) {
                for (int i = 0; i < temp.length; i++) {
                    if (i != foundKey && i != foundKey + 1) {
                        newRecord = newRecord + temp[i];
                        newRecord = newRecord + "<SEP>";
                    }
                }
                newRecord = newRecord + inputs[1].trim() + "<SEP>" + inputs[2]
                    .trim();
            }
            else {
                newRecord = oldRecord + "<SEP>" + inputs[1].trim() + "<SEP>"
                    + inputs[2].trim();
            }
            hash.remove(inputs[0]);
            byte[] record = newRecord.getBytes();
            manager.remove(handle);
            Handle newHandle = manager.insert(record, record.length, inputs[0]);
            hash.add(inputs[0], newHandle);
            System.out.println("Updated Record: |" + newRecord + "|");
        }
        else {
            System.out.println("|" + inputs[0].trim()
                + "| not updated because it does not "
                + "exist in the Name database.");
        }
    }


    private boolean recordField(String[] record, String field) {
        for (int i = 1; i < record.length; i = i + 2) {
            if (record[i].equals(field)) {
                return (true);
            }
        }
        return false;
    }


    /**
     * remove that field name and its value from the
     * record (if it exists)
     * 
     * @param updateName
     *            is the input to remove
     */
    private void updateDelete(String updateName) {
        updateName = updateName.replaceFirst("delete", "");
        updateName = formatString(updateName);
        String[] inputs = updateName.split("<SEP>");
        for (int i =0; i < inputs.length; i++) {
            inputs[i] = inputs[i].trim();
        }
        Handle handle = hash.searchHandle(inputs[0].trim());
        if (handle != null && inputs.length > 1) {
            String record = manager.getRecord(handle);
            String[] temp = record.split("<SEP>");
            if (recordField(temp, inputs[1].trim())) {
                int found = 0;
                for (int i = 0; !temp[i].equals(inputs[1].trim()); i++) {
                    found = i + 1;
                }
                found++;
                String delete = "<SEP>" + inputs[1].trim() + "<SEP>"
                    + temp[found];
                record = record.replace(delete, "");
                hash.remove(inputs[0]);
                byte[] newRecord = record.getBytes();
                manager.remove(handle);
                Handle newHandle = manager.insert(newRecord, newRecord.length,
                    inputs[0]);
                hash.add(inputs[0], newHandle);
                System.out.println("Updated Record: |" + record + "|");
            }
            else {
                System.out.println("|" + temp[0]
                    + "| not updated because the field |" + inputs[1].trim()
                    + "| does not exist");
            }
        }
        else {
            System.out.println("|" + inputs[0].trim()
                + "| not updated because it does "
                + "not exist in the Name database.");
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
            manager.dump();
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
        name = addCommand.replaceFirst("add", "");
        name = formatString(name);
        byte[] record = name.getBytes();
        boolean check = hash.search(name.trim());
        if (!check) {
            Handle handle = manager.insert(record, record.length, name);
            hash.add(name, handle);
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
        name = deleteCommand.replaceFirst("delete", "");
        name = formatString(name);
        Handle handle = hash.searchHandle(name);
        boolean check = hash.remove(name);
        if (check) {
            manager.remove(handle);
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


    /**
     * gets the Hash of commandP
     * 
     * @return CommandP's hash
     */
    public Hash getHash() {
        return hash;
    }
}
