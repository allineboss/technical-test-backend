package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.dto.Movement;
import com.playtomic.tests.wallet.dto.Wallet;
import com.playtomic.tests.wallet.service.IWalletService;
import com.playtomic.tests.wallet.service.WalletServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WalletController {

    private Logger log = LoggerFactory.getLogger(WalletController.class);

    private final IWalletService walletService;

    @Autowired
    public WalletController(IWalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping(value = "/")
    public void log() {
        log.info("Logging from /");
    }

    /*
     *   - Consultar un bono por su identificador.
     */
    @GetMapping(value = "/checkBonus/{clientId}")
    public Wallet checkBonus(@PathVariable Long clientId) {
        log.info("Request bonus for clientId {} ", clientId);
        return walletService.getBalance(clientId);
    }

    /*
     *   - Descontar saldo del monedero (un cobro).
     */
    @PostMapping(value = "/makePayment")
    public Wallet makePayment(@RequestBody Movement movement) throws WalletServiceException {
        log.info("Request payment for wallet {} ", movement);
        return walletService.makePayment(movement);
    }

    /*
     *   - Devolver saldo al monedero (una devolución).
     */
    @PostMapping(value = "/returnAmount")
    public Wallet returnAmount(@RequestBody Movement movement) throws WalletServiceException {
        log.info("Returning amount {} ", movement);
        return walletService.returnAmount(movement);
    }

    /*
     *   - Recargar dinero en ese bono a través de un servicio de pago de terceros.
     */
    @PostMapping(value = "/charge")
    public Wallet charge(@RequestBody Movement movement) throws WalletServiceException {
        log.info("Charging amount {} ", movement);
        return walletService.charge(movement);
    }

}
