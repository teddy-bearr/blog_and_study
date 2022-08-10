package d_factoryPattern.b_abstractFactoryPattern.pizza.pizzaStyle;

import d_factoryPattern.b_abstractFactoryPattern.ingredient.PizzaIngredientFactory;
import d_factoryPattern.b_abstractFactoryPattern.pizza.Pizza;

public class CheesePizza extends Pizza {
    PizzaIngredientFactory ingredientFactory;

    public CheesePizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    @Override
    public void prepare() {
        System.out.println("준비 중:" + name);
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
    }
}
