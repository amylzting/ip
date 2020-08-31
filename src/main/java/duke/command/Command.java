package duke.command;

import duke.DukeException;
import duke.storage.Storage;
import duke.tasks.TaskList;
import duke.ui.Ui;

public abstract class Command {
    protected boolean isBye = false;

    public boolean isBye() {
        return this.isBye;
    }

    /**
     * Executes action done to task in TaskList.
     * @param storage Storage data of tasks in hard disk.
     * @param taskList TaskList where task actions are done.
     * @param ui Ui that shows relevant messages to user.
     */
    public abstract void execute(Storage storage, TaskList taskList, Ui ui) throws DukeException;

    public abstract String executeToString(Storage storage, TaskList taskList, Ui ui) throws DukeException;
}

