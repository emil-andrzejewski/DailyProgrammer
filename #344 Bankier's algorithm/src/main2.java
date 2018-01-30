import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class main2 {
	public static int procnum = 0;

	public static class Process {
		public String name;
		public List<Integer> alloc;
		public List<Integer> max;
		public List<Integer> needs;

		public Process(List<Integer> in, int mid) {
			name = "P" + procnum++;
			alloc = in.stream().limit(mid).collect(toList());
			max = in.stream().skip(mid).collect(toList());
			needs = IntStream.range(0, mid).mapToObj(j -> max.get(j) - alloc.get(j)).collect(toList());
		}
	}

	public static class Problem {
		public List<Integer> available;
		public List<Process> processes;

		public Problem(List<Integer> available, List<List<Integer>> processes) {
			this.available = available;
			this.processes = processes.stream().map(l -> new Process(l, available.size())).collect(toList());
		}
	}

	public static Problem readfile(String inputFile) throws IOException {
		List<List<Integer>> allLines = Files.readAllLines(Paths.get(inputFile)).stream().map(l -> l.replaceAll("[\\[\\]]", ""))
				.map(l -> Arrays.stream(l.split(" ")).map(Integer::parseInt).collect(toList())).collect(toList());
		return new Problem(allLines.get(0), allLines.subList(1, allLines.size()));
	}

	public static void main(String[] args) throws IOException {
		Problem p = readfile(args[0]);
		Optional<List<String>> possibleOrders = orderOfProcesses(p.processes, p.available);
		if (!possibleOrders.isPresent()) {
			System.out.println("No possible solutions found!");
		} else {
			System.out.println("Possible solutions found, here they are:");
			List<String> orders = possibleOrders.get();
			orders.forEach(System.out::println);
		}
	}

	public static Optional<List<String>> orderOfProcesses(List<Process> procs, List<Integer> available) {
		if (procs.size() == 0) {
			LinkedList<String> ll = new LinkedList<>();
			ll.add("");
			return Optional.of(ll);
		}
		Map<Process, List<Integer>> runnables = procs.stream().filter(proc -> canRun(proc, available))
				.collect(toMap(p -> p, p -> process(p, available)));
		return Optional.ofNullable(runnables.entrySet().stream().map(e -> {
			List<Process> filtered = new LinkedList<>(procs);
			filtered.remove(e.getKey());
			Optional<List<String>> possibleOrders = orderOfProcesses(filtered, e.getValue());

			if (!possibleOrders.isPresent())
				return null;

			List<String> orders = possibleOrders.get();
			return orders.stream().map(sequence -> e.getKey().name + "," + sequence).collect(toList());
		}).filter(Objects::nonNull).flatMap(Collection::stream).collect(toList()));
	}

	public static List<Integer> process(Process p, List<Integer> available) {
		return IntStream.range(0, p.alloc.size()).mapToObj(j -> p.alloc.get(j) + available.get(j)).collect(toList());
	}

	public static boolean canRun(Process p, List<Integer> available) {
		return IntStream.range(0, p.needs.size()).filter(j -> available.get(j) - p.needs.get(j) >= 0).count() == p.needs
				.size();
	}
}