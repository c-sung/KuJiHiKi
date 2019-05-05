package tw.NUTN.KuJiHiKi.obj;

public class Data_to_Update {
    private static final int DATA_NOT_EXIST = 939;
    private String stu_num, security_code;

    public String getStu_num() {
        return stu_num;
    }

    public String getSecurity_code() {
        return security_code;
    }

    public void setSecurity_code(String security_code) {
        this.security_code = security_code;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }

    public boolean isEmpty() {
        boolean empty = true;
        if(!stu_num.equals(String.valueOf(DATA_NOT_EXIST)) && !security_code.equals(String.valueOf(DATA_NOT_EXIST))) {
            empty = false;
        }

        return empty;
    }
}
