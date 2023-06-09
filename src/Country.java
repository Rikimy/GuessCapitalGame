public class Country {
  private final int id;
  private final String countryName;

  private final String countryCapital;

  public Country(int id, String countryName, String countryCapital) {
    this.id = id;
    this.countryName = countryName;
    this.countryCapital = countryCapital;
  }

  public String getCountryName() {
    return countryName;
  }

  public String getCountryCapital() {
    return countryCapital;
  }

  @Override
  public String toString() {
    return "Country{" +
        "id=" + id +
        ", countryName='" + countryName + '\'' +
        ", countryCapital='" + countryCapital + '\'' +
        '}';
  }
}
