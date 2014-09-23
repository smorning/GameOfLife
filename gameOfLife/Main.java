package gameOfLife;

import java.awt.Color;

import javax.swing.*;

public class Main {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int OFFX = 50;
	private static final int OFFY = 50;
	static JFrame display;
	
	public static void main(String[] args) {
		initDisplay();
		GameOfLife gameoflife = new GameOfLife(100, 70);
		display.add(gameoflife);
		gameoflife.setBounds(OFFX, OFFY, 800, 600);
		gameoflife.setVisible(true);
	}
	
	private static void initDisplay() {
		display = new JFrame();
		display.setTitle("Game of Life");
		display.setBackground(Color.WHITE);
		display.setSize(WIDTH, HEIGHT);
		display.getContentPane().setLayout(null);
		display.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		display.setVisible(true);
	}

}
