package Gui;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Orien {
	int dx,dy;
	
	public Orien(BufferedImage image) {
		 dx = image.getWidth()/2;
		 dy = image.getHeight()/2;
	}
	
	public AffineTransformOp getTransform(double angle) {
		AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(angle), dx, dy);
		 AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		 return op;
	}
}
