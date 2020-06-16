import java.awt.*;

public class Key extends GameObject{
    public int keyCode;
    public enum KEY_STATES {MISSED, HIT}

    public Key(int _keyCode)
    {
        keyCode = _keyCode;
        switch (keyCode)
        {
            case 37: SetImage(AssetsManager.leftBlue);
                break;
            case 38: SetImage(AssetsManager.upBlue);
                break;
            case 39: SetImage(AssetsManager.rightBlue);
                break;
            case 40: SetImage(AssetsManager.downBlue);
                break;
        }
    }

    public void SetKeyState(KEY_STATES state)
    {
        switch (state)
        {
            case HIT:
                switch (keyCode)
                {
                    case 37: SetImage(AssetsManager.leftGreen);
                        break;
                    case 38: SetImage(AssetsManager.upGreen);
                        break;
                    case 39: SetImage(AssetsManager.rightGreen);
                        break;
                    case 40: SetImage(AssetsManager.downGreen);
                        break;
                }
                break;
            case MISSED:
                switch (keyCode)
                {
                    case 37: SetImage(AssetsManager.leftRed);
                        break;
                    case 38: SetImage(AssetsManager.upRed);
                        break;
                    case 39: SetImage(AssetsManager.rightRed);
                        break;
                    case 40: SetImage(AssetsManager.downRed);
                        break;
                }
                break;
        }
    }

    @Override
    public void Render(Graphics g)
    {
        //System.out.println(visible);
        if (visible)
            g.drawImage(image, x, y, null);
    }
}
