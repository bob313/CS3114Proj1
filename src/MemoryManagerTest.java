import student.TestCase;

/**
 * Test class for MemoryManager
 * 
 * @author bob bao bob313 Chrsitian Carminucci
 * 
 * @version sep 14 2018
 *
 */
public class MemoryManagerTest extends TestCase {
    private MemoryManager manager;
    private MemoryManager manager2;


    /**
     * tests the dump method
     */
    public void testDump() {
        manager = new MemoryManager(32);
        assertEquals(1, manager.getBlockList().get(0).getList().size());
        manager.dump();

        String input1 = "Christian Carminucci";
        Handle testHandle = manager.insert(input1.getBytes(), input1
            .getBytes().length, input1);
        assertTrue(manager.getBlockList().isEmpty());
        manager.dump();

        manager.remove(testHandle);
        assertEquals(1, manager.getBlockList().size());
        manager.dump();

        String input2 = "Christian";
        manager.insert(input2.getBytes(), input2.getBytes().length, input2);
        assertEquals(1, manager.getBlockList().size());
        manager.dump();

        String input3 = "A really long string";
        manager.insert(input3.getBytes(), input3.getBytes().length, input3);
        assertEquals(1, manager.getBlockList().size());
        manager.dump();
    }


    /**
     * tests insert method
     */
    public void testInsert() {
        manager = new MemoryManager(32);
        assertEquals(1, manager.getBlockList().size());
        String input = "Christian";
        assertEquals(0, manager.insert(input.getBytes(), input
            .getBytes().length, input).getMemPool());
        assertEquals(1, manager.getBlockList().size());
        String input2 = "p";
        assertEquals(1, manager.insert(input2.getBytes(), input2
            .getBytes().length, input2).getLength());
        String input3 = "A Really Long Word";
        assertEquals(18, manager.insert(input3.getBytes(), input3
            .getBytes().length, input3).getLength());
        manager2 = new MemoryManager(0);
        String input4 = "Christian";
        assertEquals(0, manager2.insert(input4.getBytes(), input4
            .getBytes().length, input4).getMemPool());
    }


    /**
     * tests the remove method
     */
    public void testRemove() {
        manager = new MemoryManager(32);
        assertEquals(1, manager.getBlockList().size());
        String input = "Christian";
        Handle testHandle = manager.insert(input.getBytes(), input
            .getBytes().length, input);
        manager.remove(testHandle);
        assertEquals(32, manager.getBlockList().get(0).getSize());
        String inputA = "Another rally long String";
        manager.insert(inputA.getBytes(), inputA.getBytes().length, inputA);

        manager2 = new MemoryManager(32);
        String input2 = "Christian";
        Handle testHandle2 = manager2.insert(input2.getBytes(), input2
            .getBytes().length, input2);
        String input3 = "William Bao";
        Handle testHandle3 = manager2.insert(input3.getBytes(), input3
            .getBytes().length, input3);
        manager2.remove(testHandle2);
        assertEquals(16, manager2.getBlockList().get(0).getSize());
        manager2.remove(testHandle3);
        assertEquals(32, manager2.getBlockList().get(0).getSize());

        MemoryManager manager3 = new MemoryManager(32);
        String dk = "Death Note<SEP>Genre<SEP>Anime";
        Handle dkHandle = manager3.insert(dk.getBytes(), dk.getBytes().length,
            dk);
        String cyh = "Can You Handle?";
        manager3.insert(cyh.getBytes(), cyh.getBytes().length, cyh);
        String atest = "Another Test";
        Handle atestHandle = manager3.insert(atest.getBytes(), atest
            .getBytes().length, atest);
        manager3.remove(dkHandle);
        String dn = "Death Note";
        manager3.insert(dn.getBytes(), dn.getBytes().length, dn);
        manager3.remove(atestHandle);
        manager3.dump();
        assertEquals(2, manager3.getBlockList().get(0).getList().size());

    }


    /**
     * tests the length method
     */
    public void testLength() {
        manager = new MemoryManager(32);
        assertEquals(1, manager.getBlockList().size());
        String input = "Christian";
        Handle testHandle = manager.insert(input.getBytes(), input
            .getBytes().length, input);
        assertEquals(9, manager.length(testHandle));
    }

}
