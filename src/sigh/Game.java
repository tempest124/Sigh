package sigh;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private Thread thread;
	private Image board;
	private Image bTott;
	private Image wTzarra;
	private Image bTzarra;
	private Image wTott;
	private Image bTzaar;
	private Image wTzaar;
	private Image boards;
	private boolean n;
	public boolean win;
	public float xRecorder;
	public float yRecorder;
	public int nm = 5;	
	public int mn = 10;
	int awer = 0;
	private boolean isRunning;
	private boolean t = false;
	private boolean k;
	private boolean visibler = false;
	private Piece dragPiece;
	private ArrayList<Piece> pieces;
	private ArrayList<Point> places;
	private ArrayList<Piece> y;
	private ArrayList<Piece> g;
	private ArrayList<Piece> b;
	private ArrayList<Piece> c;
	private ArrayList<Piece> f;
	private ArrayList<Piece> l;
	private Array[][] Point;

	public Game() {
		frame = new JFrame("Unicycle Posse");
		frame.setSize(900, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Input.setup(this);
		this.setLayout(null);
		this.setFocusable(true);
		this.setVisible(true);
		frame.getContentPane().add(this);

		boards = new ImageIcon("image/board.jpg").getImage();
		bTott = new ImageIcon("image/1.png").getImage();
		wTzarra = new ImageIcon("image/2.png").getImage();
		bTzarra = new ImageIcon("image/3.png").getImage();
		wTott = new ImageIcon("image/4.png").getImage();
		bTzaar = new ImageIcon("image/5.png").getImage();
		wTzaar = new ImageIcon("image/6.png").getImage();
		pieces = new ArrayList<Piece>();
		places = new ArrayList<Point>();
		y = new ArrayList<Piece>();
		g = new ArrayList<Piece>();
		b = new ArrayList<Piece>();
		c = new ArrayList<Piece>();
		f = new ArrayList<Piece>();
		l = new ArrayList<Piece>();
		for (int l = 1; l <= 15; l++) {
			Piece h = new Piece(wTott, 60, new Vector(50, 800), true, 1);
			pieces.add(h);
			g.add(h);
			Piece j = new Piece(bTott, 60, new Vector(600, 800), false, 1);
			pieces.add(j);
			y.add(j);
		}
		for (int k = 1; k <= 9; k++) {
			Piece h = new Piece(wTzarra, 60, new Vector(125, 800), true, 1);
			pieces.add(h);
			b.add(h);
			Piece j = new Piece(bTzarra, 60, new Vector(675, 800), false, 1);
			pieces.add(j);
			c.add(j);
		}
		for (int o = 1; o <= 6; o++) {
			Piece h = new Piece(wTzaar, 60, new Vector(200, 800), true, 1);
			pieces.add(h);
			f.add(h);
			Piece j = new Piece(bTzaar, 60, new Vector(750, 800), false, 1);
			pieces.add(j);
			l.add(j);
		}
		// fill the piece array ^

		frame.setUndecorated(true);
		JButton quitButton = new JButton("Quit");
		quitButton.setSize(frame.getWidth() / 15, frame.getHeight() / 50);
		quitButton.setLocation((int) (frame.getWidth() / 2 - quitButton.getWidth() / 2), 5);
		quitButton.addActionListener((e) -> {
			System.exit(0);
		});
		this.add(quitButton);

		JButton PASS = new JButton("Pass");
		PASS.setSize(frame.getWidth() / 8, frame.getHeight() / 16);
		PASS.setLocation((int) (700), 50);
		PASS.addActionListener((e) -> {
			nm+=5;
		});
		this.add(PASS);

		setupBoard();
		frame.setVisible(true);
	}

	public void start() {
		this.isRunning = true;
		thread = new Thread(this, "Updater");
		thread.start();
	}

	public void update() {
		
		for (int i = 0; i < pieces.size(); i++) {
			 mn = 5;
			if (Input.getMouseDown(Input.LEFT_MOUSE_BUTTON)) {
				if (pieces.get(i).containsPoint(Input.mousePosition.x, Input.mousePosition.y)) {
					mn+=5;
					System.out.println(nm);	
					// if (!wow()) {
					if (pieces.get(i).q == wTott || pieces.get(i).q == wTzarra || pieces.get(i).q == wTzaar) {

						xRecorder = pieces.get(i).position.x;
						yRecorder = pieces.get(i).position.y;
						t = true;
						dragPiece = pieces.get(i);
						
						// }
					} else {
						if (pieces.get(i).q == bTott || pieces.get(i).q == bTzarra || pieces.get(i).q == bTzaar) {

							xRecorder = pieces.get(i).position.x;
							yRecorder = pieces.get(i).position.y;
							t = true;
							dragPiece = pieces.get(i);
						}
					}
				}
			}

			// if mouse down
			// check if the mouse pos is on a tile piece
			// if true set a "dragging" variable to true
		}

		if (t) {
			dragPiece.position = new Vector(Input.mousePosition.x - dragPiece.size / 2,
					Input.mousePosition.y - dragPiece.size / 2);
		}

		if (t && Input.getMouseUp(Input.LEFT_MOUSE_BUTTON)) {
			n = true;
			for (int i = 0; i < places.size(); i++) {
				if (dragPiece.containsPoint(places.get(i).x, places.get(i).y)) {
					dragPiece.position.x = places.get(i).x - 30;
					dragPiece.position.y = places.get(i).y - 30;
					
					if(wow(5)!=wow(0))
					{
						if (second(dragPiece, places.get(i))) {
							n = false;
						}
					}
					else{
					if (sureee(dragPiece, places.get(i))) {
						n = false;
					}
					}
				}

			}
			if (n) {
				
				dragPiece.position.x = xRecorder;
				dragPiece.position.y = yRecorder;
				
			}
			else
			{
				nm+=5;
			}
			mn+=mn;
			dragPiece = null;
			t = false;
			if (check()) {
				if (g.isEmpty() || b.isEmpty() || f.isEmpty()) {
					win = false;
				} else {
					win = true;
				}
				
			}
		
		}

		// if dragging is true
		// draw the piece at the current mouse position

		// if dragging is true and mouse is released
		// set dragging to false
	}

	public boolean sureee(Piece r, Point n) {
		System.out.println("yo");
		if (placing()) {
			return true;
		}

		r.position = new Vector(0000, 0000);
		if (getPiece(n) == null) {
			return false;
		} else if (r.stack < getPiece(n).stack) {
			return false;
		} else if (r.color == getPiece(n).color) {
			return false;
		} else if (r.color != getPiece(n).color && r.stack >= getPiece(n).stack) {
			if (getPiece(n).q == wTott) {
				g.remove(0);
			}
			if (getPiece(n).q == bTott) {
				y.remove(0);
			}
			if (getPiece(n).q == wTzarra) {
				b.remove(0);
			}
			if (getPiece(n).q == bTzarra) {
				c.remove(0);
			}
			if (getPiece(n).q == wTzaar) {
				f.remove(0);
			}
			if (getPiece(n).q == bTzaar) {
				l.remove(0);
			}

			getPiece(n).position = new Vector(10000, 10000);
			r.position.x = n.x - 30;
			r.position.y = n.y - 30;
			return true;
		} else {
			return true;
		}
	}

	public boolean wow(int cv) {
		if(placing())
		{
			if ((nm / 5) % 2 == 0) {
				return true;
			} else {
				return false;
			}
		}
		while (!check()) {
			System.out.println(nm);
			if ((nm / 10) % 2 == 0) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	public boolean second(Piece r, Point n) {
		if (sureee(r, n)) {
			return true;
		} else if (r.color == getPiece(n).color && r.stack + getPiece(n).stack <= 3) {
			if (getPiece(n).q == wTott) {
				g.remove(0);
			}
			if (getPiece(n).q == bTott) {
				y.remove(0);
			}
			if (getPiece(n).q == wTzarra) {
				b.remove(0);
			}
			if (getPiece(n).q == bTzarra) {
				c.remove(0);
			}
			if (getPiece(n).q == wTzaar) {
				f.remove(0);
			}
			if (getPiece(n).q == bTzaar) {
				l.remove(0);
			}
			if (getPiece(n) != null) {
				r.stack += getPiece(n).stack;
				getPiece(n).position = new Vector(10000, 10000);
			}
			r.position.x = n.x - 30;
			r.position.y = n.y - 30;
			return true;
		} else if (visibler) {

			visibler = false;
			return true;
		}

		else {
			return false;
		}

	}

	public boolean placing() {
		k = false;
		for (int i = 0; i < pieces.size(); i++) {

			if ((pieces.get(i).containsPoint(80, 830) || pieces.get(i).containsPoint(155, 830)
					|| pieces.get(i).containsPoint(230, 830) || pieces.get(i).containsPoint(630, 830)
					|| pieces.get(i).containsPoint(705, 830) || pieces.get(i).containsPoint(780, 830))
					|| ((yRecorder == 800 && (xRecorder == 50 || xRecorder == 125 || xRecorder == 200
							|| xRecorder == 600 || xRecorder == 675 || xRecorder == 750)))) {

				k = true;
			}

		}
		System.out.println(k);
		return k;
	}

	public boolean check() {
		if (g.isEmpty() || y.isEmpty() || b.isEmpty() || c.isEmpty() || f.isEmpty() || l.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public int getWidth() {
		return frame.getWidth();
	}

	public int getHeight() {
		return frame.getHeight();
	}

	public Piece getPiece(Point jk) {
		for (int i = 0; i < pieces.size(); i++) {
			if (pieces.get(i).position.x == jk.x - 30 && pieces.get(i).position.y == jk.y - 30) {
				return pieces.get(i);
			}
		}
		return null;
	}

	@Override
	public void run() {
		try {
			while (this.isRunning) {
				this.update();
				Input.update();
				frame.repaint();
				Thread.sleep(1000 / 60);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(boards, 0, 0, this.getWidth(), this.getHeight(), null);
		for (int i = 0; i < pieces.size(); i++) {
			pieces.get(i).render(g);
		}
		g.setFont(new Font("Times New Roman", Font.BOLD, 50));
		if (!wow(0)) {
			

			g.setColor(Color.WHITE);
			g.drawString("White's turn", 25, 100);
		} else {
			g.setColor(Color.BLACK);
			g.drawString("Black's turn", 25, 100);

		}

		if (check()) {
			g.setColor(
					new Color(((int) (Math.random() * 255)), (int) (Math.random() * 255), (int) (Math.random() * 255)));
			g.setFont(new Font("Times New Roman", Font.BOLD, 100));
			if (win) {
				g.drawString("White team wins!!!", 50, 450);
			} else {
				g.drawString("Black team wins!!!", 50, 450);

			}
			awer++;
			System.out.println(awer);
			if (awer > 360) {
				System.exit(0);
			}
		}
	}

	private void setupBoard() {
		for (int i = 0; i < 5; i++) {
			for (int o = 0; o < 5; o++) {
				if (o != 2 || i != 2) {
					Point piece = new Point(o * 180 + 54 + 35, i * 93 + 234 + 31);
					places.add(piece);
				}
			}
		}
		for (int i = 0; i < 6; i++) {
			for (int o = 0; o < 4; o++) {

				Point piece = new Point(o * 180 + 144 + 35, i * 93 + 187 + 31);
				places.add(piece);

			}
		}
		for (int i = 1; i <= 3; i++) {
			for (int o = 0; o < i; o++) {

				Point piece = new Point(o * 180 + 505 + 35 - 90 * i, (i - 1) * 46 + 48 + 31);
				places.add(piece);
			}
			for (int o = 3; o >= i; o--) {
				Point piece = new Point(o * 180 + 145 - 90 * i + 35, (i - 1) * 46 + 698 + 31);
				places.add(piece);
			}
		}
	}

	public static void main(String[] args) {

		new Game().start();
	}

}