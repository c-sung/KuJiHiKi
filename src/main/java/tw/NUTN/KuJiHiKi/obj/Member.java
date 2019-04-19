package tw.NUTN.KuJiHiKi.obj;

public class Member {
    public String writer_name, writer_stuNum, name, stuNum, content, secure_code, phone_number;

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

    public String getSecure_code() {
        return secure_code;
    }

    public String getStuNum() {
        return stuNum;
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

    public void setSecure_code(String secure_code) {
        this.secure_code = secure_code;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public void setWriter_name(String writer_name) {
        this.writer_name = writer_name;
    }

    public void setWriter_stuNum(String writer_stuNum) {
        this.writer_stuNum = writer_stuNum;
    }
}
