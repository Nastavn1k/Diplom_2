package models;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {

    private final List<String> ingredients = new ArrayList<>();

    public List<String> getIngredients() {
        return ingredients;
    }

    public OrderModel addIngredients(String ingredient) {
        ingredients.add(ingredient);
        return this;
    }
}
