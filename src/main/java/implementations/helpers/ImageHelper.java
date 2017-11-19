package implementations.helpers;

import utils.ResourceManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.IOException;
import java.io.InputStream;

public class ImageHelper {
    public BufferedImage loadBufferedImageFromRes(String path) {
        InputStream imageStream = ResourceManager.getResInputStream(path);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(imageStream);
            imageStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    public boolean imagesAreEqual(BufferedImage imageA, BufferedImage imageB) {
        try {
            DataBuffer dataBufferA = imageA.getData().getDataBuffer();
            int sizeA = dataBufferA.getSize();

            DataBuffer dataBufferB = imageB.getData().getDataBuffer();
            int sizeB = dataBufferB.getSize();

            if (sizeA == sizeB) {
                for (int i = 0; i < sizeA; i++) {
                    if (dataBufferA.getElem(i) != dataBufferB.getElem(i)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public BufferedImage convertToBufferedImage(Image image, int imageType) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), imageType);

        Graphics2D bufferedImageGraphics = bufferedImage.createGraphics();
        bufferedImageGraphics.drawImage(image, 0, 0, null);
        bufferedImageGraphics.dispose();

        return bufferedImage;
    }
}
