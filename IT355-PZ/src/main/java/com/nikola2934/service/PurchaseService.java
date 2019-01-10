
package com.nikola2934.service;

import com.nikola2934.model.Purchase;
import com.nikola2934.model.Song;
import java.util.List;

public interface PurchaseService {
    public List<Purchase> getAll();
    public String generateDownloadKey(Song song);
    public Purchase findPurchaseByKey(String buyerKey);
    public void makePurchase(Purchase purchase);
    public void finished(Purchase purchase);
}
