package dbse.fopj.blinktopus;

import java.io.File;
import java.io.IOException;

import dbse.fopj.blinktopus.api.managers.LogManager;
import dbse.fopj.blinktopus.health.BlinktopusHealth;
import dbse.fopj.blinktopus.resources.QueryProcessor;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * 
 * @author urmikl18
 * Entry point of application. Registers resource class {@link QueryProcessor} and health class {@link BlinktopusHealth}
 */
public class BlinktopusApplication extends Application<BlinktopusConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BlinktopusApplication().run(args);
    }

    @Override
    public String getName() {
        return "Blinktopus";
    }

    @Override
    public void initialize(final Bootstrap<BlinktopusConfiguration> bootstrap) {
    	String baseDir="";
    	try {
			baseDir=new File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
        LogManager.getLogManager().loadData(baseDir+"/src/main/resources/dataset/O_T_0.1.tbl");
        LogManager.getLogManager().loadData(baseDir+"/src/main/resources/dataset/LI_T_0.1.tbl");
    }

    @Override
    public void run(final BlinktopusConfiguration configuration,
                    final Environment environment) {
        environment.healthChecks().register("default", new BlinktopusHealth());
        environment.jersey().register(new QueryProcessor());
    }
}
