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
import java.util.Random;

class Frame extends JFrame {
	int Gold = 10;
	int StorageCount = 0;
	String Mode = "battle"; // default mode
	int[][] StatTable = new int[37][3];

	JButton[] shop;
	JButton[] storage;
	JButton[] field;
	JLabel[] SynergyArea;
	JLabel goldNum;
	JLabel imagelabel2;
	JLabel ModeText;
	JLabel tp;
	JLabel r;

	int[] tempShopArr;
	int[] tempStorageArr;
	int[] tempFieldArr;

	int TaskPerformanceResult = 0;
	boolean fusionSynergy = false;
	int[] SynergyTable = new int[7];
	int[] RoundType = {0,1,2,3,4,5,0,1,2,3,4,5,0,1,2,3,4,5,0,1,2,3,4,5}; // 발표 - 에세이 - 리포트 - 예체능 - 수학 - 코딩
	int[] RoundTarget = {5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100,105,110,115,120};
	int round = 0;

	void setStat(int[][] statTableInput) {
		for(int loopBool = 0; loopBool < 7; loopBool++) {
			SynergyTable[loopBool] = 0;
		}
		// Task Performance , Will Power is fixed

		// 율전 혹은 명륜 0 1
		//[n][0] == 0 율전 [n][0] == 1 명륜

		// 융합 인재 시너지 나중에 판단
		// 문과대학 시너지 0 // 사회과학 시너지 1 // 자연과학 시너지 2 // 예술대학 시너지 3 // 공과대학 시너지 4 // 소프트웨어 대학 시너지 5 // 의과 대학 시너지 6
		// Synergy Table
		statTableInput[0][0] = 0;
		statTableInput[0][1] = 2;

		statTableInput[1][0] = 0;
		statTableInput[1][1] = 2;

		statTableInput[2][0] = 0;
		statTableInput[2][1] = 2;

		statTableInput[3][0] = 0;
		statTableInput[3][1] = 2;

		statTableInput[4][0] = 1;
		statTableInput[4][1] = 0;

		statTableInput[5][0] = 1;
		statTableInput[5][1] = 0;

		statTableInput[6][0] = 1;
		statTableInput[6][1] = 0;

		statTableInput[7][0] = 1;
		statTableInput[7][1] = 0;

		statTableInput[8][0] = 1;
		statTableInput[8][1] = 0;

		statTableInput[9][0] = 1;
		statTableInput[9][1] = 0;

		statTableInput[10][0] = 1;
		statTableInput[10][1] = 0;

		statTableInput[11][0] = 1;
		statTableInput[11][1] = 0;

		statTableInput[12][0] = 1;
		statTableInput[12][1] = 0;

		statTableInput[13][0] = 1;
		statTableInput[13][1] = 0;

		statTableInput[14][0] = 0;
		statTableInput[14][1] = 4;

		statTableInput[15][0] = 0;
		statTableInput[15][1] = 4;

		statTableInput[16][0] = 0;
		statTableInput[16][1] = 4;

		statTableInput[17][0] = 0;
		statTableInput[17][1] = 4;

		statTableInput[18][0] = 0;
		statTableInput[18][1] = 4;

		statTableInput[19][0] = 0;
		statTableInput[19][1] = 4;

		statTableInput[20][0] = 0;
		statTableInput[20][1] = 4;

		statTableInput[21][0] = 1;
		statTableInput[21][1] = 3;

		statTableInput[22][0] = 1;
		statTableInput[22][1] = 3;

		statTableInput[23][0] = 1;
		statTableInput[23][1] = 3;

		statTableInput[24][0] = 1;
		statTableInput[24][1] = 3;

		statTableInput[25][0] = 1;
		statTableInput[25][1] = 3;

		statTableInput[26][0] = 1;
		statTableInput[26][1] = 3;

		statTableInput[27][0] = 1;
		statTableInput[27][1] = 1;

		statTableInput[28][0] = 1;
		statTableInput[28][1] = 1;

		statTableInput[29][0] = 1;
		statTableInput[29][1] = 1;

		statTableInput[30][0] = 1;
		statTableInput[30][1] = 1;

		statTableInput[31][0] = 1;
		statTableInput[31][1] = 1;

		statTableInput[32][0] = 1;
		statTableInput[32][1] = 1;

		statTableInput[33][0] = 1;
		statTableInput[33][1] = 1;

		statTableInput[34][0] = 1;
		statTableInput[34][1] = 1;

		statTableInput[35][0] = 0;
		statTableInput[35][1] = 6;

		statTableInput[36][0] = 0;
		statTableInput[36][1] = 5;
	}

