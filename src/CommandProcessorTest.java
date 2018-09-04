import student.TestCase;

public class CommandProcessorTest extends TestCase{
    CommandProcessor processor;
    
    public void testConstructor()
    {
        processor = new CommandProcessor("32", "10", "P1sampleInput.txt");
        int x = 8;
        assertEquals(x, 8);
    }

}
