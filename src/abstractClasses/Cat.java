package abstractClasses;

public class Cat extends Animal {

	public Cat(String name) {
		super(name);
	}

	@Override
	protected void makeSound() {
		System.out.println("I am a cat, Meow!");
	}

}
