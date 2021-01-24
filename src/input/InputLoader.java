package input;
import updates.MonthlyUpdates;

import java.util.ArrayList;

public final class InputLoader {

    private
    int numberOfTurns;
    private
    InitialData initialData;
    private
    ArrayList<MonthlyUpdates> monthlyUpdates;

    public
    InitialData getInitialData() {
        return initialData; }

    public
    void setInitialData(final InitialData initialData) {
        this.initialData = initialData;
    }

    public
    int getNumberOfTurns() {
        return numberOfTurns; }

    public
    void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public
    ArrayList<MonthlyUpdates> getMonthlyUpdates() {
        return monthlyUpdates; }

    public
    void setMonthlyUpdates(final ArrayList<MonthlyUpdates> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }
}
