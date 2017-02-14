package templateMethodDesignPattern;

public class Vegetable extends Food{

	private String[] veggiesUsed = { "Broccoli", "Eggplant"};

	protected boolean customerWantsMeat() { return false; }
	
	public void addVegetables(){
		
		System.out.println("Adding the Vegetables: ");
		
		for (String vegetable : veggiesUsed){		
			System.out.print(vegetable + " ");		
		}
		System.out.println();	
	}
	
	protected void addMeat() {}
	
	/*
	 *  If we do not use Template Method Design Pattern: 
	 *  we have to write duplicate code in both Meat class and Vegetable class
	 */
}