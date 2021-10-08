package seedu.duke.parser;

import seedu.duke.calories.FoodRecord;
import seedu.duke.commands.AddFoodCommand;
import seedu.duke.commands.AddTodoCommand;
import seedu.duke.commands.AddNoteCommand;
import seedu.duke.commands.ListTasksCommand;
import seedu.duke.commands.ListFoodCommand;
import seedu.duke.commands.ExitCommand;
import seedu.duke.commands.ClearFoodCommand;
import seedu.duke.commands.Command;
import seedu.duke.commands.DisplayCalendarCommand;
import seedu.duke.exceptions.ClickException;
import seedu.duke.exceptions.IllegalDateTimeException;
import seedu.duke.exceptions.IllegalFoodParameterException;
import seedu.duke.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static seedu.duke.constants.CommandConstants.COMMAND_CALENDAR;
import static seedu.duke.constants.CommandConstants.COMMAND_EXIT;
import static seedu.duke.constants.CommandConstants.COMMAND_ADD_NOTE;
import static seedu.duke.constants.CommandConstants.COMMAND_FOOD_ADD;
import static seedu.duke.constants.CommandConstants.COMMAND_FOOD_CLEAR;
import static seedu.duke.constants.CommandConstants.COMMAND_FOOD_LIST;
import static seedu.duke.constants.CommandConstants.COMMAND_LIST_TASKS;
import static seedu.duke.constants.CommandConstants.COMMAND_TODO;
import static seedu.duke.constants.Messages.EMPTY_STRING;
import static seedu.duke.constants.Messages.TODO;

//@@author nvbinh15
public class Parser {

    static final String INPUT_DATE_TIME_FORMAT = "dd-MM-yyyy HHmm";
    static final String OUTPUT_DATE_TIME_FORMAT = "MMM dd yyyy HH:mm";


    /**
     * Converts date and time in string format to a LocalDateTime object.
     *
     * @param string Date and time in string format.
     * @return A LocalDateTime object that represents the given date and time.
     * @throws IllegalDateTimeException If the input date time is invalid.
     */
    public static LocalDateTime stringToDateTime(String string) throws IllegalDateTimeException {
        try {
            return LocalDateTime.parse(string, DateTimeFormatter.ofPattern(INPUT_DATE_TIME_FORMAT));
        } catch (Exception e) {
            throw new IllegalDateTimeException();
        }
    }

    /**
     * Converts date and time in LocalDateTime format to a string.
     *
     * @param dateTime A LocalDateTime object that represents the given date and time.
     * @return The string representation of the given LocalDateTime object.
     */
    public static String dateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(OUTPUT_DATE_TIME_FORMAT));
    }

    /**
     * Formats the user's input date time.
     *
     * @param rawDateTime The raw date time from user's input.
     * @return The string representation of other form of the given date time.
     * @throws IllegalDateTimeException If the input date time is invalid.
     */
    public static String formatDateTime(String rawDateTime) throws IllegalDateTimeException {
        try {
            return dateTimeToString(stringToDateTime(rawDateTime));
        } catch (IllegalDateTimeException e) {
            throw new IllegalDateTimeException();
        }
    }

    /**
     * Splits raw user input into command word and command arguments.
     *
     * @param userInput Raw input from user.
     * @return a String array of size 2 including the command type and the arguments.
     */
    public static String[] splitCommandAndArgs(String userInput) {
        String[] tokens = userInput.trim().split("\\s+", 2);
        String command = tokens[0];

        if (tokens.length == 2) {
            return tokens;
        } else {
            return new String[] {command, EMPTY_STRING};
        }
    }

    private static ArrayList<String> parseTodoCommand(String input) {
        ArrayList<String> argumentsTodoCommand = new ArrayList<>();
        //Split command name away from input string
        String todoDetails = input.trim().split("todo")[1];
        String descriptionAndDate = todoDetails.split("n/")[1].trim();
        //Split description and date
        String description = descriptionAndDate.split("d/")[0].trim();
        String date = descriptionAndDate.split("d/")[1].trim();

        List<String> todoArguments = Arrays.asList(TODO, description, date);
        argumentsTodoCommand.addAll(todoArguments);
        return argumentsTodoCommand;
    }

    /**
     * Returns a command to be executed based on the raw input from user.
     *
     * @param userInput The raw input from user.
     * @return The command to be executed.
     * @throws ClickException If there is an exception to type DukeException occurs.
     */
    public Command parseCommand(String userInput) throws ClickException {
        final String[] commandTypeAndParams = splitCommandAndArgs(userInput);
        final String commandType = commandTypeAndParams[0];
        final String commandArgs = commandTypeAndParams[1];

        switch (commandType) {
        case COMMAND_EXIT:
            return new ExitCommand();
        case COMMAND_CALENDAR:
            String[] todoArguments = commandArgs.split(" ");
            if (COMMAND_LIST_TASKS.equals(todoArguments[0])) {
                return new ListTasksCommand();
            } else if (COMMAND_TODO.equals(todoArguments[0])) {
                ArrayList<String> arguments = parseTodoCommand(userInput);
                return new AddTodoCommand(arguments);
            } else {
                return new DisplayCalendarCommand(userInput);
            }
        case COMMAND_ADD_NOTE:
            return new AddNoteCommand(userInput);
        case COMMAND_FOOD_ADD:
            return new AddFoodCommand();
        case COMMAND_FOOD_CLEAR:
            return new ClearFoodCommand();
        case COMMAND_FOOD_LIST:
            return new ListFoodCommand();
        default:
            throw new ClickException();
        }
    }

    //@author swatim
    public static String[] parseCalendarCommand(String input) {
        // takes substring excluding "calendar" from command
        String extractMonthYear = input.substring(9);
        String[] arguments = extractMonthYear.split("-");
        return arguments;
    }

    /**
     * Parses a string into a food item.
     *
     * @author ngnigel99
     */
    public static FoodRecord parseFoodRecord(String input) throws IllegalFoodParameterException {
        try {
            String[] splitInput = input.trim().split(" ");
            if (splitInput.length != 2) {
                throw new IllegalFoodParameterException();
            }
            int calories = Integer.parseInt(splitInput[1]);
            String name  = splitInput[0];
            FoodRecord recordToAdd = new FoodRecord(name, calories);
            return recordToAdd;
        } catch (NumberFormatException e) {
            Ui.printAddFoodSyntax();
        } catch (NullPointerException e) {
            Ui.printNonNullInput();
        }
        return null;
    }
}

