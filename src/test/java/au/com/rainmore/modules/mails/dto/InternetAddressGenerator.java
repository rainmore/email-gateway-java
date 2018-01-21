package au.com.rainmore.modules.mails.dto;

import au.com.rainmore.Generator;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class InternetAddressGenerator implements Generator<InternetAddress> {

    private Random random = new Random();

    @Override
    public InternetAddress generate() {
        Person person = Fairy.create().person();
        String name = random.nextBoolean() ? person.getFullName() : null;
        String email = person.getEmail();
        InternetAddress internetAddress = null;

        try {
            internetAddress = new InternetAddress(email, name);
        }
        catch (UnsupportedEncodingException ex) {
            // we don't need to handle this
        }

        return internetAddress;
    }

    public Set<InternetAddress> generateRandomSet(Integer size) {
        Set<InternetAddress> addresses = new HashSet<>();
        for (int i = 0; i < size; i++) {
            addresses.add(generate());
        }
        return addresses;
    }
}
