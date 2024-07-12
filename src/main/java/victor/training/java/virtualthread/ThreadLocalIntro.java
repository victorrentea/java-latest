package victor.training.java.virtualthread;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
public class ThreadLocalIntro {
    private final AController controller = new AController(new AService(new ARepo()));
    public static void main(String[] args) {
        ThreadLocalIntro app = new ThreadLocalIntro();
        System.out.println("Imagine incoming HTTP requests...");
        app.httpRequest("alice", "alice's data");
    }

    public void httpRequest(String currentUser, String data) {
        log.info("Current user is " + currentUser);
        controller.create(data,currentUser);
        staticCurrentUser = currentUser;
    }
    public static String staticCurrentUser;
}
// ---------- end of framework -----------

// ---------- Controller -----------
@RestController
@RequiredArgsConstructor
class AController {
    private final AService service;
    public void create(String data, String user) {
        service.create(data, user);
    }
}
@Service
@RequiredArgsConstructor
class AService {
    private final ARepo repo;
    @SneakyThrows
    public void create(String data, String user) {
        Thread.sleep(10); // some delay, to reproduce the race bug
        repo.save(data, user); // +1 param la toate metodele din app
    }
}
@Repository
@Slf4j
class ARepo {
    public void save(String data, String user) {
        String currentUser =user; // TODO
        log.info("INSERT INTO A (data={}, created_by={}) ", data, currentUser);
    }
}
