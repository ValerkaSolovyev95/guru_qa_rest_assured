package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    public int id;
    public String email;
    public String first_name;
    public String last_name;
    public String avatar;
}
