package ui;

import model.TodoList;
import persistence.Loader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainView extends JFrame implements ActionListener  {
    private static final int BUTTON_POSITION = 100;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 20;
    private static final String VIEW_LIST_ACTION = "VIEW_LIST_ACTION";
    private static final String EMPTY_LIST_ACTION = "EMPTY_LIST_ACTION";
    private static final String QUIT_APP_ACTION = "QUIT_APP_ACTION";
    private final TodoList todoList = new TodoList();
    private ListView listView;

    public MainView() {
        super("TodoList Application");
        this.setWindow();
        this.setBackgroundImage();
        Loader.load(todoList);
        this.setUpLabelsAndButtons();
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void setWindow() {
        setPreferredSize(new Dimension(500, 300));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(null);
    }

    private void setBackgroundImage() {
        try {
            BufferedImage backgroundImage = ImageIO.read(new File("src/main/ui/images/background.jpg"));
            setContentPane(new BackgroundImage(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpLabelsAndButtons() {
        JLabel selectOptionLabel = new JLabel("Please select an option: ", JLabel.CENTER);
        selectOptionLabel.setBounds(26, 10, 300, 20);
        add(selectOptionLabel);
        selectOptionLabel.setForeground(Color.black);

        JButton viewListButton = new JButton("View list");
        viewListButton.setBounds(BUTTON_POSITION, 40, BUTTON_WIDTH, BUTTON_HEIGHT);
        add(viewListButton);
        viewListButton.setActionCommand(VIEW_LIST_ACTION);
        viewListButton.addActionListener(this);
        viewListButton.setForeground(Color.black);

        JButton emptyListButton = new JButton("Empty list");
        emptyListButton.setBounds(BUTTON_POSITION, 80, BUTTON_WIDTH, BUTTON_HEIGHT);
        add(emptyListButton);
        emptyListButton.setActionCommand(EMPTY_LIST_ACTION);
        emptyListButton.addActionListener(this);
        emptyListButton.setForeground(Color.black);

        JButton quitAppButton = new JButton("Quit TodoList Application");
        quitAppButton.setBounds(BUTTON_POSITION, 240, BUTTON_WIDTH, BUTTON_HEIGHT);
        add(quitAppButton);
        quitAppButton.setActionCommand(QUIT_APP_ACTION);
        quitAppButton.addActionListener(this);
        quitAppButton.setForeground(Color.black);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals(VIEW_LIST_ACTION)) {
            listView = new ListView(todoList);
        } else if (action.equals(EMPTY_LIST_ACTION)) {
            todoList.emptyList();
            listView.dispose();
            listView = new ListView(todoList);
            JOptionPane.showMessageDialog(null,"Your list has been emptied!");
        } else if (action.equals(QUIT_APP_ACTION)) {
            if (listView != null) {
                listView.dispose();
            }
            dispose();
        }
    }
}
