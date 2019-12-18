public class SpacetimeEvent {
	private String name;
	private double startTime;
	private double endTime;
	private Logger logger;
	private int id;
	private static int currentID = 0;

	public SpacetimeEvent(String name, Logger logger) {
		this.name = name;
		this.logger = logger;
		this.id = uniqueID();
	}

	public void start(double startTime) {
        this.startTime = startTime;
		logger.info("Spacetime Start", new LogField("EventName", name), new LogField("ID", id));
	}

	public void end(double endTime) {
		this.endTime = endTime;
		logger.info("Spacetime End", new LogField("EventName", name), new LogField("ID", id));
	}

	public SpacetimeEvent makeChild(String name) {
		return new SpacetimeEvent(this.name + "/" + name, logger);
	}

	public static int uniqueID() {
		return currentID++;
	}
}