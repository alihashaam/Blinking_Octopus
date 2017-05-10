package dbse.fopj.blinktopus;

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
        // TODO: application initialization
    }

    @Override
    public void run(final BlinktopusConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
