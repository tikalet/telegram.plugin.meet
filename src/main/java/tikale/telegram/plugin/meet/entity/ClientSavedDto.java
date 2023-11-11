package tikale.telegram.plugin.meet.entity;

public class ClientSavedDto {

    private String data;

    public ClientSavedDto() {
        super();
    }

    public ClientSavedDto(String data) {
        super();
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClientSavedDto [data=" + data + "]";
    }
}
