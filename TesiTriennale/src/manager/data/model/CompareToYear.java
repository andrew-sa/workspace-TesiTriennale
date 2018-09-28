package manager.data.model;

import java.util.Comparator;

public class CompareToYear implements Comparator<Data> {

	public CompareToYear() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Data data1, Data data2) {
		return data1.getYear().compareTo(data2.getYear());
	}

}
