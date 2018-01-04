package br.com.oqfazer;

import br.com.oqfazer.domain.user.User;
import br.com.oqfazer.domain.user.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;

@SpringBootApplication
public class OqfazerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OqfazerApplication.class, args);
    }
}
