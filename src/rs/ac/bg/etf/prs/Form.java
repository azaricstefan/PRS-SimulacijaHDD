package rs.ac.bg.etf.prs;

import rs.ac.bg.etf.prs.beans.FormBean;

import javax.swing.*;
import java.awt.event.*;

/**
 * Project name: PRS-SimulacijaHDD
 * Created by Stefan on 21-Sep-17.
 */
public class Form{
    private JTextField rpm_textField;
    private JTextField cylinder_textField;
    private JTextField textField3;
    private JLabel brojCilindaraLabel;
    private JLabel RPMLabel;
    private JButton pokreniSimulacijuButton;

    public JPanel rootPanel;
    private JLabel parametar3Label;

    private Disc myDisc;

    public Form(Disc disc) {
        myDisc = disc;

        myInit();
    }

    private void myInit() {
        pokreniSimulacijuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable r = new Runnable () {
                    public void run() {
                        myDisc.seek();
                        myDisc.rotationalDelay();
                        myDisc.transferTime();

                        myDisc.putEvent(); //nakon racunanja stavi
                    }
                };
                new Thread(r).start();
            }
        });
    }

    /**
     * This method is used to change data in the form
     * @param data new form data
     */
    public void setData(FormBean data) {
        cylinder_textField.setText(data.getCylinderText());
        rpm_textField.setText(data.getRpmText());
        textField3.setText(data.getText3());
    }

    /**
     * This method is used to get current data in the form
     * @param data current data
     */
    public void getData(FormBean data) {
        data.setCylinderText(cylinder_textField.getText());
        data.setRpmText(rpm_textField.getText());
        data.setText3(textField3.getText());
    }

    public boolean isModified(FormBean data) {
        if (cylinder_textField.getText() != null ? !cylinder_textField.getText().equals(data.getCylinderText()) : data.getCylinderText() != null)
            return true;
        if (rpm_textField.getText() != null ? !rpm_textField.getText().equals(data.getRpmText()) : data.getRpmText() != null)
            return true;
        if (textField3.getText() != null ? !textField3.getText().equals(data.getText3()) : data.getText3() != null)
            return true;
        return false;
    }
}
