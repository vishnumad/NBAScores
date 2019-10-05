package io.github.vishnumad.nbascores.database.mappers

import dagger.Reusable
import io.github.vishnumad.nbascores.data.models.TeamStatline
import io.github.vishnumad.nbascores.remote.entities.TeamStats
import io.github.vishnumad.nbascores.utils.Utils
import io.reactivex.functions.Function
import javax.inject.Inject

@Reusable
class TeamStatlineMapper @Inject constructor() : Function<TeamStats, TeamStatline> {

    override fun apply(stats: TeamStats): TeamStatline {
        return TeamStatline(
            fg = pctString(stats.fgm, stats.fga),
            threePt = pctString(stats.tpm, stats.tpa),
            ft = pctString(stats.ftm, stats.fta),
            oreb = "${stats.oreb}",
            dreb = "${stats.dreb}",
            reb = "${stats.reb}",
            ast = "${stats.ast}",
            stl = "${stats.stl}",
            blk = "${stats.blk}",
            to = "${stats.tov}",
            pf = "${stats.pf}",
            pts = "${calculatePts(stats.ftm, stats.tpm, stats.fgm)}"
        )
    }

    private fun pctString(numerator: Int, denominator: Int): String {
        val pct = if (denominator > 0) {
            "(${Utils.percentage(numerator, denominator)})"
        } else {
            ""
        }

        return "$numerator-$denominator$pct"
    }

    private fun calculatePts(ftm: Int, tpm: Int, fgm: Int): Int {
        // Total = Free throws + 3 * 3pt attempts + 2 * 2pt attempts
        return ftm + (tpm * 3) + ((fgm - tpm) * 2)
    }
}