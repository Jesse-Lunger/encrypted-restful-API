package classtesting;

import domain.MessageType;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import persistence.impl.MessageTypeDAO;
import service.MessageTypeService;

public class MessageTypeTest {

    private static final MessageTypeService messageTypeService = new MessageTypeService(new MessageTypeDAO());

    @DataProvider(name = "messageTypes")
    public Object[][] messageTypes() {
        return new Object[][]{
                {"publicMessage"},
                {"privateMessage"}
        };
    }

    @BeforeTest
    public void populateMessageTypes() {
        Object[][] data = messageTypes();
        for (Object[] messageTypeData : data) {
            String messageTypeStr = (String) messageTypeData[0];
            MessageType messageType = new MessageType.Builder()
                    .messageTypeName(messageTypeStr)
                    .build();
            messageTypeService.saveEntity(messageType);
            Assert.assertNotNull(messageTypeService.getMessageTypeByName(messageType.getMessageTypeName()));
        }
    }

    @Test
    public void addDuplicateTest() {
        Object[][] data = messageTypes();
        String duplicateData = (String) data[0][0];
        MessageType messageType = new MessageType.Builder()
                .messageTypeName(duplicateData)
                .build();
        messageTypeService.saveEntity(messageType);
        MessageType saveMessageType = messageTypeService.getMessageTypeByName(duplicateData);
        Assert.assertNotEquals(messageType, saveMessageType);
    }

    @Test
    public void updateTest() {
        String newName = "newName";
        Object[][] data = messageTypes();
        MessageType messageType = messageTypeService.getMessageTypeByName((String) data[0][0]);
        messageType.setMessageTypeName(newName);
        messageTypeService.updateEntity(messageType);
        messageType = messageTypeService.getMessageTypeByName(newName);
        Assert.assertEquals(messageType.getMessageTypeName(), newName);
        messageType.setMessageTypeName((String) data[0][0]);
        messageTypeService.updateEntity(messageType);
    }


    @AfterTest
    public void depopulateDatabase() {
        for (MessageType messageType : messageTypeService.getAll()) {
            messageTypeService.removeEntityById(messageType.getMessageTypeId());
        }
        Assert.assertTrue(messageTypeService.getAll().isEmpty());
    }
}
