package ask.cni.enums;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public enum ItrDetailType {
    ORIGIN("start"),
    DESTINATION("end");

    private String detail;

    ItrDetailType(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return "./li[@class='" + detail + "']";
    }
}
