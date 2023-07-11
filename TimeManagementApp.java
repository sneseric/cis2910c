import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeManagementApp extends JFrame {
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;
    private JTextField taskNameField;
    private JButton addButton;
    private JLabel progressLabel;

    private List<Task> tasks;

    public TimeManagementApp() {
        super("Time Management App");

        tasks = new ArrayList<>();
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskNameField = new JTextField(20);
        addButton = new JButton("Add Task");
        progressLabel = new JLabel();

        // layout
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(taskNameField);
        topPanel.add(addButton);
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(progressLabel, BorderLayout.SOUTH);

        // Add action listener for add task button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        // Schedule timer task to update progress label at every second
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateProgress();
            }
        }, 0, 1000);
    }

    private void addTask() {
        String taskName = taskNameField.getText().trim();
        if (!taskName.isEmpty()) {
            Task task = new Task(taskName);
            tasks.add(task);
            taskListModel.addElement(task);
            taskNameField.setText("");
        }
    }

    private void updateProgress() {
        int completedTasks = 0;
        int totalTasks = tasks.size();
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completedTasks++;
            }
        }

        String progressText = "Progress: " + completedTasks + "/" + totalTasks + " tasks completed";
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressLabel.setText(progressText);
            }
        });
    }

    private class Task {
        private String name;
        private LocalDateTime dueDate;
        private boolean completed;

        public Task(String name) {
            this.name = name;
            this.dueDate = LocalDateTime.now().plusMinutes(5); // Set a default due date to 5 minutes
            this.completed = false;
        }

        public String getName() {
            return name;
        }

        public LocalDateTime getDueDate() {
            return dueDate;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDueDate = dueDate.format(formatter);
            String status = completed ? "[✓]" : "[ ]";
            return status + " " + name + " (Due: " + formattedDueDate + ")";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TimeManagementApp app = new TimeManagementApp();
                app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                app.setSize(400, 300);
                app.setVisible(true);
            }
        });
    }
}
