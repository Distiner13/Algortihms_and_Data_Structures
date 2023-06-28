/*
 * FIRST OF ALL, I THINK THE DIAGRAM SHOWN ON MY ASSIGNEMNT IS THE OPPOSITE OF THE WORDS
 * EXPLAINING THE PROBLEM. I DECIDED TO THE DIAGRAM SINCE IT MATCHES THE EXAMPLE BETTER.
 * THE WORDS DESCRIBED REQUIRE ME TO SUM ALL PREVIOUS VALUES, WHILE THE EXAMPLE ONLY ACCOUNTS
 * FOR THE VALUES 4 STEPS BACK AND 3 STEPS BACK. EVERYTHING ELSE IN THE ARRAY IS IGNORED.
 * 
 * I had a lot of trouble finding the right number of recursive calls to match the DebugRunner
 * I found a way to satisfy the requirement, but i'm not sure it works for all cases of input
 * 
 * 
Q2) Theory: Qualify This Recursion
How does the nature of the sequence (sum of all previous values except the ones we skip, and multiplication
of certain values) affect the complexity of a recursive solution? Limit your answer to 150
words.

	The complexity of our solution is mainly affected by:
	- the recursive nature of the function "process" which calls itself n times, as long as index < length.
	- the for loop within the recursive function which also repeats n times

    The removal of certain indices or multiplying them, does not have a large effect on the complexity since their 
    locations are known and we simply point to them. Because of this, the time complexity is n^2.

Q3) Theory: Impact of Size
As the size of the input sequence grows, how do you anticipate the runtime of your program to scale?
Explain why. Limit your answer to 100 words.
Page

	I expect my program's runtime to grow exponentially with the size of the input since, the time complexity is 
	an exponential function of the order n^2.

 */
package comp352.dsandalgos;

public class RecursiveFibonacciCounter {
    private static int recursiveCalls = 0;

    public static void main(String[] args) {
        //long startTime = System.currentTimeMillis();
    	if (args.length == 0) {
            System.out.println("Please provide the desired length of the sequence as an argument.");
            return;
        }

        int leng = Integer.parseInt(args[0]);

        if (leng < 1) {
            System.out.println("Length should be a non-negative integer.");
            return;
        }

        int[] sequence = generateSequence(leng);
        printSequence(sequence);
        System.out.println("\nCalls: " + (recursiveCalls-1)); //1-based indexes so -1
        //long endTime = System.currentTimeMillis();
        //System.out.print("Elapsed Time: " + (endTime-startTime)+ " milliseconds");
    }

    public static int[] generateSequence(int length) {
        int[] sequence = new int[length];
        if (length > 0) {sequence[0] = 1;}
        if (length > 1) {sequence[1] = 1;}
        if (length > 2) {sequence[2] = 1;}
        if (length > 3) {sequence[3] = 1;}
        if(length > 4) {sequence =  process(sequence, 4, length);}
        return sequence;
    }

    private static int[] process(int[] sequence, int index, int leng) {
    	if (index >= leng) {
            return sequence;
        }

        int[] s = new int[leng];    	
    	//this part gives us the value at the index inputed
    	int sum = 0;
        for (int j=0; j<index; j++){      
        	if (j == index - 4) {
            	sum+=sequence[j]*2;
            	j++;}
            if (j == index - 3) {sum += sequence[j];}
            recursiveCalls++;
        } 
        //...
        int[] b = new int[leng];
        b = sequence;
        b[index] = sum;
        int d = index + 1;
        if(index < leng) {
	        s = process(b, d, leng);
	        return s;}else {return sequence;}
    }

    private static void printSequence(int[] sequence) {
        for (int i = 0; i < sequence.length; i++) {
            System.out.print(sequence[i] + " ");
        }
    }
}

