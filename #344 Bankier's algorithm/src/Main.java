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
	public static boolean isProcessReadyToEnd(ArrayList<Resource> resors, Process proc) {
		for (int i = 0; i < resors.size(); i++) {
			if (!((proc.getMax(i) - proc.getUsed(i)) <= resors.get(i).getReso()))
				return false;
		}
		return true;
	}

	public static void main(String[] args) throws FileNotFoundException {
		int numberOfProcesses;
		int resourcesKinds;
		ArrayList<String> wersy = new ArrayList<String>();
		ArrayList<Resource> resors = new ArrayList<Resource>();
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
				resors.add(new Resource(sc2.nextInt()));
			}
			sc2.close();
//			System.out.println(reso.toString());			
		}

		// zbierania danych z plikow i poznanie ilosci procesow i wartosci zaalokowanych i max danych
		while (sc.hasNextLine()) {
			String linia = new String(sc.nextLine());
			Process tmp = new Process(new int[resors.size()],new int[resors.size()]);
			
			nrFirst = linia.indexOf("[");
			nrLast = linia.indexOf("]");
			Scanner sc2 = new Scanner(linia.substring(nrFirst + 1, nrLast - nrFirst));
			
			for (int i = 0; i < resors.size(); i++) {
				tmp.setUsed(i, sc2.nextInt());
//				processAllocated[j][i] = sc.nextInt();
			}
			for (int i = 0; i < resors.size(); i++) {
				tmp.setMax(i, sc2.nextInt());
//				processMaxToAllocate[j][i] = sc.nextInt();
			}
			sc2.close();
			System.out.println(tmp.toString());
			procs.add(tmp);
//			System.out.println(procs.toString());
			wersy.add(linia);
		}
		sc.close();
		numberOfProcesses = procs.size(); // tu zapisuje ile jest procesów

		// gathering info about avaiable resources	
		resourcesKinds = resors.size(); // info about number of separate resources

		// gathering info about processes (how much resources they have now) and their
		// max needs
		processAllocated = new int[numberOfProcesses][resors.size()];
		processMaxToAllocate = new int[numberOfProcesses][resors.size()];
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
//		displayTabSquare(processAllocated, "Zaalokowane procesy");

		// displaing max needs of processes
//		displayTabSquare(processMaxToAllocate, "Max needs of processes");

		// szukanie kolejki wyjscia i sprawdzenie czy sytuacja jest bezpieczna
		processesQueued = 0;
		sequenceOfEndingProcesses = new int[procs.size()];
		safeSituation = false;
		for (int j = 0; j < procs.size(); j++) { // przejsc bedzie maksymalnie tyle ile jest procesow
			int i = 0;
			while (i < procs.size()) {
				boolean isProcessQueued = false;
				for (int k = 0; k < processesQueued; k++) {
					if (sequenceOfEndingProcesses[k] == i)
						isProcessQueued = true;
				}
				if (!isProcessQueued) {
					if (isProcessReadyToEnd(resors, procs.get(i))) { // jesli znaleziono proces do skonczenia
						sequenceOfEndingProcesses[processesQueued++] = i; // dodanie numeru procesu do kolejki konczenia
																			// procesow
						System.out.print("Process ready to finish: P" + i + ", resources now: ");
						for (int k = 0; k < resourcesKinds; k++) {
							int temp = resors.get(k).getReso();
//							reso.set(k, temp+);
							resors.get(k).setReso(temp+processAllocated[i][k]);
							System.out.print(resors.get(k) + " ");
						}
						System.out.println("");
					}
				}
				i++;
			}
			if (processesQueued == procs.size()) {
				safeSituation = true;
				j = procs.size();
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
