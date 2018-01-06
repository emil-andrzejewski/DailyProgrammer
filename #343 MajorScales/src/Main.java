import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		int nrTonu;
		int nrMajor;

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		Tony ton = new Tony();
		Major glowne = new Major();
		while (true) {
			nrTonu = -1;

			while (nrTonu == -1) {
				System.out.print("Podaj Ton glowny z posrod: ");
				for (String x : ton.tony) {
					System.out.print(x + " ");
				}
				String znakTonow = sc.nextLine();

				for (int i = 0; i < ton.tony.length; i++) {
					if (znakTonow.toUpperCase().equals(ton.tony[i])) {
						nrTonu = ton.nr[i];
					}
				}
				if (nrTonu == -1) {
					System.out.println("Podales zla nazwe tonu.");
				}
			}

			nrMajor = -1;
			while (nrMajor == -1) {
				System.out.print("Podaj  Ton major z posrod: ");
				for (String x : glowne.major) {
					System.out.print(x + " ");
				}
				String znakMajor = sc.nextLine();

				nrMajor = -1;
				for (int i = 0; i < glowne.major.length; i++) {
					if (znakMajor.toLowerCase().equals(glowne.major[i])) {
						nrMajor = glowne.nr[i];
					}
				}
				if (nrMajor == -1) {
					System.out.println("Podales zla nazwe tonu.");
				}
			}
			System.out.println("Szukany dzwięk to: " + ton.tony[(nrTonu + nrMajor) % ton.tony.length]);
			System.out.print("Tonacja zaczynajaca sie od podanego tonu to: ");
			for (int i : glowne.nr) {
				System.out.print(ton.tony[(nrTonu + i) % ton.tony.length] + " ");
			}
			System.out.println("\n");
		}
	}
}
