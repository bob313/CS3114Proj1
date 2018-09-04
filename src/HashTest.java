import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test the hash function (you should throw this away for your project)
 *
 * @author bob313 cdc97
 * @version August, 2018
 */
public class HashTest extends TestCase {

    /**
     * Sets up the tests that follow.
     */
    public void setUp() {
        // Nothing Here
    }


    /**
     * tests add method
     */
    public void testadd() {
        Hash myHash = new Hash(4);
        Handle first = new Handle(4, 4, "first");
        Handle second = new Handle(5, 4, "second");
        Handle third = new Handle(6, 4, "third");
        Handle fourth = new Handle(4, 4, "fourth");
        Handle last = new Handle(7, 4, "last");
        myHash.add("first", first);
        assertEquals(myHash.add("first", first), false);
        assertEquals(myHash.getHandle(2).key(), "first");
        myHash.add("second", second);
        assertEquals(myHash.getHashtable().length, 4);
        assertEquals(myHash.getHandle(1).key(), "second");
        myHash.add("third", third);
        myHash.print();
        assertEquals(myHash.getHandle(0).key(), "third");
        myHash.add("fourth", fourth);
        assertEquals(myHash.getHandle(3).key(), "fourth");
        assertEquals(myHash.getHashtable().length, 8);
        myHash.add("last", last);
        myHash.print();
        assertEquals(myHash.getHashtable().length, 16);
        assertEquals(myHash.getHandle(4).key(), "last");
    }


    /**
     * tests remove method
     */
    public void testremove() {
        Hash myHash = new Hash(4);
        Handle first = new Handle(4, 4, "first");
        Handle second = new Handle(5, 4, "second");
        Handle third = new Handle(6, 4, "third");
        Handle fourth = new Handle(4, 4, "fourth");
        myHash.add("first", first);
        myHash.add("second", second);
        myHash.add("third", third);
        myHash.add("fourth", fourth);
        myHash.remove("first");
        myHash.remove("second");
        assertEquals(myHash.search("fourth"), true);
        assertEquals(myHash.search("first"), false);
    }


    /**
     * tests the search method
     */
    public void testsearch() {
        Hash myHash = new Hash(4);
        Handle first = new Handle(4, 4, "first");
        Handle fourth = new Handle(4, 4, "fourth");
        myHash.add("first", first);
        myHash.add("fourth", fourth);
        assertEquals(myHash.search("first"), true);
        assertEquals(myHash.search("fourth"), true);
        assertEquals(myHash.search("second"), false);
    }


    /**
     * tests the print method
     */
    public void testprint() {
        Hash myHash = new Hash(4);
        Handle first = new Handle(4, 4, "first");
        Handle second = new Handle(5, 4, "second");
        Handle third = new Handle(6, 4, "third");
        Handle last = new Handle(7, 4, "last");
        myHash.add("first", first);
        assertEquals(myHash.getHandle(2).key(), "first");
        myHash.add("second", second);
        assertEquals(myHash.getHashtable().length, 4);
        assertEquals(myHash.getHandle(1).key(), "second");
        myHash.add("third", third);
        myHash.print();
        assertEquals(myHash.getHandle(0).key(), "third");
        myHash.add("last", last);
        myHash.remove("first");
        myHash.remove("second");
        myHash.remove("third");
        myHash.remove("last");
        myHash.print();
    }
}
