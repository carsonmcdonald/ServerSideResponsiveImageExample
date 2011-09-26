import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import net.sourceforge.wurfl.core.Device;
import net.sourceforge.wurfl.core.WURFLHolder;
import net.sourceforge.wurfl.core.WURFLManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class ResponsiveImage extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
  {
    WURFLHolder wurflHolder = (WURFLHolder) getServletContext().getAttribute("net.sourceforge.wurfl.core.WURFLHolder");

    WURFLManager wurflManager = wurflHolder.getWURFLManager();

    Device device = wurflManager.getDeviceForRequest(req);

    // todo It would be a good idea to cache the resized images

    ImageAndTypePair imageAndTypePair = ingestImage(req.getParameter("src"));

    if (imageAndTypePair == null)
    {
      throw new RuntimeException("Could not determine image type.");
    }
    else
    {
      BufferedImage outputImage = resizeForDevice(imageAndTypePair, device);

      resp.setContentType(imageAndTypePair.getContentTypeForType());
      ImageIO.write(outputImage, imageAndTypePair.getTypeName(), resp.getOutputStream());
      resp.getOutputStream().flush();
      resp.getOutputStream().close();
    }
  }

  private ImageAndTypePair ingestImage(String imageURL)
  {
    try
    {
      ImageInputStream imageInputStream = ImageIO.createImageInputStream(new URL(imageURL).openStream());
      Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);

      String formatName = null;
      if (readers.hasNext())
      {
        ImageReader read = readers.next();
        formatName = read.getFormatName();
      }

      return new ImageAndTypePair(formatName, ImageIO.read(imageInputStream));
    }
    catch (IOException e)
    {
      System.err.println(e.getMessage());
      return null;
    }
  }

  private BufferedImage createResizedCopy(ImageAndTypePair imageAndTypePair, int scaledWidth, int scaledHeight)
  {
    int imageType = imageAndTypePair.getImageTypeId();
    BufferedImage scaledBufferedImage = new BufferedImage(scaledWidth, scaledHeight, imageType);
    Graphics2D graphics2D = scaledBufferedImage.createGraphics();
    if (imageType == BufferedImage.TYPE_INT_ARGB)
    {
      graphics2D.setComposite(AlphaComposite.Src);
    }
    graphics2D.drawImage(imageAndTypePair.getBufferedImage(), 0, 0, scaledWidth, scaledHeight, null);
    graphics2D.dispose();
    return scaledBufferedImage;
  }

  private BufferedImage resizeForDevice(ImageAndTypePair imageAndTypePair, Device device)
  {
    double deviceWidth = Double.parseDouble(device.getCapability("resolution_width"));
    double deviceHeight = Double.parseDouble(device.getCapability("resolution_height"));

    double scaledWidth = (imageAndTypePair.getBufferedImage().getWidth() < deviceWidth ? imageAndTypePair.getBufferedImage().getWidth() : deviceWidth);
    double scaledHeight = (imageAndTypePair.getBufferedImage().getHeight() < deviceHeight) ? imageAndTypePair.getBufferedImage().getHeight() : deviceHeight;

    double deviceRatio = deviceWidth / deviceHeight;
    double imageRatio = (double)imageAndTypePair.getBufferedImage().getWidth() / (double)imageAndTypePair.getBufferedImage().getHeight();

    if (deviceRatio < imageRatio)
    {
      scaledHeight = scaledWidth / imageRatio;
    }
    else
    {
      scaledWidth = scaledHeight * imageRatio;
    }

    return createResizedCopy(imageAndTypePair, (int)scaledWidth, (int)scaledHeight);
  }

  public static void main(String[] args) throws Exception
  {
    String webappDirLocation = "src/main/webapp/";

    String webPort = System.getenv("PORT");
    if (webPort == null || webPort.isEmpty())
    {
      webPort = "8080";
    }

    Server server = new Server(Integer.valueOf(webPort));
    WebAppContext root = new WebAppContext();

    root.setContextPath("/");
    root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
    root.setResourceBase(webappDirLocation);

    root.setParentLoaderPriority(true);

    server.setHandler(root);

    server.start();
    server.join();
  }
}