package gameOfLife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Cell {
	private boolean alive;
	private boolean nextGeneration;
	private int x;
	private int y;
	private int size;
	
	public Cell(int x, int y, int size) {
		alive = false;
		nextGeneration = false;
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public boolean getAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
		nextGeneration = alive;
		//setAlive sets nextGeneration equal to alive since they
		//should always be equal except between generations.
	}
	
	//the value of nextGeneration will be assigned to alive after it has been calculated for all cells
	public void setNextGeneration(boolean next) {
		this.nextGeneration = next;
	}
	
	public void nextGeneration() {
		alive = nextGeneration;
	}
	
	public void draw(int offx, int offy, int gridWidth, Graphics g) {
		if(alive) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.GRAY);
		}
		g.fillRect(offx + x*(size+gridWidth), offy + y*(size+gridWidth), size, size);
	}
}
