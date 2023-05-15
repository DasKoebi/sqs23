package inf.iso_to_name.json;

public class VolumeInfo {
    private String title;
    private IndustryIdentifiers[] industryIdentifiers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public IndustryIdentifiers[] getIndustryIdentifiers() {
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(IndustryIdentifiers[] industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }
}
