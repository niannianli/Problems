package templateMethodDesignPattern;

/*
 * A Template Method Pattern contains a method that provides the steps of the algorithm. 
 * It allows subclasses to overrides some of the methods
 * 
 * http://www.newthinktank.com/2012/10/template-method-design-pattern-tutorial/
 */
public abstract class Food {
	
	/*
	 * This is the Template Method
	 * Declare this method final to keep subclasses from changing the algorithm
	 * 
	 * http://www.newthinktank.com/2012/10/template-method-design-pattern-tutorial/
	 */
	public final void cook(){
		
		prepare();
		
		if(customerWantsMeat()){		
			addMeat();		
		}
		
		if(customerWantsVegetables()){	
			addVegetables();			
		}
	
		saveToPlate();
		
	}
	
	/*
	 *   These are called hooks
	 *   If the user wants to override these, they can
	 *   Use abstract methods when you want to force the user to override 
	 *   And use a hook when you want it to be optional
	 */	
	protected boolean customerWantsMeat() { return true; }
	protected boolean customerWantsVegetables() { return true; }
	
	// These abstract methods must be overridden by the extending subclasses
	protected abstract void addMeat();
	protected abstract void addVegetables();
	
	public void prepare(){		
		System.out.println("I am preparing to cook!");	
	}
		
	public void saveToPlate(){		
		System.out.println("Done!");	
	}
	
}