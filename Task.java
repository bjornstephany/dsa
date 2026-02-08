package Part2;
public class Task {
// DO NOT CHANGE ANYTHING IN THIS CLASS.
public final String type;
public final int duration, id;
public Task(String type, int duration, int id) {
this.type = type;
this.duration = duration;
this.id = id;
}

@Override
public String toString() {
return "Task{" +
"type='" + type + '\'' +
", duration=" + duration +
", id=" + id +
'}';
}
}
