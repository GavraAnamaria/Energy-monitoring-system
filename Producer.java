import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        String csvFile = "D://facultate/an4/sd/sensor.csv";
        String line = "";
        String csvSplitBy = ",";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("user");
        factory.setPassword("password");
        factory.setVirtualHost("/");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String queueName = "coada2";
        channel.queueDeclare(queueName, true, false, false, null);
        ObjectMapper mapper = new ObjectMapper();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] value = line.split(csvSplitBy);
                Map<String, Object> message = new HashMap<>();
                message.put("timestamp", LocalDateTime.now().toString());
                message.put("device_id", "aa9d2723-2971-40ea-a301-c0061325491f");
                message.put("measurement_value",value[0]);
                byte[] json = mapper.writeValueAsBytes(message);
                System.out.println(message);
                channel.basicPublish("", queueName, null, json);
                Thread.sleep(5000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        channel.close();
        connection.close();
        }
}
