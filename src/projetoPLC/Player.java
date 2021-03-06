package projetoPLC;

import java.util.ArrayList;
import java.util.Queue;

import processing.core.PApplet;
import processing.core.PVector;

public class Player {
	float x = 0; 
	float y = 0;
	int length = 2;
	float xSpeed = 1;
	float ySpeed = 0;
	int canvas_size;
	int gridScale = 15;
	ArrayList<PVector> tail = new ArrayList<>();
	Queue<PVector> foodQ;
	
	PApplet p;
	
	Player (PApplet parent, int size, int scale, Queue<PVector> q) {
		p = parent;
		this.canvas_size = size;
		gridScale = scale;
		foodQ = q;
	}	
	
	void display () {
		p.fill(30, 255, 255);
		for (PVector v : tail) {
			p.rect(v.x, v.y, gridScale, gridScale);
		}
		p.fill(25, 255, 255);
		p.rect(x, y, gridScale, gridScale);
	}
	
	void move () {
		if(length > 0) {
			if(length == tail.size() && !tail.isEmpty()) {
				tail.remove(0);
			}
			tail.add(new PVector(x, y));
		}
		
		x = x + xSpeed*gridScale;
		y = y + ySpeed*gridScale;
				
		int halfScreen = canvas_size/2;
		x = x > halfScreen || x < -halfScreen ? (-1)*x : x;
		y = y > halfScreen || y < -halfScreen ? (-1)*y : y;
	}
	
	public boolean eatFood() {
		for (PVector v : foodQ) {
			if(PApplet.dist(x, y, v.x, v.y) < 1) {
				this.length++;
				foodQ.remove(v);
				return true;
			}			
		}
		return false;
	}
	
	public void changeDir(float x, float y) {
		if(xSpeed != x && ySpeed != y) {
			this.xSpeed = x;
			this.ySpeed = y;	
		}
	}
	
	public boolean gameOver() {
		for(PVector v : tail) {
			if(PApplet.dist(x, y, v.x, v.y) < 1) {
				return true;
			}
		}
		return false;
	}
}
