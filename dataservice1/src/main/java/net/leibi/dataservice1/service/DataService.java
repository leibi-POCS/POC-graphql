package net.leibi.dataservice1.service;

import net.leibi.dataservice1.data.DataService1DTO;

import java.util.Set;

public sealed interface DataService permits DataServiceImpl {
    Set<DataService1DTO> getData();
    void add(DataService1DTO dataService1DTO);

    void add(Set<DataService1DTO> data);
}
