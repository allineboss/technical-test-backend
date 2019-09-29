package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.ClientModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class IClientRepositoryTest {

    @Autowired
    private IClientRepository clientRepository;

    @Test
    public void testSaveNewClient(){
        ClientModel clientModel = new ClientModel("Client for test", "client.for.test@test.com");

        clientRepository.save(clientModel);
        ClientModel savedClient = clientRepository.findOne(clientModel.getId());
        assertEquals(clientModel.getName(), savedClient.getName());
        assertEquals(clientModel.getMail(), savedClient.getMail());
    }
}
