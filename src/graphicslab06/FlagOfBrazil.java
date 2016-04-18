package graphicslab06;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

public class FlagOfBrazil extends UniqueFlag {

    private static final Color white = Color.white;
    private static final Color blue = new Color(0x3e4095);
    private static final Color gold = new Color(0xffcc29);
    private static final Color green = new Color(0xa859);

    private static final Area diamond = new Area();
    private static final Area flagBase;
    private static final Area globe = new Area();
    private static final Area band = new Area();
    private static final Area star = GL6Util.star;
    private static final Area motto = new Area();

    private static final String lema = "ORDEM E PROGRESSO";
    private static final Font mottoFont
            = new Font("Arial", Font.PLAIN, 10);

    private static final double module = 1.0 / 14;

    static final double[] starSizes = new double[]{
        0, 0.3, 0.25, 0.2, 1.0 / 7, 0.1
    };
    private static final Point2D.Double bandCtr
            = new Point2D.Double(8.0 * module, 14 * module);
    private static final Point2D.Double globeCtr
            = new Point2D.Double(10 * module, 7 * module);
    private static final double radius1 = 8.5 * module;
    private static final double radius2 = 8.0 * module;
    private static final double radius3 = 8.25 * module;

    private static final double letterH = 0.3 * module;

    private static final double radius4 = (radius1 + radius2 + letterH) / 2;
    private static final double flagRatioBR;

    static {
        flagRatioBR = 10.0 / 7;
        flagBase = getFlagBase(flagRatioBR);

        buildDiamond();
        buildSky();
        buildMotto();
    }

    private static void buildSky() {
        Ellipse2D.Double baseCirc = new Ellipse2D.Double(-1, -1, 2, 2);

        AffineTransform globeShape
                = AffineTransform
                .getTranslateInstance(globeCtr.x, globeCtr.y);
        globeShape.scale(3.5 * module, 3.5 * module);

        globe.add(new Area(baseCirc));
        globe.transform(globeShape);

        AffineTransform bandShape
                = AffineTransform.getTranslateInstance(bandCtr.x, bandCtr.y);
        bandShape.scale(radius1, radius1);

        band.add(new Area(baseCirc));
        band.transform(bandShape);

        bandShape.setToTranslation(bandCtr.x, bandCtr.y);
        bandShape.scale(radius2, radius2);

        band.subtract(new Area(bandShape.createTransformedShape(baseCirc)));
        band.intersect(globe);

        final double starUnit = 0.35 * module;

        Arrays.stream(BrazilStars.values()).map((BrazilStars s) -> {
            double x = globeCtr.x + starUnit * s.xLoc;
            double y = globeCtr.y + starUnit * s.yLoc;
            double r = starSizes[s.size] / 2 * module;
            AffineTransform starX = AffineTransform.getTranslateInstance(x, y);
            starX.scale(r, r);
            return star.createTransformedArea(starX);
        }).forEach(band::add);
    }

    private static void buildDiamond() {
        double margin = 1.7 * module;
        double width = 20 * module;
        double height = 14 * module;

        diamond.add(new Area(new Rectangle2D.Double(0, 0, 1, 1)));

        AffineTransform diamShape
                = AffineTransform.getTranslateInstance(globeCtr.x,
                        margin);
        diamShape.scale((width - 2 * margin) * Math.sqrt(0.5),
                (height - 2 * margin) * Math.sqrt(0.5));
        diamShape.rotate(Math.PI / 4.0);

        diamond.transform(diamShape);
    }

