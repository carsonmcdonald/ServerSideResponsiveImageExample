import java.awt.image.BufferedImage;

public class ImageAndTypePair
{
  private String typeName;
  private BufferedImage bufferedImage;

  public ImageAndTypePair(String typeName, BufferedImage bufferedImage)
  {
    this.typeName = typeName;
    this.bufferedImage = bufferedImage;
  }

  public String getTypeName()
  {
    return typeName;
  }

  public BufferedImage getBufferedImage()
  {
    return bufferedImage;
  }

  public int getImageTypeId()
  {
    if("JPEG".equalsIgnoreCase(typeName))
    {
      return BufferedImage.TYPE_INT_RGB;
    }
    else if("PNG".equalsIgnoreCase(typeName) || "GIF".equalsIgnoreCase(typeName))
    {
      return BufferedImage.TYPE_INT_ARGB;
    }
    else
    {
      throw new RuntimeException("Unknown image type");
    }
  }

  public String getContentTypeForType()
  {
    if("JPEG".equalsIgnoreCase(typeName))
    {
      return "image/jpeg";
    }
    else if("PNG".equalsIgnoreCase(typeName))
    {
      return "image/png";
    }
    else if("GIF".equalsIgnoreCase(typeName))
    {
      return "image/gif";
    }
    else
    {
      throw new RuntimeException("Unknown image type");
    }
  }
}
