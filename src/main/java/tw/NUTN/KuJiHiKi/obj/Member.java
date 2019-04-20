package tw.NUTN.KuJiHiKi.obj;

public class Member {
    public String writer_name, writer_stuNum, name, stu_num, content, security_code, phone_number;

    public String getWriter_name() {
        return writer_name;
    }

    public String getWriter_stuNum() {
        return writer_stuNum;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getSecurity_code() {
        return security_code;
    }

    public String getStu_num() {
        return stu_num;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setSecurity_code(String security_code) {
        this.security_code = security_code;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }

    public void setWriter_name(String writer_name) {
        this.writer_name = writer_name;
    }

    public void setWriter_stuNum(String writer_stuNum) {
        this.writer_stuNum = writer_stuNum;
    }

    public boolean check_data() {
        boolean data_alright = true;

        if(this.content == null) {
            data_alright = false;
        }

        if(this.name == null) {
            data_alright = false;
        }

        if(this.phone_number == null) {
            data_alright = false;
        }

        if(this.security_code == null) {
            data_alright = false;
        }

        if(this.stu_num == null) {
            data_alright = false;
        }

        if(this.writer_name == null) {
            data_alright = false;
        }

        if(this.writer_stuNum == null) {
            data_alright = false;
        }

        return data_alright;
    }
}
