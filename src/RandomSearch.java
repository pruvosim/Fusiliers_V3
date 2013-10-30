import java.util.Random;


public class RandomSearch {
	
	int n = 5 + 1;
	int [] rules = new int[n * n * n];
	
	public RandomSearch()
	{
		Random generator = new Random();

		try {

			for(int i=0;i<216;i++)
			{		
				rules[i] = generator.nextInt(4);
			} 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
	}


}
