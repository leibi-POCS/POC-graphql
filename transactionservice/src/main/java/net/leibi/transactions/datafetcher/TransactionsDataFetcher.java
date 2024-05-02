package net.leibi.transactions.datafetcher;

import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsEntityFetcher;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import io.micrometer.observation.annotation.Observed;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.leibi.transactions.generated.types.Account;
import net.leibi.transactions.generated.types.Transaction;
import net.leibi.transactions.service.TransactionDataService;

@DgsComponent
@RequiredArgsConstructor
@Log4j2
@Observed
public class TransactionsDataFetcher {

  private final TransactionDataService transactionDataService;

  @DgsQuery
  public List<Transaction> transactions(@InputArgument Double minAmount) {
    List<Transaction> transactions;
    if (minAmount == null) {
      transactions = transactionDataService.getData();
    } else {
      transactions = transactionDataService.getTransactionsByMinAmount(minAmount);
    }
    log.info("returning {} transactions with minAmount {}", transactions.size(), minAmount);
    return transactions;
  }

  @DgsQuery
  public Transaction transactionById(@InputArgument String id) {
    return transactionDataService.getTransactionById(id);
  }

  @DgsEntityFetcher(name = "Account")
  public Account account(Map<String, Object> values) {
    return new Account((String) values.get("id"), null);
  }

  @DgsData(parentType = "Account", field = "transactions")
  public List<Transaction> transactionFetcher(DgsDataFetchingEnvironment dataFetchingEnvironment) {
    Account account = dataFetchingEnvironment.getSource();

    List<Transaction> transactionsByAccountId =
        transactionDataService.getTransactionsByAccountId(account.getId());
    log.debug(
        "returning {} transactions for account: {}",
        transactionsByAccountId.size(),
        account.getId());
    return transactionsByAccountId;
  }
}
