package Dagger;

import BuisnessTier.AppController;
import BuisnessTier.AppControllerImpl;
import PresentationTier.RestController;
import dagger.Component;

@Component(modules = {ControllerModule.class, PresentationModule.class})
public interface AppComponent {

    void inject(AppControllerImpl appController);

    void inject(RestController restController);
}
