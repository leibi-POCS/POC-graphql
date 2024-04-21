package net.leibi.dataservice2.service;

import net.leibi.dataservice2.data.DataService2DTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public final class DataServiceImpl implements DataService {

    private final Set<DataService2DTO> dataSet = new HashSet<>();

    public Set<DataService2DTO> getData() {
        return Set.copyOf(dataSet);
    }

    public void add(DataService2DTO dataService2DTO) {
        dataSet.add(dataService2DTO);
    }

    @Override
    public void add(Set<DataService2DTO> data) {
        dataSet.addAll(data);
    }

}
