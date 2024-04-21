package com.alfian.test.repository.oauth;

import com.alfian.test.model.oauth.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findOneByClientId(String clientId);

}
