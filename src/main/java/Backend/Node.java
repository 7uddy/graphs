package Backend;

import java.awt.*;

public class Node {
   public int m_xCord;
   public int m_yCord;
   public int m_index;
   public Color colour=Color.lightGray;;


    public Node(int xCord, int yCord, int index) {
        m_xCord = xCord;
        m_yCord = yCord;
        m_index = index;
    }
    public void DrawNode(Graphics g, int node_diam) {
        g.setColor(colour);
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));

        g.fillOval(this.m_xCord-node_diam/2, this.m_yCord-node_diam/2, node_diam, node_diam);
        g.setColor(Color.BLACK);
        g.drawOval(this.m_xCord-node_diam/2, this.m_yCord-node_diam/2, node_diam, node_diam);

        int textWidth, textHeight;
        String indexStr = Integer.toString(this.m_index);

        FontMetrics metrics = g.getFontMetrics();
        textWidth = metrics.stringWidth(indexStr);
        textHeight = metrics.getHeight();
        int textX = this.m_xCord-node_diam/2 + (node_diam - textWidth) / 2;
        int textY = this.m_yCord-node_diam/2 + (node_diam - textHeight) / 2 + metrics.getAscent();

        g.setColor(Color.BLACK);
        g.drawString(indexStr, textX, textY);
    }

}
