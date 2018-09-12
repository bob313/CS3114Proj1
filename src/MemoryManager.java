

/**
 * 
 * @author bob313 cdc97
 * @version sep 5 2018
 *
 */
public class MemoryManager {

    // Constructor. poolsize defines the size of the memory pool in bytes
    private int poolSize;
    private LinkedList<FreeBlock> free;


    /**
     * 
     * @param poolsize
     *            is the size of the memory pool
     */
    public MemoryManager(int poolsize) {
        poolSize = poolsize;
        free = new LinkedList<FreeBlock>();
        FreeBlock size = new FreeBlock(poolsize);
        free.add(size);
    }


    /**
     * temp function for making a handle. NOT FINAL VERSION
     * 
     * @param name
     *            name string
     * @return the handle
     */
    public Handle getHandle(String name) {
        Handle tempHandle = new Handle(5, 5, name);
        return tempHandle;
    }


    /**
     * 
     * @return the Pool size
     */
    public int getPoolSize() {
        return poolSize;
    }
    // Insert a record and return its position handle.

    // space contains the record to be inserted, of length size.

// public Handle insert(byte[] space, int size);

    // Return the length of the record associated with theHandle

// public int length();

    // Free a block at the position specified by theHandle.

    // Merge adjacent free blocks.

// public void remove(Handle theHandle);

    // Return the record with handle posHandle, up to size bytes, by

    // copying it into space.

    // Return the number of bytes actually copied into space.

// public int get(byte[] space, Handle theHandle, int size);

    // Dump a printout of the freeblock list

// public void dump();

}
