
/**
 * 
 * @author bob313 cdc97
 *
 */
public class Handle {
    private int mem_pool;
    private int length;
    private String key;
    private boolean deleted;

/**
 * 
 * @param mem is the mem pool record location
 * @param size is the size of the record 
 * @param name is the name of the record
 */
    public Handle(int mem, int size, String name) {
        mem_pool = mem;
        length = size;
        key = name;
        this.deleted = false;
    }


    public String key() {
        return key;
    }


    public void setDeleted(boolean state) {
        this.deleted = state;
    }


    public boolean getDeleted() {
        return this.deleted;
    }
}
