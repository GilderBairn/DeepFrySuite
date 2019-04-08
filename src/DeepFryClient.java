import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.imageio.ImageWriteParam;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.IIOImage;

public class DeepFryClient
{
  public static void main(String[] args)
  {
    if (args.length > 0)
    {
      try
      {
        BufferedImage img = ImageIO.read(new File(args[0]));
        FryController control = new FryController(img);
        control.addFilter("sharpen");
        control.addFilter("contrast");
        img = control.applyFilters();
        String outpath = args[0].substring(0, args[0].length() - 4) + "_fryed.jpg";
        File outfile = new File(outpath);
        JPEGImageWriteParam jpeg = new JPEGImageWriteParam(null);
        jpeg.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpeg.setCompressionQuality(0.1f);
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        FileImageOutputStream ofstream = new FileImageOutputStream(outfile);
        writer.setOutput(ofstream);
        writer.write(null, new IIOImage(img, null, null), jpeg);
        ofstream.close();
        img = ImageIO.read(outfile);
        for (int i = 0; i < 2; i++)
        {
          img = ImageIO.read(outfile);
          control.reloadBaseImage(img);
          img = control.applyFilters();
          ofstream = new FileImageOutputStream(outfile);
          writer.setOutput(ofstream);
          writer.write(null, new IIOImage(img, null, null), jpeg);
          ofstream.close();
        }
      }
      catch(IOException e)
      {
        System.out.println("Unexpected IO exception occured.");
        e.printStackTrace();
      }
    }
  }
}
