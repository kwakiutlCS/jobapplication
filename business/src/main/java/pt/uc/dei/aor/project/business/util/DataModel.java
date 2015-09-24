package pt.uc.dei.aor.project.business.util;

import java.util.ArrayList;
import java.util.List;

public class DataModel<T, U> {

	private List<DataPoint<T, U>> points;
	
	public DataModel() {
		points = new ArrayList<>();
	}
	
	public List<DataPoint<T, U>> getPoints() {
		return points;
	}
	
	public void addPoint(DataPoint<T, U> point) {
		points.add(point);
	}
}
