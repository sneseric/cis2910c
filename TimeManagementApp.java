import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimeManagementApp extends JFrame {
    private ArrayList<Task> tasks = new ArrayList<>();

    private JButton addTaskButton;
    private JButton markAsCompleteButton;
    private JList<Task> taskList;
    private DefaultListModel<Task> taskListModel;
    private JTextField taskNameTextField;
    private JTextField dueDateTextField;

    public TimeManagementApp() {
        setTitle("Time Management App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Initialize components
        addTaskButton = new JButton("Add Task");
        markAsCompleteButton = new JButton("Mark as Complete");
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskNameTextField = new JTextField(20);
        dueDateTextField = new JTextField(10);

        // Add action listeners to buttons
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        markAsCompleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAsComplete();
            }
        });

        // Layout components using panels and layout managers
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(new JLabel("Task Name:"));
        inputPanel.add(taskNameTextField);
        inputPanel.add(new JLabel("Due Date (mm-dd-yy):"));
        inputPanel.add(dueDateTextField);
        inputPanel.add(addTaskButton);

        JPanel tasksPanel = new JPanel();
        tasksPanel.setLayout(new BorderLayout());
        tasksPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);
        tasksPanel.add(markAsCompleteButton, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(tasksPanel, BorderLayout.CENTER);
    }

    private void addTask() {
        String name = taskNameTextField.getText();
        String dueDateStr = dueDateTextField.getText();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yy");
        LocalDate dueDate = LocalDate.parse(dueDateStr, formatter);

        Task task = new Task(name, dueDate);
        tasks.add(task);
        taskListModel.addElement(task);

        taskNameTextField.setText("");
        dueDateTextField.setText("");
    }

    private void markAsComplete() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            Task selectedTask = taskListModel.getElementAt(selectedIndex);
            tasks.remove(selectedTask);
            taskListModel.remove(selectedIndex);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TimeManagementApp app = new TimeManagementApp();
                app.pack(); // Automatically resize the window to fit components
                app.setVisible(true);
            }
        });
    }
}

class Task {
    private String name;
    private LocalDate dueDate;
    private boolean isComplete;

    public Task(String name, LocalDate dueDate) {
        this.name = name;
        this.dueDate = dueDate;
        this.isComplete = false;
    }

    // Getters and setters for the name, dueDate, and isComplete

    public String getName() {
        return name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public String toString() {
        return name + " (Due: " + dueDate + ")";
    }
}
