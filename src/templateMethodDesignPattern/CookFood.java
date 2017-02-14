package templateMethodDesignPattern;

public class CookFood {
	
	public static void main(String[] args){
		
		Meat meat = new Meat();		
		meat.cook();
		
		System.out.println();
		
		Vegetable vegetable = new Vegetable();	
		vegetable.cook();
		
	}	
}