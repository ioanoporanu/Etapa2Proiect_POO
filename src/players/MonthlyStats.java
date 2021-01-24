package players;

import java.util.ArrayList;

public final
class MonthlyStats {
    private int month;
    private ArrayList<Long> distributorsIds = new ArrayList<>();

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public ArrayList<Long> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(ArrayList<Long> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
