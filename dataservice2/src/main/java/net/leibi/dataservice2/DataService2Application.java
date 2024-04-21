package net.leibi.dataservice2;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import net.leibi.dataservice2.data.DataService2DTO;
import net.leibi.dataservice2.service.DataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@Log4j2
public class DataService2Application {
    private final DataService dataService;

    @Value("${application.upperbound:1000}")
    private int upperBound;

    public DataService2Application(DataService dataServiceImpl) {
        this.dataService = dataServiceImpl;
    }

    public static void main(String[] args) {
        SpringApplication.run(DataService2Application.class, args);
    }

    @PostConstruct
    public void init() {
        Set<DataService2DTO> data = getRandomData();
        dataService.add(data);
    }

    private Set<DataService2DTO> getRandomData() {
        log.info("Creating {} random data", upperBound);
        return IntStream.range(1, upperBound)
                .mapToObj(i -> new DataService2DTO(String.valueOf(i), BigDecimal.valueOf(i), Integer.valueOf(i)))
                .collect(Collectors.toSet());
    }

}
