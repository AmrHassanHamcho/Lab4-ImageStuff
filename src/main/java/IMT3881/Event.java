package IMT3881;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Event implements ChangeListener {
    private JSlider slider;
    private int value;
    public Event(){
    }
    public JSlider slideBar(){
        slider = new JSlider(JSlider.HORIZONTAL, 0, 70, 0);
        slider.setMajorTickSpacing(30);
        slider.setMinorTickSpacing(20);
        slider.setVisible(true);
        slider.setPaintLabels(true);
        slider.setBounds(100,350,350,350);

        slider.addChangeListener(this::stateChanged);
        return slider;

    }




    @Override
    public void stateChanged(ChangeEvent e) {
        this.value = slider.getValue();
        setValue(value);
    }
private void setValue(int value){
        this.value = value;
}
    public int getValue(){
        return this.value;
    }
}
