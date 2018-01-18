package au.com.rainmore.modules.mails.dto;

import au.com.rainmore.Generator;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.text.TextProducer;

import javax.mail.internet.InternetAddress;
import java.util.Random;
import java.util.Set;

public class MessageGenerator implements Generator<Message> {

    private static final int MAX_ADDRESS = 10;

    private InternetAddressGenerator internetAddressGenerator = new InternetAddressGenerator();
    private TextProducer textProducer = Fairy.create().textProducer();
    private Random random = new Random();

    @Override
    public Message generate() {
        Message message = new Message()
                .setContent(textProducer.loremIpsum())
                .setSubject(textProducer.sentence())
                .setTo(buildInternetAddresses())
                .setCc(buildInternetAddresses())
                .setBcc(buildInternetAddresses())
                .setFrom(internetAddressGenerator.generate())
                .setReplyTo(internetAddressGenerator.generate())
                ;

        return message;
    }

    private Set<InternetAddress> buildInternetAddresses() {
        return internetAddressGenerator.generateRandomSet(random.nextInt(MAX_ADDRESS));
    }

}
