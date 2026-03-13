package task.decisionengine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import task.decisionengine.domain.data.UserRepository;
import task.decisionengine.domain.data.entity.UserData;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@Configuration
public class MockDataLoader {
    @Bean
    CommandLineRunner loadData(UserRepository repository) {
        return args -> {

            ObjectMapper mapper = new ObjectMapper();
            InputStream input = new ClassPathResource("mock-user-data.json").getInputStream();
            List<UserData> dataToLoad = mapper.readValue(input, new TypeReference<>() {});

            repository.saveAll(dataToLoad);
        };
    }
}