import java.util.Random;

public class Main {
	public static void main(String[] args) {
		int kindsOfCard = 2; // 카드 종류의 갯수
		int storage[] = new int[10];
		int shop[] = new int[5];

		for(int loop1 = 0; loop1<10; loop1++) {
			storage[loop1] = -1;
		}

		Random generator = new Random();
		// default setting
		for(int loop2 = 0; loop2 < 5; loop2++) {
			shop[loop2] = generator.nextInt(kindsOfCard);
		}

		Frame f = new Frame(shop,storage);
		}
}
