/*
Q1) When we access a huffman code tree we want to have the shortest path to certain 
    characters, most often, the ones which are repeated most. this helps make our tree
    more efficient in terms of space requirement, making huffman code very useful for 
    compression. in order to achieve this we can use a priority sorted linkedlist 
    to implement a priority queue. 
Q2) In Huffman coding, the length of a Huffman code for a symbol is inversely proportional
    to the frequency of that symbol. In other words, symbols with higher frequencies will 
    have shorter codes, while symbols with lower frequencies will have longer codes.
Q3) the time complexity of building a huffman is asymptotically speaking nlog(n). it can be optimized by choosing a different
    underlying data structure implementation. binary heaps can help get a log(n) complexity, which 
    is rather good. The manner in which we count and sort the frequency of each character can also
    be changed to attain a lower time complexity, if we go through all n elements doing so, that would be O(n). 


*/
package ca.concordia.algorithms.huffman;

import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import java.util.Arrays;
import java.util.Comparator;

public class HuffCode {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        String firstCommand = scanner.nextLine();
        
        String file = args[0];
		String command = args[1];		
		String message = firstCommand.toLowerCase();
		
		Map<Integer, Integer> source = readFile(file);
		//System.out.println("Command: " + command);
		//build the Huffman coding tree based on source file
		int[][] arr = Sort(source);
		Node root = HuffmanCoding(arr);
		Map<Integer, String> encodingMap = buildEncodingMap(root);
		
		/*
		String[] All = {"f","i","k","l","m","u","w","y"," ","h","o","p","g","d","n","r","e","t","a","s"};
		String[] BinAll = new String[arr.length];
		for(int k = 0; k < All.length; k++) {	
			BinAll[k] = encode(All[k], encodingMap);
		}	

		for(int j = 0; j<arr.length; j++) {
 	 		System.out.println("Arr key: "+ arr[j][0] + " ASCII: " + ((char) arr[j][0]) + " frq: " + arr[j][1] + " Encoding: " + BinAll[j]);
 		}*/
		
