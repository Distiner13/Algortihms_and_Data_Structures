/*
Q1) it is important to have a good hash function since it reducing the 
 	number of collisions and distributing the allocated space properly can 
 	be crucial to performance. Having a unique hash for every entry, uniformity
 	and efficiency were Characteristics I tried to focus on when making the hash function.
Q2) the load factor increases the frequency of collisions, which will obviously have
	a negative impact on the performance of the data structure, in terms of time and space
	efficiency. I managed the load factor by dynamically resizing the table when it exceeds a threshold.
Q3) collisions occur when the hash function makes the same hash index for different elements.
	In my own implementation, I opted for open hashing, however, due to the restrictions posed by the superclass,
	I decided to use a large hash space of 256 bits.This significantly reduces the likelihood of producing the same 
	hash for different inputs, thereby minimizing collisions.
*/


package comp352.dsandalgos.myhash;

import main.stuff.MyHash;
import java.io.File;
import java.io.FileNotFoundException;
import main.stuff.NotFoundException;
import main.stuff.TooFullException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OptimizedHashing extends MyHash {	
    //private LinkedList<String>[] hashTable = (LinkedList<String>[]) new LinkedList[getTableSize()];        
	//I TRIED MAKING MY OWN HASHTABLE BEING UNAWARE THAT WE NEED TO USE ONLY THE FUNCTIONS AVAILABLE WITHIN THE MyHash SUPERCLASS, THAT WASTED A LOT OF MY TIME, 
	//YOU CAN TAKE A LOOK AT HOW I DID IT IN THE COMMENTED SECTIONS
	public OptimizedHashing(String filename) throws FileNotFoundException {
		super(filename);
		File file = new File(filename);
		if(filename == null) {
	        throw new IllegalArgumentException("Filename cannot be null.");
		}
	    if (!file.exists()) {
	        throw new FileNotFoundException("File does not exist: " + filename);
	    }
	    if (file.isDirectory()) {
	        throw new IllegalArgumentException("Filename represents a directory: " + filename);
		}
	    /*for (int i = 0; i < getTableSize(); i++) {
	    	 hashTable[i] = new LinkedList<>();
        }*/
	}
    

	@Override
	protected int hash(String str) {
		try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(str.getBytes());

            int hash = 0;
            for (byte hashByte : hashBytes) {
                hash = (hash * 31) + (hashByte & 0xFF);
            }
            return Math.abs(hash) % getTableSize();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.print("Issue in Hashing Function");
            return 0;
        }
	}
	
	@Override
	protected void insert(String str) throws TooFullException {
		try {
			find(str);
		} catch (NotFoundException e) {
			if(e.getIndex() > getTableSize()) {
				throw new TooFullException(str);
			}
			put(e.getIndex(), str);
			incSize();
		}
		

		/*
		int hashIndex = hash(str);
		
		LinkedList<String> chain = hashTable[hashIndex];
        //I put an arbitrary number for the max number of elements in the chain
        int MaxChainLength = 200;
        if (chain.size() >= MaxChainLength) {
            throw new TooFullException("Hash table chain is too long.");
        }
        chain.add(str);*/

	}

	
	@Override
	protected String find(String str) throws NotFoundException, TooFullException {
		int index = hash(str);
		int tries = 0;
		while (++tries <= getTableSize() + 1) {
			if (get(index) == null)
				throw new NotFoundException(index);
			if (get(index).equals(str)) {
				return str;
			}
			index = (index + 1) % getTableSize();
		}
		throw new TooFullException(str);
		
		/*int hashIndex = hash(str);
		LinkedList<String> chain = hashTable[hashIndex];
		for (String element : chain) {
            if (element.equals(str)) {
                return element;
            }
        }
		if (get(hashIndex) == null) throw new NotFoundException(hashIndex);
        
		throw new TooFullException(str);*/
	}

	
	@Override
	protected int determineTableSize(int i) {
		return i;
	}

}
