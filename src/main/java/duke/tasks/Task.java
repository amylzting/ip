package duke.tasks;
public class Task {
    protected String taskName;
    protected boolean isDone;

    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "1" : "0"); //return 1 or 0 symbols
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + taskName;
    }

    public String storedTaskString() {
        return this.getStatusIcon() + "@" + taskName;
    }
}