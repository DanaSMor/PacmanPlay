package Gui;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * This class responsible rotate a object
 * accord a angle
 *
 */
public class Orien {
	int dx,dy;

	public Orien(BufferedImage image) {
		dx = image.getWidth()/2;
		dy = image.getHeight()/2;
	}

	/**
	 * Return a AffineTransformOp with a rotate data
	 * @param angle
	 * @return
	 */
	public AffineTransformOp getTransform(double angle) {
		if(Double.isNaN(angle)) angle = 0;

		AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(angle), dx, dy);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		return op;
	}
}
