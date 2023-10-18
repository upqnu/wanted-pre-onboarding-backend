package wanted.freeonboarding.backend.wwrn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DummyDataLoader implements ApplicationRunner {

    private final HomeService homeService;

    @Autowired
    public DummyDataLoader(HomeService homeService) {
        this.homeService = homeService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        homeService.createDummyData();
    }
}
