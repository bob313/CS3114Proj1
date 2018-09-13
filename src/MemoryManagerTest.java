import student.TestCase;

public class MemoryManagerTest extends TestCase {
    private MemoryManager manager;
    private MemoryManager manager2;

    public void testDump() {
        manager = new MemoryManager(32);
        assertEquals(1, manager.getBlockList().get(0).getList().size());
        manager.dump();
        
        String input1 = "Christian Carminucci";
        Handle testHandle = manager.insert(input1.getBytes(), input1.getBytes().length, input1);
        assertTrue(manager.getBlockList().isEmpty());
        manager.dump();
        
        manager.remove(testHandle);
        assertEquals(1, manager.getBlockList().size());
        manager.dump();
        
        String input2 = "Christian";
        Handle testHandle2 = manager.insert(input2.getBytes(), input2.getBytes().length, input2);
        assertEquals(1, manager.getBlockList().size());
        manager.dump();
        
        String input3 = "A really long string";
        Handle testHandle3 = manager.insert(input3.getBytes(), input3.getBytes().length, input3);
        assertEquals(2, manager.getBlockList().size());
        manager.dump();
    }


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
    
    public void testRemove() {
        manager = new MemoryManager(32);
        assertEquals(1, manager.getBlockList().size());
        String input = "Christian";
        Handle testHandle = manager.insert(input.getBytes(), input
            .getBytes().length, input);
        manager.remove(testHandle);
        assertEquals(32, manager.getBlockList().get(0).getSize());
        
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
    }

    
}
