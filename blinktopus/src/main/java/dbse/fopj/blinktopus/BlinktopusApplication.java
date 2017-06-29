package dbse.fopj.blinktopus;

import java.io.File;
import java.io.IOException;

import dbse.fopj.blinktopus.api.managers.LogManager;
import dbse.fopj.blinktopus.api.managers.SVManager;
import dbse.fopj.blinktopus.health.BlinktopusHealth;
import dbse.fopj.blinktopus.resources.QueryProcessor;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/** Entry-point for the Blinktopus.
 * 
 * @author urmikl18
 *
 */
public class BlinktopusApplication extends Application<BlinktopusConfiguration> {

	public static void main(final String[] args) throws Exception {
		new BlinktopusApplication().run(args);
	}

	@Override
	public String getName() {
		return "Blinktopus";
	}
	
	/**
	 * 1. Loads the initial data into the primary log.\n
	 * 2. Creates histograms and initializes HyperLogLog.
	 */
	@Override
	public void initialize(final Bootstrap<BlinktopusConfiguration> bootstrap) {
		String baseDir = "";
		try {
			baseDir = new File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LogManager.getLogManager().loadData(baseDir + "/src/main/resources/dataset/O_0.1.tbl",
				baseDir + "/src/main/resources/dataset/LI_0.1.tbl");
		SVManager.getSVManager().maintain("AQP", "aqp", "orders", "totalPrice", Double.MIN_VALUE, Double.MAX_VALUE, true, false);
	}

	@Override
	public void run(final BlinktopusConfiguration configuration, final Environment environment) {
		environment.healthChecks().register("default", new BlinktopusHealth());
		environment.jersey().register(new QueryProcessor());
	}
}
