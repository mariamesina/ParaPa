import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

    private ArrayList<BufferedImage> animations;
    private int animation_index;

    private int spriteWidth;
    private int spriteHeight;

    public int frame_numbers;
    private int frame_index;

    public long time_per_frame;

    public Animation(int _spriteWidth, int _spriteHeight)
    {
        spriteHeight = _spriteHeight;
        spriteWidth = _spriteWidth;

        animations = new ArrayList<>();
    }
    public void AddAnimation(BufferedImage image)
    {
        animations.add(image);
    }

    public void SetAnimation(int index)
    {
        animation_index = index;
        frame_numbers = animations.get(animation_index).getHeight() / spriteHeight;
        frame_index = 0;
    }

    public BufferedImage GetFrame(long timer)
    {
        if (timer - frame_index*time_per_frame >= time_per_frame)
            frame_index++;
        return animations.get(animation_index).getSubimage(0, spriteHeight*frame_index, spriteWidth, spriteHeight);
    }
}
