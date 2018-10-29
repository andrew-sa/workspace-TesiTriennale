package manager.data.model;

import java.util.Comparator;

public class CompareToOrder implements Comparator<Source> {

	public CompareToOrder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Source source1, Source source2) {
		return source1.getOrder() - source2.getOrder();
	}

}
