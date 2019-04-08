import java.awt.image.BufferedImage;

public class ContrastFilter implements FryFilter
{
  private FryFilter wrapped;
  private static final double CONT_FACT = 0.25;

  public ContrastFilter(FryFilter wrap)
  {
    this.wrapped = wrap;
  }

  public void reloadImage(BufferedImage img)
  {
    wrapped.reloadImage(img);
  }

  public BufferedImage apply()
  {
    BufferedImage img = wrapped.apply();
    for (int i = 1; i < img.getWidth() - 1; i++)
    {
      for (int j = 1; j < img.getHeight() - 1; j++)
      {
        int oldRGB = img.getRGB(i, j);
        int oldRed = getRed(oldRGB);
        int oldGreen = getGreen(oldRGB);
        int oldBlue = getBlue(oldRGB);

        int newRed = setCol((int)(CONT_FACT * (oldRed - 128)) + oldRed);
        int newGreen = setCol((int)(CONT_FACT * (oldGreen - 128)) + oldGreen);
        int newBlue = setCol((int)(CONT_FACT * (oldBlue - 128)) + oldBlue);

        int newRGB = (newRed << 16) | (newGreen << 8) | (newBlue);
        img.setRGB(i, j, newRGB);
      }
    }
    return img;
  }

  public FryFilter getWrapped()
  {
    return wrapped;
  }

  private int getRed(int rgb)
  {
    return (rgb & 0xff0000) >> 16;
  }

  private int getGreen(int rgb)
  {
    return (rgb & 0x00ff00) >> 8;
  }

  private int getBlue(int rgb)
  {
    return (rgb & 0x0000ff);
  }

  private int setCol(int color)
  {
    if (color > 255)
    {
      color = 255;
    }
    else if (color < 0)
    {
      color = 0;
    }
    return color;
  }
}
