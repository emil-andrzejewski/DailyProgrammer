import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		String tony[] = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
		String majorShort[] = { "do", "re", "mi", "fa", "sol", "la", "ti" };
		String major[] = { "do", "-", "re", "-", "mi", "fa", "-", "sol", "-", "la", "-", "ti" };
		int nrTonu;
		int nrMajor;
		
		Scanner sc = new Scanner(System.in);

		do {
			System.out.print("Podaj Tonacje glowna z posrod: ");
			for (String x : tony) {
				System.out.print(x + " ");
			}
			String znakTonow = sc.nextLine();

			System.out.print("Podaj Tonacje major z posrod: ");
			for (String x : majorShort) {
				System.out.print(x + " ");
			}
			String znakMajor = sc.nextLine();

			nrTonu = -1;
			for (int i = 0; i < tony.length; i++) {
				if (znakTonow.equals(tony[i])) {
					nrTonu = i;
				}
			}

			nrMajor = -1;
			for (int i = 0; i < tony.length; i++) {
				if (znakMajor.equals(major[i])) {
					nrMajor = i;
				}
			}
			if(nrTonu<0 || nrMajor<0) {
				System.out.println("Podales zle tonacje.");
			}
		} while (nrTonu<0 || nrMajor<0);

		System.out.println("Szukany dzwięk to: "+ tony[(nrTonu+nrMajor)%tony.length]);
	}

} 
