import student.TestCase;

public class MemoryManagerTest extends TestCase {
    private MemoryManager manager;
    private MemoryManager manager2;

    public void testDump() {
        manager = new MemoryManager(32);
        assertEquals(1, manager.getBlockList().get(0).getList().size());
        manager.dump();
    }


    public void testInsert() {
        manager = new MemoryManager(32);
        assertEquals(1, manager.getBlockList().size());
        String input = "Christian";
        assertEquals(0, manager.insert(input.getBytes(), input
            .getBytes().length, input).getMemPool());
        assertEquals(1, manager.getBlockList().size());
        //manager2 = new MemoryManager(32);
        String input2 = "p";
        assertEquals(1, manager.insert(input2.getBytes(), input2
            .getBytes().length, input2).getLength());
    }

}
