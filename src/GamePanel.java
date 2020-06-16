import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    private Thread gameThread;
    private GameButton ready;
    private Game game;

    public GamePanel()
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
        //addKeyListener(this);

        ready = new GameButton("Ready", 370, 200);
        ready.SetButtonState(GameButton.BUTTON_STATES.ACTIVE);
    }

    private void GameLoop()
    {
        game = new Game();
        long lastTime = System.currentTimeMillis();
        try {
            while (Game.GetGameState() != Game.GAME_STATES.INACTIVE) {
                if (Game.GetGameState() == Game.GAME_STATES.RUNNING) {
                    game.Update(System.currentTimeMillis() - lastTime);

                    EventQueue.invokeLater(() -> {
                        repaint();
                    });
                }
                lastTime = System.currentTimeMillis();
                Thread.sleep(11);
            }
        }
        catch (InterruptedException e) {
                e.printStackTrace(); }

        game = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(AssetsManager.background, 0, 0, null);

        if (game==null)
            ready.Render(g);
        else
            game.Render(g);
    }

    public void HandleKey(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case 27:
                Game.SetGameState(Game.GAME_STATES.PAUSED);
                GUI.ShowPanel("PausePanel");
                AssetsManager.song.Pause();
                break;

            case 10: if (game == null)
                {
                    gameThread = new Thread( ()->{GameLoop();});
                    gameThread.start();
                    AssetsManager.song.Play(Settings.GetInstance().GetVolume());
                }
                break;

            default:
                if (game!=null)
                game.HandleKeyEvent(e);
                break;
        }
    }
}
