import java.util.List;
import java.util.Set;

/*
 * HashMultiMap.java
 *
 * Version 1.0
 * Copyright 2011 BobSoft Inc
 */

/**
 * @author This interface based off the Google utility class HashMultiMap
 * 
 * @version 1.0
 *
 * A HashMultiMap allows multiple values to be associated (mapped) to a
 * single key.  For this table to work correctly, the keys must have consistent
 * hashCode and equals functions.
 * 
 *@type K - type of key  
 *@type V - type of value
 */
public interface HashMultiMap<K, V> {
    
    /**
     * report the number of elements (values) in the table
     * @return the total key->value pairs in the table.
     */
    int size();
    
    /**
     * removes all the elements (key-value pairs) in the table
     */
    void clear();
    
    /**
     * returns a list of all the values (regardless of key) held in the table
     * @return the values as a list
     */
    List<V> values();
    
    /**
     * returns a list of values associated with a specific key
     * @param key    the key to look up in the table
     * @return the values associated with that key
     */
    List<V> values(K key);
    
    /**
     * returns a set of all keys held in the table
     * @return a set of keys
     */
    Set<K> keys();
    
    /**
     * Adds a key->value pair to the table.  If this exact key value pair exists,
     * then do nothing and return false.  If there is no key yet, make a new
     * entry and add the value.  If a key exists, then check to see if that value
     * exists.  If not then add it.
     * @param key the key of the element to add
     * @param value the value of the element to add
     * @return true if something actually added, false otherwise.
     */
    boolean put(K key, V value);
    
    /**
     * Remove all values that are associated with key.  If key does not exist
     * in the table, then return false.
     * @param key the key to remove all values for
     * @return true if something removed, false otherwise
     */
    boolean removeAll(K key);
    
    /**
     * Remove this key value pair from the table.  If the pair does not exist, 
     * then return false.
     * @param key  the key to remove
     * @param value the value to remove
     * @return  true if something actually deleted, false otherwise
     */
    boolean remove(K key, V value);
    
    /**
     * Returns a string representation of the multimap
     * This representation should look like the following:
     * 
     *        [ <k, v> <k, v> <k, v> ]
     *        
     * @return the string representation
     */
    String toString();
    
    /**
     * returns the current loadfactor of the table.
     * loadfactor is the total number of occupied buckets / capacity
     * @return the loadfactor
     */
    double loadfactor();
    
    /**
     * 
     * @return the maximum capacity (at 100% loadfactor) of the current table
     */
    int capacity();

}
