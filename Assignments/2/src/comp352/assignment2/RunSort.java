/*
 Q2) I was asked to sort in ascending order
 	the structuring pass asked me to reverse 
 	any runs which were descending. This helped 
 	tremendously because every reversal had already
 	sorted a portion of the array, which minimized the
 	number of compares needed within the insertion sort.
 Q3) again, the more runs are identified as descending
 	the less work the insertion sort needs to do. the 
 	time complexity of of each identifying each run is
 	n. And the worst case for an insertion sort is avoided
 	since it would be when the array is revered, and that
 	would have a time complexity of n^2.
 Q4) A doubly-Linked list would allow efficient insertion
 	and reversals. since no operations involve randomly accessing
 	elements, having a linked list will not have any negative effects
 	within the structuring pass either. So overall, it does not affect 
 	the results badly, it might affect them positively instead.
 
 
 */

package comp352.assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class RunSort {
	public static int cDECL3 = 0;
	public static int reversals = 0;
	public static int structuringCompares = 0;
	public static int overallCompares = 0;
	public static int overallSwaps = 0;

	public static void main (String[] args) {
		if (((args.length) < 1)) {System.out.println("You need to input the file name as an argument.");
									return;}
		
		String filename = args[0];
		int[] data = readFile(filename);
		
		//System.out.println("This is the original array from the file inputed: ");
		printArray(data);
		
		//Structuring pass
		structuringPass(data);
		//System.out.println("This is the result of the structuring pass: ");
		//System.out.println(Arrays.toString(data));
		
		//insertion sort
		insertionSort(data);
		
		overallCompares+=2*structuringCompares - 1;

		//all requirements:
		System.out.println("We sorted in ASC order");
		System.out.println("We counted "+ cDECL3 + " DEC run of length 3");
		System.out.println("We performed "+ reversals + " reversals of runs in DEC order");
		System.out.println("We performed "+ structuringCompares + " compares during structuring");
		System.out.println("We performed "+ overallCompares+ " compares overall");
		System.out.println("We performed "+ overallSwaps + " swaps overall");
		//System.out.println("This is the result of insertion sorting: ");
		printArray(data);
	}
	
	public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
	
	public static int[] readFile(String file) {
		try {
			File text = new File(file);
			Scanner myReader = new Scanner(text);
			String lines = "";
			
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				lines += line;				
			}
			String[] stringArr = lines.split(" ");
			int[] nums = new int[stringArr.length];
			for (int i = 0; i < (stringArr.length); i++) {
				nums[i] = Integer.parseInt(stringArr[i]);
			}
			myReader.close();
			return nums;

		}catch (FileNotFoundException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
			return null;
		}

		}
	
	private static void insertionSort(int array[]) {
	    int size = array.length;

	    for (int step = 1; step < size; step++) {
	      int key = array[step];
	      int j = step - 1;

	      // Compare key with each element on the left of it until an element smaller than
	      // it is found.
	      while (j >= 0 && key < array[j]) {
	        array[j + 1] = array[j];
	        --j;
	        overallCompares++;
	        overallSwaps++;
	      }
	      // Place key at after the element just smaller than it.
	      array[j + 1] = key;
	    }
	  }
	private static void TreatRun(int[] inpArr, String order, int leng) {
		if(order == "Descending") {
			if (leng == 3) {
				cDECL3++;
			}
		}
	}
	
	private static void structuringPass(int[] inpArr) {
		String order = "";
		
		for (int i = 0; i<(inpArr.length); i++) {
			try {
				int a = inpArr[i+1];
			}catch (Exception e){
				System.out.println("The index reached its max range, break loop.");
				break;
			}
			if (inpArr[i+1] < inpArr[i]) {
				structuringCompares++;
				int startIndex = i;
				while (inpArr[i+1] < inpArr[i] && i!= ((inpArr.length)-2)) {
					order = "Descending";
					i++;
					structuringCompares++;
				}
				if((i == ((inpArr.length)-2))&&inpArr[i+1] < inpArr[i]) {i++;}
				int LengthRun = i - startIndex + 1;
				//make an array of the run
				int[] runArr = new int[LengthRun];

				for(int j = startIndex; j < (LengthRun + startIndex); j++) {
					runArr[j - startIndex] = inpArr[j]; 				
				}
				//System.out.println("DES run startIndex: "+startIndex+", leng: "+LengthRun+", end: "+i);

				//System.out.println("DES run: ");
				//System.out.println(Arrays.toString(runArr));
				TreatRun(runArr, order, LengthRun);
				reverse(runArr, 0, (runArr.length));
				//System.out.println("DES run reversed: ");
				//System.out.println(Arrays.toString(runArr));
				//make sure that the changes made to the run array are applied to the original array
				for(int j = startIndex; j < (LengthRun + startIndex); j++) {
					inpArr[j] = runArr[j - startIndex]; 
				}
				//System.out.println("after modification of original: ");
				//System.out.println(Arrays.toString(inpArr));
				continue;
			}
			if (inpArr[i+1] > inpArr[i]) {
				int startIndex = i;
				structuringCompares++;
				while (inpArr[i+1] > inpArr[i] && i!= ((inpArr.length)-2)) {
					order = "Ascending";
					i++;
					structuringCompares++;
				}
				if((i == ((inpArr.length)-2))&&inpArr[i+1] > inpArr[i]) {i++;}
				int LengthRun = i - startIndex + 1;
				//make an array of the run
				int[] runArr = new int[LengthRun];
				//System.out.println("ASC run startIndex: "+startIndex+", leng: "+LengthRun+", end: "+i);
				for(int j = startIndex; j < (LengthRun + startIndex); j++) {
					runArr[j - startIndex] = inpArr[j]; 
				}
				
				//System.out.println("ASC run: ");
				//System.out.println(Arrays.toString(runArr));
				continue;
			}	
			//System.out.println("SP result for loop: ");
			//System.out.println(Arrays.toString(inpArr));
		}
		
    }
    
	private static void Swap (int[] arr, int a, int b){
		int  temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
		overallSwaps++;
	}
	private static void reverse (int [] arr, int start, int end){
		for (int i = 0; i< (end-start) >> 1 ; ++i) {
			Swap(arr, start + i, end - i -1);
		}
		reversals++;
	}    
    	
}
