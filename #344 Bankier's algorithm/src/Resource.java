
public class Resource {
	private int reso;	//avaiable resource

	public int getReso() {
		return reso;
	}

	public void setReso(int reso) {
		this.reso = reso;
	}

	public Resource(int reso) {
		super();
		this.reso = reso;
	}
	
	@Override
	public String toString() {
		return "" + getReso();
	}
	
}
