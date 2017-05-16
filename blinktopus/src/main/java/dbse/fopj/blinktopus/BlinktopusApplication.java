package dbse.fopj.blinktopus;

import dbse.fopj.blinktopus.api.managers.LogManager;
import dbse.fopj.blinktopus.health.BlinktopusHealth;
import dbse.fopj.blinktopus.resources.QueryProcessor;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        LogManager.getLogManager().loadData("/home/urmikl18/Uni/SoSe17/Blinking_Octopus/blinktopus/dataset/orders1.csv");
        LogManager.getLogManager().loadData("/home/urmikl18/Uni/SoSe17/Blinking_Octopus/blinktopus/dataset/lineitems1.csv");
    }

    @Override
    public void run(final BlinktopusConfiguration configuration,
                    final Environment environment) {
        environment.healthChecks().register("default", new BlinktopusHealth());
        environment.jersey().register(new QueryProcessor());
    }

}
