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

    }


    /**
     * Tests merge manually compare
     */
    public void testConstructor2() {
        CommandProcessor processor = new CommandProcessor("128", "10",
            "P1mergetest.txt");
        assertNotNull(processor.getHash());
    }


    /**
     * tests resize manually compare
     */
    public void testConstructor3() {
        CommandProcessor processor = new CommandProcessor("32", "7",
            "P1resize.txt");
        assertNotNull(processor.getHash());
    }


    /**
     * tests simple manually compare
     */
    public void testConstructor4() {
        CommandProcessor processor = new CommandProcessor("64", "10",
            "P1simple.txt");
        assertNotNull(processor.getHash());
    }


    /**
     * tests complex, manually compare
     */
    public void testConstructor5() {
        CommandProcessor processor = new CommandProcessor("32", "16",
            "P1complex.txt");
        assertNotNull(processor.getHash());
    }
}
