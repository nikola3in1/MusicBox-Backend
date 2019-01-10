package com.nikola2934.service;

import com.nikola2934.model.Purchase;
import com.nikola2934.model.Song;
import com.nikola2934.repository.PurchaseRepository;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public List<Purchase> getAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public String generateDownloadKey(Song song) {
        Random random = new Random();
        StringBuilder buildLink = new StringBuilder();
        buildLink.append(song.getName()).append(song.getUser().getUsername()).append(System.currentTimeMillis());
        System.out.println("b: "+buildLink.toString());
        Integer[] offsetIndexes = new Integer[buildLink.length()];
        
        //Salting
        for (int i = 0; i < offsetIndexes.length; i++) {
            offsetIndexes[i]= random.nextInt(offsetIndexes.length);
        }
        
        for (int i = 0; i < offsetIndexes.length; i++) {
            Integer rng = random.nextInt(2);
            Character ch;
            if(rng == 0){
                ch = (char)(buildLink.charAt(i)+1);
            }else{
                ch = (char) (buildLink.charAt(i)-1);
            }
            buildLink.setCharAt(i, ch);
        }
        String link = Base64.getEncoder().encodeToString((buildLink.toString()).getBytes());
//        System.out.println("b: "+buildLink.toString());
//        System.out.println("encoded: " + link);
//
//        byte[] bytes = Base64.getDecoder().decode(link);
//        try {
//            String linkDecoded = new String(bytes,"UTF-8");
//            System.out.println("decoded: "+linkDecoded);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(PurchaseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return link;
    }

    @Override
    public Purchase findPurchaseByKey(String buyerKey) {
        return purchaseRepository.findPurchaseByKey(buyerKey);
    }

    @Override
    public void makePurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    @Override
    public void finished(Purchase purchase) {
        purchaseRepository.save(purchase);
    }
    
    

}
