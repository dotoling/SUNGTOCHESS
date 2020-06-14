import java.awt.*;
import java.awt.event.*;
import java.awt.Image.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ImageIcon.*;

class Frame extends JFrame {
	JButton[] shop;
	JButton[] storage;
	JButton[] field;
	JLabel goldNum;
	int Gold = 10;
	String Mode = "purchase";
	int[] tempShopArr;
	int[] tempStorageArr;
	Frame(int[] ShopArr, int[] StorageArr) {
		tempShopArr = ShopArr;
		tempStorageArr = StorageArr;

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
		text3.setBounds(260,50,200,20);
		add(text3);

		JLabel r = new JLabel("0");
		r.setBounds(320,50,200,20);
		add(r);

		JLabel textGold = new JLabel("Gold");
		textGold.setBounds(660,50,200,20);
		add(textGold);

		goldNum = new JLabel(Integer.toString(Gold));
		goldNum.setBounds(720,50,200,20);
		add(goldNum);

		//mode button
		JButton pb = new JButton("purchase mode");
		pb.addActionListener(new purchaseButtonListener());
		pb.setBounds(0,500,150,50);
		add(pb);

		JButton sb = new JButton("sale mode");
		sb.addActionListener(new saleButtonListener());
		sb.setBounds(0,550,150,50);
		add(sb);

		//shop image
		shop = new JButton[5];
		ImageIcon image = new ImageIcon("src/image/image.png");
		for(int i=0; i<5; i++)
		{
			// img 의 이름은 곧 카드의 번호
			shop[i] = new JButton(new ImageIcon("src/image/"+Integer.toString(tempShopArr[i]) + ".png"));
			shop[i].addActionListener(new ShopButtonListener());
			shop[i].setName(Integer.toString(tempShopArr[i]) + " " + Integer.toString(i));
			shop[i].setBounds(200+150*i,500,150,100);
			add(shop[i]);
		}

		//storage image
		storage = new JButton[10];
		//ImageIcon storageimage = new ImageIcon("src/image/storage.png");

		for(int i=0; i<10; i++)
		{
			if(tempStorageArr[i] != -1) {
				// img 의 이름은 곧 카드의 번호
				storage[i] = new JButton(new ImageIcon("src/image/"+Integer.toString(tempStorageArr[i]) + ".png"));//Temporarily fill up to 0~4
				storage[i].setName(Integer.toString(i));
				storage[i].setBounds(200+75*i,400,75,50);
				add(storage[i]);
			} else if(tempStorageArr[i] == -1) {
				storage[i] = new JButton(new ImageIcon("src/image/smallempty.png"));//Temporarily fill up to 0~4
				storage[i].setName(Integer.toString(i));
				storage[i].setBounds(200+75*i,400,75,50);
				add(storage[i]);
			}
		}

		//field image
		field = new JButton[3];
		ImageIcon tmp = new ImageIcon("src/image/field.png");

		for(int i=0; i<2; i++)
		{
			field[i] = new JButton(tmp);//Temporarily fill up to 0~1
		}

		for(int i=0; i<3; i++)
		{
			if(field[i]==null) {
				ImageIcon empty = new ImageIcon("src/image/empty.png");
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
		ImageIcon enemy = new ImageIcon("src/image/enemy.png");
		JLabel imagelabel2 = new JLabel(enemy);
		imagelabel2.setBounds(100,150,200,150);
		add(imagelabel2);

		//vs text
		JLabel text4 = new JLabel("VS");
		text4.setBounds(340,210,200,20);
		add(text4);


		//wallpaper image
		ImageIcon wallpapers = new ImageIcon("src/image/white.png");
		JLabel imagelabel = new JLabel(wallpapers);
		imagelabel.setBounds(0,0,1000,600);
		add(imagelabel);

		setSize(1000, 640);
		setVisible(true);
	}

	//update
	class Update {
		public void updateStore(int index) {
			shop[index].setIcon(new ImageIcon("src/image/"+Integer.toString(tempShopArr[index]) + ".png"));
			shop[index].setName(Integer.toString(-1));
		}
		public void updateStorage() {
			for(int loop = 0; loop<10; loop++) {
				if(tempStorageArr[loop] != -1) {
					ImageIcon icon = new ImageIcon("src/image/"+Integer.toString(tempStorageArr[loop]) + ".png");
					Image temp = icon.getImage();
					Image changedTemp = temp.getScaledInstance(storage[loop].getWidth(),storage[loop].getHeight(),Image.SCALE_SMOOTH);
					ImageIcon newIcon = new ImageIcon(changedTemp);
					storage[loop].setIcon(newIcon);
				}
			}
		}
		public void updateGold() {
			Gold -=3;
			goldNum.setText(Integer.toString(Gold));
		}
	}


	class purchaseButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Mode = "purchase";
			System.out.println(Mode);
		}
	}

	class saleButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Mode = "sale";
			System.out.println(Mode);
		}
	}

	class ShopButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String[] buttonName = ((JComponent)e.getSource()).getName().split(" ");

			if(Mode.equals("sale")) {
				JOptionPane.showMessageDialog(null,"Your mode is SALE, Please check your mode");
			} else if (Mode.equals("purchase")) {
				if(buttonName[0].equals("-1")) {
					JOptionPane.showMessageDialog(null,"Already purchased item");
				} else if (Gold - 3 < 0) {
					JOptionPane.showMessageDialog(null,"Gold is not enough");
				}
				else {
					int index = -1;
					for(int loopcheck = 0; loopcheck<10; loopcheck++) {
						if(tempStorageArr[loopcheck] == -1) {
							index = loopcheck;
							break;
						}
					}
					tempStorageArr[index] = Integer.parseInt(buttonName[0]);
					tempShopArr[Integer.parseInt(buttonName[1])] = -1;
					Update b = new Update();
					b.updateStore(Integer.parseInt(buttonName[1]));
					b.updateStorage();
					b.updateGold();
					repaint();
				}
			}
		}
	}
	class StorageButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}
	class FieldButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}
	class Debug {
		public void ShowArr() {
			System.out.println("tempStorageArr");
			for(int loop1 = 0; loop1 < 10; loop1++) {
				System.out.print(tempStorageArr[loop1] + " ");
			}System.out.println();
			System.out.println("tempShopArr");
			for(int loop2 = 0; loop2 < 5; loop2++) {
				System.out.print(tempShopArr[loop2] + " ");
			}System.out.println();
		}
	}
}
