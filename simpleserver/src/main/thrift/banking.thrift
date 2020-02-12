namespace java com.oyo.banking

struct Transaction {
  1: required string source;
  2: required string destination;
  3: required double amount;
}

service BankingService {
    string loginUser(1: string username, 2: string password);
    bool logoutUser(1: string username);
    string createAccountRequest(1: string username, 2: string password);
    string sendMoney(1: string source, 2: string destination, 3: double amount);
    list<Transaction> getAllTransactions(1: string source);
}
