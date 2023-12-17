package at.fhv.master.laendleenergy.application;

import at.fhv.master.laendleenergy.domain.EnergySavingTarget;

public interface EnergySavingService {
    void addSavingTarget(int value, int timeframe);
    EnergySavingTarget getCurrentSavingTarget();
    void addIncentive(String text);
    void receiveReport(boolean receive);
    void showLeaderboard(String householdId);
}