	void refreshShop() {
		int kindsOfCard = 37; // 카드 종류의 갯수
		Random generator = new Random();
		for(int loop2 = 0; loop2 < 5; loop2++) {
			tempShopArr[loop2] = generator.nextInt(kindsOfCard);
		}
	}

	void CheckSynergy() {
		//reset SynergyTable for re-check
		for(int loopBool = 0; loopBool < 7; loopBool++) {
			SynergyTable[loopBool] = 0;
		} fusionSynergy = false;

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
		if(card1College == 5 || card2College == 5 || card3College == 5) {
			SynergyTable[5] = 1;
		}
		if(card1College == 6 || card2College == 6 || card3College == 6) {
			SynergyTable[6] = 1;
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
		text1.setFont(text1.getFont().deriveFont(20.0f));
		text1.setBounds(135, 600, 200, 20);
		add(text1);

		tp = new JLabel(Integer.toString(TaskPerformanceResult));
		tp.setFont(tp.getFont().deriveFont(20.0f));
		tp.setBounds(210,640,200,20);
		add(tp);

		//Round text
		JLabel text3 = new JLabel("Round");
		text3.setFont(text3.getFont().deriveFont(20.0f));
		text3.setBounds(260,50,200,20);
		add(text3);

		ModeText = new JLabel(Mode);
		ModeText.setFont(ModeText.getFont().deriveFont(20.0f));
		ModeText.setBounds(930,50,200,20);
		add(ModeText);

		r = new JLabel(Integer.toString(round+1));
		r.setBounds(340,50,200,20);
		add(r);

		JLabel textGold = new JLabel("Gold");
		textGold.setFont(textGold.getFont().deriveFont(20.0f));
		textGold.setBounds(1500,50,200,20);
		add(textGold);

		goldNum = new JLabel(Integer.toString(Gold));
		goldNum.setBounds(1580,50,200,20);
		add(goldNum);

		//mode button
		JButton pb = new JButton("purchase mode");
		pb.addActionListener(new purchaseButtonListener());
		pb.setBounds(120,750,200,50);
		add(pb);

		JButton sb = new JButton("sale mode");
		sb.addActionListener(new saleButtonListener());
		sb.setBounds(120,800,200,50);
		add(sb);

		JButton bb = new JButton("battle mode");
		bb.addActionListener(new battleButtonListener());
		bb.setBounds(120,850,200,50);
		add(bb);

		//shop image
		shop = new JButton[5];
		ImageIcon image = new ImageIcon("src/image/image.png");
		for(int i=0; i<5; i++)
		{
			// img 의 이름은 곧 카드의 번호
			ImageIcon icon = new ImageIcon("src/image/"+Integer.toString(tempShopArr[i]) + ".PNG");
			Image temp = icon.getImage();
			Image changedTemp = temp.getScaledInstance(210,140,Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(changedTemp);
			shop[i] = new JButton(newIcon);
			shop[i].addActionListener(new ShopButtonListener());
			shop[i].setName(Integer.toString(tempShopArr[i]) + " " + Integer.toString(i));
			shop[i].setBounds(434+210*i,780,210,140);
			add(shop[i]);
		}

		//storage image
		storage = new JButton[10];
		//ImageIcon storageimage = new ImageIcon("src/image/storage.png");
		int checkLine = 0;
		for(int i=0; i<10; i++)
		{
			if(i == 5) {
				checkLine=1;
			}

			if(tempStorageArr[i] != -1) {
				// img 의 이름은 곧 카드의 번호
				ImageIcon icon = new ImageIcon("src/image/"+Integer.toString(tempStorageArr[i]) + ".PNG");
				Image temp = icon.getImage();
				Image changedTemp = temp.getScaledInstance(210,140,Image.SCALE_SMOOTH);
				ImageIcon newIcon = new ImageIcon(changedTemp);
				storage[i] = new JButton(newIcon);//Temporarily fill up to 0~4
				storage[i].addActionListener(new StorageButtonListener());
				storage[i].setName(Integer.toString(i));
				storage[i].setBounds(435+210*i,400+140*checkLine,210,140);
				add(storage[i]);
			} else if(tempStorageArr[i] == -1) {
				ImageIcon icon = new ImageIcon("src/image/empty.png");
				Image temp = icon.getImage();
				Image changedTemp = temp.getScaledInstance(210,140,Image.SCALE_SMOOTH);
				ImageIcon newIcon = new ImageIcon(changedTemp);
				storage[i] = new JButton(newIcon);//Temporarily fill up to 0~4
				storage[i].addActionListener(new StorageButtonListener());
				storage[i].setName(Integer.toString(i));
				storage[i].setBounds(435+210*(i-5*checkLine),400+140*checkLine,210,140);
				add(storage[i]);
			}
		}

		//field image
		field = new JButton[3];
		for(int i=0; i<3; i++)
		{
			if(tempFieldArr[i] == -1) {
				ImageIcon icon = new ImageIcon("src/image/empty.PNG");
				Image temp = icon.getImage();
				Image changedTemp = temp.getScaledInstance(150,100,Image.SCALE_SMOOTH);
				ImageIcon newIcon = new ImageIcon(changedTemp);
				field[i] = new JButton(newIcon);
				field[i].setName(Integer.toString(i));
				field[i].addActionListener(new FieldButtonListener());
				field[i].setBounds(950+150*i,175,150,100);
				add(field[i]);
			} else if(tempFieldArr[i] != -1) {
				ImageIcon icon = new ImageIcon("src/image/"+Integer.toString(tempFieldArr[i]) + ".PNG");
				Image temp = icon.getImage();
				Image changedTemp = temp.getScaledInstance(150,100,Image.SCALE_SMOOTH);
				ImageIcon newIcon = new ImageIcon(changedTemp);
				field[i] = new JButton(new ImageIcon("src/image/"+Integer.toString(tempFieldArr[i]) + ".PNG"));
				field[i].setName(Integer.toString(i));
				field[i].addActionListener(new FieldButtonListener());
				field[i].setBounds(950+150*i,175,150,100);
				add(field[i]);
			}
		}

		SynergyArea = new JLabel[8];

		JLabel SynergyNoti = new JLabel(" 0 : 시너지 없음 // 1 : 1단계 시너지 // 2 : 2단계 시너지");
		SynergyNoti.setBounds(1550,190,300,30);
		SynergyArea[0] = new JLabel("융합인재 시너지 : " + (fusionSynergy ? 1:0));
		SynergyArea[0].setFont(SynergyArea[0].getFont().deriveFont(20.0f));
		SynergyArea[0].setBounds(1550,250,300,30);
		SynergyArea[1] = new JLabel("문과대학 시너지 : " + SynergyTable[0]);
		SynergyArea[1].setFont(SynergyArea[1].getFont().deriveFont(20.0f));
		SynergyArea[1].setBounds(1550,280,300,30);
		SynergyArea[2] = new JLabel("사회과학 대학 시너지 : " + SynergyTable[1]);
		SynergyArea[2].setFont(SynergyArea[2].getFont().deriveFont(20.0f));
		SynergyArea[2].setBounds(1550,310,300,30);
		SynergyArea[3] = new JLabel("자연과학 대학 시너지 : " + SynergyTable[2]);
		SynergyArea[3].setFont(SynergyArea[3].getFont().deriveFont(20.0f));
		SynergyArea[3].setBounds(1550,340,300,30);
		SynergyArea[4] = new JLabel("예술대학 시너지 : " + SynergyTable[3]);
		SynergyArea[4].setFont(SynergyArea[4].getFont().deriveFont(20.0f));
		SynergyArea[4].setBounds(1550,370,300,30);
		SynergyArea[5] = new JLabel("공과대학 시너지 : " + SynergyTable[4]);
		SynergyArea[5].setFont(SynergyArea[5].getFont().deriveFont(20.0f));
		SynergyArea[5].setBounds(1550,400,300,30);
		SynergyArea[6] = new JLabel("소프트웨어 대학 시너지 : " + SynergyTable[5]);
		SynergyArea[6].setFont(SynergyArea[6].getFont().deriveFont(20.0f));
		SynergyArea[6].setBounds(1550,430,300,30);
		SynergyArea[7] = new JLabel("의과대학 시너지 : " + SynergyTable[6]);
		SynergyArea[7].setFont(SynergyArea[7].getFont().deriveFont(20.0f));
		SynergyArea[7].setBounds(1550,460,300,30);

		JLabel curRoundSynergy = new JLabel("현재 적용가능한 시너지는" + "")

		add(SynergyNoti);
		add(SynergyArea[0]);
		add(SynergyArea[1]);
		add(SynergyArea[2]);
		add(SynergyArea[3]);
		add(SynergyArea[4]);
		add(SynergyArea[5]);
		add(SynergyArea[6]);
		add(SynergyArea[7]);

		//enemy image
		ImageIcon enemy = new ImageIcon("src/image/enemy/" + (round+1) + ".PNG");
		Image temp = enemy.getImage();
		Image changedTemp = temp.getScaledInstance(300,200,Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(changedTemp);
		imagelabel2 = new JLabel(newIcon);
		imagelabel2.setBounds(460,120,300,200);
		add(imagelabel2);

		//vs text
		JLabel text4 = new JLabel("VS");
		text4.setBounds(850,210,200,20);
		add(text4);

		// submit button
		JButton submit = new JButton("제출하기!");
		submit.setBounds(1550,830,150,50);
		submit.addActionListener(new submitButtonLister());
		add(submit);

		JButton howto = new JButton("게임방법");
		howto.addActionListener(new howtoButtonListner());
		howto.setBounds(1700,830,150,50);
		add(howto);

		//wallpaper image
		ImageIcon wallpapers = new ImageIcon("src/image/white.png");
		JLabel imagelabel = new JLabel(wallpapers);
		imagelabel.setBounds(0,0,1000,700);
		add(imagelabel);
		setSize(1920, 1000);
		setVisible(true);
	}

	//update
	class Update {
		public void updateStore(int index) {
			ImageIcon icon = new ImageIcon("src/image/"+Integer.toString(tempShopArr[index]) + ".PNG");
			Image temp = icon.getImage();
			Image changedTemp = temp.getScaledInstance(210,140,Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(changedTemp);
			shop[index].setIcon(newIcon);
			shop[index].setName(Integer.toString(-1));
		}
		public void updateStorage() {
			for(int loop = 0; loop<10; loop++) {
				if(tempStorageArr[loop] != -1) {
					ImageIcon icon = new ImageIcon("src/image/"+Integer.toString(tempStorageArr[loop]) + ".PNG");
					Image temp = icon.getImage();
					Image changedTemp = temp.getScaledInstance(storage[loop].getWidth(),storage[loop].getHeight(),Image.SCALE_SMOOTH);
					ImageIcon newIcon = new ImageIcon(changedTemp);
					storage[loop].setIcon(newIcon);
				} else if(tempStorageArr[loop] == -1) {
					ImageIcon icon = new ImageIcon("src/image/empty.PNG");
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
			int fieldCount = 0;
			updateSynergy();
			for(int loop = 0; loop<3; loop++) {
				if(tempFieldArr[loop] != -1) {
					ImageIcon icon = new ImageIcon("src/image/"+Integer.toString(tempFieldArr[loop]) + ".PNG");
					Image temp = icon.getImage();
					Image changedTemp = temp.getScaledInstance(field[loop].getWidth(),field[loop].getHeight(),Image.SCALE_SMOOTH);
					ImageIcon newIcon = new ImageIcon(changedTemp);
					field[loop].setIcon(newIcon);
					fieldCount+=1;
				} else if(tempFieldArr[loop] == -1) {
					ImageIcon icon = new ImageIcon("src/image/empty.PNG");
					Image temp = icon.getImage();
					Image changedTemp = temp.getScaledInstance(field[loop].getWidth(),field[loop].getHeight(),Image.SCALE_SMOOTH);
					ImageIcon newIcon = new ImageIcon(changedTemp);
					field[loop].setIcon(newIcon);
				}
			}
			CheckSynergy();
			TaskPerformanceResult = 10*fieldCount;
			if(SynergyTable[6] == 1) {
				TaskPerformanceResult += 20;
			}
			if(fusionSynergy == true) {
				TaskPerformanceResult *=2;
			}
			if(SynergyTable[0] == 2 && RoundType[round] == 0) {
				TaskPerformanceResult*=3;
			} else if(SynergyTable[0] == 1 && RoundType[round] == 0) {
				TaskPerformanceResult*=2;
			}

			if(SynergyTable[1] == 2 && RoundType[round] == 1) {
				TaskPerformanceResult*=3;
			} else if(SynergyTable[1] == 1 && RoundType[round] == 1) {
				TaskPerformanceResult*=2;
			}
			if(SynergyTable[2] == 2 && RoundType[round] == 4) {
				TaskPerformanceResult*=3;
			} else if(SynergyTable[2] == 1 && RoundType[round] == 4) {
				TaskPerformanceResult*=2;
			}

			if(SynergyTable[3] == 2 && RoundType[round] == 3) {
				TaskPerformanceResult*=3;
			} else if(SynergyTable[3] == 1 && RoundType[round] == 3) {
				TaskPerformanceResult*=2;
			}

			if(SynergyTable[4] == 2 && RoundType[round] == 2) {
				TaskPerformanceResult*=3;
			} else if(SynergyTable[4] == 1 && RoundType[round] == 2) {
				TaskPerformanceResult*=2;
			}

			if(SynergyTable[5] == 1 && RoundType[round] == 5) {
				TaskPerformanceResult = 99999;
			}
			tp.setText(Integer.toString(TaskPerformanceResult));
		}

		public void updateSynergy() {
			CheckSynergy();
			SynergyArea[0].setText("융합인재 시너지 : " + (fusionSynergy ? 1:0));
			SynergyArea[1].setText("문과대학 시너지 : " + SynergyTable[0]);
			SynergyArea[2].setText("사회과학 대학 시너지 : " + SynergyTable[1]);
			SynergyArea[3].setText("자연과학 대학 시너지 : " + SynergyTable[2]);
			SynergyArea[4].setText("예술대학 시너지 : " + SynergyTable[3]);
			SynergyArea[5].setText("공과대학 시너지 : " + SynergyTable[4]);
			SynergyArea[6].setText("소프트웨어 대학 시너지 : " + SynergyTable[5]);
			SynergyArea[7].setText("의과대학 시너지 : " + SynergyTable[6]);
		}

		public void updateAllStore() {
			for(int loopindex = 0; loopindex<5; loopindex++) {
				ImageIcon icon = new ImageIcon("src/image/"+Integer.toString(tempShopArr[loopindex]) + ".PNG");
				Image temp2 = icon.getImage();
				Image changedTemp2 = temp2.getScaledInstance(shop[loopindex].getWidth(),shop[loopindex].getHeight(),Image.SCALE_SMOOTH);
				ImageIcon newIcon = new ImageIcon(changedTemp2);
				shop[loopindex].setIcon(newIcon);
				shop[loopindex].setName(Integer.toString(tempShopArr[loopindex]) + " " + loopindex);
			}
		}

		public void updateRound() {
			Gold+=10;
			r.setText(Integer.toString(round+1));
			ImageIcon icon = new ImageIcon("src/image/enemy/"+Integer.toString(round+1) + ".PNG");
			Image temp = icon.getImage();
			Image changedTemp = temp.getScaledInstance(imagelabel2.getWidth(),imagelabel2.getHeight(),Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(changedTemp);
			imagelabel2.setIcon(newIcon);
			refreshShop();
			updateGold();
			updateAllStore();
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

	class submitButtonLister implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(RoundTarget[round] <= TaskPerformanceResult) {
				if(round==23) {
					JOptionPane.showMessageDialog(null,"You Win");
					System.exit(0);
				} else {
					JOptionPane.showMessageDialog(null,"PASS!!");
					round+=1;
					Update updatecheck = new Update();
					updatecheck.updateRound();
					repaint();
				}
			} else {
				JOptionPane.showMessageDialog(null,"Your Score is lower than Difficulty");
				JOptionPane.showMessageDialog(null,"Game Over");
				System.exit(0);
			}
		}
	}

	class howtoButtonListner implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String howtoText = "본게임은 카드를 이용하여 주어지는 점수를 넘기는 조합을 찾는게임입니다!\n " +
					"각각의 카드는 기본적으로 10점을 가지고 있으며 여러가지 조합에 따라 점수가 늘어납니다.\n" +
					"\n\n\n" +
					"율전 시너지 : 율전학생과 명륜학생 카드가 각각 1장 이상이 필드 위에 있을 때 발동됩니다. Task Performance 가 2배가 됩니다.\n" +
					"문과대학 시너지 : 발표 과제의 경우 , 문과대학 소속 카드가 2장 혹은 3장이 필드 위에 있을 경우 각각 Task Performance가 2배, 3배가 됩니다!\n" +
					"사회과학 시너지 : 에세이 과제의 경우 , 사회과학 소속 카드가 2장 혹은 3장이 필드 위에 있을 경우 각각 Task Performance가 2배, 3배가 됩니다!\n" +
					"자연과학 대학 시너지 : 수학과제의 경우 , 자연과학 대학 소속 카드가 2장 혹은 3장이 필드 위에 있을 경우 각각 Task Performance가 2배, 3배가 됩니다!\n" +
					"예술대학 시너지 : 예체능 과제의 경우 , 예술대학 소속 카드가 2장 혹은 3장이 필드 위에 있을 경우 각각 Task Performance가 2배, 3배가 됩니다!\n" +
					"공과대학 시너지 : 리포트 과제의 경우 , 공과대학 소속 카드가 2장 혹은 3장이 필드 위에 있을 경우 각각 Task Performance가 2배, 3배가 됩니다!\n" +
					"소프트웨어대학 시너지 : 코딩 과제의 경우, 소프트웨어 대학 소속 카드가 1장이라도 필드위에 있을 경우 Task Performance 가 99999가 됩니다!\n" +
					"의과대학 시너지 : 의과대학 소속 카드가 필드위에 있을 경우 의과대학 소속 카드 한장에 한에 기본 점수가 30점이 됩니다 (10점에서 30점으로)\n" +
					"\n\n\n" +
					"위 와 같은 시너지를 각각의 과제에 맞추어 조합을 필드위에 올려 최종라운드인 24라운드 까지 완료하면 승리!";
			JOptionPane.showMessageDialog(null,howtoText);
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
