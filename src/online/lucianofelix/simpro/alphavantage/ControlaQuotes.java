package online.lucianofelix.simpro.alphavantage;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

public class ControlaQuotes {
	public ControlaQuotes() {
		// TODO Auto-generated constructor stub
	}
	public void configAPI() {
		Config cfg = Config.builder().key("L57EX40HK2B7T7FZ").timeOut(10)
				.build();
		AlphaVantage.api().init(cfg);
	}

	public TimeSeriesResponse intraDay5MIN(String ativo) {
		configAPI();
		return AlphaVantage.api().timeSeries().intraday().forSymbol(ativo)
				.interval(Interval.FIVE_MIN).outputSize(OutputSize.FULL)
				.fetchSync();
	}

	public TimeSeriesResponse intraDay(String ativo) {
		configAPI();
		return AlphaVantage.api().timeSeries().intraday().forSymbol(ativo)
				.interval(Interval.ONE_MIN).outputSize(OutputSize.FULL)
				.fetchSync();
	}

	public TimeSeriesResponse listSymbols(String ativo) {
		configAPI();
		return AlphaVantage.api().timeSeries().daily().forSymbol(ativo)
				.outputSize(OutputSize.FULL).fetchSync();
	}

	public TimeSeriesResponse daily(String ativo) {
		configAPI();
		return AlphaVantage.api().timeSeries().daily().forSymbol(ativo)
				.outputSize(OutputSize.FULL).fetchSync();
	}

	public void fundamentalData() {
		configAPI();
		// FundamentalData fund = new FundamentalData();

	}

}
