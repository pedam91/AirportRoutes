package pdamjanovic.calculation;

public class DistanceCalculator {

	/**
	 * Calculate distance between 2 coordinates
	 * @param lat1 - latitude for first point
	 * @param lon1 - longitude for first point
	 * @param lat2 - latitude for second point
	 * @param lon2 - longitude for second point
	 * @return Integer value - discarding decimal points
	 */
	public static Integer calculateDistanceBetweenCoordinates(Double lat1, Double lon1, Double lat2, Double lon2) {
		Double theta = lon1 - lon2;
		Double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515 * 1.609344;
		return dist.intValue();

	}

	/* This function converts decimal degrees to radians  */
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/* This function converts radians to decimal degrees  */
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
