package ui;

import model.Item;
import model.TodoList;
import persistence.Saver;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddItemView extends JFrame implements ActionListener {
    JTextField itemNameField;
    JTextField itemDueDateField;
    ListView listView;
    TodoList todoList;
    private static final String FINISH_ACTION = "FINISH_ACTION";

    public AddItemView(ListView listView, TodoList todoList) {
        super("Add an Item");
        this.listView = listView;
        this.todoList = todoList;
        this.setWindow();

        this.setLabelsFieldsButtons();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void setWindow() {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(700, 300));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(null);
    }

    private void setLabelsFieldsButtons() {
        JLabel itemNameLabel = new JLabel("Enter item name: ");
        itemNameLabel.setBounds(48, 40, 400, 20);
        add(itemNameLabel);
        itemNameLabel.setForeground(Color.darkGray);

        itemNameField = new JTextField(30);
        itemNameField.setBounds(50, 70, 300, 20);
        add(itemNameField);

        JLabel itemDueDateLabel = new JLabel(
                "Enter item due date: (Date format should be yyyy-MM-dd, e.g. 2019-12-31)");
        itemDueDateLabel.setBounds(50, 100, 600, 20);
        add(itemDueDateLabel);
        itemDueDateLabel.setForeground(Color.darkGray);

        itemDueDateField = new JTextField(30);
        itemDueDateField.setBounds(50, 130,300,20);
        add(itemDueDateField);

        JButton finishButton = new JButton("Finish");
        finishButton.setBounds(310,210,100,20);
        add(finishButton);
        finishButton.setActionCommand(FINISH_ACTION);
        finishButton.addActionListener(this);
        finishButton.setForeground(Color.darkGray);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals(FINISH_ACTION)) {
            String name = itemNameField.getText();
            String dueDate = itemDueDateField.getText();
            todoList.addItem(new Item(name, dueDate));
            Saver.save(todoList);
            listView.dispose();
            new ListView(todoList);
            dispose();
        }
    }
}
