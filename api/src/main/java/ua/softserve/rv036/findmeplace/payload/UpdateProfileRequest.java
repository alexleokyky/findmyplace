package ua.softserve.rv036.findmeplace.payload;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private Long userId;
    private String nickName;
    private String email;
    private String password;
    private String newPassword;
    private String confirmPassword;
}
