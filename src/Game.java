import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {
    public enum GAME_STATES {INACTIVE, RUNNING, PAUSED}
    public enum PLAYER_STATES { PERFECT, COOL, GOOD, MISSED, WAITING}

    private static volatile GAME_STATES gameState = GAME_STATES.INACTIVE;
    private PLAYER_STATES playerState = PLAYER_STATES.WAITING;

    private long time_per_combination;
    private long timer;
    private int speed;

    private ArrayList<Key> keys;
    private int keysIndex;
    private int nrOfKeys;

    private Animation danceAnimations;
    private Animation playerStateAnimations;

    private GameObject bar;
    private GameObject circle;

    public Game()
    {
        gameState = GAME_STATES.RUNNING;
        bar = new GameObject(AssetsManager.LoadImage("bar.png"), 200, 440);
        circle = new GameObject(AssetsManager.LoadImage("circle.png"), 110, 430);
        keys = new ArrayList<>();

        nrOfKeys = Settings.GetInstance().GetKeysNumber();
        speed = Settings.GetInstance().GetSpeed();
        if (speed==2)
            time_per_combination = 2500;
        else
            time_per_combination = 1700;

        Player.GetInstance().currentMaxCombo = 0;
        Player.GetInstance().currentCombo = 0;
        Player.GetInstance().score = 0;
        Player.GetInstance().hit = 0;
        Player.GetInstance().miss = 0;

        danceAnimations = new Animation(640, 360);
        playerStateAnimations = new Animation(200, 106);
        //playerStateAnimations.time_per_frame = 150;
        LoadAnimations();

        GenerateAnimation();
        GenerateCombination();

        timer = 0;
    }

    private void LoadAnimations()
    {
        danceAnimations.AddAnimation(AssetsManager.LoadImage("1.png"));
        danceAnimations.AddAnimation(AssetsManager.LoadImage("2.png"));
        danceAnimations.AddAnimation(AssetsManager.LoadImage("3.png"));
        danceAnimations.AddAnimation(AssetsManager.LoadImage("4.png"));
        danceAnimations.AddAnimation(AssetsManager.LoadImage("5.png"));
        danceAnimations.AddAnimation(AssetsManager.LoadImage("6.png"));
        danceAnimations.AddAnimation(AssetsManager.LoadImage("7.png"));

        playerStateAnimations.AddAnimation(AssetsManager.LoadImage("Perfect.png"));
        playerStateAnimations.AddAnimation(AssetsManager.LoadImage("Cool.png"));
        playerStateAnimations.AddAnimation(AssetsManager.LoadImage("Good.png"));
        playerStateAnimations.AddAnimation(AssetsManager.LoadImage("Miss.png"));
        playerStateAnimations.time_per_frame = 120;
    }

    public void Update(long delta)
    {
        timer += delta;
        //System.out.println("timer"  + timer);
        if (timer > time_per_combination) //daca a expirat timpul pt combinatie
        {
            if (playerState == PLAYER_STATES.WAITING) //daca mai sunt taste care trebuiau tastate
            {
                playerState = PLAYER_STATES.MISSED;
                //playerStateAnimations.SetAnimation(3);
            }
            //System.out.println(playerState);

            GenerateAnimation();
            GenerateCombination();

            timer = 0;
        }
        else
            circle.x += speed; //daca nu a expirat - misc cerculetul de-a lungul barei timpului
    }

    public void GenerateCombination()
    {
        keys.clear();
        //System.out.println(nrOfKeys);
        for(int i=0; i<nrOfKeys; i++) //generez taste noi si setez indexul de inceput = 0;
        {
            keys.add(new Key((int) (Math.random() * 4) + 37));
            keys.get(i).SetPosition(i*keys.get(i).imageWidth+200, 380);
        }
        keysIndex = 0;

        circle.x = bar.x; //mut cerculetul al inceputul barei de timp
        playerState = PLAYER_STATES.WAITING;
    }

    public void GenerateAnimation()
    {
        //System.out.println(playerState);
        if (playerState == PLAYER_STATES.MISSED)
        {
            Player.GetInstance().miss++;
            if (Player.GetInstance().currentCombo>Player.GetInstance().currentMaxCombo)
                Player.GetInstance().currentMaxCombo = Player.GetInstance().currentCombo;
            Player.GetInstance().currentCombo = 0;
            danceAnimations.SetAnimation(0);
            playerStateAnimations.SetAnimation(3);
        }
        else {
            switch (playerState){
                case WAITING: danceAnimations.SetAnimation(0);
                    break;
                case COOL: Player.GetInstance().score += nrOfKeys * speed;
                    if (Player.GetInstance().currentCombo>Player.GetInstance().currentMaxCombo)
                        Player.GetInstance().currentMaxCombo = Player.GetInstance().currentCombo;
                    Player.GetInstance().currentCombo = 0;
                    playerStateAnimations.SetAnimation(1);
                    danceAnimations.SetAnimation((int) (Math.random() * 5) + 1);
                    break;
                case GOOD: Player.GetInstance().score += (nrOfKeys * speed)/2;
                    if (Player.GetInstance().currentCombo>Player.GetInstance().currentMaxCombo)
                        Player.GetInstance().currentMaxCombo = Player.GetInstance().currentCombo;
                    Player.GetInstance().currentCombo = 0;
                    playerStateAnimations.SetAnimation(2);
                    danceAnimations.SetAnimation((int) (Math.random() * 5) + 1);
                    break;
                case PERFECT: Player.GetInstance().score += 2*nrOfKeys *speed;
                    Player.GetInstance().currentCombo++;
                    playerStateAnimations.SetAnimation(0);
                    danceAnimations.SetAnimation((int) (Math.random() * 5) + 1);
                    break;
                }
                Player.GetInstance().hit++;
                //danceAnimations.SetAnimation((int) (Math.random() * 5) + 1);
        }
        //System.out.println(Player.GetInstance().currentCombo);
        danceAnimations.time_per_frame = time_per_combination/danceAnimations.frame_numbers+10;
    }

    public void Render(Graphics g)
    {
        bar.Render(g);
        circle.Render(g);

        g.drawImage(danceAnimations.GetFrame(timer), 125, 70, 640, 360, null);
        if (timer<playerStateAnimations.time_per_frame*playerStateAnimations.frame_numbers)
            g.drawImage(playerStateAnimations.GetFrame(timer), 350, 0, null);

        for (Key key : keys)
            key.Render(g);
    }

    public void HandleKeyEvent(KeyEvent e)
    {
        if (playerState == PLAYER_STATES.WAITING)
        {
            if (e.getKeyCode() == 32)
            {
                if (keysIndex != keys.size())
                {
                    playerState = PLAYER_STATES.MISSED;
                    //playerStateAnimations.SetAnimation(3);
                }
                else
                {
                    int unit = bar.imageWidth/26;
                    int greenZone = unit*20+unit/2+bar.x;
                    int circleCenter = circle.x + circle.imageWidth/2;

                    if ((circleCenter>greenZone-unit/2)&& (circleCenter<greenZone+unit/2))
                    {
                        playerState = PLAYER_STATES.PERFECT;
                        //playerStateAnimations.SetAnimation(0);
                    }
                    else
                        if ((circleCenter>greenZone - unit) && (circleCenter<greenZone+unit))
                        {
                            playerState = PLAYER_STATES.COOL;
                            //playerStateAnimations.SetAnimation(1);
                        }
                        else
                            if ((circleCenter>greenZone-2*unit) && (circleCenter<greenZone+2*unit))
                            {
                                playerState = PLAYER_STATES.GOOD;
                                //playerStateAnimations.SetAnimation(2);
                            }
                            else
                            {
                                playerState = PLAYER_STATES.MISSED;
                            }
                }
            }
            else {
                if (keysIndex != keys.size()) {
                    if (e.getKeyCode() == keys.get(keysIndex).keyCode) {
                        keys.get(keysIndex).SetKeyState(Key.KEY_STATES.HIT);
                        keysIndex++;
                    }
                    else
                    {
                        keys.get(keysIndex).SetKeyState(Key.KEY_STATES.MISSED);
                        playerState = PLAYER_STATES.MISSED;
                    }
                }
            }
        }
    }

    public synchronized static void SetGameState(Game.GAME_STATES _state)
    {
        gameState = _state;
    }

    public synchronized static Game.GAME_STATES GetGameState()
    {
        return gameState;
    }
}
