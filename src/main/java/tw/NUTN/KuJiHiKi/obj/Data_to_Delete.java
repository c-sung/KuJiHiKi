package tw.NUTN.KuJiHiKi.obj;

public class Data_to_Delete {
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
        if(stu_num != null && security_code != null) {
            empty = false;
        }

        return empty;
    }
}
