package ashish.karan.enums;

/**
 * Created by Ashish Karan on 23/02/2019.
 */
public enum SortType {
    ASC("sortAsc"),
    DESC("sortDes");

    private String sortClass;

    SortType(String sortclass) {
        this.sortClass = sortclass;
    }

    public String getSortClass() {
        return sortClass;
    }
}
