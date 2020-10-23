package IMT3881;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


public class TheSlider {
    private JSlider slider;

    public TheSlider(){
    }
    public JSlider slideBar(){
        slider = new JSlider(JSlider.HORIZONTAL, 0, 70, 0);
        slider.setMajorTickSpacing(30);
        slider.setMinorTickSpacing(20);
        slider.setVisible(true);
        slider.setPaintLabels(true);
        slider.setBounds(100,500,350,50);

        //slider.addChangeListener(this);
        return slider;

    }



}
