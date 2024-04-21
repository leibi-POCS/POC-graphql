package net.leibi.dataservice2.service;

import net.leibi.dataservice2.data.DataService2DTO;

import java.util.Set;

public sealed interface DataService permits DataServiceImpl {
    Set<DataService2DTO> getData();
    void add(DataService2DTO dataService2DTO);

    void add(Set<DataService2DTO> data);
}
