package victor.training.java.virtualthread;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;


@Slf4j
public class ThreadLocalIntro {
    private final AController controller = new AController(new AService(new ARepo()));
    public static void main(String[] args) throws InterruptedException {
        ThreadLocalIntro app = new ThreadLocalIntro();
        System.out.println("Imagine incoming HTTP requests...");
        CompletableFuture.runAsync(()->app.httpRequest("alice", "alice's data"));
        CompletableFuture.runAsync(()->app.httpRequest("bob", "bob's data"));
        Thread.sleep(1000);
    }

    public void httpRequest(String currentUser, String data) {
        log.info("Current user is " + currentUser);
        staticCurrentUser.set(currentUser);
        controller.create(data);
    }
    public static ThreadLocal<String> staticCurrentUser = new ThreadLocal<>();
}
// ---------- end of framework -----------

// ---------- Controller -----------
@RestController
@RequiredArgsConstructor
class AController {
    private final AService service;
    public void create(String data) {
        service.create(data);
    }
}
@Service
@RequiredArgsConstructor
class AService {
    private final ARepo repo;
    @SneakyThrows
    public void create(String data) {
        Thread.sleep(10); // some delay, to reproduce the race bug
        repo.save(data); // +1 param la toate metodele din app
    }
}
@Repository
@Slf4j
class ARepo {
    public void save(String data) {
        String currentUser =ThreadLocalIntro.staticCurrentUser.get(); // TODO
        log.info("INSERT INTO A (data={}, created_by={}) ", data, currentUser);
    }
}