		if(command.equals("encode")) {
			//System.out.print("Encoded message: ");
			System.out.println(encode(message, encodingMap));
		}else if (command.equals("decode")) {
			//System.out.print("Decoded message: ");
			System.out.println(decode(message, root));
		}
	}
	
	static class Node {
		int C;
		int frq;
		Node left = null;
		Node right = null;
		public int getChar() {
			return C;
		}
		public int getFrq() {
			return frq;
		}
		public Node(int C, int frq) {
			this.C = C;
			this.frq = frq;
		}
	}
	
	public static Map<Integer, Integer> readFile(String file) {
		Map<Integer, Integer> charMap = new LinkedHashMap<Integer, Integer>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			int input = 0;
			while ((input = reader.read()) != -1) {
				charMap.put(input, charMap.getOrDefault(input, 0) + 1);
				//System.out.println(charMap);				
			}
			reader.close();
			return charMap;

		}catch (Exception e) {
			System.out.println("An error occured.");
			return null;
		}
	}
	
	public static void printLL(LinkedList<Node> nodes) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Node node : nodes) {
		    sb.append("(").append(node.C).append(",").append(node.frq).append("),");
		}
		// Remove the trailing comma if it exists
		if (sb.length() > 1) {
		    sb.setLength(sb.length() - 1);
		}
		sb.append("]");

		System.out.println(sb.toString());
	}
	
	private static int[][] Sort(Map<Integer, Integer> source){
		//make a new dictionary which will store the sorted elements
		//i want to try putting each value in a 2D array and sort based on the first index
		int count = 0;
		for (Map.Entry<Integer, Integer> entry : source.entrySet()) {
            count++;
        }
 		int[][] arr = new int[count][2];
 		int i = 0;
 		for (Map.Entry<Integer, Integer> entry : source.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            //System.out.println("Key: " + key + ", Value: " + value);
            
            arr[i][0] = key;
            arr[i][1] = value;
            i++;
 		}
 		// Sort the array based on arr[i][1], it's stable
        Arrays.sort(arr, Comparator.comparingInt(a -> a[0]));
        Arrays.sort(arr, Comparator.comparingInt(a -> a[1]));
 		return arr;
 	}
	private static int compareNodes(Node node1, Node node2) {
        // Compare based on priorities
        if ((node1.C != '\0' && node2.C == '\0') && (node1.frq >= node2.frq)) {
            return -1; // node1 has a character, node2 does not
        } else if ((node1.C == '\0' && node2.C != '\0') && (node1.frq >= node2.frq)) {
            return 1; // node2 has a character, node1 does not
        } else {
            // Both nodes have characters or both do not have characters
            if(node1.frq >= node2.frq) return 1;
            else return -1; 
        }
    }
	
	private static Node HuffmanCoding (int[][] arr){
	 	//convert 2D arr into NodeLL
		LinkedList<Node> nodes = new LinkedList<>();
	    for (int i = 0; i < arr.length; i++) {
	        nodes.add(new Node(arr[i][0], arr[i][1]));
	    }
	    //System.out.println("Before removing nodes: ");
        //printLL(nodes);
        
        while (nodes.size() > 1) {
            Node leftChild = nodes.get(0);
            Node rightChild = nodes.get(1);

            Node parent = new Node('\0', leftChild.frq + rightChild.frq);
            parent.left = leftChild;
            parent.right = rightChild;
            
            nodes.removeFirst();
            nodes.removeFirst();

            // Insert the parent node at the correct position based on priorities
            int insertIndex = 0;
            while (insertIndex < nodes.size() && compareNodes(parent, nodes.get(insertIndex)) > 0) {
                insertIndex++;
            }
            nodes.add(insertIndex, parent);
            
            //System.out.println("After removing nodes: ");
            //printLL(nodes);
            //System.out.println("New node: (" + parent.getChar() + "," + parent.getFrq() + ") Left child: (" + parent.left.getChar() + "," + parent.left.getFrq() + ") Right child: (" + parent.right.getChar() + "," + parent.right.getFrq() + ")");
        }
        return nodes.getFirst();
        
	}
	
	public static Map<Integer, String> buildEncodingMap(Node root) {
        Map<Integer, String> encodingMap = new LinkedHashMap<>();
        buildEncodingMapHelper(root, "", encodingMap);
        return encodingMap;
    }
    private static void buildEncodingMapHelper(Node node, String prefix, Map<Integer, String> encodingMap) {
    	if (node.getChar() != '\0') {
            encodingMap.put(node.getChar(), prefix);
            return;
        }
       	buildEncodingMapHelper(node.left, prefix + "1", encodingMap);
      	buildEncodingMapHelper(node.right, prefix + "0", encodingMap);        
    }
	
	private static String encode(String message, Map<Integer, String> encodingMap) {
		StringBuilder encodedMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
        	int d = (int) c;
            String binaryRepresentation = encodingMap.get(d);
            if (binaryRepresentation != null) {
                encodedMessage.append(binaryRepresentation);
            } else {
                //System.out.println("Character not found in the encoding map.");
            }
        }
        return encodedMessage.toString();
	}
	private static String decode(String message, Node root) {
		StringBuilder decodedMessage = new StringBuilder();
        Node currentNode = root;

        for (char bit : message.toCharArray()) {
            if (bit == '1') {
                currentNode = currentNode.left;
            } else if (bit == '0') {
                currentNode = currentNode.right;
            } else {
                System.out.println("the input for decode is not binary (0, 1).");
            }
            char CurrentNodeChar = (char) currentNode.C;
            if (CurrentNodeChar != '\0') {
                decodedMessage.append(CurrentNodeChar);
                currentNode = root;
            }
        }

        return decodedMessage.toString();
	}	

}
