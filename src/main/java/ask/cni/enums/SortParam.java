package ask.cni.enums;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public enum SortParam {
    AIRLINE("airline"),
    DEPART("dep"),
    DURATION("dur"),
    PRICE("price");

    private String label;

    SortParam(String label) {
        this.label = label;
    }

    public String getSortKey() {
        return label;
    }
}
