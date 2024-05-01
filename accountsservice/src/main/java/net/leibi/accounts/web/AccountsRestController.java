package net.leibi.accounts.web;

import lombok.RequiredArgsConstructor;
import net.leibi.accounts.service.DataService;
import net.leibi.books.generated.types.Account;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountsRestController {

    private final DataService dataService;

    @GetMapping("/{id}")
    Account getAccountById(@PathVariable String id) {
        return dataService.getAccountById(id);
    }

    @GetMapping("/random")
    Account getRandomAccount() {
        return dataService.getRandomAccount();
    }
}
