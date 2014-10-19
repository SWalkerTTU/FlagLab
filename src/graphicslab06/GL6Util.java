/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

//import gl6test.GraphicsLab06A;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;

/**
 *
 * @author scott.walker
 */
public class GL6Util {

    private static int height;
    static final Area star = new Area();
    private static int width;

    static {
        final double ratio = (3 - Math.sqrt(5)) / 2.0;
        final double angle = 2 * Math.PI / 5;
        Path2D.Double tri = new Path2D.Double();
        tri.moveTo(Math.cos(Math.PI / 2), Math.sin(-Math.PI / 2));
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

    public static void showName(Graphics g, String name) {
        Graphics2D g2 = (Graphics2D) g;
        Font nameFont = new Font("Algerian", Font.BOLD, 48);
        TextLayout layout = new TextLayout(name, nameFont, g2.getFontRenderContext());
        Rectangle2D box = layout.getBounds();

        box.setRect(25, 50, box.getWidth() + 50, box.getHeight() + 30);
        g2.setColor(Color.white);
        g2.fill(box);
        g2.setColor(Color.black);
        g2.draw(box);

        layout.draw(g2, 50.0f, (float) layout.getBounds().getHeight() + 65);

        delay(2000);                      // Wait 2 second before showing next flag.
    }

    public static void speckleDraw(Graphics g, BufferedImage image, int speed) {
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

    public static void titlePage(Graphics g, String name, int period) {
        g.setColor(new Color(16766720));
        g.fillRect(0, 0, 4800, 3600);
        g.setColor(Color.white);
        g.fillRect(100, 100, 800, 450);
        g.setColor(Color.red);
        Font title = new Font("Algerian", Font.BOLD, 48);
        g.setFont(title);
        g.drawString("Flags of the World", 225, 240);
        g.setColor(Color.blue);
        g.drawString("by: " + name, 225, 340);
        g.setColor(Color.green);
        g.drawString("Period: " + period, 225, 440);
        GL6Util.delay(3000);
    }

    protected static Path2D.Float buildMapleLeaf() {
        float radius = 1;
        Path2D.Float newPath = new Path2D.Float(new Ellipse2D.Float(-radius, radius, 2 * radius, 2 * radius));
        return newPath;
    }

    protected static BufferedImage drawBars(Color[] colors, boolean isVertical) {
        return drawBarsInBox(colors, isVertical,
                new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
    }

    protected static BufferedImage drawBarsInBox(Color[] colors,
            boolean isVertical, Rectangle2D.Double flagBox) {
        final BufferedImage myImage
                = new BufferedImage((int) Math.round(flagBox.width),
                        (int) Math.round(flagBox.height),
                        BufferedImage.TYPE_INT_ARGB);
        Graphics2D myCanvas = myImage.createGraphics();

        double rectWidth = (isVertical)
                ? myImage.getWidth() / (double) colors.length
                : myImage.getWidth();
        double rectHeight = (isVertical)
                ? myImage.getHeight()
                : myImage.getHeight() / (double) colors.length;

        IntStream.range(0, colors.length)
                .mapToObj((int i) -> {
                    Rectangle2D.Double rect = (isVertical)
                            ? new Rectangle2D.Double(i * rectWidth, 0,
                                    rectWidth, rectHeight)
                            : new Rectangle2D.Double(0, i * rectHeight, rectWidth, rectHeight);
                    return new HashMap.SimpleEntry<Rectangle2D.Double, Color>(rect, colors[i]);
                })
                .forEach(sie -> {
                    myCanvas.setColor(sie.getValue());
                    myCanvas.fill(sie.getKey());
                });

        return myImage;
    }

    protected static int enterIntGUI(String prompt) {
        String tempString = JOptionPane.showInputDialog(prompt);
        int temp = 1;
        try {
            temp = Integer.parseInt(tempString);
        } catch (NumberFormatException e) {
        }
        return temp;
    }

    protected static BufferedImage flagOfBrazil() {
        BufferedImage myImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color green = new Color(43097);
        Color gold = new Color(16763945);
        Color blue = new Color(4079765);
        Color white = Color.white;
        Point2D.Float topLeft = new Point2D.Float(50, 10);
        float flagUnit = 45.0F;
        myCanvas.setColor(Color.black);
        myCanvas.fillRect(0, 0, getWidth(), getHeight());
        myCanvas.setColor(green);
        myCanvas.fill(new Rectangle2D.Float(topLeft.x, topLeft.y, 20 * flagUnit, 14 * flagUnit));
        Path2D.Float drawPath = new Path2D.Float();
        drawPath.moveTo(getWidth() / 2.0F, getHeight() / 2.0 - 5.3 * flagUnit);
        drawPath.lineTo(getWidth() / 2.0F - 8.3 * flagUnit, getHeight() / 2.0);
        drawPath.lineTo(getWidth() / 2.0F, getHeight() / 2.0 + 5.3 * flagUnit);
        drawPath.lineTo(getWidth() / 2.0F + 8.3 * flagUnit, getHeight() / 2.0);
        drawPath.closePath();
        myCanvas.setColor(gold);
        myCanvas.fill(drawPath);
        myCanvas.setColor(blue);
        myCanvas.fill(new Ellipse2D.Float(getWidth() / 2.0F - 3.5F * flagUnit,
                getHeight() / 2.0F - 3.5F * flagUnit, 7 * flagUnit, 7 * flagUnit));
        return myImage;
    }

    protected static BufferedImage flagOfCanada() {
        Color[] base = new Color[]{Color.red, Color.white, Color.white, Color.red};
        BufferedImage myImage = drawBars(base, true);
        int imgW = myImage.getWidth();
        int imgH = myImage.getHeight();
        int blackBarHeight = (2 * imgH - imgW) / 4;
        Path2D.Float mapleLeaf;
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setColor(Color.black);
        myCanvas.fillRect(0, 0, imgW, blackBarHeight);
        myCanvas.fillRect(0, imgH - blackBarHeight, imgW, blackBarHeight);
        myCanvas.setColor(Color.red);
        mapleLeaf = buildMapleLeaf();
        AffineTransform mlXform = new AffineTransform();
        mlXform.setTransform(mlXform);
        return myImage;
    }

    protected static BufferedImage flagOfJapan() {
        BufferedImage myImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        myCanvas.setColor(Color.white);
        myCanvas.fillRect(0, 0, getWidth(), getHeight());
        myCanvas.setColor(new Color(16711705));
        myCanvas.fill(new Ellipse2D.Float(getWidth() / 2 - 0.3F * getHeight(),
                0.2F * getHeight(), 0.6F * getHeight(), 0.6F * getHeight()));
        return myImage;
    }

    protected static BufferedImage flagOfPRC() {
        BufferedImage myImage = drawBars(new Color[]{Color.red}, false);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double hMargin = (2 * getWidth() - 3 * getHeight()) / 4.0;
        double gridUnit = getHeight() / 20.0;
        Point2D.Double bigCenter = new Point2D.Double(getHeight() / 4.0 + hMargin, getHeight() / 4.0);
        AffineTransform scale
                = new AffineTransform(3 * gridUnit, 0, 0, 3 * gridUnit, bigCenter.x, bigCenter.y);
        myCanvas.setColor(Color.yellow);
        myCanvas.fill(scale.createTransformedShape(star));
        double[] littleCtrXs = new double[]{10, 12, 12, 10};
        double[] littleCtrYs = new double[]{2, 4, 7, 9};
        IntStream.range(0, littleCtrXs.length)
                .mapToObj((int i)
                        -> new Point2D.Double(
                                littleCtrXs[i] * gridUnit + hMargin,
                                littleCtrYs[i] * gridUnit))
                .map((Point2D.Double p2d) -> {
                    scale.setTransform(gridUnit, 0, 0, gridUnit, p2d.x, p2d.y);
                    double theta = Math.atan2(bigCenter.x - p2d.x,
                            p2d.y - bigCenter.y);
                    scale.rotate(theta);
                    return scale.createTransformedShape(star);
                })
                .forEach(myCanvas::fill);
        myCanvas.setColor(Color.black);
        myCanvas.fill(new Rectangle2D.Double(0, 0, hMargin, getHeight()));
        myCanvas.fill(new Rectangle2D.Double(getWidth() - hMargin, 0, hMargin, getHeight()));
        return myImage;
    }

    protected static BufferedImage flagOfPakistan() {
        Color green = new Color(26112);
        double flagUnit = getHeight() / 2.0;
        Rectangle2D.Double flag = new Rectangle2D.Double(
                getWidth() / 2.0 - 1.5 * flagUnit, 0,
                3 * flagUnit, 2 * flagUnit);
        BufferedImage myImage = drawBars(new Color[]{Color.black}, true);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        double hMargin = (2 * getWidth() - 3 * getHeight()) / 4.0;
//        double fieldWidth = getWidth() - 2 * hMargin;
//        Point2D.Double topLeft = new Point2D.Double(hMargin, 0);
//        Point2D.Double bottomRight = new Point2D.Double(getWidth() - hMargin, getHeight());
//        Point2D.Double ctr1 = new Point2D.Double(hMargin + fieldWidth * 5 / 8, getHeight() / 2.0);
//        double rad1 = 0.3 * getHeight();
//        double c2locRad = 0.65 * getHeight();
//        Point2D.Double ctr2 = new Point2D.Double(hMargin + fieldWidth - c2locRad * 9 / Math.sqrt(145), c2locRad * 8 / Math.sqrt(145));
//        double rad2 = 0.275 * getHeight();
//        myCanvas.setColor(green);
//        myCanvas.fill(new Rectangle2D.Double(hMargin, 0, fieldWidth, getHeight()));
//        myCanvas.setColor(Color.white);
//        myCanvas.fill(new Rectangle2D.Double(hMargin, 0, fieldWidth / 4.0, getHeight()));
//        Area crescent = new Area(new Ellipse2D.Double(ctr1.x - rad1, ctr1.y - rad1, 2 * rad1, 2 * rad1));
//        crescent.subtract(new Area(new Ellipse2D.Double(ctr2.x - rad2, ctr2.y - rad2, 2 * rad2, 2 * rad2)));
//        myCanvas.fill(crescent);
//        Point2D.Double ctr3 = new Point2D.Double(hMargin + fieldWidth - 0.55 * getHeight() * 9 / Math.sqrt(145), 0.55 * getHeight() * 8 / Math.sqrt(145));
//        AffineTransform starLoc = new AffineTransform(0.1 * getHeight(), 0, 0, 0.1 * getHeight(), ctr3.x, ctr3.y);
//        starLoc.rotate(Math.atan2(hMargin + fieldWidth - ctr3.x, ctr3.y));
//        myCanvas.fill(starLoc.createTransformedShape(star));
//        myCanvas.setColor(Color.black);
//        myCanvas.fill(new Rectangle2D.Double(0, 0, hMargin, getHeight()));
//        myCanvas.fill(new Rectangle2D.Double(getWidth() - hMargin, 0, hMargin, getHeight()));
        return myImage;
    }

    protected static BufferedImage flagOfROK() {
        return new FlagOfROK().getImage();
    }

    protected static BufferedImage flagOfRSA() {
        Color green = new Color(31833);
        Color red = new Color(14826792);
        Color blue = new Color(793740);
        Color gold = new Color(16561428);
        BufferedImage myImage = drawBars(new Color[]{red, blue}, false);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        double angle = Math.atan2(getHeight(), getWidth());
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;

        AffineTransform nw = AffineTransform
                .getRotateInstance(Math.PI + angle, centerX, centerY);
        AffineTransform sw = AffineTransform
                .getRotateInstance(Math.PI - angle, centerX, centerY);

        Area whiteBar = new Area(new Rectangle2D.Double(centerX,
                centerY - getHeight() / 6.0, 0.6 * getWidth(),
                getHeight() / 3.0));
        Area whiteFimbre = new Area(whiteBar);
        whiteFimbre.add(whiteBar.createTransformedArea(nw));
        whiteFimbre.add(whiteBar.createTransformedArea(sw));

        Area greenBar = new Area(new Rectangle2D.Double(centerX,
                centerY - getHeight() / 10.0, 0.6 * getWidth(),
                getHeight() / 5.0));
        Area greenFimbre = new Area(greenBar);
        greenFimbre.add(greenBar.createTransformedArea(nw));
        greenFimbre.add(greenBar.createTransformedArea(sw));

        Area goldTri = new Area(new Rectangle2D.Double(0, 0, getWidth(),
                getHeight()));
        goldTri.subtract(greenFimbre);
        Path2D.Double bigTri = new Path2D.Double();
        bigTri.moveTo(0, 0);
        bigTri.lineTo(centerX, centerY);
        bigTri.lineTo(0, getHeight());
        bigTri.closePath();
        goldTri.intersect(new Area(bigTri));

        Area blackTri = new Area(goldTri);
        blackTri.subtract(whiteFimbre);

        myCanvas.setColor(Color.white);
        myCanvas.fill(whiteFimbre);
        myCanvas.setColor(green);
        myCanvas.fill(greenFimbre);
        myCanvas.setColor(gold);
        myCanvas.fill(goldTri);
        myCanvas.setColor(Color.black);
        myCanvas.fill(blackTri);
        return myImage;
    }

    protected static BufferedImage flagOfScotland() {
        Color blue = new Color(26045);
        BufferedImage myImage = drawBars(new Color[]{blue}, false);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        double angle = Math.atan2(getHeight(), getWidth());
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;

        AffineTransform nesw = AffineTransform
                .getRotateInstance(angle, centerX, centerY);
        AffineTransform nwse = AffineTransform
                .getRotateInstance(-angle, centerX, centerY);
        Area rect = new Area(new Rectangle2D.Double(centerX - getWidth() * 0.6,
                centerY - getHeight() / 10.0, getWidth() * 1.2,
                getHeight() / 5.0));
        Area saltire = rect.createTransformedArea(nesw);
        saltire.add(rect.createTransformedArea(nwse));

        myCanvas.setColor(Color.white);
        myCanvas.fill(saltire);
        return myImage;
    }

    static BufferedImage flagOfSuisse() {
        BufferedImage myImage = drawBars(new Color[]{Color.BLACK}, true);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        double crossUnit = 5 * getHeight() / 160.0;
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;

        Rectangle2D.Double field = new Rectangle2D.Double(centerX - centerY, 0,
                getHeight(), getHeight());
        Rectangle2D.Double crossBar
                = new Rectangle2D.Double(centerX - 3 * crossUnit,
                        centerY - 10 * crossUnit, 6 * crossUnit,
                        20 * crossUnit);

        Area cross = new Area(crossBar);
        cross.add(cross.createTransformedArea(AffineTransform
                .getQuadrantRotateInstance(1, centerX, centerY)));

        myCanvas.setColor(Color.red);
        myCanvas.fill(field);
        myCanvas.setColor(Color.white);
        myCanvas.fill(cross);
        return myImage;
    }

    static BufferedImage flagOfTexas() {
        return new FlagOfTexas().getImage();
    }

    static BufferedImage flagOfUK() {
        return new FlagOfUK().getImage();
    }

    static BufferedImage flagOfUSA() {
        return new FlagOfUSA().getImage();
    }

    static BufferedImage paintOnBG(BufferedImage flagImage) {
        BufferedImage myImage = drawBars(new Color[]{Color.BLACK}, true);
        Graphics2D myCanvas = myImage.createGraphics();
        int x = (int) Math.round((getWidth() - flagImage.getWidth()) / 2.0);
        int y = (int) Math.round((getHeight() - flagImage.getHeight()) / 2.0);
        myCanvas.drawImage(flagImage, null, x, y);
        return myImage;
    }


    static BufferedImage nordicCross(Color[] colors) {
        final BufferedImage myImage = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        myCanvas.setColor(colors[0]);
        myCanvas.fillRect(0, 0, getWidth(), getHeight());
        myCanvas.setColor(colors[1]);
        myCanvas.fill(new Rectangle2D.Float(3 * getWidth() / 8 - 0.1F * getHeight(), 0.0F, 0.2F * getHeight(), 1.0F * getHeight()));
        myCanvas.fill(new Rectangle2D.Float(0.0F, 0.4F * getHeight(), 1.0F * getWidth(), 0.2F * getHeight()));
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
        double screenRatio = getWidth() / (double) getHeight();
        double flagWidth = getWidth(),
                flagHeight = getHeight();

        if (flagRatio > screenRatio) {
            flagHeight = getWidth() / flagRatio;
        } else {
            if (flagRatio < screenRatio) {
                flagWidth = flagRatio * getHeight();
            }
        }
        return new Rectangle2D.Double(0, 0,
                flagWidth, flagHeight);
    }

    static BufferedImage drawBarFlag(Color[] colors, boolean vertical) {
        Rectangle2D.Double rect = makeFlagBox(1.5);
        BufferedImage img = drawBarsInBox(colors, vertical, rect);
        return paintOnBG(img);
    }

}
