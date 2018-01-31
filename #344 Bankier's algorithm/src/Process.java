
public class Process {
	private int max[]= null;	//max needed resources
	private int used[] = null;	//used resources for this process
	public int getMax(int iter) {
		return max[iter];
	}
	public void setMax(int iter, int value) {
		this.max[iter] = value;
	}
	public int getUsed(int iter) {
		return used[iter];
	}
	public void setUsed(int iter, int value) {
		this.used[iter] = value;
	}
	public Process(int max[], int used[]) {
		super();
		this.max = max;
		this.used = used;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Allocated: ");
		for(int s :used) {
			str.append(s);
			str.append(" ");
		}
		str.append("\t Max needs: ");
		for(int s :max) {
			str.append(s);
			str.append(" ");
		}
		return str.toString();
	}
	
	
}
