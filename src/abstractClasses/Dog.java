package abstractClasses;

public class Dog extends Animal {

	public Dog(String name) {
		super(name);
	}

	@Override
	protected void makeSound() {
		System.out.println("I am a dog, Woof!");
	}

}
