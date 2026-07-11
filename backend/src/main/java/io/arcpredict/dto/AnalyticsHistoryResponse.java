package io.arcpredict.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsHistoryResponse {

    private List<ChartPoint> dailyVolume;

    private List<ChartPoint> dailyTrades;

    private List<ChartPoint> dailyMarkets;

    private List<ChartPoint> categoryBreakdown;

}