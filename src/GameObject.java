import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject {

    public int x;
    public int y;
    public int imageWidth;
    public int imageHeight;
    public boolean visible=true;

    public BufferedImage image;

    public GameObject() {}

    public GameObject(BufferedImage _sprite, int _x, int _y)
    {
        x = _x;
        y = _y;
        image = _sprite;
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
    }

    public void SetImage(BufferedImage _image)
    {
        image = _image;
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
    }

    public void SetPosition(int _x, int _y)
    {
        x = _x;
        y = _y;
    }

    public void Render(Graphics g)
    {
        if (visible)
            g.drawImage(image, x, y, null);
    }
}
