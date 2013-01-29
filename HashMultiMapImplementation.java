import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A HashMultiMap allows multiple values to be associated (mapped) to a
 * single key.  For this table to work correctly, the keys must have consistent
 * hashCode and equals functions.
 * 
 * @author Cam
 * @version 1.0
 */
public class HashMultiMapImplementation<K, V> implements HashMultiMap<K, V>{

	private int CAPACITY;
	private double LOAD_FAC_LIM;
	private Bucket<K, V>[] table;
  
	/**
	 * Inner hashmap bucket class
	 * @param <K> - type of key
	 * @param <V> - type of value
	 */
	private class Bucket<K, V> {
		public K bucKey;
		public List<V> valueList = new ArrayList<V>();
		
		public Bucket(K k, V v) {
			bucKey = k; 
			valueList.add(v); //automatically adds to END of the list
		}
	}
	
	/**
	 * a two parameter constructor that sets capacity and the loadfactor limit 
	 * @param size user input multimap capacity
	 * @param lf user input load factor limit
	 */
	public HashMultiMapImplementation(int size, double lf) {
		CAPACITY = size;
		LOAD_FAC_LIM = lf;
		table = new Bucket[CAPACITY]; //Bucket<K,V>[] bucket;
	}
	
	/**
	 * a default constructor that sets capacity to 89 and loadfactor limit to 0.5
	 */
	public HashMultiMapImplementation() {
		this(89, 0.5); 
	}								  
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		int count = 0;
		for (int i=0; i<CAPACITY; ++i) {
			if (table[i] != null) {
				count++; //return number of keys
				//or number of values ?
				//count += table[i].valueList.size(); 	
			}
		}
		return count;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		table = new Bucket[CAPACITY]; //just create a new array
	}

	@Override
	public List<V> values() {
		// TODO Auto-generated method stub
		List<V> allValues = new ArrayList<V>(); 
		for (int i=0; i<CAPACITY; ++i) {
			if (table[i] != null) {
				for (V v : table[i].valueList) {
					allValues.add(v);
				}
			}
		}
		return allValues;
	}

	@Override
	public List<V> values(K key) {
		// TODO Auto-generated method stub
		List<V> keyValues = new ArrayList<V>();
		int index = key.hashCode() % CAPACITY;
		if (table[index] != null) {
			for (V v : table[index].valueList) {
				keyValues.add(v); 
			}
		}
		return keyValues;
	}

	@Override
	public Set<K> keys() {
		// TODO Auto-generated method stub
		Set<K> allKeys = new HashSet<K>();
		for (int i=0; i<CAPACITY; ++i) {
			if (table[i] != null) {
				allKeys.add(table[i].bucKey);
			}
		}
		return allKeys;
	}

	@Override
	public boolean put(K key, V value) {
		// TODO Auto-generated method stub
		int index = key.hashCode() % CAPACITY;
		if (table[index] != null) { //hash exists
			if (table[index].bucKey.equals(key) && table[index].valueList.contains(value)) //key&value already exist
				return false;
			else if (!table[index].bucKey.equals(key)) { //hashes are the same but keys are not
				while (table[index] != null) {
					index = (index + 1) % CAPACITY;
				}
				table[index] = new Bucket<K, V>(key, value);
				rehash();
				return true;
			}
			else { //key exists but value doesn't
				table[index].valueList.add(value);
				rehash();
				return true;
			}
		}
		//if key doesn't exist yet
		table[index] = new Bucket<K, V>(key, value);
		rehash();
		return true;
	}

	@Override
	public boolean removeAll(K key) {
		// TODO Auto-generated method stub
		int index = key.hashCode() % CAPACITY;
		if (table[index] != null) {
			table[index].valueList.clear();
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(K key, V value) {
		// TODO Auto-generated method stub
		int index = key.hashCode() % CAPACITY;
		if (table[index] != null && table[index].valueList.contains(value)) {
			if (table[index].valueList.size() > 1)
				table[index].valueList.remove(value);
			else
				table[index] = null;
			//lazy remove
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for (int i=0; i<CAPACITY; ++i) {
			if (table[i] != null) {
				for (V v : table[i].valueList) {
					sb.append("<");
					sb.append(table[i].bucKey); //key
					sb.append(", ");
					sb.append(v); //value
					sb.append("> ");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	@Override
	public double loadfactor() {
		// TODO Auto-generated method stub
		double d = Math.round(((double)size() / CAPACITY) * 100.0) / 100.0;
		return d;
	}

	@Override
	public int capacity() {
		// TODO Auto-generated method stub
		return CAPACITY;
	}
	
	/**
	 * a method that regrows the hashtable to capacity: 2*old capacity + 1 and 
	 * reinserts all the key->value pairs.
	 */
	public void rehash() {
		if (loadfactor() > LOAD_FAC_LIM) {
			Bucket<K, V>[] tableCopy = table.clone();
			int OLD_CAPACITY = CAPACITY;
			CAPACITY = (CAPACITY * 2) + 1;
			table = new Bucket[CAPACITY];
			
			for (int i=0; i<OLD_CAPACITY; ++i) {
				if (tableCopy[i] != null) {
					for (V v : tableCopy[i].valueList)
						put(tableCopy[i].bucKey,  v);
				}
			}
		}
	}
	
}
