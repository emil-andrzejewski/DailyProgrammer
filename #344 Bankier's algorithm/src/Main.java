import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {


	public static void displayTabSquare(int tab[][], String comment) {
		System.out.println(comment);
		StringBuffer s = new StringBuffer("");
		for (int x[] : tab) {
			for (int y : x) {
				s.append(y);
				s.append(" ");
			}
			s.append("\n");
		}
		System.out.println(s);
	}


	// Is one process ready to end?
	// Needed:
	// - resourcesKinds - number of kinds of resources
	// - resources - avaiable resources
	// - ProcessAllocated[] - allocated resources of one process
	// - ProcessMaxToAllocate[] - max resources of one process
	public static boolean isProcessReadyToEnd(int resourcesKinds, ArrayList<Resources> reso, int[] processAllocated,
			int[] processMaxToAllocate) {
		for (int i = 0; i < resourcesKinds; i++) {
			if (!((processMaxToAllocate[i] - processAllocated[i]) <= reso.get(i).getReso()))
				return false;
		}
		return true;
	}

	public static void main(String[] args) throws FileNotFoundException {
		int numberOfProcesses;
		int resourcesKinds;
		ArrayList<String> wersy = new ArrayList<String>();
		ArrayList<Resources> reso = new ArrayList<Resources>();
		ArrayList<Process> procs = new ArrayList<Process>();
//		int[] reso;
		int processAllocated[][];
		int processMaxToAllocate[][];
		int sequenceOfEndingProcesses[];
		int processesQueued;
		boolean safeSituation;

		File file = new File("data.txt");
		Scanner sc = new Scanner(file);
		String fistLine = new String();
		int nrFirst, nrLast;

		// pobranie do String buffera pierwszej linii z info o avaiable resources
		if (sc.hasNext()) {
			fistLine = sc.nextLine();
			
			//gathering number of resources i their values
			nrFirst = fistLine.indexOf("[");
			nrLast = fistLine.indexOf("]");
			Scanner sc2 = new Scanner(fistLine.substring(nrFirst + 1, nrLast - nrFirst));
			while (sc2.hasNextInt()) {
				reso.add(new Resources(sc.nextInt()));
			}
			sc2.close();
			reso.toString();
			// System.out.println("znak: "+fistLine);
		}

		// zbierania danych z plikow i poznanie ilosci procesow
		while (sc.hasNextLine()) {
			String linia = new String(sc.nextLine());
			wersy.add(linia);
		}
		sc.close();
		numberOfProcesses = wersy.size(); // tu zapisuje ile jest procesów

		// gathering info about avaiable resources
		
		
		
		resourcesKinds = reso.size(); // info about number of separate resources
//		reso = new int[resourcesKinds];
		sc.close();
//		sc = new Scanner(fistLine.substring(nrFirst + 1, nrLast - nrFirst));
		for (int i = 0; i < resourcesKinds; i++) { // konwersja arrayList to int[]
		//	reso[i] = sc.nextInt();
		}
		sc.close();

		// gathering info about processes (how much resources they have now) and their
		// max needs
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

		// Wyswietlenie tablicy zaalokowany procesow
		displayTabSquare(processAllocated, "Zaalokowane procesy");

		// displaing max needs of processes
		displayTabSquare(processMaxToAllocate, "Max needs of processes");

		// szukanie kolejki wyjscia i sprawdzenie czy sytuacja jest bezpieczna
		processesQueued = 0;
		sequenceOfEndingProcesses = new int[numberOfProcesses];
		safeSituation = false;
		for (int j = 0; j < numberOfProcesses; j++) { // przejsc bedzie maksymalnie tyle ile jest procesow
			int i = 0;
			while (i < numberOfProcesses) {
				boolean isProcessQueued = false;
				for (int k = 0; k < processesQueued; k++) {
					if (sequenceOfEndingProcesses[k] == i)
						isProcessQueued = true;
				}
				if (!isProcessQueued) {
					if (isProcessReadyToEnd(resourcesKinds, reso, processAllocated[i],
							processMaxToAllocate[i])) { // jesli znaleziono proces do skonczenia
						sequenceOfEndingProcesses[processesQueued++] = i; // dodanie numeru procesu do kolejki konczenia
																			// procesow
						System.out.print("Process ready to finish: P" + i + ", resources now: ");
						for (int k = 0; k < resourcesKinds; k++) {
							int temp = reso.get(k).getReso();
//							reso.set(k, temp+);
							reso.get(k).setReso(temp+processAllocated[i][k]);
							System.out.print(reso.get(k) + " ");
						}
						System.out.println("");
					}
				}
				i++;
			}
			if (processesQueued == numberOfProcesses) {
				safeSituation = true;
				j = numberOfProcesses;
			}
		}

		System.out.println("Sytuacja bezpieczna?: " + safeSituation);
		if (safeSituation) {
			System.out.println("Kojelka zadan do skonczenia: ");
			for (int x : sequenceOfEndingProcesses) {
				System.out.print("P" + x + ", ");
			}
			System.out.println("");
		}
	}
}
