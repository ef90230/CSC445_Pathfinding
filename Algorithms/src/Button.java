import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

//This is a custom Button class that handles hover behaivor as well as the font of the button
public class Button extends JButton 
{
    private String normalIconName;
    private String hoverIconName;
    private String clickIconName;

    public Button(String normalIconName, String hoverIconName, String clickIconName) /*
        This method creates a JButton with the ImageIcons specified by the String filenames passed.
        For buttons that have disabling behavior, set those outside this class. */
    {
        super(new ImageIcon(normalIconName));
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);

        this.addMouseListener(new MouseListener () 
        {
            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(new ImageIcon(clickIconName));
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                setIcon(new ImageIcon(hoverIconName));
            }
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                setIcon(new ImageIcon(hoverIconName));
            }
            @Override
            public void mouseExited(MouseEvent e) 
            {
                setIcon(new ImageIcon(normalIconName));
            }
        });
    }

    public String getNormalIconName() 
    {
        return normalIconName;
    }

    public String getHoverIconName() 
    {
        return hoverIconName;
    }

    public String getClickIconName()
    {
        return clickIconName;
    }
}