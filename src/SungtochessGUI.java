package sungtochess;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ImageIcon.*; 


class SungtochessGUI {
	public static void main(String[] args) {
		Frame f = new Frame();
		}
}


class Frame extends JFrame {
	JButton[] shop;
	JButton[] storage;
	JButton[] field;
	Frame() {	
		setTitle("Sungtochess");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		//TaskPerformance text
		JLabel text1 = new JLabel("Task Performance");
		text1.setBounds(30, 400, 200, 20);
		add(text1);
		
		JLabel tp = new JLabel("0");
		tp.setBounds(70,420,200,20);
		add(tp);
		
		//Willpower text
		JLabel text2 = new JLabel("Will Power");
		text2.setBounds(45,440,200,20);
		add(text2);
		
		JLabel wp = new JLabel("0");
		wp.setBounds(70,460,200,20);
		add(wp);
		
		//Round text
		JLabel text3 = new JLabel("Round");
		text3.setBounds(460,50,200,20);
		add(text3);
		
		JLabel r = new JLabel("0");
		r.setBounds(520,50,200,20);
		add(r);
		
		//mode button
		JButton pb = new JButton("purchase mode");
		pb.setBounds(0,500,150,50);
		add(pb);
		JButton sb = new JButton("sale mode");
		sb.setBounds(0,550,150,50);
		add(sb);
		
		//shop image
		shop = new JButton[5];
		ImageIcon image = new ImageIcon("src/image.png");
		for(int i=0; i<5; i++)
		{
			shop[i] = new JButton(image);
			shop[i].setBounds(200+150*i,500,150,100);
			add(shop[i]);
		}
		
		//storage image
		storage = new JButton[10];
		ImageIcon storageimage = new ImageIcon("src/storage.png");
		
		for(int i=0; i<5; i++)
		{
			storage[i] = new JButton(storageimage);//Temporarily fill up to 0~4
		}
		
		for(int i=0; i<10; i++)
		{
			if(storage[i] == null)
			{	
				ImageIcon smallempty = new ImageIcon("src/smallempty.png");
				JLabel imagelabel4 = new JLabel(smallempty);
				imagelabel4.setBounds(200+75*i,400,75,50);
				add(imagelabel4);
			}else
			{
				storage[i].setBounds(200+75*i,400,75,50);
				add(storage[i]);
			}
		}
		
		//field image
		field = new JButton[3];
		ImageIcon tmp = new ImageIcon("src/field.png");
		
		for(int i=0; i<2; i++)
		{
			field[i] = new JButton(tmp);//Temporarily fill up to 0~1
		}
		
		for(int i=0; i<3; i++)
		{
			if(field[i]==null) {
				ImageIcon empty = new ImageIcon("src/empty.png");
				JLabel imagelabel3 = new JLabel(empty);
				imagelabel3.setBounds(400+150*i,175,150,100);
				add(imagelabel3);
			}
			else {
				field[i].setBounds(400+150*i,175,150,100);
				add(field[i]);
			}
		}
		
		
		//enemy image
		ImageIcon enemy = new ImageIcon("src/enemy.png"); 
		JLabel imagelabel2 = new JLabel(enemy);
		imagelabel2.setBounds(100,150,200,150);
		add(imagelabel2);
		
		//vs text
		JLabel text4 = new JLabel("VS");
		text4.setBounds(340,210,200,20);
		add(text4);
		
		
		//wallpaper image
		ImageIcon wallpapers = new ImageIcon("src/white.png"); 
		JLabel imagelabel = new JLabel(wallpapers);
		imagelabel.setBounds(0,0,1000,600);
		add(imagelabel);
		
		setSize(1000, 600);
		setVisible(true);
	}
}
