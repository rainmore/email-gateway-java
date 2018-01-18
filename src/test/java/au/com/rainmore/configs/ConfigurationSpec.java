package au.com.rainmore.configs;

import au.com.rainmore.BaseSpec;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Test;

public class ConfigurationSpec extends BaseSpec {

    private Configuration configuration = new Configuration();

    @Test
    public void testObjectMapper() {
        ObjectMapper objectMapper = configuration.objectMapper();
        Assert.assertNotNull(objectMapper);
        Assert.assertTrue(objectMapper.isEnabled(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT));
        Assert.assertFalse(objectMapper.isEnabled(SerializationFeature.INDENT_OUTPUT));
        Assert.assertFalse(objectMapper.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
        Assert.assertFalse(objectMapper.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS));
        Assert.assertFalse(objectMapper.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));
    }

}
