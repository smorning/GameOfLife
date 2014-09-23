package gameOfLife;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

public class GameOfLife extends JPanel implements MouseListener, MouseMotionListener{
	private final int CELL_SIZE = 5;
	private final int GRID_MARGIN = 1;
	private Cell[][] cells;
	private boolean paused;
	private int rows;
	private int cols;
	
	
	public GameOfLife(int cols, int rows) {
		this.rows = rows;
		this.cols = cols;
		paused = false;
		initComponent();
		initCells();
	}
	
	private void initComponent() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		setLayout(null);
		
		PauseButton pauseButton = new PauseButton();
		pauseButton.setBounds(0, rows*(CELL_SIZE + GRID_MARGIN) + 20, 100, 50);
		pauseButton.setVisible(true);
		//pauseButton.repaint();
		
		ClearButton clearButton = new ClearButton();
		clearButton.setBounds(150, rows*(CELL_SIZE + GRID_MARGIN) + 20, 100, 50);
		clearButton.setVisible(true);
		//clearButton.repaint();
		
		this.add(pauseButton);
		this.add(clearButton);
	}
	
	public void initCells() {
		cells = new Cell[cols][rows];
		for(int i = 0; i < cols; i++) {
			for(int j = 0; j < rows; j++) {
				cells[i][j] = new Cell(i, j, CELL_SIZE);
			}
		}
		//making an initial glider. Delete if using in another context.
		cells[59][22].setAlive(true);
		cells[57][21].setAlive(true);
		cells[59][21].setAlive(true);
		cells[59][20].setAlive(true);
		cells[58][22].setAlive(true);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGrid(g);
		drawCells(g);
		try {
			Thread.sleep(50); //Letting it wait a little between generations
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(!paused) {
			nextGeneration();
		}
		repaint();
	}
	
	public void drawGrid(Graphics g) {
		g.setColor(Color.BLACK);
		int gridCellSize = CELL_SIZE + GRID_MARGIN;
		for(int i = 0; i <= rows; i++) {
			g.drawLine(0, i*gridCellSize, cols*gridCellSize, i*gridCellSize);
		}
		for(int i = 0; i <= cols; i++) {
			g.drawLine(i*gridCellSize, 0, i*gridCellSize, rows*gridCellSize);
		}
	}
	
	public void drawCells(Graphics g) {
		for(int i = 0; i < cols; i++) {
			for(int j = 0; j < rows; j++) {
				cells[i][j].draw(0, 0, GRID_MARGIN, g);
			}
		}
	}
	
	public void nextGeneration() {
		int neighbors = 0;
		for(int i = 0; i < cols; i++) {
			for(int j = 0; j < rows; j++) {
				neighbors = 0;
				if(cells[(i + cols + 1) % cols][(j + rows) % rows].getAlive()) neighbors++; 
				if(cells[(i + cols - 1) % cols][(j + rows) % rows].getAlive()) neighbors++; 
				if(cells[(i + cols) % cols][(j + rows + 1) % rows].getAlive()) neighbors++; 
				if(cells[(i + cols) % cols][(j + rows - 1) % rows].getAlive()) neighbors++; 
				if(cells[(i + cols + 1) % cols][(j + rows - 1) % rows].getAlive()) neighbors++; 
				if(cells[(i + cols + 1) % cols][(j + rows + 1) % rows].getAlive()) neighbors++; 
				if(cells[(i + cols - 1) % cols][(j + rows - 1) % rows].getAlive()) neighbors++; 
				if(cells[(i + cols - 1) % cols][(j + rows + 1) % rows].getAlive()) neighbors++; 
				if(!cells[i][j].getAlive() && neighbors == 3) {
					cells[i][j].setNextGeneration(true);
				} else if(cells[i][j].getAlive() && neighbors > 3) {
					cells[i][j].setNextGeneration(false);
				} else if(cells[i][j].getAlive() && neighbors < 2) {
					cells[i][j].setNextGeneration(false);
				}
			}
		}
		//separated so the generation changes don't happen during calculation
		for(int i = 0; i < cols; i++) {
			for(int j = 0; j < rows; j++) {
				cells[i][j].nextGeneration();
			}
		}
	}
	
	
	public void clear() {
		for(int i = 0; i < cols; i++) {
			for(int j = 0; j < rows; j++) {
				cells[i][j].setAlive(false);
			}
		}
	}
	
	class ClearButton extends JButton implements ActionListener {
		
		public ClearButton() {
			super("Clear");
			this.addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			clear();
		}
	}
	
	class PauseButton extends JButton implements ActionListener {
		
		public PauseButton() {
			super("Pause");
			this.addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			if(paused) {
				paused = false;
				setText("Pause");
			} else {
				paused = true;
				setText("Unpause");
			}
		}
	}
	
	public void mouseClicked(MouseEvent mouse) {
		int xpos = mouse.getX();
		int ypos = mouse.getY();
		xpos = (xpos/(CELL_SIZE + 1));
		ypos = (ypos/(CELL_SIZE + 1));
		if (ypos >= 0 && ypos <= rows && xpos >= 0 && xpos <= cols) {
		 	if (cells[xpos][ypos].getAlive()) {
		 		cells[xpos][ypos].setAlive(false);
		 	} else {
		 		cells[xpos][ypos].setAlive(true);
		 	}
		}
	}
	
	public void mouseDragged(MouseEvent mouse) {
		int xpos = mouse.getX();
		int ypos = mouse.getY();
		xpos = (xpos/(CELL_SIZE + 1));
		ypos = (ypos/(CELL_SIZE + 1));
		if (ypos >= 0 && ypos <= rows && xpos >= 0 && xpos <= cols) {
		 	if (cells[xpos][ypos].getAlive()) {
		 		cells[xpos][ypos].setAlive(false);
		 	} else {
		 		cells[xpos][ypos].setAlive(true);
		 	}
		}
	}
	
	public void mouseMoved(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
