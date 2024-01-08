package models;

import lombok.Data;

@Data
public class SuccessLoginRequest {
    private String email;
    private String password;
}
