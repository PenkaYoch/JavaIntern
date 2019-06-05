package intern.Java;

public class Performer {
	private String name;
	private long totalSales;
	private long salesPeriod;
	private double experienceMultiplier;

	public Performer() {
		// TODO Auto-generated constructor stub
		super();
	}

	public Performer(String name, long totalSales, long salesPeriod, double experienceMultiplier) {
		super();
		this.name = name;
		this.totalSales = totalSales;
		this.salesPeriod = salesPeriod;
		this.experienceMultiplier = experienceMultiplier;
	}
	public String getName() {
		return name;
	}
	public long getTotalSales() {
		return totalSales;
	}
	public long getSalesPeriod() {
		return salesPeriod;
	}
	public double getExperienceMultiplier() {
		return experienceMultiplier;
	}
}
