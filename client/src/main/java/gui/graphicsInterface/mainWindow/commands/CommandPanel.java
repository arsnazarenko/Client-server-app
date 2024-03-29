package gui.graphicsInterface.mainWindow.commands;


import gui.graphicsInterface.LocaleActionListener;
import gui.graphicsInterface.controllers.Controllers;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class CommandPanel extends JPanel implements LocaleActionListener {
    private final String FONT;
    private Buttons buttons;
    private JLabel titleLabel;

    public CommandPanel(String FONT) {
        this.FONT = FONT;
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        buttons = new Buttons();
        titleLabel = new JLabel();
        setPosition();
        localeChange(Controllers.getLocale());

    }

    private void setPosition() {

        titleLabel.setFont(new Font(FONT, Font.PLAIN, 15));

        add(titleLabel, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.showButton, new GridBagConstraints(0, 1, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.addButton, new GridBagConstraints(0, 2, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.addIfMinButton, new GridBagConstraints(0, 3, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.updateIdButton, new GridBagConstraints(0, 4, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.removeIdButton, new GridBagConstraints(0, 5, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.removeLowerButton, new GridBagConstraints(0, 6, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.clearButton, new GridBagConstraints(0, 8, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.infoButton, new GridBagConstraints(0, 9, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.headButton, new GridBagConstraints(0, 10, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.filterNameButton, new GridBagConstraints(0, 11, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.maxEmplButton, new GridBagConstraints(0, 12, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
        add(buttons.printAscendButton, new GridBagConstraints(0, 13, 1, 1, 0, 0,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1),
                0, 0));
    }

    @Override
    public void localeChange(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("translate",locale);
        titleLabel.setText(bundle.getString("commands"));
        buttons.getAddButton().setToolTipText(bundle.getString("add_command"));
        buttons.getInfoButton().setToolTipText(bundle.getString("info_command"));
        buttons.getShowButton().setToolTipText( bundle.getString("show_command"));
        buttons.getUpdateIdButton().setToolTipText(bundle.getString("update_command"));
        buttons.getRemoveIdButton().setToolTipText(bundle.getString("remove_command"));
        buttons.getClearButton().setToolTipText(bundle.getString("clear_command"));
        buttons.getHeadButton().setToolTipText(bundle.getString("head_command"));
        buttons.getAddIfMinButton().setToolTipText(bundle.getString("add_if_min_command"));
        buttons.getRemoveLowerButton().setToolTipText(bundle.getString("remove_lower_command"));
        buttons.getMaxEmplButton().setToolTipText(bundle.getString("max_by_employees_count_command"));
        buttons.getFilterNameButton().setToolTipText(bundle.getString("filter_contains_name_command"));
        buttons.getPrintAscendButton().setToolTipText(bundle.getString("print_ascending_command"));
    }


    public class Buttons {
        private JButton showButton;
        private JButton addButton;
        private JButton addIfMinButton;
        private JButton clearButton;
        private JButton removeIdButton;
        private JButton removeLowerButton;
        private JButton infoButton;
        private JButton updateIdButton;
        private JButton filterNameButton;
        private JButton headButton;
        private JButton maxEmplButton;
        private JButton printAscendButton;

        {
            showButton = new JButton("show");
            showButton.setBackground(Color.LIGHT_GRAY);
            showButton.setFocusPainted(false);
            showButton.setFont(new Font(FONT, Font.PLAIN, 12));

            addButton = new JButton("add");
            addButton.setBackground(Color.LIGHT_GRAY);
            addButton.setFocusPainted(false);
            addButton.setFont(new Font(FONT, Font.PLAIN, 12));

            addIfMinButton = new JButton("add if min");
            addIfMinButton.setBackground(Color.LIGHT_GRAY);
            addIfMinButton.setFocusPainted(false);
            addIfMinButton.setFont(new Font(FONT, Font.PLAIN, 12));

            clearButton = new JButton("clear");
            clearButton.setBackground(Color.LIGHT_GRAY);
            clearButton.setFocusPainted(false);
            clearButton.setFont(new Font(FONT, Font.PLAIN, 12));

            removeIdButton = new JButton("remove by id");
            removeIdButton.setBackground(Color.LIGHT_GRAY);
            removeIdButton.setFocusPainted(false);
            removeIdButton.setFont(new Font(FONT, Font.PLAIN, 12));

            removeLowerButton = new JButton("remove Lower");
            removeLowerButton.setBackground(Color.LIGHT_GRAY);
            removeLowerButton.setFocusPainted(false);
            removeLowerButton.setFont(new Font(FONT, Font.PLAIN, 12));

            infoButton = new JButton("info");
            infoButton.setBackground(Color.LIGHT_GRAY);
            infoButton.setFocusPainted(false);
            infoButton.setFont(new Font(FONT, Font.PLAIN, 12));

            updateIdButton = new JButton("update by id");
            updateIdButton.setBackground(Color.LIGHT_GRAY);
            updateIdButton.setFocusPainted(false);
            updateIdButton.setFont(new Font(FONT, Font.PLAIN, 12));

            filterNameButton = new JButton("filter contains name");
            filterNameButton.setBackground(Color.LIGHT_GRAY);
            filterNameButton.setFocusPainted(false);
            filterNameButton.setFont(new Font(FONT, Font.PLAIN, 12));

            headButton = new JButton("head");
            headButton.setBackground(Color.LIGHT_GRAY);
            headButton.setFocusPainted(false);
            headButton.setFont(new Font(FONT, Font.PLAIN, 12));

            maxEmplButton = new JButton("max by Employees");
            maxEmplButton.setBackground(Color.LIGHT_GRAY);
            maxEmplButton.setFocusPainted(false);
            maxEmplButton.setFont(new Font(FONT, Font.PLAIN, 12));


            printAscendButton = new JButton("print ascending");
            printAscendButton.setBackground(Color.LIGHT_GRAY);
            printAscendButton.setFocusPainted(false);
            printAscendButton.setFont(new Font(FONT, Font.PLAIN, 12));

        }

        public JButton getShowButton() {
            return showButton;
        }

        public JButton getAddButton() {
            return addButton;
        }

        public JButton getAddIfMinButton() {
            return addIfMinButton;
        }

        public JButton getClearButton() {
            return clearButton;
        }

        public JButton getRemoveIdButton() {
            return removeIdButton;
        }

        public JButton getRemoveLowerButton() {
            return removeLowerButton;
        }

        public JButton getInfoButton() {
            return infoButton;
        }

        public JButton getUpdateIdButton() {
            return updateIdButton;
        }

        public JButton getFilterNameButton() {
            return filterNameButton;
        }

        public JButton getHeadButton() {
            return headButton;
        }

        public JButton getMaxEmplButton() {
            return maxEmplButton;
        }

        public JButton getPrintAscendButton() {
            return printAscendButton;
        }
    }

    public Buttons getButtons() {
        return buttons;
    }
}