/**
 * 
 * @author bob313 cdc97
 * @version sep 5 2018
 *
 */
public class MemoryManager {

    private int poolSize;
    private byte[] memoryPool;
    private LinkedList<FreeBlock> free;

    /**
     * Constructor. poolsize defines the size of the memory pool in bytes
     * @param poolsize
     *            is the size of the memory pool
     */
    public MemoryManager(int poolsize) {
        poolSize = poolsize;
        memoryPool = new byte[poolsize];
        FreeBlock size = new FreeBlock(poolsize);
        free.add(size);
        free.get(0).getList().add(0);
    
    }



    /**
     * 
     * @return the Pool size
     */
    public int getPoolSize() {
        return poolSize;
    }
    

    /**
     * Insert a record and return its position handle.
     * @param space record to be inserted
     * @param size size of the record
     * @param name name of the record
     * @return a handle containing the record's position, size and name
     */
    public Handle insert(byte[] space, int size, String name) {
        int blockSize = findBlockSize(size);
        int blockLocation = findBlockLocation(blockSize);
        for (int i = 0; i < size; i++) {
            memoryPool[blockLocation] = space[i];
        }
        removeFreeBlock(blockSize, blockLocation);
        Handle handle = new Handle(blockLocation, size, name);
        return handle;
    }
    
    /**
     * Finds the correct block size given a record size
     * @param recordSize the size of the record
     * @return the size of the block
     */
    private int findBlockSize(int recordSize)
    {
        int i = 0;
        while (Math.pow(2, i) < recordSize)
        {
            i++;
        }
        return (int)Math.pow(2, i);
    }
    
    /**
     * Finds the location of a suitable block from the free block list. 
     * If the found block is larger than expected it is resized.
     * If there is no suitable block found the memoryPool is expanded and 
     * the search is repeated.
     * @param blockSize the desired size of the block
     * @return the location of the block
     */
    private int findBlockLocation(int blockSize)
    {
        int blockLocation = 0;
        FreeBlock blocks = null;
        for (int i = blockSize; i <= poolSize; i=i*2) {
            for (int j = 0; j < free.size(); j++) {
                if (free.get(j).getSize() == i)
                {
                    blocks = free.get(j);
                }
            }
        }
        if (blocks != null)
        {
            blockLocation = blocks.getList().get(0);
            if (blocks.getSize() > blockSize)
            resizeBlock(blocks, blockSize);
        }
        else {
            expandMemoryPool();
            findBlockLocation(blockSize);
        }
        
        return blockLocation;
    }
    
    /**
     * Expands the memoryPool by doubling its size and adds
     * a new block to the free blocks list.
     */
    private void expandMemoryPool() {
        addFreeBlock(poolSize, poolSize);
        poolSize = poolSize * 2;
        byte[] newPool = new byte[poolSize];
        for (int i = 0; i < memoryPool.length; i++)
        {
            newPool[i] = memoryPool[i];
        }
        memoryPool = newPool;
    }

    /**
     * Resizes blocks by splitting them in half until they are the desired size.
     * @param blocks the FreeBlock that contains the correct sized blocks
     * @param desiredBlockSize the desired block size
     */
    private void resizeBlock(FreeBlock blocks, int desiredBlockSize) {
        int blockSize = blocks.getSize();
        int blockLocation = blocks.getList().get(0);
        while (blockSize != desiredBlockSize)
        {
            removeFreeBlock(blockSize, blockLocation);
            blockSize = blockSize / 2;
            addFreeBlock(blockSize, blockLocation + blockSize);
        }
    }
    
    /**
     * Adds a block to the free block list.
     * @param size size of the block being added
     * @param location location value of the block being added
     * @return true if block is added.
     */
    private boolean addFreeBlock(int size, int location) {
        for (int i = 0; i < free.size(); i++)
        {
            if (free.get(i).getSize() == size) {
                free.get(i).getList().add(location);
                return true;
            }
        }
        FreeBlock newBlock = new FreeBlock(size);
        newBlock.getList().add(location);
        free.add(newBlock);
        return true;
    }
    
    /**
     * Removes a block from the free block list.
     * @param size size of the block being removed
     * @param location location value of the block being removed
     */
    private void removeFreeBlock(int size, int location) {
        for (int i = 0; i < free.size(); i++) {
            if (free.get(i).getSize() == size) {
                free.get(i).getList().remove(location);
                if (free.get(i).getList().isEmpty()) {
                    free.remove(i);
                }
            }
        }
    }
    
    /**
     * Return the length of the record associated with theHandle
     * @param handle the handle to get the length of
     * @return the handle's record's length
     */
    public int length(Handle handle) {
        return handle.getLength();
    }

    
    /**
     * Free a block at the position specified by theHandle.
     * Merge adjacent free blocks.
     * @param theHandle the handle of the record to be removed
     */
    public void remove(Handle theHandle) {
        int location = theHandle.getMemPool();
        int blockSize = findBlockSize(theHandle.getLength());
        byte[] tempBytes = new byte[blockSize];
        for (int i = location; i < location + blockSize; i++) {
            memoryPool[i] = tempBytes[i];
        }
        addFreeBlock(blockSize, location);
        mergeBlocks();
    }
    
    /**
     * Merges adjacent free blocks with the same size.
     */
    private void mergeBlocks() {
        for (int i = 0; i < free.size(); i++) {
            int count = 0;
            while (free.get(i).getList().size() > 1) {
                int size = free.get(i).getSize();
                int loc = free.get(i).getList().get(count);
                if (loc + size == free.get(i).getList().get(count+1)) {
                    addFreeBlock(size*2, loc);
                    removeFreeBlock(size, loc + size);
                    removeFreeBlock(size, loc);
                }
            }
        }
    }

    // Return the record with handle posHandle, up to size bytes, by

    // copying it into space.

    // Return the number of bytes actually copied into space.

// public int get(byte[] space, Handle theHandle, int size);
    
    

    // Dump a printout of the freeblock list

    public void dump() {
        
    }

}
