
/**
 * Stub for hash table class
 *
 * @author bob313 cdc97
 * @version August 2018
 */

public class Hash {
    private Handle[] hashtable;
    private Handle[] temp;
    private int count;


    /**
     * Create a new Hash object.
     * 
     * @param size
     *            is initial size of hashtable
     * 
     */
    public Hash(int size) {
        hashtable = new Handle[size];
        count = 0;
    }


    /**
     * 
     * @param index
     *            of the hashtable
     * @return handle is returend
     */
    public Handle getHandle(int index) {
        return hashtable[index];
    }


    /**
     * 
     * @return the whole hashtable
     */
    public Handle[] getHashtable() {
        return hashtable;
    }


    /**
     * Insert e into hash table HT
     * 
     * @param k
     *            is the key
     * @param elem
     *            is the handle to be inserted
     * @return true if elem was added successfully
     */
    public boolean add(String k, Handle elem) {
        int home = h(k, hashtable.length); // Home position for e
        int pos = home; // Init probe sequence
        if (count >= hashtable.length / 2) {
            temp = hashtable;
            hashtable = new Handle[hashtable.length * 2];
            this.count = 0;
            this.remake(temp, hashtable);
        }
        for (int i = 0; (hashtable[pos] != null); i++) {
            pos = (home + probe(i)) % hashtable.length; // probe
            if (hashtable[pos] != null) {
                if (k.equals(hashtable[pos].key())) {
                    return false;
                }
            }
        }
        this.count++;
        hashtable[pos] = elem;
        return true;
    }


    /**
     * 
     * @param old
     *            is the old array
     * @param bigger
     *            is the new array
     */
    private void remake(Handle[] old, Handle[] bigger) {
        for (int i = 0; i < old.length; i++) {
            if (old[i] != null) {
                if (!old[i].getDeleted()) {
                    this.add(old[i].key(), old[i]);
                }
            }
        }
    }


    /**
     * @param key
     *            is the key to remove
     * @return true if object was removed
     */
    public boolean remove(String key) {
        if (search(key)) {
            int home = h(key, hashtable.length);
            int pos = home; // Initial position is the home slot
            for (int i = 0; (null != (hashtable[pos]) && (key != hashtable[pos]
                .key())); i++) {
                pos = (home + probe(i)) % hashtable.length; // Next on probe
            } // sequence
            hashtable[pos].setDeleted(true);
            return true;
        }
        return false;
    }


    /**
     * Search for the record with Key K
     * 
     * @param key
     *            is the key to search for
     * @return true if key was found
     */
    public boolean search(String key) {
        int home = h(key, hashtable.length); // Home position for K
        int pos = home; // Initial position is the
                        // home slot
        for (int i = 0; (null != (hashtable[pos]) && (key != hashtable[pos]
            .key())); i++) {
            pos = (home + probe(i)) % hashtable.length; // Next on probe
        } // sequence
        if (hashtable[pos] == null) {
            return false;
        }
        else if (!hashtable[pos].getDeleted() && key.equals((hashtable[pos])
            .key())) { // Found it
            return true;
        }
        return false; // K not in hash table
    }


    /**
     * prints the hashtable
     */
    public void print() {
        int sum = 0;
        for (int i = 0; i < hashtable.length; i++) {
            if (hashtable[i] != null && !hashtable[i].getDeleted()) {
                System.out.println("|" + hashtable[i].key() + "| " + i);
                sum++;
            }
        }
        if (sum >= 0) {
            System.out.println("Total records: " + sum);
        }
    }


    /**
     * @param i
     * @return i * i
     */
    private int probe(int i) {
        return i * i;
    }


    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
     * Analyzes a string 4 char at a time. so sums 4 char ascii then next 4
     *
     * @param s
     *            The string that we are hashing
     * @param m
     *            The size of the hash table
     * @return The home slot for that string
     */
    // You can make this private in your project.
    // This is public for distributing the hash function in a way
    // that will pass milestone 1 without change.
    private int h(String s, int m) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % m);
    }
}
