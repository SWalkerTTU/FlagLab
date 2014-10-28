package graphicslab06;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;

public class GL6Util {

    private static int height;
    private static int width;
    static final Area star = new Area();

    static {
        buildStar();
    }

    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int aHeight) {
        height = aHeight;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int aWidth) {
        width = aWidth;
    }

    public static void showName(Graphics g, BufferedImage nameImage) {
        g.drawImage(nameImage, 25, 50, null);
        delay(2000);
    }

    static void speckleDraw(Graphics g, BufferedImage image, int speed) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        int boxSize;
        switch (speed) {
            case 2:
                boxSize = 10;
                break;
            case 3:
                boxSize = 5;
                break;
            case 4:
                boxSize = 1;
                break;
            default:
                boxSize = 25;
        }
        int numCols = (int) Math.ceil(imgWidth / (double) boxSize);
        int numRows = (int) Math.ceil(imgHeight / (double) boxSize);
        boolean[][] map = new boolean[numCols][numRows];
        int freeCells = map.length * map[0].length;
        Random rnd = new Random();

        BufferedImage draw = new BufferedImage(numCols * boxSize,
                numRows * boxSize, image.getType());
        draw.createGraphics().drawImage(image, null, 0, 0);

        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(Color.BLACK);
        g2.clearRect(0, 0, imgWidth, imgHeight);

        while (freeCells > 0) {
            int col, row;
            int x, y;
            do {
                col = rnd.nextInt(map.length);
                row = rnd.nextInt(map[0].length);
            } while (map[col][row]);
            map[col][row] = true;
            freeCells--;
            x = col * boxSize;
            y = row * boxSize;
            BufferedImage smallBox = draw.getSubimage(x, y, boxSize, boxSize);
            g2.drawImage(smallBox, null, x, y);
        }

    }

    static void titlePage(Graphics g, String name, int period) {
        final Color gold = new Color(0xffd700);
        final Font title = new Font("Algerian", Font.BOLD, 48);
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(gold);
        g2.clearRect(0, 0, width, height);
        Rectangle2D.Double rect
                = new Rectangle2D.Double(0.1 * width, height / 6.0,
                        0.8 * width, 2 * height / 3.0);

        g2.setColor(Color.white);
        g2.fill(rect);
        g2.setColor(Color.red);
        g2.setFont(title);
        g2.drawString("Flags of the World", 225, 240);
        g2.setColor(Color.blue);
        g2.drawString("by: " + name, 225, 340);
        g2.setColor(Color.green);
        g2.drawString("Period: " + period, 225, 440);
        delay(3000);
    }

    private static void buildStar() {
        final double ratio = (3 - Math.sqrt(5)) / 2.0;
        final double angle = 2 * Math.PI / 5;
        Path2D.Double tri = new Path2D.Double();
        tri.moveTo(0, -1);
        tri.lineTo(Math.cos(Math.PI / 10) * ratio,
                Math.sin(Math.PI / 10) * ratio);
        tri.lineTo(-Math.cos(Math.PI / 10) * ratio,
                Math.sin(Math.PI / 10) * ratio);
        tri.closePath();
        IntStream.range(0, 5)
                .mapToObj((int i)
                        -> AffineTransform.getRotateInstance(i * angle))
                .map((AffineTransform at) -> at.createTransformedShape(tri))
                .map(s -> new Area(s))
                .forEach(star::add);
    }

    static int enterIntGUI(String prompt) {
        String tempString = JOptionPane.showInputDialog(prompt);
        int temp = 1;
        try {
            temp = Integer.parseInt(tempString);
        } catch (NumberFormatException e) {
        }
        return temp;
    }

    static BufferedImage paintOnBG(BufferedImage flagImage) {
        BufferedImage myImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = myImage.createGraphics();
        int x = (int) Math.round((getWidth() - flagImage.getWidth()) / 2.0);
        int y = (int) Math.round((getHeight() - flagImage.getHeight()) / 2.0);
        myCanvas.drawImage(flagImage, null, x, y);
        return myImage;
    }

    static Color[] tpGen() {
        float[] r = new float[]{0.6F, 1, 0, 0, 1, 1, 0};
        float[] g = new float[]{0.6F, 1, 1, 1, 0, 0, 0};
        float[] b = new float[]{0.6F, 0, 1, 0, 1, 0, 1};
        return IntStream.range(0, r.length)
                .mapToObj((int i) -> new Color(r[i], g[i], b[i]))
                .toArray(Color[]::new);
    }

    static Rectangle2D.Double makeFlagBox(double flagRatio) {
        double screenRatio = GL6Util.getWidth() / (double) GL6Util.getHeight();
        double flagWidth = GL6Util.getWidth();
        double flagHeight = GL6Util.getHeight();
        if (flagRatio > screenRatio) {
            flagHeight = GL6Util.getWidth() / flagRatio;
        } else {
            if (flagRatio < screenRatio) {
                flagWidth = flagRatio * GL6Util.getHeight();
            }
        }
        return new Rectangle2D.Double(0, 0, flagWidth, flagHeight);
    }

    public static BufferedImage nameDraw(String name) {
        Font nameFont = new Font("Algerian", Font.BOLD, 48);
        FontRenderContext nameRC
                = new FontRenderContext(new AffineTransform(), true, false);
        TextLayout layout = new TextLayout(name, nameFont, nameRC);
        Rectangle2D textBox = layout.getBounds();
        textBox.setRect(0, 0, textBox.getWidth() + 50,
                textBox.getHeight() + 30);
        BufferedImage nameImage
                = new BufferedImage((int) Math.ceil(textBox.getWidth()),
                        (int) Math.ceil(textBox.getHeight()),
                        BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = nameImage.createGraphics();
        myCanvas.setColor(Color.white);
        myCanvas.fill(textBox);
        myCanvas.setColor(Color.black);
        myCanvas.draw(textBox);
        layout.draw(myCanvas, 25, (float) textBox.getHeight() - 15);
        return nameImage;
    }

    static BufferedImage imageBase(Rectangle2D.Double flagRect) {
        return new BufferedImage((int) Math.round(flagRect.width),
                (int) Math.round(flagRect.height), BufferedImage.TYPE_INT_RGB);
    }
}
