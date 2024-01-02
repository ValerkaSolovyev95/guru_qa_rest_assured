package models;

import lombok.Data;

@Data
public class CreateWorkerResponse {
    String name;
    String id;
    String job;
    String createdAt;
}
