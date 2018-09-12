
public class FreeBlock {

    private int size;
    private LinkedList<Integer> block;
    
    /**
     * 
     * @param size is the size of the freeblock
     */
    public FreeBlock(int blocksize) {
        size = blocksize;
        block = new LinkedList<Integer>();
    }
    
    /**
     * 
     * @return the size
     */
    public int getSize() {
        return size;
    }
    
    /**
     * 
     * @return the linked list
     */
    public LinkedList<Integer> getList(){
        return block;
    }
    
}