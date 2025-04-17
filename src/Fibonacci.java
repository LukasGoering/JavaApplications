import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * Recursive calculation of Fibonacci numbers.
 */
public class Fibonacci 
{
	public static BigInteger fibonacci(int num) 
	{
		if (num == 0)
			return BigInteger.valueOf(0);

		if (num == 1 || num == 2)
			return BigInteger.valueOf(1);

		return fibonacci(num - 1).add(fibonacci(num - 2));
	}

	public static void main(String args[]) 
	{
		int length = 0;
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String input = new String();
			while (input.length() < 1) {
				System.out.println("Enter count of fibonacci numbers to calculate [Enter]:");
				System.out.print("> ");
				input = reader.readLine();
				length = Integer.parseInt(input);
			}	
		}
		catch (Exception e){
			System.out.println("An exception occured!");
			System.out.println(e.toString());
			System.exit(1);
		}

		System.out.println("Fibonacci Series of " + length + " number(s) is:");

		for (int i = 0; i < length; i++)
			System.out.println(fibonacci(i));
	}
}