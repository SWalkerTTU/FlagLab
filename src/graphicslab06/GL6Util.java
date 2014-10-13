/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

//import gl6test.GraphicsLab06A;
import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;

/**
 *
 * @author scott.walker
 */
public class GL6Util {

    private static int height;
    private static final Area star = buildStar();
    private static int width;

    private static Area buildStar() {
        final double ratio = (3 - Math.sqrt(5)) / 2;
        double angle = 2 * Math.PI / 5;
        Area starArea = new Area();

        Path2D.Double tri = new Path2D.Double();
        tri.moveTo(Math.cos(Math.PI / 2), Math.sin(-Math.PI / 2));
        tri.lineTo(Math.cos(Math.PI / 10) * ratio,
                Math.sin(Math.PI / 10) * ratio);
        tri.lineTo(-Math.cos(Math.PI / 10) * ratio,
                Math.sin(Math.PI / 10) * ratio);
        tri.closePath();

        IntStream.range(0, 5).mapToObj((int i)
                -> AffineTransform.getRotateInstance(i * angle))
                .map((AffineTransform at) -> at.createTransformedShape(tri))
                .forEach((Shape s) -> starArea.add(new Area(s)));

        return starArea;
    }

    public static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    /**
     * @return the height
     */
    public static int getHeight() {
        return height;
    }

    /**
     * @param aHeight the height to set
     */
    public static void setHeight(int aHeight) {
        height = aHeight;
    }

    /**
     * @return the width
     */
    public static int getWidth() {
        return width;
    }

    /**
     * @param aWidth the width to set
     */
    public static void setWidth(int aWidth) {
        width = aWidth;
    }

