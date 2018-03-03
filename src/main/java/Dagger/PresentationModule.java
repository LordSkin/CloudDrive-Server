package Dagger;

import BuisnessTier.AppController;
import BuisnessTier.AppControllerImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class PresentationModule {

    private AppController appController;

    public PresentationModule(AppController appController) {
        this.appController = appController;
    }

    @Provides
    public AppController getController() {
        return appController;
    }
}
