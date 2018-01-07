import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		int numberOfProcesses;
		int resourcesKinds;
		ArrayList<String> wersy = new ArrayList<String>();
		ArrayList resources = new ArrayList();
		int processAllocated[][];
		int processMaxToAllocate[][];

		File file = new File("data.txt");
		Scanner sc = new Scanner(file);
		String fistLine = new String();
		int znak, nrFirst, nrLast;

		// pobranie do String buffera pierwszej linii z info o avaiable resources
		if (sc.hasNext()) {
			fistLine = sc.nextLine();
			// System.out.println("znak: "+fistLine);
		}

		// zbierania danych z plikow i poznanie ilosci procesow
		while (sc.hasNextLine()) {
			String linia = new String(sc.nextLine());
			wersy.add(linia);
		}
		sc.close();
		numberOfProcesses = wersy.size();

		// gathering info about avaiable resources
		nrFirst = fistLine.indexOf("[");
		nrLast = fistLine.indexOf("]");
		sc = new Scanner(fistLine.substring(nrFirst + 1, nrLast - nrFirst));
		while (sc.hasNextInt()) {
			resources.add(sc.nextInt());
		}
		resourcesKinds = resources.size(); // info about number of separate resources
		sc.close();

		// gathering info about processes and their max needs
		processAllocated = new int[numberOfProcesses][resourcesKinds];
		processMaxToAllocate = new int[numberOfProcesses][resourcesKinds];
		for (int j = 0; j < numberOfProcesses; j++) {

			nrFirst = wersy.get(j).indexOf("[");
			nrLast = wersy.get(j).indexOf("]");
			sc = new Scanner(wersy.get(j).substring(nrFirst + 1, nrLast - nrFirst));

			for (int i = 0; i < resourcesKinds; i++) {
				processAllocated[j][i] = sc.nextInt();
			}
			for (int i = 0; i < resourcesKinds; i++) {
				processMaxToAllocate[j][i] = sc.nextInt();
			}
		}
		// iter = wersy.get(0).;
		// System.out.println(iter.next());
		// System.out.println(iter.next());
		// while() {

		// resources.add(wersy.get(0).charAt(1));
		// System.out.println(resources.get(0));
		// }

		// Object[] wers = wersy.toArray();for(
		// int i = 0;i<wers.length;i++)
		// {
		// // System.out.println(wers[i].toString());
		// }

		// System.out.println(wersy.get(0) + " " + wersy.get(1) + " " + wersy.get(0));
		System.out.println("ldl");
	}
}
