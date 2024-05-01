package net.leibi.accounts.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import net.leibi.accounts.service.DataService;
import net.leibi.books.generated.types.Account;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class AccountDataFetcher {

    private final DataService dataService;

    @DgsQuery
    public List<Account> accounts(@InputArgument String nameFinder) {
        if (nameFinder == null) {
            return dataService.getData();
        }

        return dataService.getData().stream().filter(s -> s.getName().contains(nameFinder)).toList();
    }

    @DgsQuery
    public Account accountById(@InputArgument String id) {
        return dataService.getAccountById(id);
    }


}