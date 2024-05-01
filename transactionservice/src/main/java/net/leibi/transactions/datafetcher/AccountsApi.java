package net.leibi.transactions.datafetcher;

import net.leibi.books.generated.types.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "accountsApi", url = "${accounts.service.url}")
public interface AccountsApi {

    @GetMapping("/{id}")
    Account getAccountBy(@PathVariable String id);

    @GetMapping("/random")
    Account getRandomAccount();
}
