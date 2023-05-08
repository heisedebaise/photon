package org.lpw.photon.util;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.stereotype.Component;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.inject.Inject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

@Component("photon.util.image")
public class ImageImpl implements Image {
    @Inject
    private Validator validator;
    @Inject
    private Numeric numeric;
    @Inject
    private Logger logger;

    @Override
    public BufferedImage read(byte[] bytes) throws IOException {
        return read(new ByteArrayInputStream(bytes));
    }

    @Override
    public BufferedImage read(InputStream inputStream) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        inputStream.close();

        return image;
    }

    @Override
    public BufferedImage thumbnail(BufferedImage image, int maxWidth, int maxHeight) {
        if (image == null || maxWidth <= 0 || maxHeight <= 0)
            return image;

        int width = image.getWidth();
        int height = image.getHeight();
        if (width > maxWidth) {
            height = height * maxWidth / width;
            width = maxWidth;
        }
        if (height > maxHeight) {
            width = width * maxHeight / height;
            height = maxHeight;
        }
        if (width <= 0 || height <= 0)
            return image;

        BufferedImage thumbnail = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = thumbnail.getGraphics();
        graphics.drawImage(image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH),
                0, 0, width, height, null);
        graphics.dispose();

        return thumbnail;
    }

    @Override
    public BufferedImage subimage(BufferedImage image, int x, int y, int width, int height) {
        if (x < 0 || y < 0 || width <= 0 || height <= 0)
            return image;

        int w = image.getWidth();
        int h = image.getHeight();
        if (x >= w || y >= h || (x == 0 && y == 0 && width >= w && height >= h))
            return image;

        return image.getSubimage(x, y, Math.min(width, w - x), Math.min(height, h - y));
    }

    @Override
    public BufferedImage scale(BufferedImage image, double scale) {
        return image == null || scale <= 0 || scale == 1.0D ? image :
                scale(image, numeric.toInt(image.getWidth() * scale), numeric.toInt(image.getHeight() * scale));
    }

    @Override
    public BufferedImage scale(BufferedImage image, int width, int height) {
        if (image == null || width <= 0 || height <= 0)
            return image;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH),
                0, 0, width, height, null);
        graphics.dispose();

        return bufferedImage;
    }

    @Override
    public void circle(BufferedImage image, OutputStream outputStream) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();
        int size = Math.min(width, height);
        BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.setClip(new Ellipse2D.Double(0, 0, size, size));
        graphics2D.drawImage(image, (width - size) / 2, (height - size) / 2, size, size, null);
        write(bi, Format.Png, outputStream);
    }

    @Override
    public void circle(BufferedImage image, int w, Color color, OutputStream outputStream) throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();
        int size = Math.min(width, height);
        int border = size + 2 * w;
        BufferedImage bi = new BufferedImage(border, border, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.setClip(new Ellipse2D.Double(0, 0, border, border));
        graphics2D.setColor(color);
        graphics2D.fillRect(0, 0, border, border);
        graphics2D.setClip(new Ellipse2D.Double(w, w, size, size));
        graphics2D.drawImage(image, w, w, size, size, null);
        write(bi, Format.Png, outputStream);
    }

    @Override
    public byte[] write(BufferedImage image, Format format) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        write(image, format, outputStream);

        return outputStream.toByteArray();
    }

    @Override
    public void write(BufferedImage image, Format format, OutputStream outputStream) throws IOException {
        ImageIO.write(image, toString(format), outputStream);
        outputStream.close();
    }

    private String toString(Format format) {
        return switch (format) {
            case Jpeg -> "JPEG";
            case Gif -> "GIF";
            default -> "PNG";
        };
    }

    @Override
    public void jpeg(BufferedImage image, int quality, OutputStream outputStream) throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext())
            throw new IllegalStateException("No writers found");

        BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();

        ImageWriter writer = writers.next();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
        writer.setOutput(imageOutputStream);
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality / 100.0f);
        writer.write(null, new IIOImage(bi, null, null), param);
        imageOutputStream.close();
        writer.dispose();
    }

    @Override
    public boolean svg2png(String svg, int width, int height, OutputStream outputStream) {
        try (Reader reader = new StringReader(svg)) {
            PNGTranscoder pngTranscoder = new PNGTranscoder();
            pngTranscoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, 1.0F * width);
            pngTranscoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, 1.0F * height);
            pngTranscoder.transcode(new TranscoderInput(reader), new TranscoderOutput(outputStream));
            outputStream.flush();

            return true;
        } catch (Throwable throwable) {
            logger.warn(throwable, "转换SVG文档[{}:{}:{}]为PNG图片时发生异常！", svg, width, height);

            return false;
        }
    }

    @Override
    public Format formatFromContentType(String contentType) {
        return switch (contentType) {
            case "image/jpeg", "image/jpg" -> Format.Jpeg;
            case "image/gif" -> Format.Gif;
            default -> Format.Png;
        };
    }

    @Override
    public boolean is(String contentType, String name) {
        int indexOf;
        if (validator.isEmpty(contentType) || validator.isEmpty(name) || !contentType.startsWith("image/")
                || (indexOf = name.lastIndexOf('.')) == -1)
            return false;

        String suffix = name.substring(indexOf).toLowerCase();
        return switch (contentType) {
            case "image/jpeg", "image/jpg" -> suffix.equals(".jpg") || suffix.equals(".jpeg");
            case "image/png" -> suffix.equals(".png");
            case "image/gif" -> suffix.equals(".gif");
            case "image/svg+xml" -> suffix.equals(".svg") || suffix.equals(".svg+xml");
            default -> false;
        };
    }

    @Override
    public int[] size(File file) {
        try {
            BufferedImage image = ImageIO.read(file);

            return new int[]{image.getWidth(), image.getHeight()};
        } catch (Exception e) {
            logger.warn(e, "读取图片文件[{}]宽高时发生异常！", file.getAbsolutePath());

            return null;
        }
    }

    @Override
    public int[] size(InputStream inputStream) {
        try {
            BufferedImage image = ImageIO.read(inputStream);

            return new int[]{image.getWidth(), image.getHeight()};
        } catch (Exception e) {
            logger.warn(e, "读取图片流宽高时发生异常！");

            return null;
        }
    }
}
