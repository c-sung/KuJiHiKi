package tw.NUTN.KuJiHiKi.api;

public class Response_Answer {
    private String mainDescription, description, temp, humid;

    public String getDescription() {
        return description;
    }

    public String getHumid() {
        return humid;
    }

    public String getMainDescription() {
        return mainDescription;
    }

    public String getTemp() {
        return temp;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHumid(String humid) {
        this.humid = humid;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
