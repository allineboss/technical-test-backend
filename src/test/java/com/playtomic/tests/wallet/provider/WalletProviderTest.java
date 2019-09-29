package com.playtomic.tests.wallet.provider;

import com.playtomic.tests.wallet.dto.Movement;
import com.playtomic.tests.wallet.dto.Wallet;
import com.playtomic.tests.wallet.model.ClientModel;
import com.playtomic.tests.wallet.model.MovementModel;
import com.playtomic.tests.wallet.model.WalletModel;
import com.playtomic.tests.wallet.provider.impl.WalletProvider;
import com.playtomic.tests.wallet.repository.IMovementRepository;
import com.playtomic.tests.wallet.repository.IWalletRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class WalletProviderTest {

    @InjectMocks
    private WalletProvider walletProvider;

    @Mock
    private IWalletRepository walletRepository;

    @Mock
    private IMovementRepository movementRepository;

    @Captor
    ArgumentCaptor<MovementModel> movementModelArgumentCaptor;

    @Test
    public void getBalanceByClient(){
        long clientId = 1L;
        Long walletId = 1L;
        ClientModel clientModel = new ClientModel("Client for test", "test@test.com");
        WalletModel walletModel = new WalletModel(clientModel);
        walletModel.setId(walletId);
        Mockito.when(walletRepository.findByClientModel_Id(clientId)).thenReturn(walletModel);

        BigDecimal balance = BigDecimal.valueOf(200);
        Mockito.when(movementRepository.getBalanceByWallet(walletId)).thenReturn(balance);

        Wallet wallet = walletProvider.getBalanceByClient(clientId);
        assertEquals(walletId, wallet.getId());
        assertEquals(clientModel.getName(), wallet.getClientName());
        assertEquals(balance, wallet.getBalance());
    }

    @Test
    public void getBalanceReturnEmptyWalletIfNotFound(){
        long clientId = 1L;
        Wallet wallet = walletProvider.getBalanceByClient(clientId);
        assertNotNull(wallet);
        assertNull(wallet.getId());
        assertNull(wallet.getClientName());
        assertNull(wallet.getBalance());
    }

    @Test
    public void getById(){
        Long walletId = 1L;
        ClientModel clientModel = new ClientModel("Client for test", "test@test.com");
        WalletModel walletModel = new WalletModel(clientModel);
        walletModel.setId(walletId);
        Mockito.when(walletRepository.findById(walletId)).thenReturn(walletModel);

        BigDecimal balance = BigDecimal.valueOf(200);
        Mockito.when(movementRepository.getBalanceByWallet(walletId)).thenReturn(balance);

        Wallet wallet = walletProvider.getById(walletId);
        assertEquals(walletId, wallet.getId());
        assertEquals(clientModel.getName(), wallet.getClientName());
        assertEquals(balance, wallet.getBalance());
    }

    @Test
    public void getByIdNull(){
        Wallet wallet = walletProvider.getById(null);
        assertNotNull(wallet);
        assertNull(wallet.getId());
        assertNull(wallet.getClientName());
        assertNull(wallet.getBalance());
    }

    @Test
    public void makePayment(){
        Long walletId = 1L;
        ClientModel clientModel = new ClientModel("Client for test", "test@test.com");
        WalletModel walletModel = new WalletModel(clientModel);
        walletModel.setId(walletId);
        Mockito.when(walletRepository.findById(walletId)).thenReturn(walletModel);

        BigDecimal balance = BigDecimal.valueOf(200);
        Mockito.when(movementRepository.getBalanceByWallet(walletId)).thenReturn(balance);

        Movement movement = new Movement();
        BigDecimal amount = BigDecimal.valueOf(100.0);
        movement.setAmount(amount);

        Wallet wallet = walletProvider.makePayment(movement, walletId);
        Mockito.verify(movementRepository, Mockito.times(1)).save(movementModelArgumentCaptor.capture());
        MovementModel movementModel = movementModelArgumentCaptor.getValue();
        assertEquals(amount.negate(), movementModel.getAmount());
        assertEquals(walletModel, movementModel.getWalletModel());

        assertEquals(walletId, wallet.getId());
        assertEquals(clientModel.getName(), wallet.getClientName());
        assertEquals(balance, wallet.getBalance());
    }

    @Test
    public void makePaymentForNull(){
        Movement movement = new Movement();
        BigDecimal amount = BigDecimal.valueOf(100.0);
        movement.setAmount(amount);

        Wallet wallet = walletProvider.makePayment(movement, null);
        Mockito.verify(movementRepository, Mockito.times(0)).save(Mockito.any(MovementModel.class));
        assertNotNull(wallet);
        assertNull(wallet.getId());
        assertNull(wallet.getClientName());
        assertNull(wallet.getBalance());
    }

    @Test
    public void makePaymentForInvalidWallet(){
        Movement movement = new Movement();
        BigDecimal amount = BigDecimal.valueOf(100.0);
        movement.setAmount(amount);

        Wallet wallet = walletProvider.makePayment(movement, 1L);
        Mockito.verify(movementRepository, Mockito.times(0)).save(Mockito.any(MovementModel.class));
        assertNotNull(wallet);
        assertNull(wallet.getId());
        assertNull(wallet.getClientName());
        assertNull(wallet.getBalance());
    }

    @Test
    public void returnAmount(){
        Long walletId = 1L;
        ClientModel clientModel = new ClientModel("Client for test", "test@test.com");
        WalletModel walletModel = new WalletModel(clientModel);
        walletModel.setId(walletId);
        Mockito.when(walletRepository.findById(walletId)).thenReturn(walletModel);

        BigDecimal balance = BigDecimal.valueOf(200);
        Mockito.when(movementRepository.getBalanceByWallet(walletId)).thenReturn(balance);

        Movement movement = new Movement();
        BigDecimal amount = BigDecimal.valueOf(100.0);
        movement.setAmount(amount);

        Wallet wallet = walletProvider.returnAmount(movement, walletId);
        Mockito.verify(movementRepository, Mockito.times(1)).save(movementModelArgumentCaptor.capture());
        MovementModel movementModel = movementModelArgumentCaptor.getValue();
        assertEquals(amount, movementModel.getAmount());
        assertEquals(walletModel, movementModel.getWalletModel());

        assertEquals(walletId, wallet.getId());
        assertEquals(clientModel.getName(), wallet.getClientName());
        assertEquals(balance, wallet.getBalance());
    }

    @Test
    public void returnAmountForNull(){
        Movement movement = new Movement();
        BigDecimal amount = BigDecimal.valueOf(100.0);
        movement.setAmount(amount);

        Wallet wallet = walletProvider.returnAmount(movement, null);
        Mockito.verify(movementRepository, Mockito.times(0)).save(Mockito.any(MovementModel.class));
        assertNotNull(wallet);
        assertNull(wallet.getId());
        assertNull(wallet.getClientName());
        assertNull(wallet.getBalance());
    }

    @Test
    public void returnAmountForInvalidWallet(){
        Movement movement = new Movement();
        BigDecimal amount = BigDecimal.valueOf(100.0);
        movement.setAmount(amount);

        Wallet wallet = walletProvider.returnAmount(movement, 1L);
        Mockito.verify(movementRepository, Mockito.times(0)).save(Mockito.any(MovementModel.class));
        assertNotNull(wallet);
        assertNull(wallet.getId());
        assertNull(wallet.getClientName());
        assertNull(wallet.getBalance());

    }

}
