package net.leibi.dataservice1.service;

import net.leibi.dataservice1.data.DataService1DTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public final class DataServiceImpl implements DataService {

    private final Set<DataService1DTO> dataSet = new HashSet<>();

    public Set<DataService1DTO> getData() {
        return Set.copyOf(dataSet);
    }

    public void add(DataService1DTO dataService1DTO) {
        dataSet.add(dataService1DTO);
    }

    @Override
    public void add(Set<DataService1DTO> data) {
        dataSet.addAll(data);
    }

}
