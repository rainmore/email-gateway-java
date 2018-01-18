package au.com.rainmore.controllers.v1.validators;

import au.com.rainmore.BaseSpec;
import au.com.rainmore.modules.mails.dto.InternetAddressGenerator;
import au.com.rainmore.modules.mails.dto.Message;
import au.com.rainmore.modules.mails.dto.MessageGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class MessageDtoValidatorSpec extends BaseSpec {

    private InternetAddressGenerator internetAddressGenerator = new InternetAddressGenerator();
    private MessageGenerator         messageGenerator         = new MessageGenerator();
    private MessageDtoValidator      validator                = new MessageDtoValidator();

    @Test
    public void testSupports() {
        Assert.assertTrue(validator.supports(Message.class));
        Assert.assertFalse(validator.supports(Object.class));
    }

    @Test
    public void testValidateForContent() {
        Errors errors;

        Message message = messageGenerator.generate();

        errors = bindingErrors(message);
        Assert.assertFalse(errors.hasErrors());
        Assert.assertFalse(errors.hasFieldErrors("content"));

        message.setContent(null);
        errors = bindingErrors(message);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertTrue(errors.hasFieldErrors("content"));

        message.setContent("");
        errors = bindingErrors(message);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertTrue(errors.hasFieldErrors("content"));

        message.setContent("  ");
        errors = bindingErrors(message);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertTrue(errors.hasFieldErrors("content"));

    }

    @Test
    public void testValidateForSubject() {
        Errors errors;

        Message message = messageGenerator.generate();

        errors = bindingErrors(message);
        Assert.assertFalse(errors.hasErrors());
        Assert.assertFalse(errors.hasFieldErrors("subject"));

        message.setSubject(null);
        errors = bindingErrors(message);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertTrue(errors.hasFieldErrors("subject"));

        message.setSubject("");
        errors = bindingErrors(message);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertTrue(errors.hasFieldErrors("subject"));

        message.setSubject("  ");
        errors = bindingErrors(message);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertTrue(errors.hasFieldErrors("subject"));
    }

    @Test
    public void testValidateForAddresses() {
        Errors errors;

        Message message = messageGenerator.generate();

        errors = bindingErrors(message);
        Assert.assertFalse(errors.hasErrors());
        Assert.assertFalse(errors.hasFieldErrors("to"));
        Assert.assertFalse(errors.hasFieldErrors("cc"));
        Assert.assertFalse(errors.hasFieldErrors("bcc"));

        message.getTo().clear();
        message.getCc().clear();
        message.getBcc().clear();
        errors = bindingErrors(message);
        Assert.assertTrue(errors.hasErrors());
        Assert.assertTrue(errors.hasFieldErrors("to"));
        Assert.assertTrue(errors.hasFieldErrors("cc"));
        Assert.assertTrue(errors.hasFieldErrors("bcc"));

    }

    @Test
    public void testValidateForFrom() {
        //TODO to implement the test when form's logic is confirmed
    }

    private Errors bindingErrors(Message message) {
        Errors errors = new BeanPropertyBindingResult(message, "message");
        validator.validate(message, errors);
        return errors;
    }

}
