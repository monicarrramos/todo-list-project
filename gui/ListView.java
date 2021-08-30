package ui;

import model.Item;
import model.TodoList;
import persistence.Saver;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ListView extends JFrame implements ActionListener {
    private DefaultTableModel tableModel;
    private JTable table;
    private TodoList todoList;
    private static final String ADD_ITEM_ACTION = "ADD_ITEM_ACTION";
    private static final String MARK_AS_DONE_ACTION = "MARK_AS_DONE_ACTION";

    public ListView(TodoList todList) {
        this.todoList = todList;
        this.setBackgroundImage();
        final String[] columnLabels = new String[] {
                "Index",
                "Name",
                "Due Date",
                "Status"
        };
        tableModel = new DefaultTableModel(null, columnLabels) {};
        table = new JTable(tableModel);
        this.populateTableRows();

        add(new JScrollPane(table));
        this.setButtons();
        setTitle("my todo list");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void setBackgroundImage() {
        try {
            BufferedImage backgroundImage = ImageIO.read(new File("src/main/ui/images/background.jpg"));
            setContentPane(new BackgroundImage(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateTableRows() {
        for (int i = 0; i < todoList.size(); i++) {
            Item item = todoList.getItem(i);
            Object[] tableRow = new Object[] {
                    i, // index column
                    item.getName(), // name column
                    item.getDueDate(), // due date column
                    item.getStatus().name() // status column
            };
            tableModel.addRow(tableRow);
        }
    }

    private void setButtons() {
        JButton addItemButton = new JButton(("Add a new item"));
        add(addItemButton);
        addItemButton.setActionCommand(ADD_ITEM_ACTION);
        addItemButton.addActionListener(this);
        addItemButton.setForeground(Color.darkGray);

        JButton markItemAsDoneButton = new JButton("Mark item as done");
        add(markItemAsDoneButton);
        markItemAsDoneButton.setActionCommand(MARK_AS_DONE_ACTION);
        markItemAsDoneButton.addActionListener(this);
        markItemAsDoneButton.setForeground(Color.darkGray);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals(ADD_ITEM_ACTION)) {
            new AddItemView(this, todoList);
        } else if (action.equals(MARK_AS_DONE_ACTION)) {
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex == -1) {
                JOptionPane.showMessageDialog(null,"Please select an item to mark as done.");
                return;
            }
            todoList.markItemAsDone(selectedRowIndex);
            table.setValueAt((Object) todoList.getItem(selectedRowIndex).getStatus().name(), selectedRowIndex, 3);
            Saver.save(todoList);
        }
    }
}
