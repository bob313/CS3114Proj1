import student.TestCase;

/**
 * @author {William Bao bob313 Christian cdc97}
 * @version {Sep 14 2018}
 */
public class RecordstoreTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing Here
    }


    /**
     * Get code coverage of the class declaration.
     */
    public void testRInit() {
        Recordstore recstore = new Recordstore();
        assertNotNull(recstore);
        Recordstore.main(null);
        String[] run = { "256", "10", "Piazzatrial.txt" };
        Recordstore.main(run);
        String[] dum = { "23", "sf" };
        Recordstore.main(dum);
    }
}
