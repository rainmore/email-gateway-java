package au.com.rainmore.configs.mails;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mails")
public class MailsProperties {

    private MailGun mailGun;
    private SendGrid sendGrid;

    public MailGun getMailGun() {
        return mailGun;
    }

    public MailsProperties setMailGun(MailGun mailGun) {
        this.mailGun = mailGun;
        return this;
    }

    public SendGrid getSendGrid() {
        return sendGrid;
    }

    public MailsProperties setSendGrid(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
        return this;
    }

    public static class MailGun {
        private String apiKey;
        private String domain;

        public String getApiKey() {
            return apiKey;
        }

        public MailGun setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public String getDomain() {
            return domain;
        }

        public MailGun setDomain(String domain) {
            this.domain = domain;
            return this;
        }
    }

    public static class SendGrid {
        private String apiKey;

        public String getApiKey() {
            return apiKey;
        }

        public SendGrid setApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }
    }
}
