import java.awt.image.BufferedImage;

public class SharpenFilter implements FryFilter
{
  private FryFilter wrapped;

  public SharpenFilter(FryFilter wrap)
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
    BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int i = 1; i < img.getWidth() - 1; i++)
    {
      for (int j = 1; j < img.getHeight() - 1; j++)
      {
        int oldRGB = img.getRGB(i, j);
        int oldRed = getRed(oldRGB);
        int oldGreen = getGreen(oldRGB);
        int oldBlue = getBlue(oldRGB);
        //System.out.println(oldRed + ", " + oldGreen + ", " + oldBlue);

        int newRed = oldRed * 9;
        int newGreen = oldGreen * 9;
        int newBlue = oldBlue * 9;

        newRed += getRed(img.getRGB(i - 1, j - 1)) * -1;
        newGreen += getGreen(img.getRGB(i - 1, j - 1)) * -1;
        newBlue += getBlue(img.getRGB(i - 1, j - 1)) * -1;

        newRed += getRed(img.getRGB(i - 1, j)) * -1;
        newGreen += getGreen(img.getRGB(i - 1, j)) * -1;
        newBlue += getBlue(img.getRGB(i - 1, j)) * -1;

        newRed += getRed(img.getRGB(i - 1, j + 1)) * -1;
        newGreen += getGreen(img.getRGB(i - 1, j + 1)) * -1;
        newBlue += getBlue(img.getRGB(i - 1, j + 1)) * -1;

        newRed += getRed(img.getRGB(i, j - 1)) * -1;
        newGreen += getGreen(img.getRGB(i, j - 1)) * -1;
        newBlue += getBlue(img.getRGB(i, j - 1)) * -1;

        newRed += getRed(img.getRGB(i + 1, j - 1)) * -1;
        newGreen += getGreen(img.getRGB(i + 1, j - 1)) * -1;
        newBlue += getBlue(img.getRGB(i + 1, j - 1)) * -1;

        newRed += getRed(img.getRGB(i + 1, j + 1)) * -1;
        newGreen += getGreen(img.getRGB(i + 1, j + 1)) * -1;
        newBlue += getBlue(img.getRGB(i + 1, j + 1)) * -1;

        newRed += getRed(img.getRGB(i, j + 1)) * -1;
        newGreen += getGreen(img.getRGB(i, j + 1)) * -1;
        newBlue += getBlue(img.getRGB(i, j + 1)) * -1;

        newRed += getRed(img.getRGB(i + 1, j)) * -1;
        newGreen += getGreen(img.getRGB(i + 1, j)) * -1;
        newBlue += getBlue(img.getRGB(i + 1, j)) * -1;

        newRed = setCol(newRed);
        newGreen = setCol(newGreen);
        newBlue = setCol(newBlue);

        int newRGB = (newRed << 16) | (newGreen << 8) | (newBlue);
        result.setRGB(i, j, newRGB);
      }
    }
    return result;
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
