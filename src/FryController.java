import java.awt.image.BufferedImage;

public class FryController
{
   private FryFilter base;
   private FryFilter filters;

   public FryController(BufferedImage baseImg)
   {
     this.base = new BaseFilter(baseImg);
     this.filters = this.base;
   }

   public void addFilter(String classname)
   {
     if (classname.equals("sharpen"))
     {
       this.filters = new SharpenFilter(this.filters);
     }
     else if (classname.equals("contrast"))
     {
       this.filters = new ContrastFilter(this.filters);
     }
   }

   public void undo()
   {
     if (this.filters != this.base)
     {
       this.filters = this.filters.getWrapped();
     }
   }

   public BufferedImage applyFilters()
   {
     return this.filters.apply();
   }

   public void reloadBaseImage(BufferedImage img)
   {
     this.filters.reloadImage(img);
   }

}
