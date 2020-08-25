package duke.tasks;

import duke.DukeAction;
import duke.DukeException;
import duke.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    protected List<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.taskList = new ArrayList<>(tasks);
    }

    public void printList() throws DukeException {
        if(this.taskList.size() != 0) {
            // Dino lists out all items in list
            System.out.println("Dino lists your tasks:");
            for (int i = 0; i < this.taskList.size(); i++) {
                int index = i + 1;
                System.out.println(index + ". " + this.taskList.get(i));
            }
            System.out.println("To mark off a task after completion"
                    + ", input 'done <task number>'.");
        } else {
            throw new DukeException("Rawr! Dino could not find any items in your task list."
                    + "\nGet started by entering a task. "
                    + "Formats for a task can be found by entering 'format'.");
        }
    }

    public String deleteTask(Storage storage, String input) throws DukeException {
        int taskNumber = Integer.parseInt(input.split(" ")[1]);
        if (taskNumber > this.taskList.size() || taskNumber < 1) {
            // task number is not valid
            throw new DukeException("Task " + taskNumber + " is not in your list of tasks!");
        } else {
            // Dino deletes task from list
            int taskIndex = taskNumber - 1;
            Task toDelete = this.taskList.get(taskIndex);
            this.taskList.remove(taskIndex);
            storage.writeToFile(toDelete, DukeAction.DELETE);
            return "Rawr! Dino has deleted " + "Task " + taskNumber
                    + " from your list:\n" + toDelete
                    + "\nNumber of tasks in list: " + this.taskList.size();
        }
    }

    public String addTask(Storage storage, String input) throws DukeException {
        String[] inputWords = input.split(" ");
        try {
            String successStatement;
            switch (inputWords[0]) {
            case "todo":
                String task = input.substring(5);
                Todo todo = new Todo(task);
                this.taskList.add(todo);
                storage.writeToFile(todo, DukeAction.ADD);
                successStatement = "Dino has added to your list of tasks:\n"
                        + todo
                        + "\nNumber of tasks in list: " + this.taskList.size();
                break;
            case "deadline":
                String[] taskBy = input.substring(9).split(" /by");
                if(taskBy.length == 2 && !taskBy[0].equals("")) {
                    // condition checks that input has task description and date/time
                    // task deadline taken as string after first '/by'
                    Deadline deadline = Deadline.createDeadline(taskBy[0], taskBy[1].substring(1));
                    this.taskList.add(deadline);
                    storage.writeToFile(deadline, DukeAction.ADD);
                    successStatement = "Dino has added to your list of tasks:\n"
                            + deadline
                            + "\nNumber of tasks in list: " + this.taskList.size();
                } else {
                    throw new DukeException("Rawr! Dino could not add your task. "
                            + "Make sure your format is correct."
                            + "\nFormats to input a task can be found by entering 'format'.");
                }
                break;
            case "event":
                String[] eventAt = input.substring(6).split(" /at");
                if(eventAt.length == 2 && !eventAt[0].equals("")) {
                    // condition checks that input has task description and date/time

                    // task deadline taken as string after first '/at'
                    Event event = Event.createEvent(eventAt[0], eventAt[1].substring(1));
                    this.taskList.add(event);
                    storage.writeToFile(event, DukeAction.ADD);
                    successStatement = "Dino has added to your list of tasks:\n"
                            + event
                            + "\nNumber of tasks in list: " + this.taskList.size();
                } else {
                    throw new DukeException("Rawr! Dino could not add your task. "
                            + "Make sure your format is correct."
                            + "\nFormats to input a task can be found by entering 'format'.");
                }
                break;
            default:
                // when input is not a deadline, event or todo
                throw new DukeException("Rawr! Dino could not add your task. "
                        + "Make sure your format is correct."
                        + "\nFormats to input a task can be found by entering 'format'.");
            }
            return successStatement;
        } catch(IndexOutOfBoundsException e) {
            // invalid task format entered
            throw new DukeException("Rawr! Dino could not add your task. "
                    + "Make sure your format is correct."
                    + "\nFormats to input a task can be found by entering 'format'.");
        }
    }

    public String markDone(Storage storage, String input) throws DukeException {
        int taskNumber = Integer.parseInt(input.split(" ")[1]);
        if (taskNumber > this.taskList.size() || taskNumber < 1) {
            // task number is not valid
            throw new DukeException("Task " + taskNumber + " is not in your list of tasks!");
        } else {
            // Dino marks task as done
            int taskIndex = taskNumber - 1;
            Task doneTask = this.taskList.get(taskIndex);
            storage.writeToFile(doneTask, DukeAction.MARK_DONE);
            doneTask.markAsDone();
            return "Great! Dino has marked " + "Task " + taskNumber
                    + " as done:\n" + doneTask;
        }
    }

    public String findTask(String input) throws DukeException {
        String[] inputWords = input.split(" ");
        String keyWord = inputWords[1];
        StringBuilder result = new StringBuilder();
        result.append("Here are the matching tasks in your list:");

        int matchingTasks = 0;
        for (int i = 0; i < this.taskList.size(); i++) {
            String taskString = taskList.get(i).toString();
            if (taskString.contains(keyWord)) {
                result.append("\n" + taskString);
                matchingTasks++;
            }
        }

        if (matchingTasks == 0) {
            throw new DukeException("Rawr! Dino could not find " +
                    "any matching tasks in your list.");
        } else {
            return result.toString();
        }
    }

}
