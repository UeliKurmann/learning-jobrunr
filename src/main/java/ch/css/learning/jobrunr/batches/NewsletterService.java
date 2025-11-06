package ch.css.learning.jobrunr.batches;

import ch.css.learning.jobrunr.domain.MailService;
import ch.css.learning.jobrunr.domain.User;
import ch.css.learning.jobrunr.domain.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;

import java.util.stream.Stream;

@ApplicationScoped
public class NewsletterService {

    @Inject
    UserRepository userRepository;

    @Inject
    MailService mailService;

    @Inject
    JobScheduler jobScheduler;


    @Transactional
    public void sendEmailsToAllSubscribers(String mailTemplateKey) {
        var konfiguration = Konfiguration.builder()
                .jahr(2025)
                .kanton("LU")
                .template("t1")
                .build();
        jobScheduler.startBatch(() -> this.blubb(konfiguration));
    }

    @Transactional
    @Job(name="pran")
    public void blubb(Konfiguration konfiguration) {
        Stream<Long> userIdStream = userRepository.streamAll().map(User::getId);
        jobScheduler.enqueue(
                userIdStream,
                (userId) -> mailService.send(userId, konfiguration.getTemplate()));
    }
}