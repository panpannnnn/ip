package duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Storage object to keep track of the final TaskList
 * and store into the file
 */

public class Storage {
    private File file;
    private boolean hasData;
    private TaskList list;

    /**
     * Constructor of the Storage class
     * @param list Current TaskList
     */
    public Storage(TaskList list) {
        this.list = list;
        Path path = Paths.get("src/main/data/duke.txt");
        this.hasData = java.nio.file.Files.exists(path);
        this.file = path.toFile();
    }

    /**
     * Checks whether there is data available
     * @throws DukeException
     */
    public void findData() throws DukeException {
        try {
            System.out.println("ʕっ￫ᴥ￩ʔっ :: Checking past storage...");
            if (!hasData) {
                throw new DukeException("ʕ•̀ω•́ʔ!! :: Past data does not exist!");
            }
            System.out.println("ʕっ￫ᴥ￩ʔっ :: Successfully retrieved past data!");
        } catch (DukeException e) {
            System.out.println(e);
        }
    }

    /**
     * Connects the file to the application and restores the past data
     * @throws DukeException
     */
    public void connect() throws DukeException {
        try {
            Scanner sc = new Scanner(this.file);
            while (sc.hasNextLine()) {
                String[] input = sc.nextLine().split(" \\| ");
                Task task;
                if (input[0].equals("T")) {
                    task = new ToDo(input[2]);
                } else if (input[0].equals("D")) {
                    task = new Deadline(input[2], input[3]);
                } else if (input[0].equals("E")) {
                    task = new Event(input[2], input[3], input[4]);
                } else {
                    throw new DukeException("╮ʕ˚ᴥ˚ʔ╭ :: ☹ OOPS!!! I don't know what the input means!");
                }
                if (input[1].equals("1")) {
                    task.setDone();
                }
                list.addTask(task);
            }
        } catch (FileNotFoundException e) {
            throw new DukeException(e.toString());
        } catch (DukeException e) {
            System.out.println(e);
        }
    }

    /**
     * Saves the final state of the TaskList into the file
     */
    public void save() {
        try {
            FileWriter writer = new FileWriter("src/main/data/duke.txt");
            for (int i = 0; i < list.getSize(); i++) {
                String line = list.getTask(i).toSave();
                writer.write(line);

            }
            writer.close();
            System.out.println("ʕ•̀ω•́ʔ✧ :: Data successfully saved!");
        } catch (IOException e) {
            System.out.println("ʕ•̀ω•́ʔ!! :: ☹ OOPS!!! I cannot write to the file!");
        }
    }
}