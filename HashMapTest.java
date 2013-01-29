import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/*
 * HashMapTest.java
 *
 * Version 1.0
 * Copyright 2011 BobSoft Inc
 */

/**
 * @author Robert
 * @version 1.0
 *
 */
public class HashMapTest {
    HashMultiMap<String, String> map;
    
    @Before
    public void setUp() {
        map = new HashMultiMapImplementation<String, String>();
    }
    
    @Test
    public void testInitialSize() {
         assertEquals("Map initial size not zero", 0, map.size());
    }
    
    @Test
    public void testSizeAfterOneInsert() {
        map.put("A", "AA");
        assertEquals("Map size after insert wrong", 1, map.size());
    }
    
    @Test
    public void testSizeAfterClear() {
        map.put("A", "AA");
        map.put("B", "BB");
        map.put("C", "CC");
        map.clear();
        assertEquals("Map size wrong after clear", 0 , map.size());
    }
    
    @Test
    public void testKeysWhenEmpty() {
        Set<String> keys = map.keys();
        assertTrue("Should be no keys in empty map", keys.isEmpty());
    }
    
    @Test
    public void testKeysWhenOccupiedUnique() {
        map.put("A", "AA");
        map.put("B", "BB");
        map.put("C", "CC");
        map.put("S", "SS");
        
        Set<String> keys = map.keys();
        assertEquals("Wrong number of keys", 4, keys.size());
        assertTrue("Missing A", keys.contains("A"));
        assertTrue("Missing B", keys.contains("B"));
        assertTrue("Missing C", keys.contains("C"));
        assertTrue("Missing S", keys.contains("S"));
    }
    
    @Test
    public void testKeysWhenOccupiedDuplicates() {
        map.put("A", "AA");
        map.put("A", "LL");
        map.put("A", "KK");
        map.put("B", "BB");
        map.put("C", "CC");
        map.put("C", "HH");
        map.put("S", "SS");
        
        Set<String> keys = map.keys();
        assertEquals("Wrong number of keys", 4, keys.size());
        assertTrue("Missing A", keys.contains("A"));
        assertTrue("Missing B", keys.contains("B"));
        assertTrue("Missing C", keys.contains("C"));
        assertTrue("Missing S", keys.contains("S"));
    }
    
    @Test
    public void testValuesWhenEmpty() {
        List<String> vals = map.values();
        assertTrue("Should be no values in empty table", vals.isEmpty());
    }
    
    @Test
    public void testValuesWhenOccupiedUnique() {
        map.put("A", "AA");
        map.put("B", "BB");
        map.put("C", "CC");
        map.put("S", "SS");
        
        List<String> vals = map.values();
        assertEquals("Wrong values returned", 4, vals.size());
        assertTrue("Missing AA", vals.contains("AA"));
        assertTrue("Missing BB", vals.contains("BB"));
        assertTrue("Missing CC", vals.contains("CC"));
        assertTrue("Missing SS", vals.contains("SS"));
    }
    
    @Test
    public void testValuesWhenOccupiedDuplicates() {
        map.put("A", "AA");
        map.put("A", "LL");
        map.put("B", "BB");
        map.put("C", "CC");
        map.put("C", "KK");
        map.put("C", "PP");
        map.put("S", "SS");
        
        List<String> vals = map.values();
        assertEquals("Wrong values returned", 7, vals.size());
        assertTrue("Missing AA", vals.contains("AA"));
        assertTrue("Missing BB", vals.contains("BB"));
        assertTrue("Missing CC", vals.contains("CC"));
        assertTrue("Missing KK", vals.contains("KK"));
        assertTrue("Missing SS", vals.contains("SS"));
    }
    
    @Test
    public void testValueWhenEmpty() {
        List<String> res = map.values("C");
        assertTrue("Empty should not have values", res.isEmpty());
    }
    
    @Test
    public void testValueWhenOccupiedUnique() {
        map.put("A", "AA");
        map.put("B", "BB");
        map.put("C", "CC");
        map.put("S", "SS");
        
        List<String> vals = map.values("B");
        assertEquals("Wrong values returned", 1, vals.size());
        assertTrue("Missing BB", vals.contains("BB"));
    }
    
    @Test
    public void testValueWhenOccupiedDuplicates() {
        map.put("A", "AA");
        map.put("A", "LL");
        map.put("B", "BB");
        map.put("C", "CC");
        map.put("C", "KK");
        map.put("C", "PP");
        map.put("S", "SS");
        
        List<String> vals = map.values("C");
        assertEquals("Wrong values returned", 3, vals.size());
        assertTrue("Missing AA", vals.contains("KK"));
        assertTrue("Missing BB", vals.contains("PP"));
        assertTrue("Missing CC", vals.contains("CC"));
        assertFalse("Incorrect value", vals.contains("AA"));
    }
    
