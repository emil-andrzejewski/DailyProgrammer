import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
	
	public static void displayTabSquare(int tab[][], String comment) {
		System.out.println(comment);
		StringBuffer s = new StringBuffer("");
		for(int x[]: tab) {
			for (int y: x) {
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
	// - ProcessMaxToAllocate[]  - max resources  of one process
	public static boolean isProcessReadyToEnd(int resourcesKinds, int[] resources,int[] processAllocated, int[] processMaxToAllocate) {
		for(int i=0;i<resourcesKinds; i++) {
			if(!((processMaxToAllocate[i]-processAllocated[i]) <= resources[i])) return false;
		}		
		return true;
	}

	public static void main(String[] args) throws FileNotFoundException {
		int numberOfProcesses;
		int resourcesKinds;
		ArrayList<String> wersy = new ArrayList<String>();
		List<Integer> resourcesList = new ArrayList<Integer>();	
		int[] resourcesInt;
		int processAllocated[][];
		int processMaxToAllocate[][];
		int sequenceOfEndingProcesses;
		int processesQueued;

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
		numberOfProcesses = wersy.size();	//tu zapisuje ile jest procesów
		sequenceOfEndingProcesses = numberOfProcesses;

		// gathering info about avaiable resources
		nrFirst = fistLine.indexOf("[");
		nrLast = fistLine.indexOf("]");
		sc = new Scanner(fistLine.substring(nrFirst + 1, nrLast - nrFirst));
		while (sc.hasNextInt()) {
			resourcesList.add(sc.nextInt());
		}
		resourcesKinds = resourcesList.size(); // info about number of separate resources
		resourcesInt = new int[resourcesKinds];
		sc.close();
		sc = new Scanner(fistLine.substring(nrFirst + 1, nrLast - nrFirst));
		for(int i=0;i<resourcesKinds;i++) { //konwersja arrayList to int[]
			resourcesInt[i]=sc.nextInt();
		}		
		sc.close();
		
		

		// gathering info about processes (how much resources they have now) and their max needs
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
		
		//Wyswietlenie tablicy zaalokowany procesow
		displayTabSquare(processAllocated,"Zaalokowane procesy");
		
		// displaing max needs of processes
		displayTabSquare(processMaxToAllocate,"Max needs of processes");

		processesQueued=0;
		for (int i=0;i<numberOfProcesses;i++) {
			System.out.println(isProcessReadyToEnd(resourcesKinds,resourcesInt,processAllocated[i],processMaxToAllocate[i]));
		}

		System.out.println("ldl");
	}
}
