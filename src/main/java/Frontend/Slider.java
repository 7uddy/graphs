package Frontend;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Slider extends JPanel implements ChangeListener {
    private JSlider wSlider;
    private JLabel valueLabel;
    private String weightOrCapacity;

public static  int sliderValue;

    public Slider(String argWeightOrCapacity) {
        weightOrCapacity=argWeightOrCapacity;
        wSlider = new JSlider(0, 10, 0);
        wSlider.setPreferredSize(new Dimension(200, 45));
        wSlider.setPaintTicks(true);
        wSlider.setMajorTickSpacing(1);
        wSlider.setPaintTrack(true);
        wSlider.setPaintLabels(true);
        wSlider.setOpaque(false);
        wSlider.setFont(new Font("Helvetica", Font.BOLD, 15));

        wSlider.addChangeListener(this);

        // Add the JSlider to this panel
        add(wSlider);
        // Cream un JLabel pentru a afișa valoarea curentă a slider-ului
        valueLabel = new JLabel(weightOrCapacity + wSlider.getValue());
        valueLabel.setFont(new Font("Helvetica", Font.BOLD, 15));
        add(valueLabel);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // Actualizăm textul JLabel cu valoarea curentă a slider-ului
        valueLabel.setText(weightOrCapacity+ wSlider.getValue());

        // Puteți face și alte acțiuni în funcție de valoarea slider-ului, dacă este necesar
        sliderValue = wSlider.getValue();
        // ... alte acțiuni ...
    }
}
