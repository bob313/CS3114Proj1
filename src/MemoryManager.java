import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Christian
 *
 */
public class CommandProcessor {
    
    private Hash hash;
    private MemoryManager manager;
    
    public CommandProcessor(int memorySize, int hashSize, File file)
    {
        hash = new Hash(hashSize);
        manager = new MemoryManager(memorySize);
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            System.out.println("Comman File Not Found");
            e.printStackTrace();
        }
        while (scan.hasNextLine())
        {
            processCommand(scan.nextLine());
        }
        
    }
    
    private boolean processCommand(String commandString) {
        if (commandString.contains("update"))
        {
            update(commandString);
            return true;
        }
        if (commandString.contains("add"))
        {
            add(commandString);
            return true;
        }
        if (commandString.contains("delete"))
        {
            delete(commandString);
            return true;
        }
        if (commandString.contains("print"))
        {
            print(commandString);
            return true;
        }
        return false;
        
    }

    private void update(String updateCommand) {
        String updateShort = updateCommand.replace("update", "");
        //boolean check;
        if (updateCommand.contains("add"))
        {
            updateShort = updateShort.replace("add", "");
            updateShort = formatString(updateShort);
            String[] inputs = updateShort.split("<SEP>");
            //check = hash.updateAdd(inputs[0], inputs[1], inputs[2]);
           
            System.out.println("|"+inputs[0]+"| todo");
        } else {
            updateShort = updateShort.replace("delete", "");
            updateShort = formatString(updateShort);
            String[] inputs = updateShort.split("<SEP>");
            //check = hash.updateDelete(inputs[0], inputs[1]);
            System.out.println("|"+inputs[0]+"| todo");
        }
        
        
    }

    private void print(String printCommand) {
        if (printCommand.contains("hashtable"))
        {
            hash.print();
        }
        else {
            System.out.println("Formatting?");
        }
        
        
    }

    private void add(String addCommand)
    {
        String name;
        name = addCommand.replace("add", "");
        name = formatString(name);
        boolean check;
        Handle handle = manager.getHandle(name);
        check = hash.add(name, handle);
        if (check)
        {
            System.out.println("|"+name+"| has been added to the Name database.");
        }
        else {
            System.out.println("|"+name+"| duplicates a record already in the Name database.");
        }
    }
    
    private void delete(String deleteCommand)
    {
        String name;
        name = deleteCommand.replace("delete", "");
        name = formatString(name);
        boolean check;
        check = hash.remove(name);
        if (check)
        {
            System.out.println("|"+name+"| has been deleted from the Name database.");
        }
        else {
            System.out.println("|"+name+"| not deleted because it does not exist in the Name database.");
        }
    }
    
    private String formatString(String nameString)
    {
        StringBuilder newString = new StringBuilder();
        char[] chars = nameString.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            if (!Character.isWhitespace(chars[i]))
            {
                while (i < chars.length && !Character.isWhitespace(chars[i]))
                {
                    newString.append(chars[i]);
                    i++;
                }
                newString.append(" ");
            }
        }
        newString.deleteCharAt(newString.length()-1);
        return newString.toString();
    }

}
