import java.awt.*;

public class GameButton extends GameObject {
    String label;
    public enum BUTTON_STATES {INACTIVE, ACTIVE}

    public GameButton(String _label, int x, int y)
    {
        super(AssetsManager.inactiveButton, x, y);
        label = _label;
    }

    public void SetButtonState(BUTTON_STATES state)
    {
        if (state == BUTTON_STATES.ACTIVE)
            super.SetImage(AssetsManager.activeButton);
        else
            super.SetImage(AssetsManager.inactiveButton);
    }

    @Override
    public void Render(Graphics g)
    {
        if (visible)
        {
            g.drawImage(image, x, y, null);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times Roman", Font.BOLD, 26));
            g.drawString(label, x+50, y+50);
        }
    }
}
