package dickie.utils;

import dickie.task.Priority;
import dickie.task.Task;
import dickie.task.Todo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static dickie.task.Priority.HIGH;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void find_success() {
        ArrayList<Task> tasks = new ArrayList<>();
        Todo task1 = new Todo("run marathon", Priority.LOW);
        Todo task2 = new Todo("eat breakfast", Priority.MEDIUM);
        Todo task3 = new Todo("make breakfast", Priority.HIGH);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        TaskList taskList = new TaskList(tasks);

        ArrayList<Task> matchingTasks = taskList.find("breakfast");

        ArrayList<Task> expectedResult = new ArrayList<Task>();
        expectedResult.add(task2);
        expectedResult.add(task3);

        assertEquals(matchingTasks, expectedResult);
    }
}
