/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author swalker
 */
public abstract class UniqueFlagA extends Flag {

    private static double angle;
    private static AffineTransform blowUp;
    private static double flagRatio;

    static double getAngle() {
        return angle;
    }

    static void setAngle(double aAngle) {
        angle = aAngle;
    }

    static AffineTransform getBlowUp() {
        return blowUp;
    }

    static void setBlowUp(AffineTransform aBlowUp) {
        blowUp = aBlowUp;
    }

    static double getFlagRatio() {
        return flagRatio;
    }

    static void setFlagRatio(double aFlagRatio) {
        flagRatio = aFlagRatio;
    }

    private Rectangle2D.Double flag;
    private BufferedImage flagImage;

    public UniqueFlagA(String n) {
        super(n, null);
    }

    @Override
    public void drawFlag() {
        flag = GL6Util.makeFlagBox(flagRatio);
        flagImage = GL6Util.imageBase(flag);
        Graphics2D canvas = flagImage.createGraphics();
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        blowUp = AffineTransform.getScaleInstance(flag.height, flag.height);
        getAreas().stream()
                .forEachOrdered(aMap -> {
                    canvas.setColor(aMap.getValue());
                    canvas.fill(aMap.getKey().createTransformedArea(blowUp));
                });
        setImage(GL6Util.paintOnBG(flagImage));
    }

    Rectangle2D.Double getFlag() {
        return flag;
    }

    void setFlag(Rectangle2D.Double flag) {
        this.flag = flag;
    }

    BufferedImage getFlagImage() {
        return flagImage;
    }

    void setFlagImage(BufferedImage flagImage) {
        this.flagImage = flagImage;
    }
}
