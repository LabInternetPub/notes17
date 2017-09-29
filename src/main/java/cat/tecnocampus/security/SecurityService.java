package cat.tecnocampus.security;

import cat.tecnocampus.domain.UserLab;

public interface SecurityService {

    void login(String username, String password);

    void insertUser(UserLab user);
}
