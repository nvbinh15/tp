package seedu.duke.food;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.AddFoodCommand;
import seedu.duke.constants.Messages;
import seedu.duke.exceptions.ArgumentsNotFoundException;
import seedu.duke.exceptions.IllegalFoodParameterException;
import seedu.duke.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FoodTest {
    private Parser parser = new Parser();

    /**
     * Checks food toString correctly.
     * @author ngnigel99
     */
    @Test
    void testFoodToString() {
        assertEquals("Samurai Burger : 433", new FoodRecord("Samurai Burger", 433).toString());
    }

    /**
     * Checks correct food syntax parsed in correctly.
     * @author ngnigel99
     */
    @Test
    void testAddFoodCommand() throws IllegalFoodParameterException, ArgumentsNotFoundException {
        String correctUserInput = "food add n/ Samurai Burger c/ 433";
        FoodRecord testFoodRecord =  parser.parseFoodRecord(correctUserInput);
        FoodRecord actualFoodRecord = new FoodRecord("Samurai Burger", 433);
        //test fields are correctly constructed
        assertEquals(testFoodRecord.getFoodName(), actualFoodRecord.getFoodName());
        assertEquals(testFoodRecord.getCalorieCount(), actualFoodRecord.getCalorieCount());
    }

    /**
     * Checks for NumberFormatException on adding a food item.
     * @author ngnigel99
     */
    @Test
    void addFoodItem_stringNotInt_illegalFoodParameterThrown() throws IllegalFoodParameterException {
        boolean nfeThrown = false;
        try {
            FoodRecord foodRecord = parser.parseFoodRecord("name_test" + " " + "sample_string");
        } catch (Exception e) {
            assertEquals(Messages.PRINT_ADD_FOOD_SYNTAX, e.getMessage());
        }
    }

    /**
     * Checks for IllegalFoodParameterException thrown when word count != 2.
     * @author ngnigel99
     */
    @Test
    void addFoodItem_wrongSyntax_illegalFoodParameterExceptionThrown() throws IllegalFoodParameterException {
        try {
            FoodRecord foodRecord = parser.parseFoodRecord("Cheezburger");  //calorie count not included
        } catch (Exception e) {
            assertEquals(Messages.PRINT_ADD_FOOD_SYNTAX, e.getMessage());
        }
    }

    /**
     * Checks for illegal food parameter exception thrown on null input.
     * @author ngnigel99
     */
    @Test
    void addFoodItem_noInputGiven_illegalFoodParameterThrown() throws IllegalFoodParameterException {
        try {
            FoodRecord foodRecord = parser.parseFoodRecord("");
        } catch (Exception e) {
            assertEquals(Messages.PRINT_ADD_FOOD_SYNTAX, e.getMessage());
        }
    }

}
