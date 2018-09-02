import java.util.ArrayList;

/**
 * Stub for hash table class
 *
 * @author CS3114 staff
 * @version August 2018
 */

public class Hash {
    Handle[] hashtable;
    Handle[] temp;
    int count;


    /**
     * Create a new Hash object.
     * 
     */
    public Hash(int size) {
        hashtable = new Handle[size];
        count = 0;
    }
    
 /**
  * Insert e into hash table HT
  * @param k
  * @param elem
  * @return
  */
    boolean add(String k, Handle elem) {
      int home;                     // Home position for e
      int pos = home = h(k, hashtable.length);        // Init probe sequence
      if (count >= hashtable.length/2) {
          temp = hashtable;
          hashtable = new Handle[hashtable.length*2];
          this.remake(temp, hashtable);
          return true;
      }
      for (int i=1; "" != (hashtable[pos]).key(); i++) {
        pos = (home + probe(k, i)) % hashtable.length; // probe
        if (k == hashtable[pos].key()) {
          return false;
        }
      }
      hashtable[pos] = elem;
      return true;
    }
    
    private void remake(Handle[] old, Handle[] bigger) {
        for (int i=1; i < old.length; i++) {
            if (old[i].key() != "") {
                this.add(old[i].key, old[i]);
            }
        }
    }
    
    /**
     * @param k
     * @param elem
     */
    boolean remove(String k, String elem) {
        
    }
    
 /**
  * Search for the record with Key K
  * @param k
  * @param elem
  * @return
  */
    boolean search(String key, String elem) {
      int home;              // Home position for K
      int pos = home = h(key, hashtable.length); // Initial position is the home slot
      for (int i = 1;
           (key != (hashtable[pos]).key()) && ("" != (hashtable[pos]).key());
           i++)
        pos = (home + probe(key, i)) % hashtable.length; // Next on probe sequence
      if (key == (hashtable[pos]).key()) {   // Found it
        return true;
      }
      else return false;            // K not in hash table
    }
    
    /**
     * 
     */
    void print() {
        int sum = 0;
        for (int i=0; i < hashtable.length; i++) {
            if(hashtable[i].key() != "") {
                System.out.println("|" + hashtable[i].key() + "| " + i);
                sum++;
            }
        }
        if (sum > 0) {
            System.out.println("Total records: " + sum);
        }
    }
    
    /**
     * @param k
     * @param i
     * @return
     */
    int probe(String k, int i) {
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
