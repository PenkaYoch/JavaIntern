package intern.Java;

public class ReportDefinition {
	private long topPerformersThreshold;
	private boolean useExprienceMultiplier;
	private long periodLimit;
	
	public ReportDefinition() {
		super();
	}
	
	public ReportDefinition(long topPerformersThreshold, boolean useExprienceMultiplier, long periodLimit) {
		super();
		this.topPerformersThreshold = topPerformersThreshold;
		this.useExprienceMultiplier = useExprienceMultiplier;
		this.periodLimit = periodLimit;
	}
	public long getTopPerformersThreshold() {
		return topPerformersThreshold;
	}
	public boolean isUseExprienceMultiplier() {
		return useExprienceMultiplier;
	}
	public long getPeriodLimit() {
		return periodLimit;
	}
}
