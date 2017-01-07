class Account{


    def number,hello


    def number(number){
        this.number = number;
    }

    def hello(hello){
        this.hello = hello;
    }


    static create(closrue){
        def account = new Account();
        account.with closrue
        account
    }


    public static void main(String[] args) {
        def account = Account.create {
            number "!!!!"
            hello "????"
        }
        println(account.number)
    }
}