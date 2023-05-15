import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class BankApp {
    private static Account[] accountArray = new Account[100];
    private static Scanner sc = new Scanner(System.in);
    private static final String PREFIX = "0000";
    private static int seq=0;
    private static boolean isCreated=false;//계좌등록여부

    public static void main (String[] args) {
        boolean run =true;
        while(run) {
            System.out.println("=======Bank Menu=======");
            System.out.println("1.계좌개설");
            System.out.println("2.입금하기");
            System.out.println("3.출금하기");
            System.out.println("4.전체조회");
            System.out.println("5.계좌이체");
            System.out.println("6.프로그램 종료");
            System.out.println("=======================");
            System.out.print(">>>");
            int selectNo = sc.nextInt();
            switch(selectNo) {
                case 1: createAccount(); break;
                case 2: deposit(); break;
                case 3: withdraw(); break;
                case 4: accountList(); break;
                case 5: send(); break;
                case 6: run=false; break;
            }
        }
        System.out.println("프로그램 종료");
    }
    private static void createAccount() {
        //13자리, 이름, 잔고
        long randomNumber = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        String ano = Long.toString(randomNumber).substring(0, 13);
        System.out.println(ano);
        System.out.print("소유주명>");
        String owner = sc.next();//소유주 입력
        System.out.print("초기입금액>");
        int amount = sc.nextInt();//초기입금액 입력
        for(int i=0;i<accountArray.length;i++) {
            if(accountArray[i]==null) {
                accountArray[i]
                        =new Account(ano,owner,amount);
                System.out.println("계좌 등록 성공");
                isCreated=true;
                break;
            }
        }
    }//
    private static void deposit() {
        if(!isRegistered()) {
            System.out.println("먼저 계좌등록을 하세요");
            return;
        }
        //계좌번호 출력
        accountList();
        //계좌번호 선택
        System.out.println("입금할 계좌번호를 선택하세요>");
        //입금
        Account account;
        while(true) {
            String ano = sc.next();
            account = findAccount(ano);
            if(account==null)
                System.out.println("계좌번호를 확인하세요>");
            else
                break;
        }
        System.out.print("입금할 금액을 입력하세요>");
        int amount = sc.nextInt();
        account.deposit(amount);//입금 처리
        System.out.println("예금 성공");
    }
    private static void withdraw() {
        if(!isRegistered()) {
            System.out.println("먼저 계좌등록을 하세요");
            return;//메소드 실행 종료.
        }
        //계좌번호 출력
        accountList();
        //계좌번호 선택
        System.out.println("출금할 계좌번호를 선택하세요>");
        //출금(잔액<출금액)
        Account account;
        while(true) {
            String ano = sc.next();//출금 계좌번호 입력
            account = findAccount(ano);//입력받은 정보로 계좌조회
            if(account==null)
                System.out.println("계좌번호를 확인하세요");
            else
                break;//반복문 빠져나가기
        }
        //출금처리
        System.out.print("출금할 금액을 입력하세요>");
        int amount = sc.nextInt();
        int result;
        try {
            result = account.withdraw(amount);//잔액 >출금액보다 큰 경우
            System.out.println("출금액:"+result);
        }catch(Exception e) {//잔액 <출금액보다 작은경우
            System.out.println(e.getMessage());
        }
    }
    private static void accountList() {
        if(!isRegistered()) {// if(!isCreated)
            System.out.println("먼저 계좌등록을 하세요");
            return;//return;-더이상 진행을 하지 않고 메소드 호출한곳으로 되돌아감.
        }
        for(int i=0;i<accountArray.length;i++) {
            if(accountArray[i]!=null) {
                System.out.println(accountArray[i].getAno()+accountArray[i].getOwner()+accountArray[i].getBalance());
            }
        }
    }//

    //계좌이체 시작
    private static void send() {

    }

    private static boolean isRegistered() {
        return isCreated;//최종상태값을 리턴
        //초기값은 false이고, 계좌를 등록하면 true
    }
    //조회
    private static Account findAccount(String ano) {
        Account account=null;
        for(int i =0;i<accountArray.length;i++) {
            if(accountArray[i]!=null)
                if(accountArray[i].getAno().equals(ano)) {
                    //문자열의 내용비교: 문자열1.equals(문자열2)
                    account = accountArray[i];
                }
        }
        return account;
    }


}