    private static void buildMotto() {
        double letterW = 1.0 / 3 * module;
        double eW = 0.3 * module;
        double eH = 0.25 * module;

        FontRenderContext mottoFRC
                = new FontRenderContext(new AffineTransform(), true, false);

        GlyphVector mottoVec = mottoFont.createGlyphVector(mottoFRC, lema);

        Point2D.Double base
                = new Point2D.Double(bandCtr.x, bandCtr.y - radius3);

        AffineTransform shrink = new AffineTransform();
        AffineTransform shift = new AffineTransform();
        AffineTransform spin = new AffineTransform();

        Area[] letters = IntStream.range(0, mottoVec.getNumGlyphs())
                .mapToObj(i -> {
                    double wd = (i >= 5 && i <= 7) ? eW : letterW;
                    double ht = (i >= 5 && i <= 7) ? eH : letterH;
                    Shape s = mottoVec.getGlyphOutline(i);
                    shrink.setToScale(wd / s.getBounds2D().getWidth(),
                            ht / s.getBounds2D().getHeight());
                    return new Area(shrink.createTransformedShape(s));
                }).toArray(Area[]::new);

        Arrays.stream(letters).forEach(a -> {
            shift.setToTranslation(-a.getBounds2D().getX(),
                    -a.getBounds2D().getY());
            a.transform(shift);
            shift.setToTranslation(base.x - a.getBounds2D().getCenterX(),
                    base.y - a.getBounds2D().getCenterY());
            a.transform(shift);
        });

        IntStream.range(0, letters.length).forEach(i -> {
            double spread = Math.PI / 60;
            double small = 1 * spread / 3;
            double theta = (i - 8) * spread;
            if (i < 8) {
                theta += small;
            }
            if (i < 5) {
                theta += small;
            }
            spin.setToRotation(theta, bandCtr.x, bandCtr.y);
            letters[i].transform(spin);
        });

        Arrays.stream(letters).forEach(motto::add);

        // radius4 is the radius of the circle where the tops of the letters
        // should go and globeCtr.x is the horizontal centerline.
        double pX = letters[8].getBounds2D().getX();
        double rot = Math.asin((globeCtr.x - pX) / radius4);
        spin.setToRotation(rot, bandCtr.x, bandCtr.y);
        motto.transform(spin);
    }

    public FlagOfBrazil() {
        super("Brazil");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, green));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(diamond, gold));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(globe, blue));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(band, white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(motto, green));
    }

    @Override
    public double getFlagRatio() {
        return flagRatioBR;
    }

    static enum BrazilStars {

        ACRE(2.5, -0.5, 3),
        AMAPÁ(-8.25, 4.25, 2),
        AMAZONAS(-8.25, -1.75, 1),
        PARÁ(3.25, -3.25, 1),
        MARANHÃO(8.25, 3.5, 3),
        PIAUÍ(7, 3.5, 1),
        CEARÁ(7.25, 4.5, 2),
        RIO_GRANDE_DO_NORTE(6.75, 5.25, 2),
        PARAÍBA(6, 5.75, 3),
        PERNAMBUCO(5, 5.5, 3),
        ALAGOAS(5, 6.5, 2),
        SERGIPE(5, 7.5, 3),
        BAHIA(0, 1.5, 2),
        ESPÍRITO_SANTO(-0.5, 3.25, 4),
        RIO_DE_JANEIRO(1, 2.5, 2),
        SÃO_PAULO(0, 4.5, 1),
        PARANÁ(2.25, 5.5, 3),
        SANTA_CATARINA(3.75, 5.75, 3),
        RIO_GRANDE_DO_SUL(3, 6.75, 2),
        MINAS_GERAIS(-1, 2.5, 3),
        GOIÁS(-4, 5.5, 1),
        MATO_GROSSO(-7.25, 2.5, 1),
        MATO_GROSSO_DO_SUL(-3.75, 0.25, 2),
        RONDÔNIA(-6.25, 1.75, 4),
        RORAIMA(-5.25, 3.25, 2),
        TOCANTINS(-5.5, 4.5, 3),
        BRASILIA_DF(0, 7.75, 5);

        public double xLoc;
        public double yLoc;
        public int size;

        BrazilStars(double xGrid, double yGrid, int s) {
            xLoc = xGrid;
            yLoc = yGrid;
            size = s;
        }
    }

}
