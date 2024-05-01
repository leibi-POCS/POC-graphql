package net.leibi.accounts.datafetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import net.leibi.accounts.generated.types.Account;
import net.leibi.accounts.generated.types.AccountsByBank;
import net.leibi.accounts.service.DataService;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
@Observed
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

    @DgsQuery
    public AccountsByBank accountsByBank(@InputArgument String bic) {
        return dataService.getAccountsByBankByBic(bic);
    }

    @DgsQuery
    public List<Account> accountsByBic(@InputArgument String bic, @InputArgument Integer page, @InputArgument Integer pageSize) {
        return dataService.getAccountsByBic(bic, page, pageSize);
    }


}
