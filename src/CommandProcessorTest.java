import student.TestCase;

/**
 * 
 * @author bob313 cdc97
 * @version sep 5 2018
 *
 */
public class CommandProcessorTest extends TestCase {

    /**
     * Tests the command processor
     */
    public void testConstructor() {
        CommandProcessor processor = new CommandProcessor("32", "10",
            "P1sampleInput.txt");
        assertNotNull(processor.getHash());
        processor = new CommandProcessor("256", "10", "Piazzatrial.txt");
        assertNotNull(processor.getHash());
    }

}
