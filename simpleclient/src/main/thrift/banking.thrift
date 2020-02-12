namespace java com.oyo.banking

struct TManager {
  1: required string accountNumber;
  2: required string branchCode;
  3: required string username;
}

struct TCustomer{
    1: string username;
    2: string password;
    3: string branchCode;
}

struct TTransaction{
    1: string debitAccountNumber;
    2: string creditAccountNumber;
    3: double amount;
}

service TAuthService{
    string login(1: string accountNumber, 2: string password);
    string logout(1: string accountNumber);
}
service TAdminService {
    string addManager(1: string username, 2: string password, 3: string branchCode);
    string removeManager(1: string accountNumber);
    list<TManager> getAllManagers();
    string addBranch(1: string branchName, 2: string branchCity);
    string getBranchCode(1: string branchName, 2: string branchCity);
    string removeBranch(1: string branchCode);
}

service TManagerService{
    string addCustomer(1: string customerAccountNumber, 2: string managerAccountNumber);
    string removeCustomer(1: string accountNumber);
    string deposit(1: string accountNumber, 2: double amount);
}

service TCustomerService{
    string addAccountRequest(1: TCustomer tCustomer);
    string withdraw(1: string accountNumber, 2: double amount);
    string sendMoney(1: string debitAccountNumber, 2: string creditAccountNumber, 3: double amount);
    list<TTransaction> getAllTransactions(1: string accountNumber);
}

