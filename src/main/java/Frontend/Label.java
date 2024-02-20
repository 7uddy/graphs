package Frontend;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel{
    Label(String string){
        this.setText(string);
        //this.setBackground(Color.WHITE);
        //this.setOpaque(true);
        this.setFont(new Font("HelveticaNeue", Font.BOLD, 18));
    }
}
