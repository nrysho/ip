public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }
    
    public String toString() {
        return description;
    }

    public void mark() {
        this.isDone = true;
        System.out.println("good job! I've marked " + "[task: " +  this.toString() + "] as DONE");
    }

    public void unmark() {
        this.isDone = false;
        System.out.println("aite, I've unmarked " + "[task: " + this.toString() + "]");
    }

    //...
}