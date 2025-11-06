package ch.css.learning.jobrunr.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.jobrunr.jobs.annotations.Job;

@ApplicationScoped
public class MailService {
    @Inject
    MailRepository mailRepository;

    @Inject
    EntityManager entityManager;

    @Transactional
    @Job(name = "send-mail-job", retries = 2, queue = "batch-1-mail-queue", labels="batch:pran")
    public void send(Long userId, String mailTemplateKey) {
        System.out.println("Sending email to " + mailTemplateKey);
        User user = new User();
        user.setId(userId);
        Mail mail = new Mail();
        mail.setUser(user);
        mail.setMailTemplateKey(mailTemplateKey);
        entityManager.persist(mail);

        if(userId % 2000 == 0) {
            throw new RuntimeException("Simulated failure for userId: " + userId);
        }
    }
}

