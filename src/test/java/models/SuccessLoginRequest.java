package models;

import lombok.Data;

@Data
public class SuccessLoginRequest {
    String email;
    String password;
}
