package Gameboard;

import java.util.Comparator;

/**
 * Comparator class for sorting accord the speed of each pacman
 * 
 *
 */
public class PacmanComperator implements Comparator<Pacman> {

	@Override
	public int compare(Pacman p1, Pacman p2) {
		return (int)Math.round((p1.getAttribute() - p2.getAttribute()));
	}
	
}
