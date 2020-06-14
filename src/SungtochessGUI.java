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
	int Gold = 10;
	int StorageCount = 0;
	String Mode = "battle"; // default mode
	int[][] StatTable = new int[10][3];
	JButton[] shop;
	JButton[] storage;
	JButton[] field;
	JLabel goldNum;
	JLabel ModeText;
	int[] tempShopArr;
	int[] tempStorageArr;
	int[] tempFieldArr;

	int TaskPerformanceResult = 0;
	int WillPowerResult = 0;
	boolean fusionSynergy = false;
//	LanguageSynergy, index 0
//	NatureSynergy, index 1
//	ArtSynergy, index 2
//	EngineeringSynergy, index 3
//	SoftwareSynergy, index 4
//	DoctorSynergy, index 5
	int[] SynergyTable = new int[6];

	void setStat(int[][] statTableInput) {
		for(int loopBool = 0; loopBool < 6; loopBool++) {
			SynergyTable[loopBool] = 0;
		}
		// Task Performance , Will Power is fixed

		// 율전 혹은 명륜 0 1
		//[n][0] == 0 율전 [n][0] == 1 명륜

		// 융합 인재 시너지 나중에 판단
		// 문과대학 시너지 0 // 자연과학 시너지 1 // 예술대학 시너지 2 // 공과대학 시너지 3 // 소프트웨어 대학 시너지 4 // 의과 대학 시너지 5
		// Synergy Table
		statTableInput[0][0] = 0;
		statTableInput[0][1] = 1;

		statTableInput[1][0] = 1;
		statTableInput[1][1] = 2;
	}

	void CheckSynergy() {
		//reset SynergyTable for re-check
		for(int loopBool = 0; loopBool < 6; loopBool++) {
			SynergyTable[loopBool] = 0;
		}

		int card1 = tempFieldArr[0];
		int card1Region = -1;
		int card1College = -1;
		int card2 = tempFieldArr[1];
		int card2Region = -2;
		int card2College = -2;
		int card3 = tempFieldArr[2];
		int card3Region = -3;
		int card3College = -3;

		if(card1!=-1) {
			card1Region = StatTable[card1][0];
			card1College = StatTable[card1][1];
		}
		if(card2!=-1) {
			card2Region = StatTable[card2][0];
			card2College = StatTable[card2][1];
		}
		if(card3!=-1) {
			card3Region = StatTable[card3][0];
			card3College = StatTable[card3][1];
		}

		// fusion check
		if(card1Region != -1 && card2Region != -2 && (card1Region!=card2Region)) {
			fusionSynergy = true;
		} else if(card1Region != -1 && card3Region != -3 && (card1Region!=card3Region)) {
			fusionSynergy = true;
		} else if(card2Region != -2 && card3Region != -3 && (card2Region!=card3Region)) {
			fusionSynergy = true;
		}

		if(card1College == card2College && card2College == card3College) {
			SynergyTable[card2College] = 2; // Super Synergy
		} else if(card1College == card2College) {
			SynergyTable[card2College] = 1; // normal Synergy
		} else if(card1College == card3College) {
			SynergyTable[card1College] = 1; // normal Synergy
		} else if(card2College == card3College) {
			SynergyTable[card2College] = 1; // normal Synergy
		}
		// specific synergy
		if(card1College == 4 || card2College == 4 || card3College == 4) {
			SynergyTable[4] = 1;
		}
		if(card1College == 5 || card2College == 5 || card3College == 5) {
			SynergyTable[5] = 1;
		}
	}

	Frame(int[] ShopArr, int[] StorageArr, int[] FieldArr) {
		tempShopArr = ShopArr;
		tempStorageArr = StorageArr;
		tempFieldArr = FieldArr;
		setStat(StatTable);

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

		ModeText = new JLabel(Mode);
		ModeText.setBounds(460,50,200,20);
		add(ModeText);

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

		JButton bb = new JButton("battle mode");
		bb.addActionListener(new battleButtonListener());
		bb.setBounds(0,600,150,50);
		add(bb);

		//shop image
		shop = new JButton[5];
		ImageIcon image = new ImageIcon("src/image/image.png");
		for(int i=0; i<5; i++)
		{
			// img 의 이름은 곧 카드의 번호
			shop[i] = new JButton(new ImageIcon("src/image/"+Integer.toString(tempShopArr[i]) + ".png"));
			shop[i].addActionListener(new ShopButtonListener());
			shop[i].setName(Integer.toString(tempShopArr[i]) + " " + Integer.toString(i));
			shop[i].setBounds(200+150*i,530,150,100);
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
				storage[i].addActionListener(new StorageButtonListener());
				storage[i].setName(Integer.toString(i));
				storage[i].setBounds(200+75*i,400,75,50);
				add(storage[i]);
			} else if(tempStorageArr[i] == -1) {
				storage[i] = new JButton(new ImageIcon("src/image/smallempty.png"));//Temporarily fill up to 0~4
				storage[i].addActionListener(new StorageButtonListener());
				storage[i].setName(Integer.toString(i));
				storage[i].setBounds(200+75*i,400,75,50);
				add(storage[i]);
			}
		}

		//field image
		field = new JButton[3];
		ImageIcon tmp = new ImageIcon("src/image/field.png");

		for(int i=0; i<3; i++)
		{
			if(tempFieldArr[i] == -1) {
				field[i] = new JButton(new ImageIcon("src/image/empty.png"));
				field[i].setName(Integer.toString(i));
				field[i].addActionListener(new FieldButtonListener());
				field[i].setBounds(400+150*i,175,150,100);
				add(field[i]);
			} else if(tempFieldArr[i] != -1) {
				field[i] = new JButton(new ImageIcon("src/image/"+Integer.toString(tempStorageArr[i]) + ".png"));
				field[i].setName(Integer.toString(i));
				field[i].addActionListener(new FieldButtonListener());
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
		imagelabel.setBounds(0,0,1000,700);
		add(imagelabel);

		setSize(1000, 700);
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
				} else if(tempStorageArr[loop] == -1) {
					ImageIcon icon = new ImageIcon("src/image/smallempty.png");
					Image temp = icon.getImage();
					Image changedTemp = temp.getScaledInstance(storage[loop].getWidth(),storage[loop].getHeight(),Image.SCALE_SMOOTH);
					ImageIcon newIcon = new ImageIcon(changedTemp);
					storage[loop].setIcon(newIcon);
				}
			}
		}
		public void updateGold() {
			goldNum.setText(Integer.toString(Gold));
		}
		public void updateMode() {
			ModeText.setText(Mode);
		}
		public void updateField() {
			for(int loop = 0; loop<3; loop++) {
				if(tempFieldArr[loop] != -1) {
					ImageIcon icon = new ImageIcon("src/image/"+Integer.toString(tempFieldArr[loop]) + ".png");
					Image temp = icon.getImage();
					Image changedTemp = temp.getScaledInstance(field[loop].getWidth(),field[loop].getHeight(),Image.SCALE_SMOOTH);
					ImageIcon newIcon = new ImageIcon(changedTemp);
					field[loop].setIcon(newIcon);
				} else if(tempFieldArr[loop] == -1) {
					ImageIcon icon = new ImageIcon("src/image/empty.png");
					Image temp = icon.getImage();
					Image changedTemp = temp.getScaledInstance(field[loop].getWidth(),field[loop].getHeight(),Image.SCALE_SMOOTH);
					ImageIcon newIcon = new ImageIcon(changedTemp);
					field[loop].setIcon(newIcon);
				}
			}
			CheckSynergy();
			Debug a = new Debug();
			a.ShowSynergy();
		}
	}


	class purchaseButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Mode = "purchase";
			Update updateCheck = new Update();
			updateCheck.updateMode();
			repaint();
		}
	}

	class battleButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Mode = "battle";
			Update updateCheck = new Update();
			updateCheck.updateMode();
			repaint();
		}
	}

	class saleButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Mode = "sale";
			Update updateCheck = new Update();
			updateCheck.updateMode();
			repaint();
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
				} else if (StorageCount == 10) {
					JOptionPane.showMessageDialog(null,"Storage is full");
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
					Gold-=3;
					Update b = new Update();
					b.updateStore(Integer.parseInt(buttonName[1]));
					b.updateStorage();
					b.updateGold();
					StorageCount+=1;
					repaint();
				}
			} else if(Mode.equals("battle")) {
				JOptionPane.showMessageDialog(null,"Your mode is BATTLE, Please check your mode");
			}
		}
	}
	class StorageButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String buttonName = ((JComponent)e.getSource()).getName();
			if(Mode.equals("battle")) {
				if(tempStorageArr[Integer.parseInt(buttonName)] == -1) {
					JOptionPane.showMessageDialog(null,"Empty Slot, you can't export empty to field");
				}
				int index = -1;
				for(int looptem = 0; looptem<3; looptem++){
					if(tempFieldArr[looptem]==-1) {
						index = looptem;
						break;
					}
				}
				if(index == -1) {
					JOptionPane.showMessageDialog(null,"Field is full");
				} else {
					tempFieldArr[index] = tempStorageArr[Integer.parseInt(buttonName)];
					for(int loopInit = Integer.parseInt(buttonName) ; loopInit < 9; loopInit++) {
						tempStorageArr[loopInit] = tempStorageArr[loopInit + 1];
					} tempStorageArr[tempStorageArr.length-1] = -1;
					StorageCount-=1;
					Update updateCheck = new Update();
					updateCheck.updateStorage();
					updateCheck.updateField();
					repaint();
				}

			} else if(Mode.equals("sale")) {
				if(tempStorageArr[Integer.parseInt(buttonName)] == -1) {
					JOptionPane.showMessageDialog(null,"Empty Slot, you can't sell empty");
				} else {
					for(int loopInit = Integer.parseInt(buttonName) ; loopInit < 9; loopInit++) {
						tempStorageArr[loopInit] = tempStorageArr[loopInit + 1];
					} tempStorageArr[tempStorageArr.length-1] = -1;
					StorageCount-=1;
					Gold+=3;
					Update updateCheck = new Update();
					updateCheck.updateStorage();
					updateCheck.updateGold();
					repaint();
				}
			} else if(Mode.equals("purchase")) {
				JOptionPane.showMessageDialog(null,"Your mode is PURCHASE, Please check your mode");
			} else {
				JOptionPane.showMessageDialog(null,"ERRRRRRR!!!!!!!!!");
			}
		}
	}
	class FieldButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			String buttonName = ((JComponent)e.getSource()).getName();
			if(tempFieldArr[Integer.parseInt(buttonName)] == -1) {
				JOptionPane.showMessageDialog(null,"Empty Slot, you can't move back empty slot");
			}
			if(StorageCount==10) {
				JOptionPane.showMessageDialog(null,"You can't move your chess to storage because your storage is full\n please sell your chess in your storage to return your chess");
			}else {
				int index = -1;
				for(int loop = 0; loop < 10; loop++) {
					if(tempStorageArr[loop] == -1) {
						index = loop;
						break;
					}
				}
				if(index == -1) {
					JOptionPane.showMessageDialog(null,"ERRRRRRRRRRRR!!!!!!!!!");
				} else {
					tempStorageArr[index] = tempFieldArr[Integer.parseInt(buttonName)];
					for(int loopInit = Integer.parseInt(buttonName) ; loopInit < 2; loopInit++) {
						tempFieldArr[loopInit] = tempFieldArr[loopInit + 1];
					} tempFieldArr[tempFieldArr.length-1] = -1;
					StorageCount +=1;
					Update updatecheck = new Update();
					updatecheck.updateStorage();
					updatecheck.updateField();
					repaint();
				}
			}
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
		public void ShowSynergy() {
			System.out.println(fusionSynergy);
			for(int loop3 = 0; loop3<6; loop3++) {
				System.out.println(SynergyTable[loop3]);
			}

		}
	}
}
