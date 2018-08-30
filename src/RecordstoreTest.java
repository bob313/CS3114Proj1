import student.TestCase;

/**
 * @author {Your Name Here}
 * @version {Put Something Here}
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
    }
}
