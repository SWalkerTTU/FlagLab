/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.AbstractMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;

/**
 *
 * @author scott.walker
 */
public class SVGGen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
        SVGDocument doc = (SVGDocument) impl.createDocument(svgNS, "svg", null);
        SVGGraphics2D g = new SVGGraphics2D(doc);

        Flag f = new FlagOfBrazil();
        double ratio = f.getFlagRatio();

        Dimension size = (ratio > 1.5) ? new Dimension(1000, (int) (1000 / ratio))
                : new Dimension((int) (650 * ratio), 650);

        g.setSVGCanvasSize(size);

        AffineTransform blowUp = AffineTransform.getScaleInstance(size.height, size.height);

        f.getAreas().stream().forEach((AbstractMap.SimpleImmutableEntry<Area, Color> area) -> {
            g.setColor(area.getValue());
            g.fill(area.getKey().createTransformedArea(blowUp));
        });

        try {
            g.stream("Brazil.svg");
        } catch (SVGGraphics2DIOException ex) {
            Logger.getLogger(SVGGen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
