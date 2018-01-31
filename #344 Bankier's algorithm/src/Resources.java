
public class Resources {
	private int reso;	//avaiable resource

	public int getReso() {
		return reso;
	}

	public void setReso(int reso) {
		this.reso = reso;
	}

	public Resources(int reso) {
		super();
		this.reso = reso;
	}
	
	@Override
	public String toString() {
		return "" + getReso();
	}
	
}
