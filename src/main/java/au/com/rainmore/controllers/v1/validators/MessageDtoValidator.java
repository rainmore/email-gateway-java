package au.com.rainmore.controllers.v1.validators;

import au.com.rainmore.controllers.v1.dto.MessageDto;
import au.com.rainmore.controllers.v1.dto.MessageDtoConverter;
import au.com.rainmore.modules.mails.dto.Message;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.mail.internet.InternetAddress;
import java.util.Optional;

public class MessageDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MessageDto.class == clazz;
    }


    @Override
    public void validate(Object target, Errors errors) {
        MessageDto dto = (MessageDto) target;

        String to = StringUtils.trimToNull(dto.getTo());
        String cc = StringUtils.trimToNull(dto.getCc());
        String bcc = StringUtils.trimToNull(dto.getBcc());

        if (to == null && cc == null && bcc == null) {
            errors.rejectValue("to", "mail.send.message.to.empty", "TO is empty");
            errors.rejectValue("cc", "mail.send.message.cc.empty", "CC is empty");
            errors.rejectValue("bcc", "mail.send.message.bcc.empty", "BCC is empty");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "mail.send.message.subject.empty","Subject is empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "mail.send.message.content.empty", "Content is empty");

        ValidationUtils.rejectIfEmpty(errors, "from", "mail.send.message.from.empty","FROM is empty");

        if (errors.hasFieldErrors()) {
            return; // force stop the further validation
        }

        MessageDtoConverter messageDtoConverter = new MessageDtoConverter();
        Message message = messageDtoConverter.from(dto);

        if (message.getFrom() == null || !EmailValidator.getInstance().isValid(message.getFrom().getAddress())) {
            errors.rejectValue("from", "mail.send.message.from.invalid","FROM is invalid");
        }

        if (Optional.ofNullable(dto.getReplyTo()).isPresent() &&
                (message.getReplyTo() == null ||
                        !EmailValidator.getInstance().isValid(message.getReplyTo().getAddress()))) {
            errors.rejectValue("replyTo", "mail.send.message.replyTo.invalid", "ReplyTo is invalid");
        }

        if (Optional.ofNullable(to).isPresent() &&
                (message.getTo().isEmpty() ||
                        message.getTo().stream().anyMatch(internetAddress ->
                            !EmailValidator.getInstance().isValid(internetAddress.getAddress()))
                )) {
            errors.rejectValue("to", "mail.send.message.to.invalid", "TO contains invalid email(s)");
        }

        if (Optional.ofNullable(cc).isPresent() && (message.getCc().isEmpty() ||
                message.getCc().stream().anyMatch(internetAddress ->
                        !EmailValidator.getInstance().isValid(internetAddress.getAddress()))
            )) {
            errors.rejectValue("cc", "mail.send.message.cc.invalid","CC contains invalid email(s)");
        }

        if (Optional.ofNullable(bcc).isPresent() && (message.getBcc().isEmpty() ||
                message.getBcc().stream().anyMatch(internetAddress ->
                        !EmailValidator.getInstance().isValid(internetAddress.getAddress()))
            )) {
            errors.rejectValue("bcc", "mail.send.message.bcc.invalid", "BCC contains invalid email(s)");
        }

    }

}
