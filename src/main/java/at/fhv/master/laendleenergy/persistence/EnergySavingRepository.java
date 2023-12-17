package at.fhv.master.laendleenergy.persistence;

public interface EnergySavingRepository {
    void addSavingTarget(int value, int timeframe);
    void addIncentive(String text);
    void receiveReport(boolean receive);
    void showLeaderboard(String householdId);
}
