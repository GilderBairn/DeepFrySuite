import java.awt.image.BufferedImage;

interface FryFilter
{
  public BufferedImage apply();
  public FryFilter getWrapped();
  public void reloadImage(BufferedImage img);
}