    @Test
    public void testPutSingleValue() {
        assertTrue("didn't add only item", map.put("A", "AA"));
        List<String> vals = map.values("A");
        assertTrue("Missing value", vals.contains("AA"));
    }
    
    @Test
    public void testPutDuplicateKeys() {
        assertTrue("Didn't add value", map.put("A","AA"));
        assertTrue("Didn't add value", map.put("B", "C"));
        assertTrue("Didn't add value", map.put("A","JJ"));
        assertFalse("Added a duplicate", map.put("A","AA"));
    }
    
    @Test 
    public void testRemoveAllEmpty() {
        assertFalse("Wrong return value for remove empty", map.removeAll("A"));
    }
    
    @Test 
    public void testRemoveAllOneItem() {
        map.put("A", "AA");
        map.put("B", "bb");
        assertTrue("Did not return value for valid remove", map.removeAll("A"));
        List<String> vals = map.values("A");
        assertTrue("Should not have had anything left", vals.isEmpty());
    }
    
    @Test
    public void testRemoveAllMultipleItems() {
        map.put("A", "AA");
        map.put("A", "CC");
        map.put("A", "DD");
        map.put("C", "FF");
        map.put("F", "yy");
        assertTrue("Did not return value for valid remove", map.removeAll("A"));
        List<String> vals = map.values("A");
        assertTrue("Should not have had anything left", vals.isEmpty());
        
    }
    
    @Test 
    public void testRemoveEmpty() {
        assertFalse("Wrong return value for remove empty", map.remove("A","AA"));
    }
    
    @Test 
    public void testRemoveOneItem() {
        map.put("A", "AA");
        map.put("B", "bb");
        assertTrue("Did not return value for valid remove", map.remove("A", "AA"));
        List<String> vals = map.values("A");
        assertTrue("Should not have had anything left", vals.isEmpty());
    }
    
    @Test
    public void testRemoveMultipleItems() {
        map.put("A", "AA");
        map.put("A", "CC");
        map.put("A", "DD");
        map.put("C", "FF");
        map.put("F", "yy");
        assertTrue("Did not return value for valid remove", map.remove("A", "DD"));
        List<String> vals = map.values("A");
        assertEquals("Did not remove the item", 2, vals.size());
        assertFalse("Still has old value", vals.contains("DD"));
        
    }
    
    @Test
    public void testLoadFactor() {
        assertEquals("Loadfactor should be zero for empty table", 0 , map.loadfactor(), 0.01);
        for (int i = 0; i < 40 ; ++i) map.put("A"+i, "H"+i);
        assertEquals("Loadfactor wrong for unique elements", 0.44, map.loadfactor(), 0.01);
        map.clear();
        for (int i = 0; i < 20; ++i) map.put("A" + i, "H" + i);
        for (int i = 0; i < 20; ++i) map.put("A", "G" + i);
        assertEquals("Loadfactor wrong for dup elements", 0.23, map.loadfactor(), 0.01);
    }
    
    @Test
    public void testToString() {
        assertEquals("Empty string wrong","[ ]", map.toString());
        map.put("A", "AA");
        map.put("B", "BB");
        map.put("C", "CC");
        assertEquals("Occupied string wrong", "[ <A, AA> <B, BB> <C, CC> ]", map.toString());
    }
    
    @Test
    public void testRegrowSimple() {
        map = new HashMultiMapImplementation<String, String>(5, 0.5);
        assertEquals("Wrong initial capacity", 5, map.capacity());
        map.put("A", "BB");
        map.put("B", "HH");
        assertEquals("Regrew too soon", 5, map.capacity());

        map.put("D", "JJ");
        assertEquals("Regrow wrong size", 11, map.capacity());
        List<String> vals = map.values();
        assertTrue("Rehash missing data", vals.contains("BB"));
        assertTrue("Rehash missing data", vals.contains("JJ"));
        
    }
    
    @Test
    public void testRegrowDuplicates() {
        map = new HashMultiMapImplementation<String, String>(5, 0.5);
        assertEquals("Wrong initial capacity", 5, map.capacity());
        map.put("A", "BB");
        map.put("A", "CC");
        map.put("B", "JJ");
        map.put("B", "HH");
        assertEquals("Regrew too soon", 5, map.capacity());
        map.put("D", "JJ");
        assertEquals("Regrow wrong size", 11, map.capacity());
        List<String> vals = map.values();
        assertTrue("Rehash missing data", vals.contains("BB"));
        assertTrue("Rehash missing data", vals.contains("JJ"));
        assertEquals("Values missing", 5, vals.size());
        Set<String> keys = map.keys();
        assertEquals("Keys missing", 3, keys.size());
    }

}
