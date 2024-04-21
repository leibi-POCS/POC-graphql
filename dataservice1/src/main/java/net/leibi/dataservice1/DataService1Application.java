package net.leibi.dataservice1;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import net.leibi.dataservice1.data.DataService1DTO;
import net.leibi.dataservice1.service.DataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@Log4j2
public class DataService1Application {
    private final DataService dataService;

    @Value("${application.upperbound:1000}")
    private int upperBound;

    public DataService1Application(DataService dataServiceImpl) {
        this.dataService = dataServiceImpl;
    }

    public static void main(String[] args) {
        SpringApplication.run(DataService1Application.class, args);
    }

    @PostConstruct
    public void init() {
        Set<DataService1DTO> data = getRandomData();
        dataService.add(data);
    }

    private Set<DataService1DTO> getRandomData() {
        log.info("Creating {} random data", upperBound);
        return IntStream.range(1, upperBound)
                .mapToObj(i -> new DataService1DTO(String.valueOf(i), BigDecimal.valueOf(i)))
                .collect(Collectors.toSet());
    }

}