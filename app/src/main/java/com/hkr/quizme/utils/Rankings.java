package com.hkr.quizme.utils;

import com.hkr.quizme.database_utils.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Rankings {
    private List<Ranking> rankings;

    public Rankings() {
        rankings = new ArrayList<>();
        rankings.add(new Ranking(49, "NOOB"));
        rankings.add(new Ranking(299, "ROOKIE"));
        rankings.add(new Ranking(499, "GEEK"));
        rankings.add(new Ranking(999, "ELITE"));
        rankings.add(new Ranking(Integer.MAX_VALUE, "LEGEND"));
    }

    public Ranking getRanking(User user) {
        int points = user.getPoints();
        for (Ranking ranking : rankings) {
            if (points < ranking.getPoints()) {
                return ranking;
            }
        }
        return null;
    }

    public int getProgressPercent(User user) {
        Ranking ranking = getRanking(user);
        int nextRankingIndex = rankings.indexOf(ranking) + 1;
        int previousRankingIndex = rankings.indexOf(ranking) - 1;
        if (nextRankingIndex < rankings.size()) {
            int lowerBound = 0;
            if (previousRankingIndex > -1) {
                lowerBound = rankings.get(previousRankingIndex).getPoints();
            }
            double top = (user.getPoints()) - lowerBound;
            double bottom = (ranking.getPoints()) - lowerBound;
            return (int)((top / bottom) * 100);
        } else {
            return 100;
        }
    }
}
