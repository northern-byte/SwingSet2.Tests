package implementations.wrappers;

import implementations.helpers.ImageHelper;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class TestImage extends ImageIcon {
    private ImageHelper imageHelper = new ImageHelper();
    private final int imageType = 5;

    private BufferedImage bufferedImage;

    public TestImage(ImageIcon image) {
        bufferedImage = imageHelper.convertToBufferedImage(image.getImage(), imageType);
    }

    public TestImage(String path) {
        bufferedImage = imageHelper.loadBufferedImageFromRes(path);
    }

    public ImageHelper getImageHelper() {
        return imageHelper;
    }

    public int getImageType() {
        return imageType;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TestImage)) {
            return false;
        }

        TestImage otherTestImage = (TestImage) other;
        int bh = bufferedImage.getHeight();
        int bw = bufferedImage.getWidth();
        int oh = otherTestImage.bufferedImage.getHeight();
        int ow = otherTestImage.bufferedImage.getWidth();

        if (bh != oh || bw != ow) {
            return false;
        }
        return imageHelper.imagesAreEqual(bufferedImage, otherTestImage.bufferedImage);
    }
}
