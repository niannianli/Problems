package templateMethodDesignPattern;

public class Meat extends Food {
	
	private String[] meatUsed = { "Chicken", "Shrimp", "Beef" };
	protected boolean customerWantsVegetables() { return false; }
	
	public void addMeat(){		
		
		System.out.println("Adding the Meat: ");	
		for (String meat : meatUsed){		
			System.out.print(meat + " ");		
		}	
		System.out.println();	
	}

	@Override
	protected void addVegetables() {
	}
	
	/*
	 *  If we do not use Template Method Design Pattern: 
	 *  we have to write duplicate code in both Meat class and Vegetable class
	 */
}