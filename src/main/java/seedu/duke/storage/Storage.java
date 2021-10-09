package seedu.duke.storage;

import seedu.duke.calories.WhatIAteList;
import seedu.duke.task.Task;
import seedu.duke.task.TaskList;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import static seedu.duke.constants.Messages.STORAGE_FILEPATH_SCHEDULE;

public class Storage {

    /**
     * Synchronised date with food record  list.
     *
     * @author ngnigel99
     * */
    //TODO storage  file  implementation with hard-disk capability
    //TODO sync todaysDate from  file as well

    private Date todaysDate;
    public WhatIAteList whatIAteTodayList =  new WhatIAteList(todaysDate);

    //@author swatim
    //Loads data in the form of ArrayList<String> data from the save file
    public ArrayList<String> loadDataFromSaveFile(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> data = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            data.add(line);
        }
        return data;
    }

    //Write data from ArrayList<String> data onto save file
    public void writeDataOntoSaveFile(String filePath, ArrayList<String> data) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        for (String dataLine : data) {
            bufferedWriter.write(dataLine + '\n');
        }
        bufferedWriter.close();
    }

    //SCHEDULE BEGIN
    //Write tasks data onto save file using writeDataOntoSaveFile() method
    //ArrayList<Task> tasks--->ArrayList<String> data--->save file
    //Can do the same for other save files by replacing the filepath constant
    public void writeTaskList(TaskList taskList) throws IOException {
        ArrayList<Task> tasks = taskList.getTaskList();
        ArrayList<String> data = StorageTasks.tasksToData(tasks);
        writeDataOntoSaveFile(STORAGE_FILEPATH_SCHEDULE, data);
    }

    //save file--->ArrayList<String> data--->ArrayList<Task> tasks
    //Can do the same for other save files by replacing the filepath constant
    public ArrayList<Task> readTaskList() throws NullPointerException, IOException {
        ArrayList<String> data = loadDataFromSaveFile(STORAGE_FILEPATH_SCHEDULE);
        return StorageTasks.dataToTask(data);
    }
    //SCHEDULE END
}
