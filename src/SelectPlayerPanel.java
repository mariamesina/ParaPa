import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SelectPlayerPanel extends JPanel {

    private JTextField textField;
    private String name;
    private int namesOffset = 0;

    private ArrayList<String> playersNames;
    private GameObject indicator;
    private GameObject arrowUp;
    private GameObject arrowDown;
    private GameObject textSeparator;
    private int selectedOption;

    public SelectPlayerPanel()
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
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 25));
        textField.requestFocus();
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = textField.getText();
                textField.setText("");
                DBManager.GetInstance().SavePlayer(name);
                UpdatePlayers();
                requestFocusInWindow();
            }
        });
        add(textField);

        textSeparator = new GameObject(AssetsManager.textSeparator, Launcher.WIDTH/2-AssetsManager.textSeparator.getWidth()/2, 50);
        arrowUp = new GameObject(AssetsManager.arrowUp, Launcher.WIDTH/2 - AssetsManager.arrowUp.getWidth()/2, 150);
        indicator = new GameObject(AssetsManager.indicator, Launcher.WIDTH/2 - AssetsManager.indicator.getWidth()/2, 200+selectedOption*50);
        arrowDown = new GameObject(AssetsManager.arrowDown, Launcher.WIDTH/2 - AssetsManager.arrowDown.getWidth()/2, 450);
        UpdatePlayers();
    }

    public void UpdatePlayers()
    {
        playersNames = DBManager.GetInstance().GetPlayers();
        if (playersNames.size()>0)
            indicator.visible = true;
        if (playersNames.size()>5)
            arrowDown.visible = true;
        EventQueue.invokeLater(()->{repaint();});
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(AssetsManager.background, 0, 0,null);
        g.drawImage(AssetsManager.MikuLukaBackground, 50, 0, null);

        indicator.Render(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Times Roman", Font.BOLD, 26));
        for(int i=0; (i<5 && i<playersNames.size()); i++)
            g.drawString(playersNames.get(i+namesOffset), Launcher.WIDTH/2-100, 200+50*i+30);

        textSeparator.Render(g);
        arrowUp.Render(g);
        arrowDown.Render(g);

    }

    public void HandleKey(KeyEvent e) {
        //System.out.println("playersNames size(): "+playersNames.size());
        switch (e.getKeyCode()) {
            case 38: if (namesOffset == 0)
            {
                if (selectedOption > 0) {
                    selectedOption--;
                    indicator.SetPosition(Launcher.WIDTH / 2 - AssetsManager.indicator.getWidth() / 2, 200 + selectedOption * 50);
                    repaint();
                }
            }
            else
            {
                if (selectedOption > 0) {
                    selectedOption--;
                    indicator.SetPosition(Launcher.WIDTH / 2 - AssetsManager.indicator.getWidth() / 2, 200 + selectedOption * 50);
                    repaint();
                }
                else
                {
                    namesOffset--;
                    repaint();
                }
            }
            break;

            case 40: if(namesOffset == 0)
                {
                    if ((selectedOption != 4) && (selectedOption<playersNames.size()-1)) {
                        ++selectedOption;
                        indicator.SetPosition(Launcher.WIDTH / 2 - AssetsManager.indicator.getWidth() / 2, 200 + selectedOption * 50);
                        repaint();
                    }
                    else
                    {
                        if ((selectedOption<playersNames.size()-1)) {
                            namesOffset++;
                            repaint();
                        }
                    }
                }
            else
            {
                if (selectedOption != 4) {
                    ++selectedOption;
                    indicator.SetPosition(Launcher.WIDTH / 2 - AssetsManager.indicator.getWidth() / 2, 200 + selectedOption * 50);
                    repaint();
                }
                else
                {
                    if (namesOffset+5<playersNames.size()) {
                        namesOffset++;
                        repaint();
                    }
                }
            }
            break;

            case 27: GUI.ShowPanel("MenuPanel");
            break;

            case 10:
                if (playersNames.size()!=0)
                {
                    DBManager.GetInstance().LoadPlayer(playersNames.get(selectedOption+namesOffset));
                    GUI.ShowPanel("GamePanel");
                }
        }
    }
}
