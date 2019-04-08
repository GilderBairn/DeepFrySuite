import java.awt.image.BufferedImage;

public class BaseFilter implements FryFilter
{
  private FryFilter wrapped;
  private BufferedImage srcImg;

  public BaseFilter(BufferedImage source)
  {
    this.srcImg = source;
    this.wrapped = null;
  }

  public BufferedImage apply()
  {
    return this.srcImg;
  }

  public FryFilter getWrapped()
  {
    return null;
  }

  public void reloadImage(BufferedImage img)
  {
    this.srcImg = img;  
  }
}
