import java.util.Random;

public class MakeRandom {
	public static void main(String[] args) {
		int MAX_INT;
		long seed;
		
		if (args.length == 1) { 
			MAX_INT = Integer.parseInt(args[0]);
			seed = System.currentTimeMillis();
		}else if (args.length == 2) {
			seed = Long.parseLong(args[0]);
			MAX_INT = Integer.parseInt(args[1]);
		}else {
			System.out.println("You can enter either 1 argument (MAX_INT) or 2 maximum (seed, MAX_INT).");
			return;
		}
		
		Random random = new Random(seed);
		int[] randomIntArr = new int[MAX_INT];
		
		for (int i = 0; i < MAX_INT; i++) {
			randomIntArr[i] = random.nextInt(Integer.MAX_VALUE);
		}
		
		for (int i = 0; i < MAX_INT; i++) {
			System.out.print(randomIntArr[i] + " ");
		}
		
	}
	
}