package net.leibi.accounts.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.leibi.accounts.generated.types.Account;
import net.leibi.accounts.service.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class AccountsRestController {

    private final DataService dataService;

    @GetMapping("/{id}")
    Account getAccountById(@PathVariable String id) {
        return dataService.getAccountById(id);
    }

    @GetMapping("/random")
    Account getRandomAccount() {
        log.info("Get random Account");
        return dataService.getRandomAccount();
    }
}
