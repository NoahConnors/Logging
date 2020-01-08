public class SpacetimeEvent {
	private String name;
	private Logger logger;
	private int id;
	private int parentID;
	private static int currentID = 0;

	public SpacetimeEvent(String name, Logger logger) {
		this(name, logger, -1);
	}

	private SpacetimeEvent(String name, Logger logger, int parentID) {
		this.name = name;
		this.logger = logger;
		this.id = uniqueID();
		this.parentID = parentID;
	}

	public void start() {
		logger.info("Spacetime Start", new LogField("EventName", name), new LogField("ID", id), 
			new LogField("ParentID", parentID));
	}

	public void end() {
		logger.info("Spacetime End", new LogField("EventName", name), new LogField("ID", id), 
			new LogField("ParentID", parentID));
	}

	public SpacetimeEvent makeChild(String name) {
		return new SpacetimeEvent(name, logger, this.id);
	}

	public static int uniqueID() {
		return currentID++;
	}
}