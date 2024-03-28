package com.fun.clientpayment.controllers;

import com.fun.clientpayment.dto.UpdateReceiptRequest;
import com.fun.clientpayment.model.Client;
import com.fun.clientpayment.model.Receipt;
import com.fun.clientpayment.model.ReceiptDetailed;
import com.fun.clientpayment.proxy.PostClientsProxy;
import com.fun.clientpayment.service.ReceiptService;
import com.fun.clientpayment.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    @Autowired
    private ReceiptService service;
    @Autowired
    private SendService sendService;

    private final PostClientsProxy postClientsProxy;

    public ReceiptController(PostClientsProxy postClientsProxy) {
        this.postClientsProxy = postClientsProxy;
    }

    @PostMapping
    public Receipt addReceipt(@RequestBody Receipt receipt) {

        Client client = postClientsProxy.getClients(UUID.fromString(receipt.getClientId())).getBody();

        if (client != null) {
            service.saveOrUpdateReceipt(receipt);
            ReceiptDetailed receiptDetailed = new ReceiptDetailed();
            receiptDetailed.setId(receipt.getId());
            receiptDetailed.setClientId(receipt.getClientId());
            receiptDetailed.setClientName(client.getClientName());
            receiptDetailed.setServiceName(receipt.getServiceName());
            receiptDetailed.setServicePrice(receipt.getServicePrice());
            receiptDetailed.setAdditionalServices(receipt.getAdditionalServices());
            sendService.sendMessage(receiptDetailed);

            return receipt;
        }
        Receipt receipt1 = new Receipt();
        receipt1.setId("You need to set actual clientId from post office, your receipt not saved - try again");
        return receipt1;
    }

    @GetMapping("/clients")
    public List<Client> getAllPostClients() {
        return postClientsProxy.getClients().getBody();
    }

    @GetMapping
    public Page<Receipt> getAllReceipts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return service.getAllReceipts(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Receipt getReceiptById(@PathVariable String id) {
        return service.getReceiptById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReceipt(@PathVariable String id) {
        service.deleteReceiptById(id);
    }

    @PostMapping("/update")
    public Receipt updateReceipt(@RequestBody Receipt receipt) {
        return service.saveOrUpdateReceipt(receipt);
    }

    @PutMapping("/{id}")
    public Receipt updateDocument(@PathVariable String id,
                                  @RequestBody UpdateReceiptRequest updateReceiptRequest) {
        return service.updateReceiptDocument(id, updateReceiptRequest.getServiceName(), updateReceiptRequest.getServicePrice());
    }
}

