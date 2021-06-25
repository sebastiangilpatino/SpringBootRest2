package mvc;

import java.time.LocalDate;

import mvc.Model.Account;
import mvc.Model.Bank;
import mvc.Model.Transacctions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class BankController {

    Bank banks = new Bank();

    public BankController() {
        banks.addAccount(new Account(1111, "Jon"));
    }

    @PostMapping("/bank")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        banks.addAccount(new Account(account.getAccountNumber(), account.getAccountHolder()));
        return new ResponseEntity<Bank>(banks, HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody Transacctions transacctions) {

        for (int i = 0; i < banks.getAccounts().size(); i++) {
            if (banks.getAccounts().get(i).getAccountNumber() == transacctions.getAccountNumber()) {
                banks.getAccounts().get(i).setBalance(
                        banks.getAccounts().get(i).getBalance() + transacctions.getAmount());
                banks.getAccounts().get(i).addTransaction(transacctions);
                return new ResponseEntity<Bank>(banks, HttpStatus.OK);
            }
        }

        return new ResponseEntity<CustomErrorType>(new CustomErrorType("AccountNumb= "
                + transacctions.getAccountNumber() + " is not available"), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody Transacctions transacctions) {
        for (int i = 0; i < banks.getAccounts().size(); i++) {
            if (banks.getAccounts().get(i).getAccountNumber() == transacctions.getAccountNumber()) {
                banks.getAccounts().get(i).setBalance(
                        banks.getAccounts().get(i).getBalance() - transacctions.getAmount());
                banks.getAccounts().get(i).addTransaction(transacctions);
                return new ResponseEntity<Bank>(banks, HttpStatus.OK);
            }
        }

        return new ResponseEntity<CustomErrorType>(new CustomErrorType("AccountNumb= "
                + transacctions.getAccountNumber() + " is not available"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<?> getAccount(@PathVariable int accountNumber) {
        for (int i = 0; i < banks.getAccounts().size(); i++) {
            if (banks.getAccounts().get(i).getAccountNumber() == accountNumber) {
                return new ResponseEntity<Account>(banks.getAccounts().get(i), HttpStatus.OK);
            }
        }

        return new ResponseEntity<CustomErrorType>(new CustomErrorType("AccountNumb= "
                + accountNumber + " is not available"), HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/account/{accountNumber}")
    public ResponseEntity<?> removeAccount(@PathVariable int accountNumber) {
        for (int i = 0; i < banks.getAccounts().size(); i++) {
            if(banks.getAccounts().get(i).getAccountNumber()==accountNumber){
                banks.getAccounts().remove(i);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

        return new ResponseEntity<CustomErrorType>(new CustomErrorType("AccountNumb= "
                + accountNumber + " is not available"), HttpStatus.NOT_FOUND);
    }
}
