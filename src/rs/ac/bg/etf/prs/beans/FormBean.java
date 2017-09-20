package rs.ac.bg.etf.prs.beans;

public class FormBean {
    private String cylinderText;
    private String rpmText;
    private String text3;

    public FormBean() {
    }

    public String getCylinderText() {
        return cylinderText;
    }

    public void setCylinderText(final String cylinderText) {
        this.cylinderText = cylinderText;
    }

    public String getRpmText() {
        return rpmText;
    }

    public void setRpmText(final String rpmText) {
        this.rpmText = rpmText;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(final String text3) {
        this.text3 = text3;
    }
}