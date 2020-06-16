import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PausePanel extends JPanel {

    private int selectedOption = 0;
    private ArrayList <GameButton> buttons;

    public PausePanel()
    {
        setPreferredSize(new Dimension(Launcher.WIDTH, Launcher.HEIGHT));
        setFocusable(true);
        setVisible(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                HandleKey(e);
            }
        });

        buttons = new ArrayList<>();
        buttons.add(new GameButton("Resume", 600, 50));
        buttons.add(new GameButton("Main Menu", 600, 200));
        buttons.add(new GameButton("Quit", 600, 350));
        buttons.get(0).SetButtonState(GameButton.BUTTON_STATES.ACTIVE);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(AssetsManager.background, 0, 0,null);
        g.drawImage(AssetsManager.MikuLukaBackground, 50, 0, null);

        for(GameButton button : buttons)
            button.Render(g);
    }

    public void HandleKey(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case 38: if (selectedOption>0) {
                buttons.get(selectedOption).SetButtonState(GameButton.BUTTON_STATES.INACTIVE);
                --selectedOption;
                buttons.get(selectedOption).SetButtonState(GameButton.BUTTON_STATES.ACTIVE);
                repaint();
            }
                break;
            case 40: if (selectedOption<2) {
                buttons.get(selectedOption).SetButtonState(GameButton.BUTTON_STATES.INACTIVE);
                ++selectedOption;
                buttons.get(selectedOption).SetButtonState(GameButton.BUTTON_STATES.ACTIVE);
                repaint();
            }
                break;
            case 10: switch (selectedOption)
            {
                case 0: Game.SetGameState(Game.GAME_STATES.RUNNING);
                    GUI.ShowPanel("GamePanel");
                    AssetsManager.song.Resume();
                    break;

                case 1: Game.SetGameState(Game.GAME_STATES.INACTIVE);
                    GUI.ShowPanel("MenuPanel");
                    break;

                case 2: GUI.ExitGUI();
                    break;
            }
        }
    }
}