    public static void showName(Graphics g, String name) {
        Graphics2D g2 = (Graphics2D) g;
        Font countryName = new Font("Algerian", Font.BOLD, 48);
        TextLayout layout = new TextLayout(name, countryName, g2.getFontRenderContext());
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
        boolean[][] map = new boolean[imgWidth / boxSize][imgHeight / boxSize];
        int freeCells = map.length * map[0].length;
        Random rnd = new Random();
        BufferedImage draw = new BufferedImage(imgWidth, imgHeight, image.getType());
        Graphics2D drawG = draw.createGraphics();
        drawG.setBackground(Color.black);
        drawG.clearRect(0, 0, imgWidth, imgHeight);

        while (freeCells > 0) {
            int x, y;
            do {
                x = rnd.nextInt(map.length);
                y = rnd.nextInt(map[0].length);
            } while (map[x][y]);
            map[x][y] = true;
            freeCells--;
            draw.setData(image.getData(new Rectangle(x * boxSize, y * boxSize, boxSize, boxSize)));
            g.drawImage(draw, 0, 0, null);
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
        final BufferedImage myImage = new BufferedImage(getWidth(), height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D myCanvas = myImage.createGraphics();

        double rectWidth = (isVertical)
                ? width / (double) colors.length : width;
        double rectHeight = (isVertical)
                ? height : height / (double) colors.length;

        IntStream.range(0, colors.length)
                .mapToObj(i -> new ColorRect(
                                ((isVertical)
                                        ? new Rectangle2D.Double(i * rectWidth, 0,
                                                rectWidth, rectHeight)
                                        : new Rectangle2D.Double(0, i * rectHeight,
                                                rectWidth, rectHeight)), colors[i]))
                .forEachOrdered((ColorRect cr) -> {
                    myCanvas.setColor(cr.getColor());
                    myCanvas.fill(cr.getRect());
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
        BufferedImage myImage = new BufferedImage(getWidth(), height, BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color green = new Color(43097);
        Color gold = new Color(16763945);
        Color blue = new Color(4079765);
        Color white = Color.white;
        Point2D.Float topLeft = new Point2D.Float(50, 10);
        float flagUnit = 45.0F;
        myCanvas.setColor(Color.black);
        myCanvas.fillRect(0, 0, width, height);
        myCanvas.setColor(green);
        myCanvas.fill(new Rectangle2D.Float(topLeft.x, topLeft.y, 20 * flagUnit, 14 * flagUnit));
        Path2D.Float drawPath = new Path2D.Float();
        drawPath.moveTo(getWidth() / 2.0F, height / 2.0 - 5.3 * flagUnit);
        drawPath.lineTo(getWidth() / 2.0F - 8.3 * flagUnit, height / 2.0);
        drawPath.lineTo(getWidth() / 2.0F, height / 2.0 + 5.3 * flagUnit);
        drawPath.lineTo(getWidth() / 2.0F + 8.3 * flagUnit, height / 2.0);
        drawPath.closePath();
        myCanvas.setColor(gold);
        myCanvas.fill(drawPath);
        myCanvas.setColor(blue);
        myCanvas.fill(new Ellipse2D.Float(getWidth() / 2.0F - 3.5F * flagUnit,
                height / 2.0F - 3.5F * flagUnit, 7 * flagUnit, 7 * flagUnit));
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
        BufferedImage myImage = new BufferedImage(getWidth(), height, BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        myCanvas.setColor(Color.white);
        myCanvas.fillRect(0, 0, width, height);
        myCanvas.setColor(new Color(16711705));
        myCanvas.fill(new Ellipse2D.Float(getWidth() / 2 - 0.3F * height,
                0.2F * height, 0.6F * height, 0.6F * height));
        return myImage;
    }

    protected static BufferedImage flagOfPRC() {
        BufferedImage myImage = drawBars(new Color[]{Color.red}, false);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double hMargin = (2 * width - 3 * height) / 4.0;
        double gridUnit = height / 20.0;
        Point2D.Double bigCenter = new Point2D.Double(getHeight() / 4.0 + hMargin, height / 4.0);
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
        myCanvas.fill(new Rectangle2D.Double(0, 0, hMargin, height));
        myCanvas.fill(new Rectangle2D.Double(getWidth() - hMargin, 0, hMargin, height));
        return myImage;
    }

    protected static BufferedImage flagOfPakistan() {
        Color green = new Color(26112);
        BufferedImage myImage = new BufferedImage(getWidth(), height, BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double hMargin = (2 * width - 3 * height) / 4.0;
        double fieldWidth = width - 2 * hMargin;
        Point2D.Double topLeft = new Point2D.Double(hMargin, 0);
        Point2D.Double bottomRight = new Point2D.Double(getWidth() - hMargin, height);
        Point2D.Double ctr1 = new Point2D.Double(hMargin + fieldWidth * 5 / 8, height / 2.0);
        double rad1 = 0.3 * height;
        double c2locRad = 0.65 * height;
        Point2D.Double ctr2 = new Point2D.Double(hMargin + fieldWidth - c2locRad * 9 / Math.sqrt(145), c2locRad * 8 / Math.sqrt(145));
        double rad2 = 0.275 * height;
        myCanvas.setColor(green);
        myCanvas.fill(new Rectangle2D.Double(hMargin, 0, fieldWidth, height));
        myCanvas.setColor(Color.white);
        myCanvas.fill(new Rectangle2D.Double(hMargin, 0, fieldWidth / 4.0, height));
        Area crescent = new Area(new Ellipse2D.Double(ctr1.x - rad1, ctr1.y - rad1, 2 * rad1, 2 * rad1));
        crescent.subtract(new Area(new Ellipse2D.Double(ctr2.x - rad2, ctr2.y - rad2, 2 * rad2, 2 * rad2)));
        myCanvas.fill(crescent);
        Point2D.Double ctr3 = new Point2D.Double(hMargin + fieldWidth - 0.55 * height * 9 / Math.sqrt(145), 0.55 * height * 8 / Math.sqrt(145));
        AffineTransform starLoc = new AffineTransform(0.1 * height, 0, 0, 0.1 * height, ctr3.x, ctr3.y);
        starLoc.rotate(Math.atan2(hMargin + fieldWidth - ctr3.x, ctr3.y));
        myCanvas.fill(starLoc.createTransformedShape(star));
        myCanvas.setColor(Color.black);
        myCanvas.fill(new Rectangle2D.Double(0, 0, hMargin, height));
        myCanvas.fill(new Rectangle2D.Double(getWidth() - hMargin, 0, hMargin, height));
        return myImage;
    }

    protected static BufferedImage flagOfROK() {
        double slope = 2.0 / 3.0;
        double flagWidth = 3 * height / 2.0;
        double flagUnit = height / 2.0;
        Point2D.Double topLeft = new Point2D.Double(getWidth() / 2.0 - flagWidth / 2.0, 0);
        Point2D.Double bottomRight = new Point2D.Double(getWidth() / 2.0 + flagWidth / 2.0, height);
        BufferedImage myImage = drawBars(new Color[]{Color.white}, false);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color red = new Color(12979248);
        Color blue = new Color(13432);
        double angle = Math.atan(slope);
        AffineTransform shift = new AffineTransform(1, 0, 0, 1, width / 2.0, height / 2.0);
        AffineTransform yflip = new AffineTransform(-1, 0, 0, 1, 0, 0);
        AffineTransform xflip = new AffineTransform(1, 0, 0, -1, 0, 0);
        Area tao = new Area(new Ellipse2D.Double(-flagUnit / 2, -flagUnit / 2, flagUnit, flagUnit));
        Area smallCirc = new Area(new Ellipse2D.Double(-flagUnit / 2, -flagUnit / 4, flagUnit / 2, flagUnit / 2));
        tao.subtract(smallCirc);
        tao.subtract(smallCirc.createTransformedArea(yflip));
        tao.subtract(new Area(new Rectangle2D.Double(-flagUnit / 2, 0, flagUnit, flagUnit / 2)));
        tao.add(smallCirc);
        shift.rotate(angle);
        myCanvas.setColor(red);
        myCanvas.fill(tao.createTransformedArea(shift));
        shift.concatenate(xflip);
        shift.concatenate(yflip);
        myCanvas.setColor(blue);
        myCanvas.fill(tao.createTransformedArea(shift));
        final double tgXOffset = flagUnit * 3 / 4;
        final double tgYOffset = -flagUnit / 4;
        final double tgWidth = flagUnit / 3;
        final double tgHeight = flagUnit / 2;
        final double tgRemWidth = flagUnit / 24;
        final double tgBarWidth = flagUnit / 12;
        final double tgDivOff = tgYOffset + tgHeight / 2 - tgRemWidth / 2;
        Area trigramBase = new Area(new Rectangle2D.Double(tgXOffset, tgYOffset, tgWidth, tgHeight));
        Area[] tgRemovers = new Area[4];
        for (int i = 0; i < tgRemovers.length; i++) {
            tgRemovers[i] = new Area(new Rectangle2D.Double(tgXOffset + tgBarWidth, tgYOffset, tgRemWidth, tgHeight));
            tgRemovers[i].add(new Area(new Rectangle2D.Double(tgXOffset + 2 * tgBarWidth + tgRemWidth, tgYOffset, tgRemWidth, tgHeight)));
        }
        tgRemovers[0].add(new Area(new Rectangle2D.Double(tgXOffset, tgDivOff, tgWidth, tgRemWidth)));
        tgRemovers[1].add(new Area(new Rectangle2D.Double(tgXOffset + tgBarWidth + tgRemWidth, tgDivOff, tgBarWidth, tgRemWidth)));
        tgRemovers[3].add(new Area(new Rectangle2D.Double(tgXOffset, tgDivOff, tgWidth, tgRemWidth)));
        tgRemovers[3].subtract(new Area(new Rectangle2D.Double(tgXOffset + tgBarWidth + tgRemWidth, tgDivOff, tgBarWidth, tgRemWidth)));
        shift.setTransform(1, 0, 0, 1, width / 2.0, height / 2.0);
        myCanvas.setColor(Color.BLACK);
        for (int i = 0; i < 4; i++) {
            Area drawGram = new Area(trigramBase);
            drawGram.subtract(tgRemovers[i]);
            switch (i) {
                case 0:
                    shift.rotate(angle);
                    break;
                case 1:
                case 3:
                    shift.rotate(Math.PI - 2 * angle);
                    break;
                case 2:
                    shift.rotate(2 * angle);
                    break;
                default:
            }
            myCanvas.fill(drawGram.createTransformedArea(shift));
        }
        myCanvas.setColor(Color.black);
        myCanvas.fill(new Rectangle2D.Double(0, 0, topLeft.x, height));
        myCanvas.fill(new Rectangle2D.Double(bottomRight.x, 0, width - bottomRight.x, height));
        return myImage;
    }

    protected static BufferedImage flagOfRSA() {
        Color green = new Color(31833);
        Color red = new Color(14826792);
        Color blue = new Color(793740);
        Color gold = new Color(16561428);
        double barWidth = height / 30.0;
        double slope = height / (double) width;
        double cosFact = Math.sqrt(slope * slope + 1);
        double barHzn = Math.ceil(barWidth * cosFact / slope);
        double semComSlp = -slope / (1 + cosFact);
        double hStep = -semComSlp * barWidth;
        BufferedImage myImage = drawBars(new Color[]{green}, false);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform shift = new AffineTransform();
        Path2D.Double drawPath = new Path2D.Double();
        drawPath.moveTo(0, 0);
        drawPath.lineTo(getWidth() / 2.0, height / 2.0);
        drawPath.lineTo(getWidth(), height / 2.0);
        drawPath.lineTo(getWidth(), 0);
        drawPath.closePath();
        shift.setTransform(1, 0, 0, 1, 3 * hStep, -3 * barWidth);
        myCanvas.setColor(Color.white);
        myCanvas.fill(drawPath.createTransformedShape(shift));
        shift.setTransform(1, 0, 0, -1, 3 * hStep, height + 3 * barWidth);
        myCanvas.fill(drawPath.createTransformedShape(shift));
        shift.setTransform(1, 0, 0, 1, 5 * hStep, -5 * barWidth);
        myCanvas.setColor(red);
        myCanvas.fill(drawPath.createTransformedShape(shift));
        shift.setTransform(1, 0, 0, -1, 5 * hStep, height + 5 * barWidth);
        myCanvas.setColor(blue);
        myCanvas.fill(drawPath.createTransformedShape(shift));
        drawPath.reset();
        drawPath.moveTo(0, 0);
        drawPath.lineTo(getWidth() / 2.0, height / 2.0);
        drawPath.lineTo(0, height);
        shift.setTransform(1, 0, 0, 1, -3 * barHzn, 0);
        myCanvas.setColor(gold);
        myCanvas.fill(drawPath.createTransformedShape(shift));
        shift.setTransform(1, 0, 0, 1, -5 * barHzn, 0);
        myCanvas.setColor(Color.BLACK);
        myCanvas.fill(drawPath.createTransformedShape(shift));
        return myImage;
    }

    protected static BufferedImage flagOfScotland() {
        double slope = height / (double) width;
        double offsetFactor = Math.sqrt(1 + slope * slope);
        double stripeHeight = height / 10 * offsetFactor;
        Color blue = new Color(26045);
        BufferedImage myImage = drawBars(new Color[]{blue}, false);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Path2D.Float drawPath = new Path2D.Float();
        drawPath.moveTo(0, -stripeHeight);
        drawPath.lineTo(getWidth(), height - stripeHeight);
        drawPath.lineTo(getWidth(), height + stripeHeight);
        drawPath.lineTo(0, stripeHeight);
        myCanvas.setColor(Color.white);
        myCanvas.fill(drawPath);
        drawPath.transform(new AffineTransform(1.0, 0.0, 0.0, -1.0, 0.0, height));
        myCanvas.fill(drawPath);
        return myImage;
    }

    protected static BufferedImage flagOfSuisse() {
        BufferedImage myImage = new BufferedImage(getWidth(), height, BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        myCanvas.setColor(Color.black);
        myCanvas.fillRect(0, 0, width, height);
        myCanvas.setColor(Color.red);
        myCanvas.fillRect(getWidth() / 2 - height / 2, 0, height, height);
        myCanvas.setColor(Color.white);
        float crossUnit = 5 * height / 160.0F;
        myCanvas.fill(new Rectangle2D.Float(getWidth() / 2 - 3 * crossUnit, height / 2 - 10 * crossUnit, 6 * crossUnit, 20 * crossUnit));
        myCanvas.fill(new Rectangle2D.Float(getWidth() / 2 - 10 * crossUnit, height / 2 - 3 * crossUnit, 20 * crossUnit, 6 * crossUnit));
        return myImage;
    }

    protected static BufferedImage flagOfTSA() {
        BufferedImage myImage = drawBars(new Color[]{Color.red}, false);
        Graphics2D myCanvas = myImage.createGraphics();
        double hMargin = (2 * width - 3 * height) / 4.0;
        double outRadius = 0.3 * height;
        double inRadius = 0.24 * height;
        Point2D.Double topLeft = new Point2D.Double(hMargin, 0);
        Point2D.Double bottomRight = new Point2D.Double(getWidth() - hMargin, height);
        Area shield = new Area(new Ellipse2D.Double(-outRadius, -outRadius, 2 * outRadius, 2 * outRadius));
        shield.subtract(new Area(new Ellipse2D.Double(-inRadius, -inRadius, 2 * inRadius, 2 * inRadius)));
        shield.add(new Area(AffineTransform.getScaleInstance(inRadius, inRadius).createTransformedShape(star)));
        myCanvas.setColor(new Color(224, 224, 224));
        myCanvas.fill(shield.createTransformedArea(new AffineTransform(1, 0, 0, 1, width / 2.0, height / 2.0)));
        myCanvas.setColor(Color.black);
        myCanvas.draw(shield.createTransformedArea(new AffineTransform(1, 0, 0, 1, width / 2.0, height / 2.0)));
        myCanvas.fill(new Rectangle2D.Double(0, 0, topLeft.x, height));
        myCanvas.fill(new Rectangle2D.Double(bottomRight.x, 0, width - bottomRight.x, height));
        return myImage;
    }

    protected static BufferedImage flagOfTexas() {
        Color red = new Color(12519984);
        Color blue = new Color(10344);
        Color blank = new Color(0, 0, 0, 0);
        BufferedImage myImage = drawBars(new Color[]{Color.white, red}, false);
        Graphics2D myCanvas = myImage.createGraphics();
        BufferedImage overlay = drawBars(new Color[]{blue, blank, blank}, true);
        myCanvas.drawImage(overlay, null, 0, 0);
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        myCanvas.setColor(Color.white);
        AffineTransform txStar
                = new AffineTransform(getWidth() / 8.0, 0.0, 0.0,
                        getWidth() / 8.0, getWidth() / 6.0, getHeight() / 2.0);
        myCanvas.fill(txStar.createTransformedShape(star));
        return myImage;
    }

    protected static BufferedImage flagOfUK() {
        Color red = new Color(13504806);
        Color blue = new Color(11135);
        BufferedImage myImage = drawBars(new Color[]{blue}, false);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double jackUnit = width / 60.0;
        double slope = -0.5;
        double angle = Math.atan(slope);
        double centerX = 30 * jackUnit;
        double centerY = getHeight() / 2.0;
        
        AffineTransform nesw = AffineTransform.getRotateInstance(angle,
                centerX, centerY);
        AffineTransform nwse = AffineTransform.getRotateInstance(-angle,
                centerX, centerY);
        AffineTransform ns = AffineTransform
                .getQuadrantRotateInstance(1, centerX, centerY);
        AffineTransform ew = AffineTransform
                .getQuadrantRotateInstance(2, centerX, centerY);
        
        Area stripe = new Area(new Rectangle2D.Double(centerX - 35 * jackUnit,
                centerY - 3 * jackUnit, 70 * jackUnit, 6 * jackUnit));
        
        Area saltire = new Area(nesw.createTransformedShape(stripe));
        saltire.add(new Area(stripe.createTransformedArea(nwse)));
        
        Area redStripe = new Area(new Rectangle2D.Double(centerX,
                centerY - 2 * jackUnit, 35 * jackUnit, 2 * jackUnit));
        redStripe.add(redStripe.createTransformedArea(ew));

        Area redSaltire = redStripe.createTransformedArea(nesw);
        redSaltire.add(redStripe.createTransformedArea(nwse));

        Area bigStripe = new Area(
                new Rectangle2D.Double(centerX - 35 * jackUnit,
                        centerY - 5 * jackUnit, 70 * jackUnit, 10 * jackUnit));
        bigStripe.add(bigStripe.createTransformedArea(ns));
        
        Area redCross = new Area();
        redCross.add(stripe);
        redCross.add(stripe.createTransformedArea(ns));
        
        Area blackout = new Area(new Rectangle2D.Double(0,
                getHeight() / 2.0 + 15 * jackUnit, width, 5 * jackUnit));
        blackout.add(blackout.createTransformedArea(ew));
        
        myCanvas.setColor(Color.white);
        myCanvas.fill(saltire);
        myCanvas.setColor(red);
        myCanvas.fill(redSaltire);
        myCanvas.setColor(Color.white);
        myCanvas.fill(bigStripe);
        myCanvas.setColor(red);
        myCanvas.fill(redCross);
        myCanvas.setColor(Color.black);
        myCanvas.fill(blackout);
        return myImage;
    }

    protected static BufferedImage flagOfUSA() {
        Color red = new Color(0xb22234);
        Color blue = new Color(0x3c3b6e);
        Color white = Color.white;
        Color[] stripes = new Color[13];
        IntStream.range(0, stripes.length)
                .filter(i -> i % 2 == 0)
                .forEach((int i) -> {
                    stripes[i] = red;
                });
        IntStream.range(0, stripes.length)
                .filter(i -> i % 2 == 1)
                .forEach((int i) -> {
                    stripes[i] = white;
                });
        BufferedImage myImage = drawBars(stripes, false);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        final float starDiam = 2 * height / 65.0F;
        final float starHSpace = width / 30.0F;
        final float starVSpace = 7 * height / 130.0F;
        myCanvas.setColor(blue);
        myCanvas.fill(new Rectangle2D.Float(0, 0, 0.4F * width, 7.0F * height / 13));
        myCanvas.setColor(Color.white);
        IntStream.range(0, 99)
                .filter(i -> i % 2 == 0)
                .parallel()
                .mapToObj(i -> new AffineTransform(starDiam, 0, 0, starDiam,
                                (i % 11 + 1) * starHSpace,
                                (i / 11 + 1) * starVSpace))
                .map((AffineTransform at) -> at.createTransformedShape(star))
                .forEach(myCanvas::fill);
        return myImage;
    }

    protected static BufferedImage flagOfUSSR() {
        BufferedImage myImage = new BufferedImage(getWidth(), height, BufferedImage.TYPE_INT_RGB);
        return myImage;
    }

    protected static void niceShot(BufferedImage img) {

    }

    protected static BufferedImage nordicCross(Color[] colors) {
        final BufferedImage myImage = new BufferedImage(getWidth(), height, BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        myCanvas.setColor(colors[0]);
        myCanvas.fillRect(0, 0, width, height);
        myCanvas.setColor(colors[1]);
        myCanvas.fill(new Rectangle2D.Float(3 * width / 8 - 0.1F * height, 0.0F, 0.2F * height, 1.0F * height));
        myCanvas.fill(new Rectangle2D.Float(0.0F, 0.4F * height, 1.0F * width, 0.2F * height));
        return myImage;
    }

    protected static Color[] tpGen() {
        float[] r = new float[]{0.6F, 1, 0, 0, 1, 1, 0};
        float[] g = new float[]{0.6F, 1, 1, 1, 0, 0, 0};
        float[] b = new float[]{0.6F, 0, 1, 0, 1, 0, 1};
        return IntStream.range(0, r.length)
                .mapToObj((int i) -> new Color(r[i], g[i], b[i]))
                .toArray(Color[]::new);
    }
}
