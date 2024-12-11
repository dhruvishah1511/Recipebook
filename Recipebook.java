import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class RecipeBook {
    public static void main(String[] args) {
        List<Recipe> recipes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("############################################");

        System.out.println("############## Lj Recipy Book ##############");
        System.out.println("############################################");
        System.out.println("***** Lets Make Something Delicious... *****");

        while (true) {

            System.out.println("Recipe Book Menu:");
            System.out.println("1. Add a Recipe");
            System.out.println("2. View Recipes");
            System.out.println("3. Find Recipes by Ingredients");
            System.out.println("4. Save Found Recipes to File");
            System.out.println("5. Save Recipe to File by Name");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addRecipe(recipes, scanner);
                    break;
                case 2:
                    viewRecipes(recipes);
                    break;
                case 3:
                    findRecipesByIngredients(recipes, scanner);
                    break;
                case 4:
                    saveFoundRecipesToFile(recipes, scanner);
                    break;

                case 5:
                    saveRecipeToFileByName(recipes, scanner);
                    break;
                case 6:
                    System.out.println("********** Thanks **********");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addRecipe(List<Recipe> recipes, Scanner scanner) {
        System.out.print("Enter the recipe name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the ingredients (comma-separated): ");
        String ingredientsStr = scanner.nextLine();
        List<String> ingredients = List.of(ingredientsStr.split(", "));
        System.out.print("Enter the instructions (one per line, enter 'done' to finish):\n");
        List<String> instructions = new ArrayList<>();
        while (true) {
            String instruction = scanner.nextLine();
            if (instruction.equalsIgnoreCase("done")) {
                break;
            }
            instructions.add(instruction);
        }
        Recipe recipe = new Recipe(name, ingredients, instructions);
        recipes.add(recipe);
        System.out.println("**********Recipe added successfully!**********");
    }

    private static void viewRecipes(List<Recipe> recipes) {
        if (recipes.isEmpty()) {
            System.out.println("No recipes to display.");
        } else {
            System.out.println("Recipes:");
            for (int i = 0; i < recipes.size(); i++) {
                System.out.println("Recipe " + (i + 1) + ":");
                System.out.println(recipes.get(i));
            }
        }
    }

    private static void findRecipesByIngredients(List<Recipe> recipes, Scanner scanner) {
        System.out.print("Enter ingredients to search for (comma-separated): ");
        String searchIngredientsStr = scanner.nextLine().toLowerCase();
        String[] searchIngredientArray = searchIngredientsStr.split(",");

        List<Recipe> foundRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            List<String> recipeIngredients = recipe.getIngredients();
            boolean found = true;

            for (String searchIngredient : searchIngredientArray) {
                boolean ingredientFound = false;

                for (String recipeIngredient : recipeIngredients) {
                    if (recipeIngredient.toLowerCase().contains(searchIngredient.trim())) {
                        ingredientFound = true;
                        break;
                    }
                }

                if (!ingredientFound) {
                    found = false;
                    break;
                }
            }

            if (found) {
                foundRecipes.add(recipe);
            }
        }

        if (foundRecipes.isEmpty()) {
            System.out.println("No recipes found with the specified ingredients.");
        } else {
            System.out.println("Recipes found with the specified ingredients:");
            for (Recipe recipe : foundRecipes) {
                System.out.println(recipe);
            }
        }
    }

    private static void saveFoundRecipesToFile(List<Recipe> recipes, Scanner scanner) {
        System.out.print("Enter the file name to save found recipes: ");
        String fileName = scanner.nextLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Recipe recipe : recipes) {
                writer.write("Recipe: " + recipe.getName());
                writer.newLine();
                writer.write("Ingredients: " + recipe.getIngredients());
                writer.newLine();
                writer.write("Instructions:");
                writer.newLine();
                List<String> instructions = recipe.getInstructions();
                for (int i = 0; i < instructions.size(); i++) {
                    writer.write((i + 1) + ". " + instructions.get(i));
                    writer.newLine();
                }
                writer.newLine();
            }
            System.out.println("Found recipes saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving found recipes to file: " + e.getMessage());
        }
    }

    private static void saveRecipeToFileByName(List<Recipe> recipes, Scanner scanner) {
        System.out.print("Enter the recipe name to save: ");
        String recipeName = scanner.nextLine();
        Recipe recipe = null;

        for (Recipe r : recipes) {
            if (r.getName().equalsIgnoreCase(recipeName)) {
                recipe = r;
                break;
            }
        }

        if (recipe != null) {
            System.out.print("Enter the file name to save the recipe: ");
            String fileName = scanner.nextLine();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("Recipe: " + recipe.getName());
                writer.newLine();
                writer.write("Ingredients: " + recipe.getIngredients());
                writer.newLine();
                writer.write("Instructions:");
                writer.newLine();
                List<String> instructions = recipe.getInstructions();
                for (int i = 0; i < instructions.size(); i++) {
                    writer.write((i + 1) + ". " + instructions.get(i));
                    writer.newLine();
                }
                System.out.println("Recipe saved to " + fileName);
            } catch (IOException e) {
                System.out.println("Error saving recipe to file: " + e.getMessage());
            }
        } else {
            System.out.println("Recipe not found.");
        }
    }
}

class Recipe {
    private String name;
    private List<String> ingredients;
    private List<String> instructions;

    public Recipe(String name, List<String> ingredients, List<String> instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        StringBuilder recipeStr = new StringBuilder();
        recipeStr.append("Name: ").append(name).append("\n");
        recipeStr.append("Ingredients: ").append(ingredients).append("\n");
        recipeStr.append("Instructions:\n");
        for (int i = 0; i < instructions.size(); i++) {
            recipeStr.append((i + 1)).append(". ").append(instructions.get(i)).append("\n");
        }
        return recipeStr.toString();
    }
}
